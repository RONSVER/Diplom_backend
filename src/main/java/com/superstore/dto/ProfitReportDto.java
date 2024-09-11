package com.superstore.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProfitReportDto(LocalDateTime period, BigDecimal totalProfit) {}
