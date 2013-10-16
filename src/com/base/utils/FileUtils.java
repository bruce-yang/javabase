package com.base.utils;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.util.Calendar;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tools.ant.FileScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Copy;
import org.apache.tools.ant.taskdefs.Delete;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.taskdefs.Move;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.selectors.DateSelector;
import org.apache.tools.ant.types.selectors.DateSelector.TimeComparisons;
/**
 * 
 * @author lucene_yang
 * @date 2010-5-19下午02:47:00
 *
 */
public class FileUtils{

	static Logger log = Logger.getLogger(FileUtils.class);

	public static void moveFile(String srcFileName, String targetFileName)
			throws IOException {
		copyFile(srcFileName, targetFileName);
		new File(srcFileName).delete();
	}

	private static void copyFile(String srcFileName, String targetFileName)
			throws IOException {

		File srcFile = new File(srcFileName);
		File targetFile = new File(targetFileName);

		java.io.FileInputStream fileread = new java.io.FileInputStream(srcFile); // 读入原文件
		java.io.FileOutputStream filewout = new java.io.FileOutputStream(
				targetFile);
		byte[] bufferbyte = new byte[2048];
		int buffer = 0;
		while ((buffer = fileread.read(bufferbyte)) != -1) {
			filewout.write(bufferbyte, 0, buffer);
		}
		filewout.flush();
		fileread.close();
		filewout.close();
	}

	


	/**
	 * 取得图片的格式
	 * 
	 * @param s
	 * @return 字符串 如jpg
	 */
	public static String getFileExt(String s) {
		String s1 = new String();
		int i = 0;
		int j = 0;
		if (s == null)
			return null;
		i = s.lastIndexOf(46) + 1;
		j = s.length();
		s1 = s.substring(i, j);
		if (s.lastIndexOf(46) > 0)
			return s1;
		else
			return "";
	}

	public static String getFileMain(String name) {
		name = name.replaceAll("\\\\", "/");
		int n = name.lastIndexOf(".");
		int m = name.lastIndexOf("/");
		return name.substring(m + 1, n);
	}

	public static String getRanomName(String fileName) {
		Random rand = new Random();
		Random rand1 = new Random();
		int j = rand.nextInt(10000);
		int k = rand1.nextInt(10000);
		Integer jStr = new Integer(j);
		Integer kStr = new Integer(k);
		String fnameExt = getFileExt(fileName);
		String fnameStr = jStr.toString() + kStr.toString() + "." + fnameExt;
		return fnameStr;
	}
	
	public static String showPhotoUrl(String photoUrl){
		
		if(null == photoUrl)
			return "";
		
		photoUrl = photoUrl.replace("|", "/");
		
		int m = photoUrl.lastIndexOf("/");
		
		String url = photoUrl.substring(0,m);
		
		String main = getFileMain(photoUrl);
		
		String maintxt = getFileExt(photoUrl);
		
		if(main.endsWith("M")){
			return photoUrl;
		}
		if(main.endsWith("S")){
			return photoUrl;
		}
		
		String newFile = main+"S."+maintxt;

		url = url +"/"+newFile;
		
		return url;
	}
	
	/**
	 * 年月日+时分秒+3位随机数
	 * @param fileName
	 * @return
	 */
	
	public static String getStorageFileName(String fileName){
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		String monthStr = null;
		String dayStr = null;
		if (month < 10)
			monthStr = "0" + Integer.toString(month);
		else
			monthStr = Integer.toString(month);
		if (day < 10)
			dayStr = "0" + Integer.toString(day);
		else
			dayStr = Integer.toString(day);
		String currentDayStr = year + monthStr + dayStr;
		String minuteStr = null;
		String hourStr = null;
		String secondStr = null;
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minutes = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);		
		
		if(hours < 10){
			hourStr ="0"+ Integer.toString(hours);
		}else{
			hourStr = Integer.toString(hours);
		}
		
		if(minutes < 10){
			minuteStr ="0"+ Integer.toString(minutes);
		}else{
			minuteStr = Integer.toString(minutes);
		}
		
