package com.codex.trial.trial;

import java.net.URL;

import org.apache.commons.io.FilenameUtils;

public class FilenameUtilTest {

	public static void main(String[] args) throws Exception {
		URL url = new URL("https://drive.google.com/uc?id=1OjVXW2xK9eHcBAjJJygtcyYtiQBwX-tt&export=download");

		System.out.println(FilenameUtils.getBaseName(url.getPath())); // -> file
		System.out.println(FilenameUtils.getExtension(url.getPath())); // -> xml
		System.out.println(FilenameUtils.getName(url.getPath())); // -> file.xml
	}

}