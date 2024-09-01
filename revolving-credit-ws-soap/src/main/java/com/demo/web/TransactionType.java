package com.demo.web;

/**
 *
 * @author Leu A. Manuel
 * @see https://github.com/Leupesquisa
 */
public enum TransactionType {
    MMI(1),
    OTC(2);

    private final int value;

    TransactionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}