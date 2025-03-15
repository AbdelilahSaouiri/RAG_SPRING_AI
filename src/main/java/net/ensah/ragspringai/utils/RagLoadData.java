package net.ensah.ragspringai.utils;

import jakarta.annotation.PostConstruct;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

@Component
public class RagLoadData {

    @Value("classpath:/pdf/dps.pdf")
    private Resource resource;

    private final JdbcClient jdbcClient;

    private final VectorStore vectorStore;

    public RagLoadData(JdbcClient jdbcClient, VectorStore vectorStore1) {
        this.jdbcClient = jdbcClient;
        this.vectorStore = vectorStore1;

    }

    @PostConstruct
    public void init() {
        try {
            Integer count = jdbcClient.sql("SELECT count(*) FROM vector_store")
                    .query(Integer.class).single();
            if (count == 0) {
                PagePdfDocumentReader reader = new PagePdfDocumentReader(resource);
                TextSplitter splitter = new TokenTextSplitter();
                vectorStore.add(splitter.split(reader.get()));
            }
        } catch (Exception e) {
            System.out.println("Openai connection failed "+ e.getMessage());

        }
    }
}
