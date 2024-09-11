package com.superstore.services;

import com.superstore.dto.ProductAwaitingPaymentReportDto;
import com.superstore.dto.ProductReportDto;
import com.superstore.dto.ProfitReportDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportService {
    List<ProductReportDto> getTop10SoldProducts();
    List<ProductReportDto> getTop10CancelledProducts();
    List<ProfitReportDto> getProfitReport(String interval, LocalDateTime startDate, LocalDateTime endDate);
    List<ProductAwaitingPaymentReportDto> finOrdersProcessingBeforeDate(int days);
/*
    List<ProductReport2Dto> finOrdersProcessingBeforeDate(LocalDateTime days);

    */
}
