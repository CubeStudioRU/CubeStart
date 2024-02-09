package com.github.fadegor05.models;

public class Config {
    private Integer instanceId;
    private String instanceVersion;
    private String instanceDirectory;
    private String apiUrl;

    public Config(int instanceId, String instanceVersion, String instanceDirectory, String apiUrl) {
        this.instanceId = instanceId;
        this.instanceVersion = instanceVersion;
        this.instanceDirectory = instanceDirectory;
        this.apiUrl = apiUrl;
    }

    public Integer getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(Integer instanceId) {
        this.instanceId = instanceId;
    }

    public String getInstanceVersion() {
        return instanceVersion;
    }

    public void setInstanceVersion(String instanceVersion) {
        this.instanceVersion = instanceVersion;
    }

    public String getInstanceDirectory() {
        return instanceDirectory;
    }

    public void setInstanceDirectory(String instanceDirectory) {
        this.instanceDirectory = instanceDirectory;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

}
