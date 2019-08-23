package cn.monitor4all.springbootwebsocketdemo.controller;

import cn.monitor4all.springbootwebsocketdemo.model.ChatMessage;
import cn.monitor4all.springbootwebsocketdemo.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

    @Value("${redis.channel.msgToAll}")
    private String msgToAll;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        try {
            redisTemplate.convertAndSend(msgToAll, JsonUtil.parseObjToJson(chatMessage));
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {

        // Add username in web socket session
        LOGGER.info("User added in Chatroom:" + chatMessage.getSender());
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

}
