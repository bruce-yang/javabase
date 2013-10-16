package com.base.concurrent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 多线程多点续传
 * @author Administrator
 *
 */
public class DownLoadTask implements Callable<String>{
	//超时重连时间
	private long reconnectTime = 5;
	//缓冲区大小
	private int bufferSize = 1024;
	
	private CountDownLatch latch;
	private RandomAccessFile file = null;
	private RandomAccessFile tempFile = null;
	private URL url = null;
	private int id;
	private long startPosition;
	private long endPosition;
	private long currentPosition ;
	
	public DownLoadTask(File file,File tempFile,URL url,int id,CountDownLatch latch,long reconnectTime,int bufferSize){
		try {
			this.file = new RandomAccessFile(file, "rw");
			this.tempFile = new RandomAccessFile(tempFile, "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.url = url;
		this.id = id;
		this.latch = latch;
	}
	
	public String call(){
		
		try {
			tempFile.seek((id-1)*28);
			tempFile.readInt();
			this.startPosition = tempFile.readLong();
			this.endPosition = tempFile.readLong();
			this.currentPosition = tempFile.readLong();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Thread " + id + " begin!");
		
		HttpURLConnection conn = null;
		InputStream inputStream = null;

		while(true){
			try {
				tempFile.seek(id*28 - 8);
				// 打开URLConnection
				conn = (HttpURLConnection) this.url.openConnection();
				setHeader(conn);
				// 设置连接超时时间为10000ms
				conn.setConnectTimeout(10000);
				// 设置读取数据超时时间为10000ms
				conn.setReadTimeout(10000);

				if (currentPosition < endPosition) {
					// 设置下载数据的起止区间
					conn.setRequestProperty("Range", "bytes=" + currentPosition + "-" + endPosition);
					
					System.out.println("Thread " + id + " startPosition=" + startPosition 
							+ ",endPosition=" + endPosition + ",currentPosition=" + currentPosition);

					file.seek(currentPosition);

					// 判断http status是否为HTTP/1.1 206 Partial Content或者200 OK
					// 如果不是以上两种状态，把status改为STATUS_HTTPSTATUS_ERROR
					if (conn.getResponseCode() != HttpURLConnection.HTTP_OK
							&& conn.getResponseCode() != HttpURLConnection.HTTP_PARTIAL) {
						System.out.println("Thread " + id + ": code = " + conn.getResponseCode() + ", status = " + conn.getResponseMessage());
						file.close();
						conn.disconnect();
						System.out.println("Thread " + id + " finished.");
						break;
					}

					inputStream = conn.getInputStream();
					int len = 0;
					byte[] b = new byte[bufferSize];
					while ((len = inputStream.read(b)) != -1) {
						file.write(b, 0, len);

						currentPosition += len;
						// set tempFile now position
						tempFile.seek(id*28 - 8);
						tempFile.writeLong(currentPosition);
					}

					file.close();
					tempFile.close();
					inputStream.close();
					conn.disconnect();
				}

				System.out.println("Thread " + id + " finished.");
				break;
			} catch (IOException e) {
				try {
					TimeUnit.SECONDS.sleep(getReconnectTime());
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				continue;
			}
		}
		latch.countDown();
		return "finish";
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

	public long getReconnectTime() {
		return reconnectTime;
	}

	public void setReconnectTime(long reconnectTime) {
		this.reconnectTime = reconnectTime;
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	
}
