package ru.otus.webapp.service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import ru.otus.webapp.dto.QuoteDto;
import ru.otus.webapp.exceptions.RandomQuoteApiException;
import ru.otus.webapp.repository.QuoteRepository;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings("unused")
@Slf4j
@RequiredArgsConstructor
@Service
public class QuoteServiceImpl implements QuoteService {
    private final AtomicInteger counter = new AtomicInteger(1);


    private final QuoteRepository repository;
    private final CacheManager cacheManager;

    //@Bulkhead(name = "randomQuoteApi", fallbackMethod = "findInCache")
    //@RateLimiter(name = "randomQuoteApi", fallbackMethod = "findInCache")
    //@Retry(name = "randomQuoteApi", fallbackMethod = "findInCache")
    @CircuitBreaker(name = "randomQuoteApi", fallbackMethod = "findInCache")
    @Override
    public QuoteDto findRandom() {
        log.info("findRandom. Thread: {}", Thread.currentThread().getName());

        if (dataExistsInCache()) {
            throw new RandomQuoteApiException("API REQUEST FAILED!!!!!");
        }

        return repository.randomQuote();
    }

    public QuoteDto findInCache(Throwable e) {
        log.info("findInCache. Thread: {},  exception: {}", Thread.currentThread().getName(), e.getMessage());

        return getDataFromCache();
    }

    private QuoteDto getDataFromCache() {
        //noinspection UnnecessaryLocalVariable
        var data = (QuoteDto) Optional.ofNullable(cacheManager.getCache(RANDOM_QUOTE_CACHE_NAME))
                .map(cache -> cache.get(RANDOM_QUOTE_CACHE_NAME))
                .map(Cache.ValueWrapper::get)
                .orElse(new QuoteDto("Отсутствующая цитата"));
        return data;
    }

    private boolean dataExistsInCache() {
        return Optional.ofNullable(cacheManager.getCache(RANDOM_QUOTE_CACHE_NAME))
                .map(cache -> cache.get(RANDOM_QUOTE_CACHE_NAME))
                .map(Cache.ValueWrapper::get)
                .isPresent();
    }

}
