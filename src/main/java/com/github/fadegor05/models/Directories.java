package com.github.fadegor05.models;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.github.fadegor05.Main.CUSTOM_MODS_FOLDER;
import static com.github.fadegor05.Main.MODS_FOLDER;

public class Directories {
    private final Path minecraftModsPath;
    private final Path customModsPath;

    public Directories(String instanceDirectory) throws IOException {
        Path minecraftModsPath = Path.of(String.format("%s/%s", instanceDirectory, MODS_FOLDER)).toAbsolutePath();
        Path customModsPath = Path.of(String.format("%s/%s", instanceDirectory, CUSTOM_MODS_FOLDER)).toAbsolutePath();
        if (!Files.isDirectory(minecraftModsPath)) {
            Files.createDirectory(minecraftModsPath);
        }
        if (!Files.isDirectory(customModsPath)) {
            Files.createDirectory(customModsPath);
        }

        this.minecraftModsPath = minecraftModsPath;
        this.customModsPath = customModsPath;
    }

    public Path getMinecraftModsPath() {
        return minecraftModsPath;
    }

    public Path getCustomModsPath() {
        return customModsPath;
    }
}
