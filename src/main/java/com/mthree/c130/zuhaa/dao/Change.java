package com.mthree.c130.zuhaa.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum Change {
    TWENTY_POUND(new BigDecimal("20.00")),
    TEN_POUND(new BigDecimal("10.00")),
    FIVE_POUND(new BigDecimal("5.00")),
    TWO_POUND(new BigDecimal("2.00")),
    ONE_POUND(new BigDecimal("1.00")),
    FIFTY_PENCE(new BigDecimal("0.50")),
    TWENTY_PENCE(new BigDecimal("0.20")),
    TEN_PENCE(new BigDecimal("0.10")),
    FIVE_PENCE(new BigDecimal("0.05")),
    TWO_PENCE(new BigDecimal("0.02")),
    ONE_PENCE(new BigDecimal("0.01"));


    public final BigDecimal value;

    Change(BigDecimal value) {
        this.value = value;
    }

    Map<Change, BigDecimal> changeCount = new HashMap<>();
}