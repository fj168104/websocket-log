package com.jxltech.websocket.server;

import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.InputStream;

@ServerEndpoint(value = "/log")
@Component
public class SocketServer {

	/**
	 * websocket封装的session,信息推送，就是通过它来信息推送
	 */
	private Session session;

	private InputStream inputStream;

	private Process process;

	/**
	 * 用户连接时触发，我们将其添加到
	 * 保存客户端连接信息的socketServers中
	 *
	 * @param session
	 */
	@OnOpen
	public void open(Session session) {

		this.session = session;// 执行tail -f命令
		try {
			process = Runtime.getRuntime().exec("tail -f /app/docker/logs/ws.log");
			inputStream = process.getInputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}


		// 一定要启动新的线程，防止InputStream阻塞处理WebSocket的线程
		TailLogThread thread = new TailLogThread(inputStream, session);
		thread.start();


	}

	/**
	 * 连接关闭触发，通过sessionId来移除
	 * socketServers中客户端连接信息
	 */
	@OnClose
	public void onClose() {
		try {
			this.session.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发生错误时触发
	 *
	 * @param error
	 */
	@OnError
	public void onError(Throwable error) {

	}

}
