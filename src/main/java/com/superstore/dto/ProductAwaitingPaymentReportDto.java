package com.superstore.dto;

import java.time.LocalDateTime;

//public record ProductReportDto(Long productId, String productName, Long count) {}
public record ProductAwaitingPaymentReportDto(Long productId, String productName, LocalDateTime period) {}
