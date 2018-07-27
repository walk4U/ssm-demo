package com.jia.shiro;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @Auther: jia
 * @Date: 2018/7/27 13:50
 * @Description:
 */
public class ShiroConfigLoader {
	
	private Logger logger = LoggerFactory.getLogger(ShiroConfigLoader.class);
	
	private String configPath = "shiro-filter.properties";
	
	public void setConfigPath(String path) {
		configPath = path;
	}

	public String loadFilterConfig() {
		return loadConfig();
	}
	
	public String loadConfig() {
		StringBuilder sb = new StringBuilder();
		System.out.println("load shiro filter config file:" + configPath);
		ClassPathResource resource = new ClassPathResource(configPath);
		if (resource != null && resource.exists()) {  
			if (resource.isReadable()) {
				try {
					InputStream is = resource.getInputStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(is));
					String line;
					while ((line=br.readLine()) != null) {
						sb.append(line).append("\r\n");
					}  
					if (is != null) {
						is.close();  
					}  
					if (br != null) {
						br.close();
					}  
				} catch (IOException e) {
					logger.error("Failed to load shiro config: " + configPath, e);
				}
			}
		}
		return sb.toString();
	}
}
