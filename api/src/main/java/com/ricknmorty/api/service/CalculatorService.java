package com.ricknmorty.api.service;

import org.springframework.stereotype.Service;

/**
 * CalculatorService — TP3 Etapa 6
 *
 * Serviço de calculadora integrado à API Rick & Morty.
 * Implementa operações matemáticas básicas acessíveis via endpoints REST.
 */
@Service
public class CalculatorService {

    public double add(double a, double b) {
        return a + b;
    }

    public double subtract(double a, double b) {
        return a - b;
    }

    public double multiply(double a, double b) {
        return a * b;
    }

    public double divide(double a, double b) {
        if (b == 0) {
            throw new IllegalArgumentException("Divisão por zero não é permitida.");
        }
        return a / b;
    }

    /**
     * Calcula a raiz quadrada de x.
     * TP3 — Etapa 6: nova funcionalidade adicionada ao pipeline.
     *
     * @param x número de entrada (deve ser >= 0)
     * @return raiz quadrada de x
     * @throws IllegalArgumentException se x for negativo
     */
    public double sqrt(double x) {
        if (x < 0) {
            throw new IllegalArgumentException(
                "Raiz quadrada de número negativo não é permitida. Valor recebido: " + x
            );
        }
        return Math.sqrt(x);
    }
}
