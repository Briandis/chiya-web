package chiya.web.server.rpc;

import java.nio.charset.StandardCharsets;
import java.util.List;

import chiya.server.ChiyaRpcPack;
import chiya.server.RpcPackParser;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * RPC netty自定义编码
 * 
 * @author chiya
 *
 */
public class ChiyaRpcPackNettyDecode extends ByteToMessageDecoder {
	/** 头部数据包大小字段长度 */
	private static final int HEADER_LENGTH_FIELD_SIZE = 4;
	/** 数据体大小字段长度 */
	private static final int BODY_LENGTH_FIELD_SIZE = 4;
	/** 魔法值 */
	private static final String MAGIC_WORD = new String(RpcPackParser.checkFlag);

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// 标记当前读指针位置
		in.markReaderIndex();
		// 检查是否有足够的数据读取标志位
		if (in.readableBytes() < RpcPackParser.checkFlag.length) {
			return; // 数据不足，等待下一次读取
		}

		// 读取标志位
		ByteBuf magicWordBuf = in.readBytes(RpcPackParser.checkFlag.length);
		String magicWord = magicWordBuf.toString(StandardCharsets.UTF_8);
		magicWordBuf.release();

		// 检查标志位是否正确
		if (!MAGIC_WORD.equals(magicWord)) { throw new IllegalStateException("Invalid magic word: " + magicWord); }

		// 检查是否有足够的数据读取头部数据包大小和数据体大小
		if (in.readableBytes() < HEADER_LENGTH_FIELD_SIZE + BODY_LENGTH_FIELD_SIZE) {
			in.resetReaderIndex(); // 重置读指针，等待下一次读取
			return;
		}

		// 读取头部数据包大小
		int headerLength = in.readInt();

		// 读取数据体大小
		int bodyLength = in.readInt();
		// 检查是否有足够的数据读取头部数据和数据体
		if (in.readableBytes() < headerLength + bodyLength) {
			in.resetReaderIndex(); // 重置读指针，等待下一次读取
			return;
		}
		ChiyaRpcPack chiyaRpcPack = new ChiyaRpcPack();

		// 读取头部数据
		byte[] headerBytes = new byte[headerLength];
		in.readBytes(headerBytes);

		// 读取数据体
		byte[] bodyBytes = new byte[bodyLength];
		in.readBytes(bodyBytes);

		chiyaRpcPack.setChiyaRpcHead(RpcPackParser.parserHead(headerBytes));
		chiyaRpcPack.setData(bodyBytes);

		out.add(chiyaRpcPack); // 将解析出的报文传递给下一个 ChannelHandler
	}

}
