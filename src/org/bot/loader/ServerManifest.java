package org.bot.loader;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ServerManifest {

	String serverName();
	
	String author();
	
	double version();

	Class<?> type();

}
