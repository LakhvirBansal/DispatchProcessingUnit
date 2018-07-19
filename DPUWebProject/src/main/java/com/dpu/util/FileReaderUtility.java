package com.dpu.util;

import java.io.File;

import org.springframework.stereotype.Component;

@Component
public class FileReaderUtility {

	public File readFile() {

		File file = null;
		ClassLoader classLoader = FileReaderUtility.class.getClassLoader();
		file = new File(classLoader.getResource("Driver_List.xls").getFile());
		return file;
	}
}
