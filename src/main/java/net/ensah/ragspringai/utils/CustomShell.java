package net.ensah.ragspringai.utils;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;

@Configuration
public class CustomShell implements PromptProvider {
    @Override
    public AttributedString getPrompt() {
        return new AttributedString(
                "Abdelilah:> ",
                AttributedStyle.DEFAULT.foreground(AttributedStyle.YELLOW)
                        .bold()
                        .italic()
        );
    }
}
