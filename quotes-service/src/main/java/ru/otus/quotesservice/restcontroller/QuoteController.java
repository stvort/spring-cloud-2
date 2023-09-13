package ru.otus.quotesservice.restcontroller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.quotesservice.model.Quote;
import ru.otus.quotesservice.service.QuoteService;


@RequiredArgsConstructor
@RestController
public class QuoteController {
    private static final Logger log = LoggerFactory.getLogger(QuoteController.class);

    private final QuoteService quoteService;

    @GetMapping("/api/quote")
    public Quote randomQuote(){
        log.info("Quote was requested");
        return quoteService.findRandom().orElseThrow();
    }
}
