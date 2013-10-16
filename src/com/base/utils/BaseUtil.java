package com.base.utils;

import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * @author zhanghua
 * @version 1.0
 * @date 2009-11-13
 * @class_displayName BaseUtil
 */

public class BaseUtil
{

	private static Logger log=Logger.getLogger(BaseUtil.class.getName());
    public BaseUtil()
    {
    }

    public static String getLocalHostIP()
        throws UnknownHostException
    {
        return InetAddress.getLocalHost().getHostAddress();
    }

    public static String getLocalHostName()
        throws UnknownHostException
    {
        return InetAddress.getLocalHost().getHostName();
    }

    public static boolean isLocalHost(String multicastIP)
        throws UnknownHostException
    {
        return multicastIP.equals(getLocalHostIP()) || multicastIP.equals(getLocalHostName());
    }


    public static String formatNumberString(String str, String pattern)
    {
        if(str == null)
            return "";
        if(pattern == null || pattern.trim().equals(""))
            return str;
        DecimalFormat formater = new DecimalFormat(pattern);
        return formater.format(roundUp(str, pattern));
    }

    private static double roundUp(String str, String pattern)
    {
        BigDecimal decimal = new BigDecimal(str);
        int op = pattern.indexOf(".");
        int scale = 0;
        if(op == -1)
        {
            scale = 0;
        } else
        {
            char chars[] = pattern.toCharArray();
            for(int i = op + 1; i < chars.length && (chars[i] == '0' || chars[i] == '#'); i++)
                scale++;

        }
        if(pattern.endsWith("\u2030"))
            scale += 3;
        else
        if(pattern.endsWith("%"))
            scale += 2;
        return decimal.setScale(scale, 4).doubleValue();
    }

    public static String formatString(String str, String pattern)
    {
        if(str == null)
            return "";
        if(pattern == null || pattern.trim().equals(""))
        {
            return str;
        } else
        {
            Object objs[] = new Object[1];
            objs[0] = str;
            return MessageFormat.format(pattern, objs);
        }
    }

    public static Date convertTimestampToDate(Timestamp time)
    {
        if(time == null)
            return null;
        else
            return new Date(time.getTime());
    }

 
    public static String number2String(Object obj, String pattern)
	{
	        if(obj == null)
	            return "";
	        String tmp = number2String(obj);
	        if(pattern == null || pattern.equals(""))
	            return tmp;
	        else
	            return BaseUtil.formatNumberString(tmp, pattern);
	 }
	 
    public static String number2String(Object obj)
	{
	        if(obj == null)
	            return "";
	        String tmp = obj.toString();
	        if(tmp.endsWith("0"))
	        {
	            if(tmp.indexOf("E") != -1)
	                return tmp;
	            else
	                return removeNumberZero(tmp);
	        } else
	        {
	            return tmp;
	        }
	}
	    
    public static String removeNumberZero(String str)
	{
	        if(str.indexOf(".") == -1)
	            return str;
	        char chars[] = str.toCharArray();
	        int op = -1;
	        int i = chars.length - 1;
	        do
	        {
	            if(i < 0)
	                break;
	            if(chars[i] == '0')
	            {
	                op = i;
	            } else
	            {
	                if(chars[i] == '.')
	                    op = i;
	                break;
	            }
	            i--;
	        } while(true);
	        if(op == -1)
	        {
	            return str;
	        } else
	        {
	            char newChars[] = new char[op];
	            System.arraycopy(chars, 0, newChars, 0, newChars.length);
	            return new String(newChars);
	        }
	 }
    
    /**从字符串中截取数字 */
	   public static String extractNumbers(String content) {  
	       Pattern pattern = Pattern.compile("\\d+");  
	       Matcher matcher = pattern.matcher(content);  
	       while (matcher.find()) {  
	           return matcher.group(0);  
	       }  
	       return "";  
	   }  
	   
