package database;

import main.Main;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Database {

    static {
        basePath = String.format("%s%sDocuments%sjudge-server%s",
                System.getProperty("user.home"),
                File.separator,
                File.separator,
                File.separator
        );
    }

    private static String basePath;

    public static void save(String path, String data){

        if(!path.contains(".data")) path += ".data";

        try {
            if(Files.notExists(Paths.get(basePath))) {
                Files.createDirectories(Paths.get(basePath));
            }

            Files.writeString(Paths.get(basePath + path), data);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static String load(String path){
        if(!path.contains(".data")) path += ".data";
        if(Files.exists(Paths.get(basePath + path))){
            try {
                return Files.readString(Paths.get(basePath + path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
