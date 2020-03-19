package com.example.demo.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileUtils {

    public static InputStream getResourcesFileInputStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);
    }

    public static String getPath() {
        return FileUtils.class.getResource("/").getPath();
    }

    public static File createNewFile(String pathName) {
        File file = new File(getPath() + pathName);
        if (file.exists()) {
            file.delete();
        } else {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
        }
        return file;
    }

    public static File readFile(String pathName) {
        return new File(getPath() + pathName);
    }
    
    /**
     * 检查URL资源路径是否有效
     * @param imagePath
     * @return
     */
    @Async
	public Future<URL> checkConnect(String imagePath) {
		URL url;
		HttpURLConnection con;
		int state = -1;
		int sun = 0;
		if (imagePath == null || imagePath.length() <= 0) {
			return null;
		}
		try {
			url = new URL(imagePath);
			con = (HttpURLConnection) url.openConnection();
			state = con.getResponseCode();
			if (state == 200) {
				sun++;
				log.info("URL可用！"+ sun);
			}
		} catch (Exception ex) {
			sun++;
			log.info("URL不可用，连接第 "+sun);
			url = null;
		}
		return new AsyncResult<>(url);
	}
    
    /*
	 * 获取链接地址文件的byte数据 
	 */
	@Async("UrlToByte")
	public Future<byte[]> getUrlFileData(String imagePath) { 
		log.info(Thread.currentThread().getName());
		byte[] fileData = null;
		URL url;
		try {
			url = new URL(imagePath);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection(); 
			httpConn.connect(); 
			int state = httpConn.getResponseCode();
			if(state == 200) {
				InputStream cin = httpConn.getInputStream(); 
				ByteArrayOutputStream outStream = new ByteArrayOutputStream(); 
				byte[] buffer = new byte[1024]; 
				int len = 0; 
				while ((len = cin.read(buffer)) != -1) { 
					outStream.write(buffer, 0, len); 
				} 
				cin.close(); 
				fileData = outStream.toByteArray(); 
				outStream.close(); 
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return new AsyncResult<>(fileData); 
	} 

}
