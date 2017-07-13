package org.bot.provider.manifest;

import java.awt.Component;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ServerManifest {

	String serverName();

	String author();

	double version();

	Class<? extends Component> type();

	Revision revision();

	String info();

	HookType hookType();

}
