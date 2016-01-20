package com.speed.util;

import java.io.File;
import java.util.Comparator;

@SuppressWarnings("rawtypes")
public class FileDate implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		File file1 = (File) o1;
		File file2 = (File) o2;
		
		long result =file1.getName().compareTo(file2.getName()) ;
		
		//long result = file1.lastModified() - file2.lastModified();
		if(result<0){
			return 1;
		}else if(result>0){
			return -1;
		}else{
			return 0;
		}
	}

}
