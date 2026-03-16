package com.ricknmorty.api.controller;

import com.ricknmorty.api.service.CalculatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * CalculatorController — TP3 Etapa 6
 *
 * Expõe operações matemáticas da CalculatorService via endpoints REST.
 * Base path: /calc
 */
@RestController
@RequestMapping("/calc")
public class CalculatorController {

    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    // GET /calc/add?a=10&b=5  → {"a":10,"b":5,"operation":"add","result":15.0}
    @GetMapping("/add")
    public ResponseEntity<Map<String, Object>> add(
            @RequestParam double a,
            @RequestParam double b) {
        return ok("add", a, b, calculatorService.add(a, b));
    }

    // GET /calc/subtract?a=10&b=3
    @GetMapping("/subtract")
    public ResponseEntity<Map<String, Object>> subtract(
            @RequestParam double a,
            @RequestParam double b) {
        return ok("subtract", a, b, calculatorService.subtract(a, b));
    }

    // GET /calc/multiply?a=4&b=5
    @GetMapping("/multiply")
    public ResponseEntity<Map<String, Object>> multiply(
            @RequestParam double a,
            @RequestParam double b) {
        return ok("multiply", a, b, calculatorService.multiply(a, b));
    }

    // GET /calc/divide?a=20&b=4
    @GetMapping("/divide")
    public ResponseEntity<Map<String, Object>> divide(
            @RequestParam double a,
            @RequestParam double b) {
        try {
            return ok("divide", a, b, calculatorService.divide(a, b));
        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new LinkedHashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * GET /calc/sqrt?x=16
     * Retorna a raiz quadrada do número informado.
     * TP3 — Etapa 6: novo endpoint adicionado.
     *
     * Exemplo de resposta:
     *   {"x": 16.0, "operation": "sqrt", "result": 4.0}
     *
     * Erro (x negativo):
     *   HTTP 400 {"error": "Raiz quadrada de número negativo não é permitida. Valor recebido: -1.0"}
     */
    @GetMapping("/sqrt")
    public ResponseEntity<Map<String, Object>> sqrt(@RequestParam double x) {
        try {
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("x", x);
            response.put("operation", "sqrt");
            response.put("result", calculatorService.sqrt(x));
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, Object> error = new LinkedHashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    // Utilitário para montar resposta padrão de operações binárias
    private ResponseEntity<Map<String, Object>> ok(
            String operation, double a, double b, double result) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("a", a);
        response.put("b", b);
        response.put("operation", operation);
        response.put("result", result);
        return ResponseEntity.ok(response);
    }
}
