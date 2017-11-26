package com.meti.io;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/25/2017
 */
public interface Source {
    InputStream getInputStream();

    OutputStream getOutputStream();
}
