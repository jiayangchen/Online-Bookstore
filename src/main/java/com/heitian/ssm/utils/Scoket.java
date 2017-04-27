package com.heitian.ssm.utils;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
//注意此访问地址格式如:"ws://"+ window.location.host+"/${pageContext.request.contextPath}/game"是ws开头的,而不是以http:开头的.
@ServerEndpoint(value = "/game")
public class Scoket {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    static Map<String,Session> sessionMap = new Hashtable<String,Session>();

    @OnOpen
    public void onOpen(Session session) {
        sessionMap.put(session.getId(), session);
    }

    @OnMessage
    public void onMessage(String unscrambledWord, Session session) {
        broadcastAll("message",unscrambledWord);
    }
    /**
     * 广播给所有人
     * @param message
     */
    public static void broadcastAll(String type,String message){
        Set<Map.Entry<String,Session>> set = sessionMap.entrySet();
        for(Map.Entry<String,Session> i: set){
            try {
                i.getValue().getBasicRemote().sendText("{type:'"+type+"',text:'"+message+"'}");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) {
        sessionMap.remove(session.getId());
        logger.info(String.format("Session %s closed because of %s", session.getId(), closeReason));
    }

    @OnError
    public void error(Session session, java.lang.Throwable throwable){
        sessionMap.remove(session.getId());
        System.err.println("session "+session.getId()+" error:"+throwable);
    }
}
