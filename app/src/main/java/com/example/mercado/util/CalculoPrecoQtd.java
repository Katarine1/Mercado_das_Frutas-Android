package com.example.mercado.util;

import com.example.mercado.impl.CalcularPrecoQtd;

public class CalculoPrecoQtd implements CalcularPrecoQtd {

    @Override
    public double precoQtd(double valor, int quantidade) {
        return valor * quantidade;
    }
}
