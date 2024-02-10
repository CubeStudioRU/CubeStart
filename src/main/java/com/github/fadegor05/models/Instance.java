package com.github.fadegor05.models;

import java.util.List;

public class Instance {
    private final int id;
    private final String name;
    private final String version;
    private final String changelog;
    private final List<InstanceMod> mods;

    public Instance(int id, String name, String version, String changelog, List<InstanceMod> mods) {
        this.id = id;
        this.name = name;
        this.version = version;
        this.changelog = changelog;
        this.mods = mods;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getChangelog() {
        return changelog;
    }

    public List<InstanceMod> getMods() {
        return mods;
    }
}
