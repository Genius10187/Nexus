package com.meti.server;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/30/2017
 */
interface Action<T> {
    void perform(T obj);
}
