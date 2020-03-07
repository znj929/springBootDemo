package com.example.demo.utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;



/**
 * url客户端
 * **/
public class HttpClient 
{
	public static final String UTF8 = "UTF-8";
	
	public static final String GBK = "GBK";
	
	public static final String GET = "GET";
	
	public static final String POST = "POST";
	
	public static final String HEAD = "HEAD";
	
	public static final String OPTIONS = "OPTIONS";
	
	public static final String PUT = "PUT";
	
	public static final String DELETE = "DELETE";
	
	public static final String TRACE = "TRACE";
	
	public static final String PATCH = "PATCH";
	
	private static final int HTTP_OK = HttpURLConnection.HTTP_OK;
	
	private static final int HTTP_BAD_REQUEST = HttpURLConnection.HTTP_BAD_REQUEST;
	
	private static final int HTTP_EXCEPTION = 999;
	
	//下载的文件
	private File file;
	
	/**
	 * 超时时间默认5秒
	 * **/
	private int timeout = 5000;
	
	/**
	 * 默认超时5秒
	 * **/
	public HttpClient(){}
	
	/**
	 * 默认超时时间
	 * **/
	public HttpClient setTimeout(int timeout)
	{
		this.timeout = timeout;
		return this;
	}
	
	/**
	 * 参数转换字节
	 * @param Map<String,String> params 参数
	 * **/
	public byte[] paramsToBytes(Map<String,String> params)throws Exception
	{
		StringBuffer data = new StringBuffer();
		for(String key : params.keySet())
		{
			if(data.length() == 0)
				data.append(key).append("=").append(params.get(key));
			else
				data.append("&").append(key).append("=").append(params.get(key));
		}
		
		return data.toString().getBytes();
	}
	
	/**
	 * get请求
	 * @param urlStr  http地址
	 * @param headers 请求头
	 * **/
	public HttpResult get(String urlStr,Map<String,String> headers)
	{
		return requestToProxy(HttpClient.GET,urlStr, headers, null,null, 0);
	}
	
	/**
	 * get请求下载文件
	 * **/
	public HttpResult getFile(String urlStr,File file,Map<String,String> headers)
	{
	    this.file = file;
	    return requestToProxy(HttpClient.GET,urlStr, headers, null,null, 0);
	}
	
	/**
	 * post请求
	 * @param urlStr  http地址
	 * @param headers 请求头
	 * @param params 请求参数
	 * **/
	public HttpResult post(String urlStr,Map<String,String> headers,byte[] bytes)
	{
		return requestToProxy(HttpClient.POST,urlStr, headers, bytes, null, 0);
	}
	
   /**
     * delete请求
     * @param urlStr  http地址
     * @param headers 请求头
     * @param params 请求参数
     * **/
    public HttpResult delete(String urlStr,Map<String,String> headers,byte[] bytes)
    {
        return requestToProxy(HttpClient.DELETE,urlStr, headers, bytes, null, 0);
    }
    

    /**
     * put请求
     * @param urlStr  http地址
     * @param headers 请求头
     * @param params 请求参数
     * **/
    public HttpResult put(String urlStr,Map<String,String> headers,byte[] bytes)
    {
        return requestToProxy(HttpClient.PUT,urlStr, headers, bytes, null, 0);
    }
    
    /**
     * patch请求
     * @param urlStr  http地址
     * @param headers 请求头
     * @param params 请求参数
     * **/
    public HttpResult patch(String urlStr,Map<String,String> headers,byte[] bytes)
    {
        return requestToProxy(HttpClient.PATCH,urlStr, headers, bytes, null, 0);
    }
	
