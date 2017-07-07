package org.bot.server;

import java.awt.*;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ServerManifest {

    String serverName();

    String author();

    String desc();

    double version();

    Class<? extends Component> type();

    Revision rev();

}
