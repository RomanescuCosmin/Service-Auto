package com.service.auto.dto;

public enum FileStorageTypeEnum {

    PROGRAMARE_AUTO("file.storage.subdir.programare_auto");

    public final String path;

    FileStorageTypeEnum(String path) {
        this.path = path.toLowerCase();
    }
}
