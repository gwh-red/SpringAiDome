package com.gwh.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatDeepSeekController {

    @Autowired
    private OllamaChatModel ollamaChatModel;

    private final ChatClient chatClient;

    public ChatDeepSeekController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping("/ai/test01")
    public String test01(@RequestParam(value = "message", defaultValue = "hello")
                           String message) {
        String response = ollamaChatModel.call(message);
        System.out.println("response : "+response);
        return response;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam(value = "msg",defaultValue = "给我讲个笑话")
                       String message) {
        //prompt:提示词
        return chatClient.prompt()
                //用户输入的信息
                .user(message)
                //请求大模型
                .call()
                //返回文本
                .content();
    }
}
