package com.geo.spring.websocket.geospringwebsocket.handler;



import com.geo.spring.websocket.geospringwebsocket.constant.CacheConstant;
import com.geo.spring.websocket.geospringwebsocket.constant.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;

/**
 * stomp连接处理类
 * Created by earl on 2017/4/19.
 */
@Component
public class PresenceChannelInterceptor extends ChannelInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(PresenceChannelInterceptor.class);

//    @Override
//    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
//        StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
//        // ignore non-STOMP messages like heartbeat messages
//        if(sha.getCommand() == null) {
//            return;
//        }
//        //这里的sessionId和accountId对应HttpSessionIdHandshakeInterceptor拦截器的存放key
////        String sessionId = sha.getSessionAttributes().get(Constants.SESSIONID).toString();
//        String sessionId = "123";
////        String accountId = sha.getSessionAttributes().get(Constants.SKEY_ACCOUNT_ID).toString();
//        String accountId = "456";
//        //判断客户端的连接状态
//        switch(sha.getCommand()) {
//            case CONNECT:
////                connect(sessionId,accountId);
//                break;
//            case CONNECTED:
//                break;
//            case DISCONNECT:
////                disconnect(sessionId,accountId,sha);
//                break;
//            default:
//                break;
//        }
//    }
//
//    //连接成功
//    private void connect(String sessionId,String accountId){
//        logger.debug(" STOMP Connect [sessionId: " + sessionId + "]");
//        //存放至ehcache
//        String cacheName = CacheConstant.WEBSOCKET_ACCOUNT;
//        //若在多个浏览器登录，直接覆盖保存
////        CacheManager.put(cacheName ,cacheName+accountId,sessionId);
//    }
//
//    //断开连接
//    private void disconnect(String sessionId,String accountId, StompHeaderAccessor sha){
//        logger.debug("STOMP Disconnect [sessionId: " + sessionId + "]");
//        sha.getSessionAttributes().remove(Constants.SESSIONID);
//        sha.getSessionAttributes().remove(Constants.SKEY_ACCOUNT_ID);
//        //ehcache移除
//        String cacheName = CacheConstant.WEBSOCKET_ACCOUNT;
////        if (CacheManager.containsKey(cacheName,cacheName+accountId) ){
////            CacheManager.remove(cacheName ,cacheName+accountId);
////        }
//
//    }


    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        System.out.println("11111");
        return message;
    }

    public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
        System.out.println("22222");


        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.wrap(message);
        if(stompHeaderAccessor.getCommand() == null) {
            System.out.println("Command is null!!!");
        }
        //这里的sessionId和accountId对应HttpSessionIdHandshakeInterceptor拦截器的存放key
//        String sessionId = stompHeaderAccessor.getSessionAttributes().get(Constants.SESSIONID).toString();
//        String accountId = stompHeaderAccessor.getSessionAttributes().get(Constants.SKEY_ACCOUNT_ID).toString();

        String sessionId = "123";
        String accountId = "456";
        System.out.println("SessionId:" + sessionId);
        System.out.println("AccountId:" + accountId);
        //判断客户端的连接状态
        switch(stompHeaderAccessor.getCommand()) {
            case CONNECT:
//                connect(sessionId,accountId);
                System.out.println("第一次建立连接！！！");
                break;
            case CONNECTED:
                System.out.println("连接状态为已连接！！！");
                break;
            case DISCONNECT:
                System.out.println("断开连接！！！");
//                disconnect(sessionId,accountId,sha);
                break;
            default:
                break;
        }



    }

    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent,  Exception ex) {
        System.out.println("33333");
    }

    public boolean preReceive(MessageChannel channel) {
        System.out.println("44444");
        return true;
    }

    public Message<?> postReceive(Message<?> message, MessageChannel channel) {
        System.out.println("55555");
        return message;
    }

    public void afterReceiveCompletion(Message<?> message, MessageChannel channel, Exception ex) {
        System.out.println("66666");
    }

}

