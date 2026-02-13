package com.gwh.ai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeepSeekController {

    @Autowired
    private OpenAiChatModel chatModel;

//    @Autowired
//    private ChatModel chatModel1;

    final ChatClient chatClient;

    public DeepSeekController(ChatClient.Builder chatClient) {
        this.chatClient = chatClient.build();
    }

    @GetMapping("/ai/generate")
    public String generate(@RequestParam(value = "message", defaultValue = "hello")
                           String message) {
        String response = chatModel.call(message);
        System.out.println("response : "+response);
        return response;
    }
    //

    @GetMapping("/ai/test")
    public String test(@RequestParam(value = "message", defaultValue = "hello")
                           String message) {
        return chatClient.prompt().user(message).call().content();
    }
}
