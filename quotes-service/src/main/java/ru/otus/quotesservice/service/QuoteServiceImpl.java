package ru.otus.quotesservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.quotesservice.config.QuotesProps;
import ru.otus.quotesservice.model.Quote;
import ru.otus.quotesservice.repository.QuoteRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.lang.Thread.sleep;

@Slf4j
@RequiredArgsConstructor
@Service
public class QuoteServiceImpl implements QuoteService {

    private final QuotesProps quotesProps;
    private final QuoteRepository repository;

    @Override
    public Optional<Quote> findRandom() {
        List<Quote> quotes = repository.findAll();
        sneakySleep(1);
        Collections.shuffle(quotes);
        return quotes.stream().findFirst().map(q -> {
            q.setQuote(quotesProps.getPrefix() + q.getQuote());
            return q;
        });
    }

    @SuppressWarnings("SameParameterValue")
    private void sneakySleep(long timeoutMills) {
        try {
            sleep(timeoutMills);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        }
    }
}
