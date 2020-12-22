package com.szbase.credit.util;

import java.io.IOException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;



public class Base64Code {
	
	public static String Encode(byte[] data){
		BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);//返回Base64编码过的字节数组字符串  
	}
	
	public static byte[] Decode(String imgStr) throws IOException{
		BASE64Decoder decoder = new BASE64Decoder();
		return	decoder.decodeBuffer(imgStr);
	}
}
