package com.hp.cmcc.bboss.bdc.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hp.cmcc.bboss.bdc.pojo.HandleReturnPara;

@Service
public class FileService {

	Logger L = LoggerFactory.getLogger(FileService.class);
	public List<String> GetFileBody(String path) throws Exception{
		List<String> list = new LinkedList<String>();
		File f = new File(path);
		if(!f.exists() || f.isDirectory()) {
			return null;
		}
		BufferedReader in = new BufferedReader(new FileReader(f));
		try {
			String line;
			while((line = in.readLine()) != null) {
				list.add(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			in.close();
		}
		return list;
	}
	
	public String getFileName(String path) {
		File D = new File(path);
		if(!D.isDirectory()) {
			L.error("'"+path+"' is not a directory");
			return null;
		}
		for(String f : D.list()) {
			return f;
		}
		return null;
	}
	
	public void writeFile(HandleReturnPara hrp) throws IOException {
		String record = hrp.getRecords().get(1);
		String[] R = record.split(",",-1);
		String fileName = R[R.length-3].substring(1, R[R.length-3].length()-1);
		File file = new File("D:\\test\\outFile\\"+fileName);
		if(!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream out = null;
		try {
			out= new FileOutputStream(file);
			for(String rec : hrp.getRecords()) {
				out.write(rec.getBytes());
				out.write("\r\n".getBytes());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			out.close();
		}
	}
}
