package com.meti.util;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class LoggerHandler extends Handler {

  @Override
  public void publish(LogRecord record) {
    System.err.println(getFormatter().format(record));
  }

  @Override
  public void flush() {

  }

  @Override
  public void close() throws SecurityException {

  }
}