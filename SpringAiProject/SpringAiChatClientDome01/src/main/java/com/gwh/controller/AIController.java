package com.gwh.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
public class AIController {

    @Autowired
    private ChatClient chatClient;
    @Autowired
    private OllamaChatModel chatModel;

    @GetMapping("/chat")
    public String chat(@RequestParam(value = "msg") String message) {
        return chatClient.prompt().user(message).call().content();
    }


    @GetMapping("/openai")
    public String openai(@RequestParam("msg") String msg) {
        ChatResponse call = chatModel.call(
                new Prompt(msg, ChatOptions.builder().model("deepseek-r1:1.5b").temperature(0.8).build())
        );
        return call.getResult().getOutput().getText();
    }

    @GetMapping(value = "test",produces = "text/html;charset=UTF-8")
    public Flux<String> test(@RequestParam("msg") String msg) {
        return chatModel.stream(new Prompt(  msg)).map(ChatResponse::getResult).mapNotNull(result -> result.getOutput().getText());
    }
}
