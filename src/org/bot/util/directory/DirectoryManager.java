package org.bot.util.directory;

import org.bot.util.directory.exceptions.InvalidDirectoryNameException;

import java.io.File;
import java.io.IOException;

public class DirectoryManager {

    public final static String BOT_DIRECTORY_PATH = System.getProperty("user.home") + File.separator + "uBot";
    public static String CACHE_PATH;
    public static String SERVER_PROVIDERS_PATH;
    public static String SCRIPTS_PATH;
    public static String TEMP_PATH;
    public static String SERVER_JARS_PATH;
    private final Directory botDirectory;

    public DirectoryManager() {
        botDirectory = getRootDirectory();
        try {
            createSubDirectories();
        } catch (InvalidDirectoryNameException | IOException e) {
            e.printStackTrace();
        }
    }

    public Directory getRootDirectory() {
        final Directory directory = new Directory(BOT_DIRECTORY_PATH);
        if (!directory.exists()) {
            try {
                if (directory.create()) {
                    return directory;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return directory;
    }

    private void createSubDirectories() throws IOException, InvalidDirectoryNameException {
        botDirectory.createSubDirectory("Cache");
        CACHE_PATH = botDirectory.getSubDirectory("Cache").getPath();
        botDirectory.createSubDirectory("Server Providers");
        SERVER_PROVIDERS_PATH = botDirectory.getSubDirectory("Server Providers").getPath();
        botDirectory.createSubDirectory("Scripts");
        SCRIPTS_PATH = botDirectory.getSubDirectory("Scripts").getPath();
        botDirectory.getSubDirectory("Cache").createSubDirectory("Server Jars");
        SERVER_JARS_PATH = botDirectory.getSubDirectory("Cache").getSubDirectory("Server Jars").getPath();
        botDirectory.getSubDirectory("Cache").createSubDirectory("Temp");
        TEMP_PATH = botDirectory.getSubDirectory("Cache").getSubDirectory("Temp").getPath();
    }

}
