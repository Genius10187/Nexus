package com.meti.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utility {
    private Utility() {
    }

    public static <T> T castIfOfInstance(Object obj, Class<T> c) {
        if (c.isAssignableFrom(obj.getClass())) {
            return c.cast(obj);
        } else {
            return null;
        }
    }

    public static boolean createIfNotExists(File file, boolean directory) throws IOException {
        if (directory) {
            return file.mkdirs();
        } else {
            boolean directoryCreated = true;
            if (file.getParentFile() != null) {
                directoryCreated = createIfNotExists(file.getParentFile(), true);
            }
            return file.createNewFile() && directoryCreated;
        }
    }

    public static List<File> getFiles(File directory, String... extensions) {
        List<File> fileList = new ArrayList<>();
        search(directory, fileList, extensions);
        return fileList;
    }

    public static void search(File file, List<File> fileList, String... extensions) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length != 0) {
                for (File foundFile : files) {
                    search(foundFile, fileList, extensions);
                }
            }
        } else {
            if (extensions.length != 0) {
                String fileExtension = getExtension(file);
                for (String extension : extensions) {
                    if (fileExtension.equals(extension)) {
                        fileList.add(file);
                    }
                }
            } else {
                fileList.add(file);
            }
        }
    }

    public static String getExtension(File file) {
        String[] nameArgs = file.getName().split("\\.");
        return nameArgs[nameArgs.length - 1];
    }
}
