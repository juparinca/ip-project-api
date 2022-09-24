package com.ip.project.api.singleton;


public enum KeySingleton {

    INSTANCE("","");

    private String nameKey;
    private String valueKey;

    KeySingleton(String nameKey, String valueKey) {
        this.nameKey = nameKey;
        this.valueKey = valueKey;
    }

    public KeySingleton getInstance() {
        return INSTANCE;
    }

    public String getNameKey() {
        return nameKey;
    }

    public void setNameKey(String nameKey) {
        this.nameKey = nameKey;
    }

    public String getValueKey() {
        return valueKey;
    }

    public void setValueKey(String valueKey) {
        this.valueKey = valueKey;
    }
}