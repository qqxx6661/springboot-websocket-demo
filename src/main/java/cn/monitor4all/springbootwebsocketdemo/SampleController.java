package cn.monitor4all.springbootwebsocketdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * redis生产者测试
     * @param data
     * @return
     */
    @GetMapping("/send1")
    String send1(String data) {
        redisTemplate.convertAndSend("msg.toAll", data);
        return "success";
    }
    /**
     * redis生产者测试
     * @param data
     * @return
     */
    @GetMapping("/send2")
    String send2(String data) {
        redisTemplate.convertAndSend("msg.toCid", data);
        return "success";
    }


}
