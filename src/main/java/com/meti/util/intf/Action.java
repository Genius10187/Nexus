package com.meti.util.intf;

import java.io.IOException;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/30/2017
 */
interface Action<T> {

  void perform(T obj) throws IOException, ClassNotFoundException;
}
