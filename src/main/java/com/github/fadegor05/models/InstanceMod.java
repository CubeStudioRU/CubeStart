package com.github.fadegor05.models;

public class InstanceMod {
    private final String file;
    private final String url;

    public InstanceMod(String file, String url) {
        this.file = file;
        this.url = url;
    }

    public String getFile() {
        return file;
    }

    public String getUrl() {
        return url;
    }
}
