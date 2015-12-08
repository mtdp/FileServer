package me.wanx.file.server.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import org.csource.common.IniFileReader;

public class FastDFSClientTest {
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		URL url = FastDFSClientTest.class.getResource("/fastdfs-client.properties");
		System.out.println(url);
		System.out.println(url.getPath());
		IniFileReader iniFile = new IniFileReader(url.getPath());
		System.out.println(iniFile);
	}

}
