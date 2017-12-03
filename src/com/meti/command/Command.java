package com.meti.command;

import java.io.Serializable;

public interface Command extends Serializable {
    void perform();
}
