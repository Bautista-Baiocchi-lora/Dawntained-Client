package org.bot.server;

import java.awt.Component;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ServerManifest {

	String serverName();

	String author();

	double version();

	Class<? extends Component> type();

}
