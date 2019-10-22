package com.process.learn.redis;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author Lynn
 * @since 2019-07-30
 */
public class Collection {

    private Socket socket;
    private String host;
    private int port;
    private InputStream inputStream;
    private OutputStream outputStream;

    public Collection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Collection collection() throws IOException {
        socket = new Socket();
        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();
        return this;
    }

    public Collection sendCommand() throws IOException {
        collection();
        return this;
    }

}
