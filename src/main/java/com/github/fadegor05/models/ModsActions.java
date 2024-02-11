package com.github.fadegor05.models;

import java.util.ArrayList;

public class ModsActions {
    private ArrayList<String> deleteMinecraftModsList = new ArrayList<>();
    private ArrayList<String> copyCustomModsList = new ArrayList<>();
    private ArrayList<String> installExternalModsList = new ArrayList<>();

    public ModsActions(ModsLists modsLists) {
        ModsLists updatedModsLists = copyCustomModsListHandler(modsLists);
        this.installExternalModsList = modsListsHandler(updatedModsLists.getExternalMods(), updatedModsLists.getMinecraftMods());
        this.deleteMinecraftModsList = modsListsHandler(updatedModsLists.getMinecraftMods(), updatedModsLists.getExternalMods());
    }

    private ModsLists copyCustomModsListHandler(ModsLists modsLists) {
        for (String customMod : modsLists.getCustomMods()) {
            if (modsLists.getMinecraftMods().contains(customMod)) {
                ArrayList<String> minecraftMods = modsLists.getMinecraftMods();
                minecraftMods.remove(customMod);
                modsLists.setMinecraftMods(minecraftMods);
            } else {
                this.copyCustomModsList.add(customMod);
            }
        }
        return modsLists;
    }

    private ArrayList<String> modsListsHandler(ArrayList<String> firstModsList, ArrayList<String> secondModsList) {
        ArrayList<String> outList = new ArrayList<>();
        for (String firstListMod : firstModsList) {
            if (!secondModsList.contains(firstListMod)) {
                outList.add(firstListMod);
            }
        }
        return outList;
    }

    public ArrayList<String> getDeleteMinecraftModsList() {
        return deleteMinecraftModsList;
    }

    public ArrayList<String> getCopyCustomModsList() {
        return copyCustomModsList;
    }

    public ArrayList<String> getInstallExternalModsList() {
        return installExternalModsList;
    }
}
