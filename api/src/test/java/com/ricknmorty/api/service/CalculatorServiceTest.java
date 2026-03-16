package com.ricknmorty.api.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * CalculatorServiceTest — TP3 Etapa 6
 *
 * Testes unitários para CalculatorService.
 * Cobre todas as operações incluindo o novo endpoint sqrt.
 */
class CalculatorServiceTest {

    private CalculatorService service;

    @BeforeEach
    void setUp() {
        service = new CalculatorService();
    }

    // ── Testes existentes ──────────────────────────────────────

    @Test
    @DisplayName("Adição: 3 + 2 = 5")
    void testAdd() {
        assertEquals(5.0, service.add(3, 2));
    }

    @Test
    @DisplayName("Subtração: 10 - 4 = 6")
    void testSubtract() {
        assertEquals(6.0, service.subtract(10, 4));
    }

    @Test
    @DisplayName("Multiplicação: 3 * 4 = 12")
    void testMultiply() {
        assertEquals(12.0, service.multiply(3, 4));
    }

    @Test
    @DisplayName("Divisão: 20 / 4 = 5")
    void testDivide() {
        assertEquals(5.0, service.divide(20, 4));
    }

    @Test
    @DisplayName("Divisão por zero deve lançar IllegalArgumentException")
    void testDivideByZero() {
        assertThrows(IllegalArgumentException.class, () -> service.divide(10, 0));
    }

    // ── Novos testes — sqrt (TP3 Etapa 6) ─────────────────────

    @Test
    @DisplayName("sqrt(16) deve retornar 4.0")
    void testSqrtDezesseis() {
        assertEquals(4.0, service.sqrt(16), 0.0001);
    }

    @Test
    @DisplayName("sqrt(0) deve retornar 0.0")
    void testSqrtZero() {
        assertEquals(0.0, service.sqrt(0), 0.0001);
    }

    @Test
    @DisplayName("sqrt(2) deve retornar aproximadamente 1.4142")
    void testSqrtDois() {
        assertEquals(1.4142, service.sqrt(2), 0.0001);
    }

    @Test
    @DisplayName("sqrt(100) deve retornar 10.0")
    void testSqrtCem() {
        assertEquals(10.0, service.sqrt(100), 0.0001);
    }

    @Test
    @DisplayName("sqrt(1) deve retornar 1.0")
    void testSqrtUm() {
        assertEquals(1.0, service.sqrt(1), 0.0001);
    }

    @Test
    @DisplayName("sqrt de número negativo deve lançar IllegalArgumentException")
    void testSqrtNegativo() {
        IllegalArgumentException ex = assertThrows(
            IllegalArgumentException.class,
            () -> service.sqrt(-1)
        );
        assertTrue(ex.getMessage().contains("negativo"));
    }

    @Test
    @DisplayName("sqrt de número negativo grande deve lançar IllegalArgumentException")
    void testSqrtNegativoGrande() {
        assertThrows(IllegalArgumentException.class, () -> service.sqrt(-100));
    }
}
