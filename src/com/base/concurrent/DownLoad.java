package com.base.concurrent;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多线程多点续传
 * @author Administrator
 *
 */
public class DownLoad {
	//文件目录、文件名
	public String fileDir = "E:/MyDownLoad";
	public String fileName;
	//超时重连时间
	public long reconnectTime = 5;
	//线程数
	private int poolSize = 5;
	//每个线程的缓冲区大小
	public int bufferSize = 1024;
	//url地址
	private String urlLocation = null;
	
	public DownLoad(){}
	public DownLoad(String url){
		this.urlLocation = url;
	}
	public void downLoad(){
		if(this.urlLocation == null || "".equals(this.urlLocation))return;
		downLoad(this.urlLocation);
	}
	public void downLoad(String urlLocation){
		File file = null;
		File tempFile = null;
		CountDownLatch latch;
		URL url = null;
		ExecutorService pool = Executors.newCachedThreadPool();
		long contentLength = 0;
		long threadLength = 0;
		try {
			//如果未指定名称，则从url中获得下载的文件格式与名字
			if(fileName == null || "".equals(fileName)){
				this.fileName = urlLocation.substring(urlLocation.lastIndexOf("/") + 1,
						urlLocation.lastIndexOf("?") > 0 ? urlLocation.lastIndexOf("?")
								: urlLocation.length());
				if ("".equalsIgnoreCase(this.fileName)) {
					this.fileName = UUID.randomUUID().toString();
				}
			}
			new File(fileDir).mkdirs();
			file = new File(fileDir + File.separator + fileName);
			tempFile = new File(fileDir + File.separator + fileName + "_temp");
			
			url = new URL(urlLocation);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			setHeader(conn);
			//得到content的长度
			contentLength = conn.getContentLength();
			
			System.out.println("total length=" + contentLength);
			
			//把context分为poolSize段，计算每段的长度。
//			threadLength = contentLength / this.poolSize;
			BigDecimal b1 = new BigDecimal(Double.toString(contentLength));
			BigDecimal b2 = new BigDecimal(Double.toString(this.poolSize));
			threadLength = b1.divide(b2, 0, BigDecimal.ROUND_HALF_UP).longValue();
			
			if(file.exists() && tempFile.exists()){
				//如果文件已存在，根据临时文件中记载的线程数量，继续上次的任务
				latch = new CountDownLatch((int)tempFile.length()/28);
				for(int i=0;i<tempFile.length()/28;i++){
					pool.submit(new DownLoadTask(file, tempFile, url, i+1,latch,reconnectTime,bufferSize));
				}
			}else{
				//如果下载的目标文件不存在，则创建新文件
				latch = new CountDownLatch(poolSize);
				file.createNewFile();
				tempFile.createNewFile();
				DataOutputStream os = new DataOutputStream(new FileOutputStream(tempFile));
				for(int i=0;i<this.poolSize;i++){
					os.writeInt(i+1);
					os.writeLong(i*threadLength);
					if(i==this.poolSize-1){//最后一个线程的结束位置应为文件末端
						os.writeLong(contentLength);
					}else{
						os.writeLong((i+1)*threadLength);
					}
					os.writeLong(i*threadLength);
					pool.submit(new DownLoadTask(file, tempFile, url, i+1,latch,reconnectTime,bufferSize));
				}
				os.close();
			}
			//等待下载任务完成
			latch.await();
			//删除临时文件
			tempFile.delete();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally{
			pool.shutdown(); 
		}
	}
	
	private void setHeader(URLConnection conn) {
		conn.setRequestProperty("User-Agent",
						"Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.3) Gecko/2008092510 Ubuntu/8.04 (hardy) Firefox/3.0.3");
		conn.setRequestProperty("Accept-Language", "en-us,en;q=0.7,zh-cn;q=0.3");
		conn.setRequestProperty("Accept-Encoding", "aa");
		conn.setRequestProperty("Accept-Charset","ISO-8859-1,utf-8;q=0.7,*;q=0.7");
		conn.setRequestProperty("Keep-Alive", "300");
		conn.setRequestProperty("Connection", "keep-alive");
		conn.setRequestProperty("If-Modified-Since", "Fri, 02 Jan 2009 17:00:05 GMT");
		conn.setRequestProperty("If-None-Match", "\"1261d8-4290-df64d224\"");
		conn.setRequestProperty("Cache-Control", "max-age=0");
		conn.setRequestProperty("Referer","http://www.skycn.com/soft/14857.html");
	}

	
	public String getFileDir() {
		return fileDir;
	}
	public void setFileDir(String fileDir) {
		this.fileDir = fileDir;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public long getReconnectTime() {
		return reconnectTime;
	}
	public void setReconnectTime(long reconnectTime) {
		this.reconnectTime = reconnectTime;
	}
	public int getPoolSize() {
		return poolSize;
	}
	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}
	public int getBufferSize() {
		return bufferSize;
	}
	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DownLoad dl = new DownLoad();
		dl.setFileDir("E:/MyDownLoad/music/");
		dl.setFileName("大笑江湖.mp3");
		dl.setPoolSize(20);
		long beginTime = System.currentTimeMillis();
		dl.downLoad("http://mh.163k.com/UploadFile/video/2010/12-13/201012131213448942190.mp3");
		long endTime = System.currentTimeMillis();
		BigDecimal b1 = new BigDecimal(endTime - beginTime);
		BigDecimal b2 = new BigDecimal(1000);
		double cost = b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
		System.out.println("Time cost:" + cost + "s");
	}

}
