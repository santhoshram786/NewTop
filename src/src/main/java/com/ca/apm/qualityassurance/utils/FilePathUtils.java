package com.ca.apm.qualityassurance.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class FilePathUtils {

	private static Logger log = Logger.getLogger(FilePathUtils.class);

	public static InputStream getjarFilePath(String resourceName)
			throws IOException {

		log.debug("Checking for Resource Name in class path " + resourceName);
		ClassLoader cl = DataUtils.class.getClassLoader();
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(
				cl);
		Resource[] resources = resolver.getResources("classpath*:/*");
		for (Resource resource : resources) {
			log.debug("Resource Name is " + resource.getFilename());
			if (resource.getFilename().equalsIgnoreCase(resourceName))
				return resource.getInputStream();
		}
		log.warn("File with File Name " + resourceName
				+ "doesn't exist in classpath");
		return null;
	}

}