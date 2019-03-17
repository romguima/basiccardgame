package org.romguima.cardgame.configuration;


import org.romguima.cardgame.controllers.view.DeckView;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CardGamesConfiguration implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new DeckViewConverter());
    }

    private class DeckViewConverter implements Converter<String, DeckView> {
        @Override
        public DeckView convert(String s) {
            DeckView deckView = DeckView.fromString(s);

            if (deckView == null) {
                deckView = DeckView.valueOf(s);
            }
            return deckView;
        }
    }
}


