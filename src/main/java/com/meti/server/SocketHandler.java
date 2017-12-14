package com.meti.server;

import com.meti.command.Command;
import com.meti.util.concurrent.Loop;
import com.meti.util.concurrent.Queuer;
import com.meti.util.intf.Callback;
import com.meti.util.intf.Handler;
import com.meti.util.io.Change;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

public class SocketHandler implements Handler<Socket> {

  private final ExecutorService executorService;

  private final ObjectInputStream inputStream;
  private final ObjectOutputStream outputStream;
  private final Server server;

  private final Queuer queuer;

  private final Callback<Exception> exceptionCallback;
  private final Socket socket;

  public SocketHandler(ExecutorService executorService, Callback<Exception> exceptionCallback,
      Socket socket, Server server) throws IOException {
    this.executorService = executorService;
    this.exceptionCallback = exceptionCallback;
    this.socket = socket;

    this.outputStream = new ObjectOutputStream(socket.getOutputStream());
    this.inputStream = new ObjectInputStream(socket.getInputStream());
    this.server = server;

    this.queuer = new Queuer(inputStream);
  }

  @Override
  public void perform(Socket obj) {
    //TODO: change it such that the field and the socket are not the same object, remove one or the other
    executorService.submit(queuer);

    CommandLoop commandLoop = new CommandLoop(exceptionCallback);
    executorService.submit(commandLoop);

    ChangeLoop changeLoop = new ChangeLoop(exceptionCallback);
    executorService.submit(changeLoop);
  }

  private class ChangeLoop extends Loop {

    public ChangeLoop(Callback<Exception> exceptionCallback) {
      super(exceptionCallback);
    }

    @Override
    public void loop() {
      //TODO: handle changes
      queuer.poll(Change.class);
    }
  }

  private class CommandLoop extends Loop {

    public CommandLoop(Callback<Exception> exceptionCallback) {
      super(exceptionCallback);
    }

    @Override
    public void loop() throws IOException {
      queuer.poll(Command.class).perform(server, inputStream, outputStream);
    }
  }
}
