package cn.monitor4all.springbootwebsocketdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

@Component
public class RedisSubscriber extends MessageListenerAdapter {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public void onMessage(Message message, byte[] bytes) {
        byte[] body = message.getBody();
        byte[] channel = message.getChannel();
        String msg = redisTemplate.getStringSerializer().deserialize(body);
        String topic = redisTemplate.getStringSerializer().deserialize(channel);
        System.out.println("监听到topic为" + topic + "的消息：" + msg);
    }
}
