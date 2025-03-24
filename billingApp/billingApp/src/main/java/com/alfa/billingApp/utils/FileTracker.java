package com.alfa.billingApp.utils;

import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class FileTracker {
    private static final ThreadLocal<File> fileHolder = new ThreadLocal<>();

    public void setFile(File file) {
        fileHolder.set(file);
    }

    public File getFile() {
        return fileHolder.get();
    }

    public void clear() {
        fileHolder.remove();
    }
}