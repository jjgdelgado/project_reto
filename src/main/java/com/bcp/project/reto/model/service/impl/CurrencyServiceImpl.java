package com.bcp.project.reto.model.service.impl;

import com.bcp.project.reto.model.entity.Currency;
import com.bcp.project.reto.beans.ExchangeRate;
import com.bcp.project.reto.model.repository.CurrencyRepository;
import com.bcp.project.reto.model.service.ICurrencyService;
import io.reactivex.Completable;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class CurrencyServiceImpl implements ICurrencyService {

    @Autowired
    private CurrencyRepository currencyRepository;

    @Override
    @Transactional
    public Completable updateCurrency(Currency currencyRequest) {
        return Completable.create(subscriber -> {
            Optional<Currency> optionalCurrency = currencyRepository.findById(currencyRequest.getIso());
            if (!optionalCurrency.isPresent()) {
                subscriber.onError(new EntityNotFoundException());
            } else {
                Currency currency = optionalCurrency.get();
                currency.setExchange(currencyRequest.getExchange());
                currencyRepository.save(currency);
                subscriber.onComplete();
            }
        });
    }

    @Override
    public Single<ExchangeRate> getExchangeRate(String sourceISO, String targetISO, Double mount) {
        return Single.create(subscriber -> {
            Optional<Currency> optionalSourceCurrency = currencyRepository.findById(sourceISO);
            Optional<Currency> optionalTargetCurrency = currencyRepository.findById(targetISO);

            if (!optionalSourceCurrency.isPresent() || !optionalTargetCurrency.isPresent()) {
                subscriber.onError(new EntityNotFoundException());
            } else {
                Currency sourceCurrency = optionalSourceCurrency.get();
                Currency targetCurrency = optionalTargetCurrency.get();
                ExchangeRate exchangeRate = new ExchangeRate();
                exchangeRate.setMount(mount);
                exchangeRate.setSourceIso(String.format("%s - %s", sourceCurrency.getIso(), sourceCurrency.getName()));
                exchangeRate.setTargetIso(String.format("%s - %s", targetCurrency.getIso(), targetCurrency.getName()));
                exchangeRate.setMountExchange(new BigDecimal((mount / sourceCurrency.getExchange()) * targetCurrency.getExchange()).setScale(2, RoundingMode.HALF_UP).doubleValue());
                exchangeRate.setExchangeRate(new BigDecimal((1 / sourceCurrency.getExchange()) * targetCurrency.getExchange()).setScale(2, RoundingMode.HALF_UP).doubleValue());
                subscriber.onSuccess(exchangeRate);
            }
        });
    }
}
