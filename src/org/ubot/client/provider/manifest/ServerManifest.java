package org.ubot.client.provider.manifest;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ServerManifest {

    String serverName();

    String author() default "uBot";

    double version() default 1.0;

    String info() default "";

}
