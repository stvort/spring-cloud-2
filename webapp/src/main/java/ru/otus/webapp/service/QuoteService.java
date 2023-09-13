package ru.otus.webapp.service;

import ru.otus.webapp.dto.QuoteDto;

public interface QuoteService {
    String RANDOM_QUOTE_CACHE_NAME = "randomQuote";

    QuoteDto findRandom();
}
