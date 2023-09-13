package ru.otus.webapp.restcontroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.webapp.dto.QuoteDto;
import ru.otus.webapp.service.QuoteService;

import static ru.otus.webapp.service.QuoteService.RANDOM_QUOTE_CACHE_NAME;

@SuppressWarnings("unused")
@Slf4j
@RestController
public class QuoteController {

    private final QuoteService quoteService;

    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @CachePut(key = "#root.methodName", value = RANDOM_QUOTE_CACHE_NAME)
    @GetMapping("/api/quote")
    public QuoteDto randomQuote() {
        log.info("QuoteController#randomQuote. Thread: {}", Thread.currentThread().getName());
        return quoteService.findRandom();
    }
}
