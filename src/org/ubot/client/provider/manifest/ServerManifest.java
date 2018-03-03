package org.ubot.client.provider.manifest;

/**
 * Created by Ethan on 3/2/2018.
 */
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ServerManifest {

    String serverName();

    String author();

    double version();

    String info();

}
