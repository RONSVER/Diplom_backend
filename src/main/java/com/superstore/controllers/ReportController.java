package com.superstore.controllers;

import com.superstore.controllers.swagger.ReportControllerSwagger;
import com.superstore.dto.ProductAwaitingPaymentReportDto;
import com.superstore.dto.ProductReportDto;
import com.superstore.dto.ProfitReportDto;
import com.superstore.services.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller class for handling reports related endpoints.
 */
@RestController
@RequestMapping("/v1/reports")
@AllArgsConstructor
public class ReportController implements ReportControllerSwagger {
    private final ReportService service;

    @GetMapping("/top-sold-products")
    @Override
    public ResponseEntity<List<ProductReportDto>> getTop10SoldProducts() {
        return ResponseEntity.ok(service.getTop10SoldProducts());
    }

    @GetMapping("/top-cancelled-products")
    @Override
    public ResponseEntity<List<ProductReportDto>> getTop10CancelledProducts() {
        return ResponseEntity.ok(service.getTop10CancelledProducts());
    }

    @GetMapping("/profit")
    @Override
    public ResponseEntity<List<ProfitReportDto>> getProfitReport(@RequestParam String interval,
                                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                                 @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(service.getProfitReport(interval, startDate, endDate));
    }

    @GetMapping("/awaiting-payment")
    @Override
    public ResponseEntity<List<ProductAwaitingPaymentReportDto>> getAwaitingPayment(@RequestParam int days) {
        return ResponseEntity.ok(service.finOrdersProcessingBeforeDate(days));
    }
}