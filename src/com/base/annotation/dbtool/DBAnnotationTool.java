package com.base.annotation.dbtool;

import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

@SupportedSourceVersion(SourceVersion.RELEASE_6)
@SupportedAnnotationTypes({"DbInfo","Id","columns"})
public class DBAnnotationTool extends AbstractProcessor
{
	Map<String,String> dbInfo=new HashMap<String, String>();  //用来存储数据库相关信息
	Map<String,String> pkInfo=new HashMap<String, String>(); //主键信息
	Map<String,Map<String,Object>> columnInfo=new HashMap<String, Map<String, Object>>(); //字段信息
	@Override
	public boolean process(Set<? extends TypeElement> annotations,RoundEnvironment roundEnv)
	{
		//它会循环处理每个程序对象，最后一个是空的用于结束的（不是我们主动创建的），我们这里不对其进行处理
		if(roundEnv.getRootElements().isEmpty()){ return false;} 
			
		String annotationName="";
		//遍历当前处理类的所有annotation
		for(TypeElement t:annotations)
		{
			annotationName="@"+t.getSimpleName().toString();
			//遍历被t(annotation)修饰的所有元素
			for(Element e : roundEnv.getElementsAnnotatedWith(t))
			{
				//System.out.println("当前annotation是："+annotationName);
				//System.out.println("当前被修饰的元素是："+e.getSimpleName());
				//System.out.println("----------------------");
				//获取数据库信息
				if(annotationName.equals("@DbInfo"))
				{
					dbInfo.put("url",e.getAnnotation(DbInfo.class).url());
					dbInfo.put("un",e.getAnnotation(DbInfo.class).un());
					dbInfo.put("pw",e.getAnnotation(DbInfo.class).pw());
					dbInfo.put("table",e.getAnnotation(DbInfo.class).tableName());
				}
				
				//获取主键信息
				if(annotationName.equals("@Id"))
				{
					pkInfo.put("t",jtd(e.getClass().getSimpleName())+"(32)");
					pkInfo.put("c",e.getAnnotation(Id.class).column());
					pkInfo.put("d",e.getAnnotation(Id.class).describe());
					pkInfo.put("u",e.getAnnotation(Id.class).generator());
				}
				
				//获取字段信息
				Map<String,Object> tempOne=null;
				if(annotationName.equals("@columns"))
				{
					tempOne =new HashMap<String, Object>();
					tempOne.put("t", jtd(e.getAnnotation(columns.class).type()));
					tempOne.put("c", e.getAnnotation(columns.class).column());
					tempOne.put("d", e.getAnnotation(columns.class).describe());
					tempOne.put("l", e.getAnnotation(columns.class).length());
					columnInfo.put(e.getSimpleName().toString(), tempOne);
				}
			}
		}
		
		System.out.println("annotation信息获取结束。");
		System.out.println(dbInfo);
		System.out.println(pkInfo);
		System.out.println(columnInfo);
		try
		{
			FileOutputStream fos=new FileOutputStream("mysql.sql");
			fos.write(createSql().toString().getBytes());
			fos.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return true; //annotations是否被这个处理器声明，如果是的话随后的处理器将不再处理这些annotation
	}
	
	/**
	 * 创建数据表
	 * @throws Exception 
	 */
	public String createSql() throws Exception
	{
		String uuid=UUID.randomUUID().toString().replaceAll("-", "");
		StringBuilder sql= new StringBuilder("CREATE TABLE IF NOT EXISTS ");
		sql.append(dbInfo.get("table"));
		sql.append(" ( ");
		sql.append(pkInfo.get("c")+" "+pkInfo.get("t")+" NOT NULL UNIQUE PRIMARY KEY,");
		for(String one : columnInfo.keySet())
		{
			sql.append(one +" "+columnInfo.get(one).get("t")+"("+columnInfo.get(one).get("l")+"),");
		}
		sql.delete(sql.length()-1, sql.length());
		sql.append(" ); \r\n");
		
		//拼装insert语句
		sql.append("insert into "+dbInfo.get("table")+" ("+pkInfo.get("c")+",");
		for(String one : columnInfo.keySet())
		{
			sql.append(one+",");
		}
		sql.delete(sql.length()-1, sql.length());
		sql.append(") VALUES ('"+uuid+"'," );
		for(String one : columnInfo.keySet())
		{
			if(columnInfo.get(one).get("t").equals("varchar"))
			{
				sql.append("'?',");
			}
			if(columnInfo.get(one).get("t").equals("int"))
			{
				sql.append("?,");
			}
		}
		sql.delete(sql.length()-1, sql.length());
		sql.append( ");\r\n");
		
		//拼装删除语句
		sql.append("delete from "+dbInfo.get("table")+" where "+pkInfo.get("c")+"='"+uuid+"';\r\n");
		
		//拼装修改语句
		sql.append("UPDATE "+dbInfo.get("table")+" set ");
		for(String one : columnInfo.keySet())
		{
			if(columnInfo.get(one).get("t").equals("varchar"))
			{
				sql.append(one+"='?',");
			}
			if(columnInfo.get(one).get("t").equals("int"))
			{
				sql.append(one+"=?,");
			}
		}
		sql.delete(sql.length()-1, sql.length());
		sql.append(" where "+pkInfo.get("c")+"='"+uuid+"';\r\n");
		
		//查询语句
		sql.append("select ");
		for(String one : columnInfo.keySet())
		{
			sql.append(one+" as "+columnInfo.get(one).get("d")+",");
		}
		sql.delete(sql.length()-1, sql.length());
		sql.append(" from "+dbInfo.get("table"));
		return sql.toString();
	}
	
	
	/** javaTypeToDbType、java类型和数据库类型的转换
	 * @param type String
	 * @return VARCHAR
	 */
	public String jtd(String type)
	{
		if("String".equals(type))	return  "varchar";
		if("int".equals(type))	return  "int";
		//其他的自己扩展吧
		return  "varchar";
	}
}
