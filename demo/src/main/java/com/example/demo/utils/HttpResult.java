package com.example.demo.utils;

import java.io.File;
import java.util.Map;

/**
 * 返回值
 * **/
public class HttpResult 
{
	private int code = 0;
	
	private byte[] body;

	private File file;
	
	private Map<String,String> responseHeaders;
	
	public int getCode() 
	{
		return code;
	}

	public HttpResult setCode(int code) 
	{
		this.code = code;
		return this;
	}

	public byte[] getBody() 
	{
		return body;
	}

	public HttpResult setBody(byte[] body)
	{
		this.body = body;
		return this;
	}

	public Map<String, String> getResponseHeaders() 
	{
		return responseHeaders;
	}

	public HttpResult setResponseHeads(Map<String, String> responseHeaders) 
	{
		this.responseHeaders = responseHeaders;
		return this;
	}

    public File getFile()
    {
        return file;
    }

    public HttpResult setFile(File file)
    {
        this.file = file;
        return this;
    }

}