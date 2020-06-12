package com.bcp.project.reto.beans;

import java.io.Serializable;

public class ExchangeRate implements Serializable {
    private Double mount;
    private Double mountExchange;
    private String sourceIso;
    private String targetIso;
    private Double exchangeRate;

    public Double getMount() {
        return mount;
    }

    public void setMount(Double mount) {
        this.mount = mount;
    }

    public Double getMountExchange() {
        return mountExchange;
    }

    public void setMountExchange(Double mountExchange) {
        this.mountExchange = mountExchange;
    }

    public String getSourceIso() {
        return sourceIso;
    }

    public void setSourceIso(String sourceIso) {
        this.sourceIso = sourceIso;
    }

    public String getTargetIso() {
        return targetIso;
    }

    public void setTargetIso(String targetIso) {
        this.targetIso = targetIso;
    }

    public Double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(Double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
