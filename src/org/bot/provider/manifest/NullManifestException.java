package org.bot.provider.manifest;

public class NullManifestException extends Exception {

    private static final long serialVersionUID = 7842522473797693263L;

    public NullManifestException() {
        super("Could not find manifest server.");
    }

}
