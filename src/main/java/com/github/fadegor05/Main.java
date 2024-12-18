package com.github.fadegor05;

import com.github.fadegor05.cli.CreditsOutCli;
import com.github.fadegor05.cli.InstanceDirectoryCli;
import com.github.fadegor05.cli.InstanceOutCli;
import com.github.fadegor05.models.*;
import com.github.fadegor05.utils.ConfigUtil;
import org.apache.commons.cli.*;


import java.nio.file.Files;
import java.nio.file.Path;

import static com.github.fadegor05.cli.ExitWaitCli.exitWaitCli;
import static com.github.fadegor05.utils.CubeApiUtil.getInstanceByAPI;
import static com.github.fadegor05.utils.ModsActionsUtil.ModsActionsHandler;

public class Main {
    public static final String CUBE_API_URL = "http://api.cubeshield.ru:8000/api/v1";
    public static final String CUBESTART_VERSION = "0.1.3";
    public static final String CONFIG_FILE = "cubestart_config.json";
    public static final String MODS_FOLDER = "mods";
    public static final String CUSTOM_MODS_FOLDER = "custom_mods";

    public static void main(String[] args) throws Exception {
        Options options = new Options();
        options.addOption("s", "skip", false, "Пропустить ожидание Enter в конце");
        options.addOption(Option.builder("d")
                    .longOpt("dir")
                    .hasArg()
                    .desc("Путь до директории со сборкой (Например: C:/MCData/Instances/CubeShield/.minecraft)")
                    .build());
        CreditsOutCli.CreditsOutCli();
        ConfigUtil.handleConfig();

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(options, args);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        Config config = ConfigUtil.getConfig();
        if (config.getInstanceDirectory() == null) {
            String directory;
            if (cmd.hasOption("dir")) {
                Path path = Path.of(cmd.getOptionValue("dir")).toAbsolutePath();
                if (!Files.isDirectory(path)) {
                    throw new Exception("Данного пути не существует");
                }
                directory = path.toString();
            }
            else {
               directory = InstanceDirectoryCli.getInstanceDirectoryCli();
            }
            config.setInstanceDirectory(directory);
            ConfigUtil.updateConfig(config);
        }

        Instance instance = getInstanceByAPI(config.getApiUrl());
        InstanceOutCli.InstanceOutCli(instance);

        Directories directories = new Directories(config.getInstanceDirectory());
        ModsLists modsLists = new ModsLists(directories, instance);
        ModsActions modsActions = new ModsActions(modsLists);

        if (modsActions.getCopyCustomModsList().isEmpty() && modsActions.getDeleteMinecraftModsList().isEmpty() && modsActions.getInstallExternalModsList().isEmpty()) {
            System.out.println("Никаких необходимых изменений нет, Minecraft готов к запуску!");
            if (!cmd.hasOption("skip")){
                exitWaitCli();
            }
            return;
        }
        System.out.println("Есть необходимые изменения, выполняем их...\n");

        ModsActionsHandler(modsActions, instance, config.getInstanceDirectory());

        System.out.println("\nВсе изменения были совершены, Minecraft готов к запуску!");
        if (!cmd.hasOption("skip")){
            exitWaitCli();
        }
    }
}