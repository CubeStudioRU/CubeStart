package com.github.fadegor05.models;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

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
            for (Path minecraftModsFile : minecraftModsFiles) {
                String fileName = minecraftModsFile.getFileName().toString();
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

    public void setMinecraftMods(ArrayList<String> minecraftMods) {
        this.minecraftMods = minecraftMods;
    }

    public void setCustomMods(ArrayList<String> customMods) {
        this.customMods = customMods;
    }

    public void setExternalMods(ArrayList<String> externalMods) {
        this.externalMods = externalMods;
    }
}
