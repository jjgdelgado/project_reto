package com.bcp.project.reto.model.service;

import com.bcp.project.reto.model.entity.Currency;
import com.bcp.project.reto.beans.ExchangeRate;
import io.reactivex.Completable;
import io.reactivex.Single;

public interface ICurrencyService {

    Completable updateCurrency(Currency currency);

    Single<ExchangeRate> getExchangeRate(String sourceISO, String targetISO, Double mount);
}
