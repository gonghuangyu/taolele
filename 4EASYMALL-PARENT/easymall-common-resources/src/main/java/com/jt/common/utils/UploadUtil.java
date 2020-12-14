package com.jt.common.utils;



public class UploadUtil {
	
	public static String getUploadPath(String fileName,String prefix){
		
		//根据文件名称,生成hash字符串,截取前8位
		//1k2k2k3l--1/k/2/k/2/k/3/l
		//dasdk--d/a/s/d/k/0/0/0
		String hash = Integer.toHexString(fileName.hashCode());
		while(hash.length()<8){
			hash += "0";
		}
		for (int i = 0; i < hash.length(); i++) {
			prefix += "/"+hash.charAt(i);
		}
		
		//upload/8级路径 upload/1/d/3/f/4/f/5/g
		return prefix;
	}
}
