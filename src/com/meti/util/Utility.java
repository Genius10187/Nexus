package com.meti.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Utility {
    private Utility() {
    }

    public static List<File> search(File directory, String... extensions) {
        List<File> fileList = new ArrayList<>();
        searchList(directory, fileList, extensions);
        return fileList;
    }

    private static void searchList(File file, List<File> list, String... extensions) {
        if (!list.contains(file)) {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null && files.length != 0) {
                    for (File f : files) {
                        searchList(f, list);
                    }
                }
            } else {
                if (extensions.length != 0) {
                    for (String ext : extensions) {
                        if (getExtension(file).equals(ext)) {
                            list.add(file);
                        }
                    }
                } else {
                    list.add(file);
                }
            }
        }
    }

    public static String getExtension(File file) {
        String[] fileNameArgs = file.getName().split("\\.");
        return fileNameArgs[fileNameArgs.length - 1];
    }
}
