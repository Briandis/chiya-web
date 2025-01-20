package chiya.web.server.rpc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;

import chiya.core.base.collection.ObjectPool;
import chiya.core.base.string.StringUtil;
import chiya.core.base.thread.ThreadUtil;
import chiya.server.ChiyaRpcHead;
import chiya.server.ChiyaRpcPack;
import chiya.server.RpcPackParser;

/**
 * RPC服务
 * 
 * @author chiya
 *
 */
public class ChiyaRpcServer {
	/** 连接 */
	private ServerSocket socket;
	/** 端口 */
	private int port = 32123;
	/** 是否运行 */
	private volatile boolean isRun = false;
	/** 引用的方法 */
	private ConcurrentHashMap<String, RpcMethod> refMethod = new ConcurrentHashMap<>();
	/** 阻塞队列 */
	private ArrayBlockingQueue<ChiyaRpcPack> queue = new ArrayBlockingQueue<>(8192);
	/** 线程池配置 */
	private ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(32, 128, 2, TimeUnit.SECONDS, new ArrayBlockingQueue<>(65536));
	/** 响应 */
	private ConcurrentHashMap<String, ChiyaRpcPack> resultMap = new ConcurrentHashMap<>();

	/** 线程池配置 */
	private ThreadPoolExecutor queuePool = new ThreadPoolExecutor(4, 4, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(16));

	private ObjectPool<String, Socket> clientSocket = new ObjectPool<>();

	/** 无参构造方法 */
	public ChiyaRpcServer() {}

	/**
	 * RPC服务
	 * 
	 * @param port 端口
	 */
	public ChiyaRpcServer(int port) {
		this.port = port;
	}

	/**
	 * 获取端口
	 * 
	 * @return 端口
	 */
	public int getPort() {
		return port;
	}

	/** 队列消费任务 */
	public void sumbitTask() {
		while (isRun) {
			try {
				ChiyaRpcPack rpcPack = queue.take();
				// 消费队列不足则增加线程处理
				if (queue.size() > 8000) { queuePool.submit(() -> sumbitTask()); }
				String url = rpcPack.getChiyaRpcHead().getUrl();
				RpcMethod method = refMethod.get(url);
				if (method != null) {
					// 反射构建方法参数
					poolExecutor.submit(() -> {
						Object result = method.execute(url, rpcPack);
						if (rpcPack.getChiyaRpcHead().getResult()) {
							// 处理回调
							ChiyaRpcPack resultPack = new ChiyaRpcPack();
							resultPack
								.chainChiyaRpcHead(
									new ChiyaRpcHead()
										.chainPort(port)
										.chainType("result")
										.chainKey(rpcPack.getChiyaRpcHead().getKey())
								)
								.chainData(JSONObject.toJSONString(result).getBytes());
							// 响应数据
							send(resultPack.toByte(), rpcPack.getIp(), rpcPack.getChiyaRpcHead().getPort());

						}

					});
				}
			} catch (InterruptedException e) {}
		}
	}

	/** 开启线程 */
	public void start() {
		isRun = true;
		queuePool.submit(() -> sumbitTask());

		ThreadUtil.createAndStart(() -> {
			try {
				socket = new ServerSocket(port);
				while (isRun) {
					Socket clinet = socket.accept();
					String ip = clinet.getInetAddress().getHostAddress();
					// 如果有连接进来，则创建反向会话

					ThreadUtil.createAndStart(() -> {
						// 解析后的结果将加入RPC任务队列
						RpcPackParser.parser(clinet, rpcPack -> {
							rpcPack.setIp(ip);
							if (StringUtil.eqString(rpcPack.getChiyaRpcHead().getType(), "request")) {
								queue.add(rpcPack);
							} else {
								resultMap.put(rpcPack.getChiyaRpcHead().getKey(), rpcPack);
								// 接受数据后需要唤醒在等待的线程
								synchronized (this) {
									notifyAll();
								}
							}
						});
						// 解析中会关闭连接
					});
				}
				socket.close();

			} catch (IOException e) {}
		});

	}

	/**
	 * 注册实例
	 * 
	 * @param classer 类信息
	 */
	public void register(Class<?> classer) {
		RpcMethod rpcMethod = new RpcMethod(classer);
		rpcMethod.register(refMethod);
	}

	/**
	 * 注册实例
	 * 
	 * @param instance 实例对象
	 */
	public void register(Object instance) {
		RpcMethod rpcMethod = new RpcMethod(instance);
		rpcMethod.register(refMethod);
	}

	/**
	 * RPC通信
	 * 
	 * @param chiyaRpcPack rpc数据包
	 * @param ip           IP
	 * @param port         端口
	 * @return 响应内容
	 */
	public Object rpcPack(ChiyaRpcPack chiyaRpcPack, String ip, int port) {
		send(chiyaRpcPack.toByte(), ip, port);
		if (chiyaRpcPack.getChiyaRpcHead().getResult()) {
			// 如果需要返回，则需要阻塞当前业务线程
			try {
				synchronized (this) {
					while (!resultMap.containsKey(chiyaRpcPack.getChiyaRpcHead().getKey())) {
						this.wait();
					}
				}
			} catch (InterruptedException e) {}
			return resultMap.get(chiyaRpcPack.getChiyaRpcHead().getKey());
		}
		return null;
	}

	/**
	 * RPC请求
	 * 
	 * @param url  目标URL
	 * @param data 请求参数
	 * @param ip   目标IP
	 * @param port 目标端口
	 * @return 请求响应的RPC报文
	 */
	public Object rpcResult(String url, String data, String ip, int port) {
		return rpcPack(
			new ChiyaRpcPack()
				.chainData(data != null ? data.getBytes() : null)
				.chainChiyaRpcHead(
					new ChiyaRpcHead()
						.chainUrl(url)
						.chainResult(true)
						.chainPort(this.port)
				),
			ip,
			port
		);
	}

	/**
	 * RPC请求
	 * 
	 * @param url  目标URL
	 * @param data 请求参数
	 * @param ip   目标IP
	 * @param port 目标端口
	 * @return 请求响应的RPC报文
	 */
	public Object rpcNotResult(String url, String data, String ip, int port) {
		return rpcPack(
			new ChiyaRpcPack()
				.chainData(data != null ? data.getBytes() : null)
				.chainChiyaRpcHead(
					new ChiyaRpcHead()
						.chainUrl(url)
						.chainResult(false)
						.chainPort(this.port)
				),
			ip,
			port
		);
	}

	/**
	 * 发送
	 * 
	 * @param data 数据
	 * @param ip   IP
	 * @param port 端口
	 */
	private void send(byte[] data, String ip, int port) {
		clientSocket.tryUse(
			ip + ":" + port,
			client -> {
				try {

					client.getOutputStream().write(data);
					client.getOutputStream().flush();
					return true;
				} catch (IOException e) {
					return false;
				}
			},
			key -> {
				try {
					return new Socket(ip, port);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		);

	}

}
