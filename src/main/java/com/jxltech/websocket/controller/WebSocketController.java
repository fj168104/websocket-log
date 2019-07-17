package com.jxltech.websocket.controller;

import com.jxltech.websocket.server.SocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;

/**
 * websocket
 * 消息推送(个人和广播)
 */
@Controller
@Slf4j
public class WebSocketController {

    @PostConstruct
    private void init(){
        new Thread(()->{
            while (true){
                log.info("*************************************************");
                log.warn("*************************************************");
                log.error("*************************************************");
                log.info("*************************************************");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Autowired
    private SocketServer socketServer;

    /**
     *
     * 客户端页面
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() {

        return "index";
    }

}
