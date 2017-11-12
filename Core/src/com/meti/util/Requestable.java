package com.meti.util;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 11/12/2017
 */
public interface Requestable<T> {
    void onRequest(Request<T> request);
}