		if(seconds<10){
			secondStr ="0"+Integer.toString(seconds);
		}else{
			secondStr = Integer.toString(seconds);
		}
		String currentTimeStr = hourStr + minuteStr + secondStr;
		Random rand = new Random();
		int random = rand.nextInt(1000);
		String fnameExt = getFileExt(fileName);
		String fnameStr = currentDayStr+currentTimeStr + random  + "." + fnameExt;
		return fnameStr;
	}
	
	

	

	public static void rename(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				rename(files[i]);
			}
		} else {
			String fullPath = file.getAbsolutePath();
			String name = file.getName();
			int n = fullPath.lastIndexOf(name);
			String newName = fullPath.substring(0, n)
					+ name.substring(0, name.length() / 2);
			log.info(fullPath + ">>>>" + newName);
			file.renameTo(new File(newName));
		}
	}
	
	public static void accessFile(File file){
		if(file.isDirectory()){
			File[] fs = file.listFiles();
			for (int i = 0; i < fs.length; i++) {
				accessFile(fs[i]);
			}
		}else{
			file.setLastModified(System.currentTimeMillis());
		}
	}
	
	
	public static void writeFile(String content, File file) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.flush();
			bw.write(content);
			bw.close();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
    
    /**
     * 取得指定路径文件的文件大小(单位:K)
     * @param fileName
     * @return
     */
    public static Long getFileSize(String fileName) {
        Long fileSize = new Long(0);
        File file = new File(fileName);
        if (file != null) {
            fileSize = file.length() / 1024;
        }
        return fileSize;
    }
    
    private static Project project;
    static{
    	project = new Project();
    	project.init();
    }


    public static void unZip(File zipFile, File dest) {
        Expand unzip = (Expand) project.createTask("unzip");
        unzip.setDest(dest);
        unzip.setSrc(zipFile);
        unzip.setOverwrite(true);
        unzip.execute();
    }

    public static void zip(File baseDir, String[] fileNames, File destFile) {
        Zip zip = (Zip) project.createTask("zip");
        zip.setBasedir(baseDir);
        String includeNames = StringUtils.join(fileNames, ",");
        zip.setIncludes(includeNames);
        zip.setCompress(true);
        zip.setDestFile(destFile);
        zip.execute();
    }

    public static String[] getModifiedFileNames(File dir, long date) {
        Project project = new Project();
        FileSet fileSet = new FileSet();
        fileSet.setIncludes("**/*");
        fileSet.setDir(dir);
        TimeComparisons after = new DateSelector.TimeComparisons();
        after.setValue("after");
        DateSelector selector = new DateSelector();
        selector.setMillis(date);
        selector.setWhen(after);
        selector.setProject(project);
        fileSet.addDate(selector);
        FileScanner scanner = fileSet.getDirectoryScanner(project);
        String[] files = scanner.getIncludedFiles();
        return files;
    }
    
    public static String[] getZipNames(File dir, long date) {
        Project project = new Project();
        FileSet fileSet = new FileSet();
        fileSet.setIncludes("**/*.zip");
        fileSet.setDir(dir);
        TimeComparisons before = new DateSelector.TimeComparisons();
        before.setValue("before");
        DateSelector selector = new DateSelector();
        selector.setMillis(date);
        selector.setWhen(before);
        selector.setProject(project);
        fileSet.addDate(selector);
        FileScanner scanner = fileSet.getDirectoryScanner(project);
        String[] files = scanner.getIncludedFiles();
        return files;
    }

    public static String[] getFileNames(File dir, String name) {
        Project project = new Project();
        FileSet fileSet = new FileSet();
        fileSet.setIncludes(name);
        fileSet.setDir(dir);
        FileScanner scanner = fileSet.getDirectoryScanner(project);
        String[] files = scanner.getIncludedFiles();
        return files;
    }

    public static String[] getFileNames(File dir, String name, String excludes) {
        Project project = new Project();
        FileSet fileSet = new FileSet();
        fileSet.setIncludes(name);
        fileSet.setExcludes(excludes);
        fileSet.setDir(dir);
        //fileSet.setCaseSensitive(true);
        FileScanner scanner = fileSet.getDirectoryScanner(project);
        String[] files = scanner.getIncludedFiles();
        return files;

    }

    public static void copy(File src, File target) {
    	if(!target.exists()){
    		createFolder(target.getAbsolutePath());
    	}
        Copy copy = (Copy) project.createTask("copy");
        copy.setFile(src);
        copy.setTofile(target);
        copy.execute();
    }

    public static void copyDir(File srcFolder, File targetFolder, boolean overwrite) {
        Copy copy = (Copy) project.createTask("copy");
        FileSet fileSet = new FileSet();
        fileSet.setDir(srcFolder);
        fileSet.setIncludes("**/*");
        copy.setOverwrite(overwrite);
        copy.addFileset(fileSet);
        copy.setTodir(targetFolder);
        copy.execute();
    }

    public static void copyDir(File srcFolder, File targetFolder, String[] excludes) {
        Copy copy = (Copy) project.createTask("copy");
        FileSet fileSet = new FileSet();
        fileSet.setDir(srcFolder);
        fileSet.setIncludes("**/*");
        if (excludes != null) {
            for (int i = 0; i < excludes.length; i++) {
                fileSet.setExcludes(excludes[i]);
            }
        }
        copy.addFileset(fileSet);
        copy.setTodir(targetFolder);
        copy.execute();
    }
    
    public static void copyIncludeDir(File srcFolder, File targetFolder, String[] includes) {
        Copy copy = (Copy) project.createTask("copy");
        FileSet fileSet = new FileSet();
        fileSet.setDir(srcFolder);
        if (includes != null) {
            for (int i = 0; i < includes.length; i++) {
                fileSet.setIncludes(includes[i]);
            }
        }
        copy.addFileset(fileSet);
        copy.setTodir(targetFolder);
        copy.execute();
    }

    public static void move(File src, File target) {
        Move move = (Move) project.createTask("move");
        move.setFile(src);
        move.setTodir(target);
        move.execute();
    }

    /**
     * @param file
     */
    public static void delete(File file) {
        Delete delete = (Delete) project.createTask("delete");
        delete.setDir(file);
        delete.execute();
    }

    public static void delete(File file, String includes) {
        Delete delete = (Delete) project.createTask("delete");
        FileSet fileSet = new FileSet();
        fileSet.setDir(file);
        fileSet.setIncludes(includes);
        delete.addFileset(fileSet);
        delete.execute();
    }
    
    public static void delete(File file, String[] includes) {
    	Delete delete = (Delete) project.createTask("delete");
        FileSet fileSet = new FileSet();
        fileSet.setDir(file);
        String includeNames = StringUtils.join(includes, ",");
        fileSet.setIncludes(includeNames); 
        delete.addFileset(fileSet);
        delete.execute();
    }

    

    public static void flow(InputStream input, OutputStream out, byte[] buf)
            throws IOException {
        int numRead;
        while ((numRead = input.read(buf)) >= 0) {
            out.write(buf, 0, numRead);
        }
        out.flush();
    }

    public static void flow(Reader input, Writer out, char[] buf)
            throws IOException {
        int numRead;
        while ((numRead = input.read(buf)) >= 0) {
            out.write(buf, 0, numRead);
        }
        out.flush();
    }

    public static String getFileName(String name) {
    	name = name.replaceAll("\\\\", "/");
		int m = name.lastIndexOf("/");
		return name.substring(m + 1);
    }
    
    /**
     * 返回文件的路径
     * 如upload/test/a.jpg，返回upload/test
     * a.jpg，返回空字符
     * @param name
     * @return
     */
    public static String getFilePath(String name) {
    	String path="";
    	name = name.replaceAll("\\\\", "/");
		int m = name.lastIndexOf("/");
		if(m>-1){
			path=name.substring(0,m);
		}
		return path;
    }

	public static void deletefile(File delpath) 
	     throws FileNotFoundException,IOException{
	  File[] filelist=delpath.listFiles();
	  if (filelist.length!=0){
		  for (int i=0;i<filelist.length;i++){
			  if (filelist[i].isDirectory())
				  deletefile(filelist[i]);
			  else
				  filelist[i].delete();
		  }
		  delpath.delete();
	  }
	  else
		  delpath.delete();
	 }
	
	public static String readFile(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			StringBuffer buf = new StringBuffer();
			String line;
			while ((line = reader.readLine()) != null) {
				buf.append(line + "\n");
			}
			reader.close();
			return buf.toString();

		} catch (FileNotFoundException ex) {
			throw new RuntimeException("没有找到文件:" + file.getAbsolutePath());
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	public static String readFile(String file) {
		return readFile(new File(file));
	}

	public static void writeFile(String content, String file) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.flush();
			bw.write(content);
			bw.close();
		} catch (IOException ex) {
			log.warn(ex);
			throw new RuntimeException(ex);
		}
	}
	
	public static void writeFile(String content, String file,String charEncode) {
		try {
			Writer   out =new BufferedWriter(new OutputStreamWriter(new FileOutputStream( file),  charEncode)); 
			out.write(content); 
			out.close(); 
		} catch (IOException ex) {
			log.warn(ex);
			throw new RuntimeException(ex);
		}
	}

	public static InputStream getResource(String fileName) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		return classLoader.getResourceAsStream(fileName);
	}

	public static String getWebRoot() {
//		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//		URL url = classLoader.getResource("web.xml");
//		String path = "";
//		if (url.toString().charAt(7) == ':') {
//			// windows
//			path = url.toString().substring(6);
//		} else {
//			// unix
//			path = url.toString().substring(5);
//		}
//		int n = path.lastIndexOf("/WEB-INF/web.xml");
//		return path.substring(0, n);
		
		 ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		URL url = classLoader.getResource("log4j.properties");
		String filepath = url.getPath();
		filepath = filepath.substring(0, filepath.lastIndexOf("/"));
		filepath = filepath.substring(0, filepath.lastIndexOf("/"));
		filepath = filepath.substring(0, filepath.lastIndexOf("/"));
		log.info("filepath------"+filepath);
		return filepath;
	}

	public static void createFolder(String file) {
		file = file.replaceAll("\\\\", "/");
		int n = file.lastIndexOf("/");
		int m = file.lastIndexOf(".");
		if (m > n) {
			file = file.substring(0, n);
		}
		if(new File(file).mkdirs()){
			log.info("createFolder:"+file);
		}
	}
	
	public static String getParentFolderPath(String path) {
		String parent="";
		path = path.replaceAll("\\\\", "/");
		int n = path.lastIndexOf("/");
		if(n>-1){
			parent=path.substring(0,n);
		}
		return parent;
	}
	
	/**
	 * 上传远程图片
	 * @param photoUrl 远程图片链接
	 * @param fileName 新图片名称
	 */
	public static void saveUrlAs(String photoUrl, String fileName) {
		try {
			FileOutputStream fos = new FileOutputStream(fileName);
			URL url = new URL(photoUrl);
			InputStream is = url.openStream();
			byte b[] = new byte[4096];
			while (true) {
				int i = is.read(b);
				if (i == -1)
					break;
				fos.write(b);
			}
			fos.close();
		} catch (Exception ex) {
			System.out.println("------------------------------------远程图片链接不上-------------------------");
			ex.printStackTrace();
		}
	}
	
	public static String getFileURL(String imgSrc){
		String newPath =null;
		String regEx = "(http://|https://){1}[^\\s|'|\"]+[\\.jpg|\\.gif|\\.png|\\.bmp|\\.jpeg]";
		Pattern p = Pattern.compile(regEx);
		p = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(imgSrc);
		while (m.find()) {
			String imgURL = m.group();
			String fileName = getStorageFileName(imgURL);
			String path = "/data/upload/temp/"+fileName;
			saveUrlAs(imgURL, path);
			newPath = "";//Configuation.getConfigValue("img_path")+path;
		}
		return newPath;
	}
	
	public static void main(String[] args) {
		getWebRoot();
	}
	
	
	
}