package net.ensah.ragspringai.utils;


import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;
import java.util.Map;


@ShellComponent
public class RagShellController {

    private final ChatClient chatClient;

    @Value("classpath:/prompt/prompt.st")
    private Resource prompt;

    private final VectorStore vectorStore;

    public RagShellController(ChatClient.Builder builder, VectorStore vectorStore) {
        this.chatClient = builder.build();
        this.vectorStore = vectorStore;
    }


    @ShellMethod(value = "Chat function", key="q")
    public String chat(@ShellOption(defaultValue="What Is A Design Pattern") String question) {
        List<Document> documents = vectorStore.similaritySearch(question);
        if(documents!=null){
            List<String> context = documents.stream().map(Document::getContent).toList();
            PromptTemplate promptTemplate= new PromptTemplate(prompt);
            Prompt prompt1= promptTemplate.create(Map.of(
                    "context", context,
                    "question",question));
            return chatClient.prompt(prompt1)
                    .call().content();
        }
        return null;
    }
}

