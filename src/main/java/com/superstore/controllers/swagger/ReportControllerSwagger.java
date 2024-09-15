package com.superstore.controllers.swagger;

import com.superstore.dto.ProductAwaitingPaymentReportDto;
import com.superstore.dto.ProductReportDto;
import com.superstore.dto.ProfitReportDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportControllerSwagger {
    @Operation(
            summary = "Получение списка 10 самых продаваемых товаров",
            description = "Показывает список 10 самых продаваемых товаров",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ок"),
                    @ApiResponse(responseCode = "401", description = "Не авторизован")
            }
    )
    @GetMapping("/top-sold-products")
    ResponseEntity<List<ProductReportDto>> getTop10SoldProducts();

    @Operation(
            summary = "Получение списка 10 товаров, отмена заказа по которым произошла чаще всего",
            description = "Показывает списка 10 товаров, отмена заказа по которым произошла чаще всего",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ок"),
                    @ApiResponse(responseCode = "401", description = "Не авторизован")
            }
    )

    @GetMapping("/top-cancelled-products")
    ResponseEntity<List<ProductReportDto>> getTop10CancelledProducts();

    @Operation(
            summary = "Получение отчёта о прибыли за указанный интервал времени",
            description = "Показывает отчёт о прибыли за указанный интервал времени",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ок"),
                    @ApiResponse(responseCode = "401", description = "Не авторизован")
            }
    )

    @GetMapping("/profit")
    ResponseEntity<List<ProfitReportDto>> getProfitReport(@RequestParam String interval,
                                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate);

    @Operation(
            summary = "Получение списка товаров, ожидающих оплаты в течение указанного количества дней",
            description = "Показывает список товаров, ожидающих оплаты в течение указанного количества дней",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Ок"),
                    @ApiResponse(responseCode = "401", description = "Не авторизован")
            }
    )

    @GetMapping("/awaiting-payment")
    ResponseEntity<List<ProductAwaitingPaymentReportDto>> getAwaitingPayment(@RequestParam int days);
}
