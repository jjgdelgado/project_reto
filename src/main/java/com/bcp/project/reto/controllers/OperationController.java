package com.bcp.project.reto.controllers;

import com.bcp.project.reto.beans.ExchangeRate;
import com.bcp.project.reto.model.entity.Currency;
import com.bcp.project.reto.model.service.ICurrencyService;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class OperationController {
    @Autowired
    private ICurrencyService currencyService;

    @GetMapping
    public String index() {
        return "API Rest BCP";
    }

    @GetMapping("/exchange/{sourceISO}/{targetISO}/{mount}")
    public Single<ResponseEntity<ExchangeRate>> exchange(@PathVariable String sourceISO, @PathVariable String targetISO, @PathVariable Double mount) {
        return currencyService.getExchangeRate(sourceISO, targetISO, mount)
                .subscribeOn(Schedulers.io())
                .map(er -> ResponseEntity.created(URI.create("/")).body(er));
    }

    @PostMapping("/updateExchangeRate")
    public Single<ResponseEntity<Completable>> updateExchangeRate(@RequestBody Currency currency) {
        return currencyService.updateCurrency(currency)
                .subscribeOn(Schedulers.io()).toSingle(() -> ResponseEntity.ok(Completable.complete()));
    }
}