	   /**把字符串写入文本文件*/
	   public static void writeToFile(String contens,String pathname){
		   try {
			BufferedWriter fw=new BufferedWriter( new FileWriter(new File(pathname)));
			fw.write(contens);
			fw.flush();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	   }
	 
	 /**
	  * 
	  * @param list 字符串数组
	  * @return
	  * @throws Exception
	  */
   @SuppressWarnings("all")
  public static Map josnArrayToMap(List list) throws Exception{
	   try {
		   Map<String, Object> map=null;
			  if(StringUtilsExtends.collectionIsNotBlank(list)){
				  Object[] array=list.toArray();
				  for (int i = 0; i < array.length; i++) {
					 map=JsonUtil.getMap4Json(array[i].toString());
				}
			  }
			  return map;
		} catch (Exception e) {
			throw e;
		}
   }
   
   /**非标准，缺少双引号，json字符串转map*/
   @SuppressWarnings("rawtypes")
public static Map josn2Map(List jsonStr) throws Exception{
	  Map<String, Object> map=new LinkedHashMap<String, Object>();
	  if(StringUtilsExtends.collectionIsNotBlank(jsonStr)){
		  String  json=jsonStr.toString();
			 String keyValue=StringUtilsExtends.substringBetween(json,"{","}");
			 String[] str=keyValue.split(",");
			   for (String pairs : str) {
				   String[] pair=pairs.split("=");
				   map.put(pair[0], pair[1]);
			   }
	   }
    return map;
   }
   
   /**非标准，缺少双引号，json字符串转map*/
   @SuppressWarnings("rawtypes")
public static Map josn2Map(String jsonStr) throws Exception{
	  Map<String, Object> map=new LinkedHashMap<String, Object>();
	  if(StringUtilsExtends.isNotBlank(jsonStr)){
			 String keyValue=StringUtilsExtends.substringBetween(jsonStr,"{","}");
			 String[] str=keyValue.split(",");
			   for (String pairs : str) {
				   String[] pair=pairs.split("=");
				   map.put(pair[0], pair[1]);
			   }
	   }
    return map;
   }
   
   private void createThumbnail(String filename, int thumbWidth, int thumbHeight, int quality, String outFilename)   
	        throws InterruptedException, FileNotFoundException, IOException   
	    {   
	        // load image from filename   
	        Image image = Toolkit.getDefaultToolkit().getImage(filename);   
	        MediaTracker mediaTracker = new MediaTracker(new Container());   
	        mediaTracker.addImage(image, 0);   
	        mediaTracker.waitForID(0);   
	        // use this to test for errors at this point: System.out.println(mediaTracker.isErrorAny());   
	   
	        // determine thumbnail size from WIDTH and HEIGHT   
	        double thumbRatio = (double)thumbWidth / (double)thumbHeight;   
	        int imageWidth = image.getWidth(null);   
	        int imageHeight = image.getHeight(null);   
	        double imageRatio = (double)imageWidth / (double)imageHeight;   
	        if (thumbRatio < imageRatio) {   
	            thumbHeight = (int)(thumbWidth / imageRatio);   
	        } else {   
	            thumbWidth = (int)(thumbHeight * imageRatio);   
	        }   
	   
	        // draw original image to thumbnail image object and   
	        // scale it to the new size on-the-fly   
	        BufferedImage thumbImage = new BufferedImage(thumbWidth, thumbHeight, BufferedImage.TYPE_INT_RGB);   
	        Graphics2D graphics2D = thumbImage.createGraphics();   
	        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);   
	        graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);   
	   
	        // save thumbnail image to outFilename   
	        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(outFilename));   
	        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);   
	        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(thumbImage);   
	        quality = Math.max(0, Math.min(quality, 100));   
	        param.setQuality((float)quality / 100.0f, false);   
	        encoder.setJPEGEncodeParam(param);   
	        encoder.encode(thumbImage);   
	        out.close();   
	    }  
}