package com.github.fadegor05;

import com.github.fadegor05.cli.CreditsOutCli;
import com.github.fadegor05.cli.InstanceDirectoryCli;
import com.github.fadegor05.models.Config;
import com.github.fadegor05.utils.ConfigUtil;

import java.io.IOException;

public class Main {
    public static final String CUBE_API_URL = "http://127.0.0.1:8000/api/v1";
    public static final String CUBESTART_VERSION = "0.0.1";
    public static final String CONFIG_FILE = "config.json";
    public static final String MODS_FOLDER = "mods";
    public static final String CUSTOM_MODS_FOLDER = "custom_mods";

    public static void main(String[] args) throws IOException {
        CreditsOutCli.CreditsOutCli();
        ConfigUtil.handleConfig();

        Config config = ConfigUtil.getConfig();
        if (config.getInstanceDirectory() == null) {
            String directory = InstanceDirectoryCli.getInstanceDirectoryCli();
            config.setInstanceDirectory(directory);
            ConfigUtil.updateConfig(config);
        }
    }
}