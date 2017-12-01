package com.meti.server;

import com.meti.util.Handler;
import com.meti.util.Loop;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ExecutorService;

public class SocketHandler implements Handler<Socket> {
    private final ExecutorService executorService;

    private final ObjectInputStream objectInputStream;
    private final ObjectOutputStream objectOutputStream;
    private final HashMap<Class<?>, Queue<Object>> queueHashMap = new HashMap<>();

    private final Socket socket;

    public SocketHandler(ExecutorService executorService, Socket socket) throws IOException {
        this.executorService = executorService;
        this.socket = socket;

        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void perform(Socket obj) {
        //TODO: change it such that the field and the socket are not the same object, remove one or the other
        StreamHandler handler = new StreamHandler(objectInputStream);
        executorService.submit(handler);

        //TODO: handle client commands here
    }

    private class StreamHandler extends Loop {
        private final ObjectInputStream objectInputStream;

        StreamHandler(ObjectInputStream objectInputStream) {
            this.objectInputStream = objectInputStream;
        }

        @Override
        public void loop() throws Exception {
            Object obj = objectInputStream.readObject();
            Class<?> objectClass = obj.getClass();

            if(queueHashMap.containsKey(objectClass)){
                queueHashMap.get(objectClass).add(obj);
            }
            else{
                buildClass(objectClass);

                queueHashMap.get(objectClass).add(obj);
            }
        }

        void buildClass(Class<?> c){
            queueHashMap.put(c, new PriorityQueue<>());
        }
    }
}
