package com.meti.command;

public class ListCommand implements Command {

    @Override
    public void perform() {
        System.out.println("Command performed!");
    }
}
