package net.ensah.ragspringai.utils;

import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HintRegister implements RuntimeHintsRegistrar {


    @Override
    public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
       hints.resources().registerPattern("*.pdf");
       hints.resources().registerPattern("*.st");
    }
}
