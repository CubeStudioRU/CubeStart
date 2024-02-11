package com.github.fadegor05;

import com.github.fadegor05.cli.CreditsOutCli;
import com.github.fadegor05.cli.InstanceDirectoryCli;
import com.github.fadegor05.cli.InstanceOutCli;
import com.github.fadegor05.models.Config;
import com.github.fadegor05.models.Directories;
import com.github.fadegor05.models.Instance;
import com.github.fadegor05.models.ModsLists;
import com.github.fadegor05.utils.ConfigUtil;
import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.IOException;

import static com.github.fadegor05.utils.CubeApiUtil.getInstanceByAPI;

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

        Instance instance = getInstanceByAPI(config.getApiUrl());
        InstanceOutCli.InstanceOutCli(instance);

        Directories directories = new Directories(config.getInstanceDirectory());
        ModsLists modsLists = new ModsLists(directories, instance);
    }
}