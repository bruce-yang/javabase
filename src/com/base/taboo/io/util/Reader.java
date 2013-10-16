package com.base.taboo.io.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;

public class Reader {
	public URI pwd() {
		File here = new File("");
		return here.getAbsoluteFile().toURI();
	}

	public String getResourcePath(String file) throws FileNotFoundException {
		String rtn = new File("").getAbsolutePath()
				+ System.getProperty("file.separator") + file;
		File check = new File(rtn);
		if (!check.exists()) {
			throw new FileNotFoundException(file);
		}
		return rtn;
	}

	public File getResource(String file) throws FileNotFoundException {
		/*String rtn1 = new File("").getAbsolutePath()
				+ System.getProperty("file.separator") + file;
		File check1= new File(rtn1);*///不依赖web容器时使用此方法取得路径，web下使用下面的路径方式
		File check2= new File(getPath(file));
		if (!check2.exists()) {
			throw new FileNotFoundException(file);
		}
		return check2;
	}
	public String getPath(String filename){
		String   path   =   getClass().getProtectionDomain().getCodeSource().getLocation().getPath();  
        String realpath="";
        if(path.indexOf("WEB-INF")>0){  
                path   =   path.substring(0,path.indexOf("/WEB-INF/classes"));  
                realpath =path+"/"+filename;
        }
        return realpath;
	}
}
