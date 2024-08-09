package com.example.demo.dto;

import java.math.BigDecimal;

public record ProductResponse(Long id, String name, String description, BigDecimal price) {
}