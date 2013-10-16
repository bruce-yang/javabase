package com.base.utils.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * 此类用于对象的读写
 * 
 * @author Touch
 */
public class ObjectFile {
	// 把一个对象写入文件，isAppend为true表示追加方式写，false表示重新写
	public static void write(String filePath, Object o, boolean isAppend) {
		if (o == null)
			return;
		try {
			File f = new File(filePath);
			MyObjectOutputStream out = MyObjectOutputStream.newInstance(f,
					new FileOutputStream(f, isAppend));
			try {
				out.writeObject(o);
			} finally {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 把一个对象数组写入文件，isAppend为true表示追加方式写，false表示重新写
	public static void write(String filePath, Object[] objects, boolean isAppend) {
		if (objects == null)
			return;
		try {
			File f = new File(filePath);
			MyObjectOutputStream out = MyObjectOutputStream.newInstance(f,
					new FileOutputStream(f, isAppend));
			try {
				for (Object o : objects)
					out.writeObject(o);
			} finally {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 读取对象，返回一个对象
	public static Object read(String filePath) {
		Object o = null;
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					filePath));
			try {
				o = in.readObject();
			} finally {
				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}

	// 读取对象，返回一个对象数组，count表示要读的对象的个数
	public static Object[] read(String filePath, int count) {
		Object[] objects = new Object[count];
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(
					filePath));
			try {
				for (int i = 0; i < count; i++) {
					objects[i] = in.readObject();
				}
			} finally {
				in.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objects;
	}
}
