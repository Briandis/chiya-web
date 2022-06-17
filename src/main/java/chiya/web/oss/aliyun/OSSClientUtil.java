package chiya.web.oss.aliyun;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.OSSObject;

/**
 * 阿里云OSS连接工具<br>
 * 使用前需要先传入必要的字段
 * 
 * @author brain
 *
 */
public class OSSClientUtil {
	/** OSS地址 */
	public static String endpoint;
	/** accessKeyId */
	public static String accessKeyId;
	/** accessKeySecret */
	public static String accessKeySecret;
	/** 命名空间 */
	public static String bucketName;

	/**
	 * 删除OSS文件
	 * 
	 * @param objectName
	 */
	public static void deleteFile(String objectName) {
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
		ossClient.deleteObject(bucketName, objectName);
		ossClient.shutdown();
	}

	/**
	 * 下载文件，并把读取成byte数组
	 * 
	 * @param objectName 对象路径
	 * @return byte[]
	 */
	public static byte[] downloadFile(String objectName) {
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
		OSSObject ossObject = ossClient.getObject(bucketName, objectName);

		// 调用ossObject.getObjectContent获取文件输入流，可读取此输入流获取其内容。
		InputStream content = ossObject.getObjectContent();
		byte[] data = null;
		if (content != null) {
			try (BufferedInputStream bufferedInputStream = new BufferedInputStream(content);) {
				data = new byte[bufferedInputStream.available()];
			} catch (IOException e) {} finally {
				try {
					// 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
					content.close();
				} catch (IOException e) {}
			}
		}
		// 关闭OSSClient。
		ossClient.shutdown();
		return data;
	}

	/**
	 * 上传文件至OSS
	 * 
	 * @param objectName 对象名称，不能/\\开头，1-1023个字节
	 * @param bytes      字节数组
	 */
	public static void uploadFile(String objectName, byte bytes[]) {
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
		ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));
		ossClient.shutdown();
	}

	/**
	 * 上传文件至OSS
	 * 
	 * @param objectName  对象名称，不能/\\开头，1-1023个字节
	 * @param inputStream IO对象
	 */
	public static void uploadFile(String objectName, InputStream inputStream) {
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
		ossClient.putObject(bucketName, objectName, inputStream);
		ossClient.shutdown();
	}

	/**
	 * 创建bucketName空间
	 * 
	 * @param bucketName 空间名字
	 */
	public static void createBucket(String bucketName) {
		// 创建OSSClient实例。
		OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
		// 创建存储空间。
		ossClient.createBucket(bucketName);
		// 关闭OSSClient。
		ossClient.shutdown();
	}

}
