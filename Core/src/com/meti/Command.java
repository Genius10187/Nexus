package com.meti;

public class Command {
    private final String name;
    private final String[] params;

    public Command(String name, String... params) {
        this.name = name;
        this.params = params;
    }

    public String getName() {
        return name;
    }

    public String[] getParams() {
        return params;
    }
}
