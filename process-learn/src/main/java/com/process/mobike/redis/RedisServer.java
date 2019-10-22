package com.process.mobike.redis;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Lynn
 * @since 2019-07-30
 */
@Slf4j
public class RedisServer {

    public static void main(String[] args) throws IOException {
        ServerSocket socketServer= new ServerSocket(6379);
        Socket accept = socketServer.accept();
        byte[] bytes = new byte[1024];
        accept.getInputStream().read(bytes);
        log.info(new String(bytes));
    }

}
