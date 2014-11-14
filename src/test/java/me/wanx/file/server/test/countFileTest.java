package me.wanx.file.server.test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class countFileTest {
	public static void main(String[] args) {
		try {
			//FileInputStream in = new FileInputStream("d:/1.txt");
			FileReader in = new FileReader("d:/1.txt");
			BufferedReader reader = new BufferedReader(in);
			ArrayList<String> list = new ArrayList<String> (); 
			while(reader.read() != -1){
				String t = reader.readLine();
				list.add(t.substring(t.lastIndexOf("\\")+1));
			}
			System.out.println(list);
			System.out.println(list.size());
			for(int i = 0; i < list.size(); i++){
				System.out.println(list.get(i));
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
