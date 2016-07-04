package com.mt.android.sys.common.log;

import java.io.File;
import org.apache.log4j.Level;

import android.os.Environment;
import de.mindpipe.android.logging.log4j.LogConfigurator;

public class LogConfig {
	static {
		LogConfigurator logConfigurator = new LogConfigurator();

		System.out
				.println("ÈÕÖ¾Â·¾¶::" + Environment.getExternalStorageDirectory());

		logConfigurator.setFileName(Environment.getExternalStorageDirectory()

		+ File.separator + "log4j.txt");

		logConfigurator.setRootLevel(Level.DEBUG);

		logConfigurator.setLevel("org.apache", Level.ERROR);

		logConfigurator.setFilePattern("%d %-5p [%c{2}]-[%L] %m%n");

		logConfigurator.setMaxFileSize(1024 * 1024 * 5);

		logConfigurator.setImmediateFlush(true);

		logConfigurator.configure();
	}
}
