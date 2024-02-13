package com.github.fadegor05.utils;

import com.github.fadegor05.models.Instance;
import com.github.fadegor05.models.InstanceMod;
import com.github.fadegor05.models.ModsActions;
import kong.unirest.HttpResponse;
import kong.unirest.ProgressMonitor;
import kong.unirest.Unirest;
import me.tongfei.progressbar.ProgressBar;
import me.tongfei.progressbar.ProgressBarBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import static com.github.fadegor05.Main.CUSTOM_MODS_FOLDER;
import static com.github.fadegor05.Main.MODS_FOLDER;

public class ModsActionsUtil {
    public static void ModsActionsHandler(ModsActions modsActions, Instance instance, String instanceDirectory) {
        deleteMinecraftModsListHandler(modsActions.getDeleteMinecraftModsList(), instanceDirectory);
        copyCustomModsListHandler(modsActions.getCopyCustomModsList(), instanceDirectory);
        installExternalModsListHandler(modsActions.getInstallExternalModsList(), instanceDirectory, instance);
    }

    private static void deleteMinecraftModsListHandler(ArrayList<String> modsList, String instanceDirectory) {
        for (String mod : modsList) {
            Path modPath = Path.of(String.format("%s/%s/%s", instanceDirectory, MODS_FOLDER, mod));
            System.out.println(String.format("· Удаление %s", mod));
            try {
                Files.delete(modPath);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void copyCustomModsListHandler(ArrayList<String> modsList, String instanceDirectory) {
        for (String mod : modsList) {
            Path customModPath = Path.of(String.format("%s/%s/%s", instanceDirectory, CUSTOM_MODS_FOLDER, mod));
            Path modPath = Path.of(String.format("%s/%s/%s", instanceDirectory, MODS_FOLDER, mod));
            System.out.println(String.format("· Копирование %s", mod));
            try {
                Files.copy(customModPath, modPath);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void installExternalModsListHandler(ArrayList<String> modsList, String instanceDirectory, Instance instance) {
        for (String mod : modsList) {
            for (InstanceMod instanceMod : instance.getMods()) {
                if (mod.equals(instanceMod.getFile())) {
                    System.out.println(String.format("\n· Установка %s", instanceMod.getFile()));
                    String modPath = String.format("%s/%s/%s", instanceDirectory, MODS_FOLDER, instanceMod.getFile());
                    HttpResponse<File> fileHttpResponse = Unirest.get(instanceMod.getUrl())
                            .downloadMonitor((String b, String fileName, Long bytesWritten, Long bytesTotal) -> {
                                try (ProgressBar pb = new ProgressBar(fileName, bytesTotal)) {
                                    pb.stepTo(bytesWritten);
                                }
                            })
                            .asFile(modPath);

                }
            }
        }
    }

}
