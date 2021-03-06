package com.gustavo.telnet;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 启动服务器后，通过telnet连接，发送命令
 * Created by gustaov on 2019/8/3.
 */
@Slf4j
public class TelnetServer {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(100));
        ServerSocket server = null;
        try {
            server = new ServerSocket(8787);
            log.info("server start up");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        while (true) {
            try {
                log.info("listener accept");
                Socket socket = server.accept();
                executor.execute(new SocketHandler(socket));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