	/**
	 * post请求
	 * @param urlStr  http地址
	 * @param headers 请求头
	 * @param params 请求参数
	 * @param String ip 代理IP
	 * @param int port 代理端口
	 * **/
	public HttpResult requestToProxy(String httpMethod,String urlStr,Map<String,String> headers,byte[] bytes,String ip,int port)
	{
		if(urlStr.getBytes().length < 8)
		{
			return null;
		}
		
		if("https://".equals(urlStr.substring(0, 8)))
		{
			if(null == ip)
				return httpsRequest(urlStr,httpMethod,headers,bytes,null);
			else
				return httpsRequest(urlStr,httpMethod,headers,bytes,new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port)));
		}
		else if("http://".equals(urlStr.substring(0, 7)))
		{
			if(null == ip)
				return httpRequest(urlStr,httpMethod,headers,bytes,null);
			else
				return httpRequest(urlStr,httpMethod,headers,bytes,new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port)));
		}
		else
		{
			return null;
		}
	}
	
	public HttpResult httpRequest(String urlStr,String method,Map<String,String> headers,byte[] bytes,Proxy proxy)
	{
		URL url;
		HttpURLConnection http;
		HttpResult result = new HttpResult();
		try
		{
			url  = new URL(urlStr);
			
			if(null == proxy)
				http = (HttpURLConnection)url.openConnection();
			else
				http = (HttpURLConnection)url.openConnection(proxy);

			return this.request(http, method, headers, bytes, result);
		}
		catch(Exception exe)
		{
		    exe.printStackTrace();
			return result.setCode(HTTP_EXCEPTION).setBody(exe.getMessage().getBytes());
		}
		
	}
	
	public HttpResult httpsRequest(String urlStr,String method,Map<String,String> headers,byte[] bytes,Proxy proxy)
	{
		URL url;
		HttpsURLConnection https;
		HttpResult result = new HttpResult();
		try
		{
			url  = new URL(urlStr);
			if(null == proxy)
				https = (HttpsURLConnection)url.openConnection();
			else
				https = (HttpsURLConnection)url.openConnection(proxy);
	
			return this.request(https, method, headers, bytes, result);
		}
		catch(Exception exe)
		{
			return result.setBody(exe.getMessage().getBytes());
		}
	}
	
	/**
	 * http请求处理
	 * @param HttpURLConnection http 请求客户端
	 * @param String method 方法
	 * @param Map<String,String> headers 请求头
	 * @param Map<String,String> params 请求参数
	 * @param HttpResult result 结果集
	 * **/
	private HttpResult request(HttpURLConnection http,String method,Map<String,String> headers,byte[] bytes,HttpResult result) throws Exception
	{

        //设置请求头
        if(null!= headers)
        {
            for(String key : headers.keySet())
            {
                System.out.println("set:"+key);
                http.setRequestProperty(key, headers.get(key));
            }
        }

		http.setDoInput(true);
		http.setDoOutput(true);

		if(method.equals(HttpClient.PATCH))
		{
		    addPatch(http);
		    http.setRequestMethod(method);
		}
		else
		{
		    http.setRequestMethod(method);
		}

        for(String key : http.getRequestProperties().keySet())
        {
            System.out.println("key:"+key);
        }

		http.setUseCaches(false);
		http.setInstanceFollowRedirects(false);
		http.setConnectTimeout(timeout);
		http.setReadTimeout(timeout);
		http.connect();
		
		//发送数据
		if(null != bytes)
			this.sendBytes(http.getOutputStream(), bytes);
		
	    //设置响应状态码
        result.setCode(http.getResponseCode());

        //获取响应数据
        if(result.getCode() >= HTTP_OK && result.getCode() < HTTP_BAD_REQUEST )
            this.readResponseBody(http.getInputStream(), result);
        else
            this.readResponseBody(http.getErrorStream(), result);

		//获取响应头
		this.readResponseHeaders(result, http.getHeaderFields());

		//关闭客户端连接
		http.disconnect();

		return result;

	}
	
	/**
	 * 发送数据(关闭输出流)
	 * @param OutputStream outputStream 输出流
	 * @param byte[] bytes 字节
	 * **/
	private void sendBytes(OutputStream outputStream,byte[] bytes)throws Exception
	{
		DataOutputStream out = new DataOutputStream(outputStream);
		out.write(bytes);
		out.flush();
		out.close();
		outputStream.close();
	}
	
	/**
	 * 接收返回数据头
	 * @param HttpResult result 结果集
	 * @param Map<String, List<String>> headers 原始响应头
	 * **/
	private void readResponseHeaders(HttpResult result,Map<String, List<String>> headers)throws Exception
	{
		result.setResponseHeads(new HashMap<String,String>());

		for(String key : headers.keySet())
		{
			if(null != key)
			{
				for(String value : headers.get(key))
				{
					result.getResponseHeaders().put(key, value);
				}
			}
		}
	}
	
   /**
     * 接收返回数据体
     * @param InputStream bodyInStream 响应体输入流
     * @param InputStream errorInStream 错误信息输入流
     * @param HttpResult result 结果集
     * @param String charset 字符编码
     * **/
    private void readResponseBody(InputStream in,HttpResult result)throws Exception
    {
        if(null == file)
        {
            result.setBody(IoTools.inputStreatToBytes(in));in.close();
        }
        else
        {
            result.setFile(IoTools.inputStreatToFile(in,file));in.close();
        }

    }

    /**
     * 拓展JDK HttpURLConnection methods
     * **/
    private void addPatch(HttpURLConnection http) throws Exception
    {
        String[] methods = {GET, POST, HEAD, OPTIONS, PUT, DELETE, TRACE, PATCH};

        Field field = HttpURLConnection.class.getDeclaredField("methods");
        field.setAccessible(true);
        
        Field modifiers = field.getClass().getDeclaredField("modifiers");
        modifiers.setAccessible(true);
        modifiers.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        
        field.set(http, methods);
    }
	
}