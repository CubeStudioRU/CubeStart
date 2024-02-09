package com.github.fadegor05.utils;

import com.github.fadegor05.models.Config;
import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import static com.github.fadegor05.Main.CUBE_API_URL;

public class ConfigUtil {

    private static Config config;

    private static void loadConfig() {
        Gson gson = new Gson();
        String path = "./config.json";
        File file = new File(path);
        if (file.exists()){
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8));
                StringBuilder out = new StringBuilder();
                String str;

                while ((str = in.readLine()) != null) {
                    out.append(str);
                }

                config = gson.fromJson(String.valueOf(out), Config.class);
            }
            catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private static void saveConfig() {
        try {
            Gson gson = new Gson();
            File file = new File("./config.json");
            file.createNewFile();
            Writer writer = new BufferedWriter(new OutputStreamWriter(
                    Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8));
            try {
                gson.toJson(config, writer);
                writer.flush();
            } finally {
                writer.close();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void handleConfig() {
        loadConfig();
        if (config == null){
            config = new Config(0,null,null, CUBE_API_URL);
        }
        saveConfig();
    }

    public static Config getConfig() {
        return config;
    }

    public static void updateConfig(Config newConfig) {
        config = newConfig;
        saveConfig();
    }
}
