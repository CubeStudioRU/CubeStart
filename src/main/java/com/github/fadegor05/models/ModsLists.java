package com.github.fadegor05.models;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ModsLists {
    private ArrayList<String> minecraftMods = new ArrayList<>();
    private ArrayList<String> customMods = new ArrayList<>();
    private ArrayList<String> externalMods = new ArrayList<>();
    public ModsLists(Directories directories, Instance instance) {
        this.minecraftMods = getModsByDirectory(directories.getMinecraftModsPath());
        this.customMods = getModsByDirectory(directories.getCustomModsPath());
        this.externalMods = getModsFromInstance(instance);
    }

    private ArrayList<String> getModsByDirectory(Path path) {
        ArrayList<String> mods = new ArrayList<>();
        try {
            List<Path> minecraftModsFiles = Files.list(path).toList();
            for (int i = 0; i < minecraftModsFiles.size(); i++) {
                String fileName = minecraftModsFiles.get(i).getFileName().toString();
                mods.add(fileName);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return mods;
    }

    private ArrayList<String> getModsFromInstance(Instance instance) {
        ArrayList<String> mods = new ArrayList<>();
        for (int i = 0; i < instance.getMods().size(); i++) {
            mods.add(instance.getMods().get(i).getFile());
        }
        return mods;
    }

    public ArrayList<String> getMinecraftMods() {
        return minecraftMods;
    }

    public ArrayList<String> getCustomMods() {
        return customMods;
    }

    public ArrayList<String> getExternalMods() {
        return externalMods;
    }
}
