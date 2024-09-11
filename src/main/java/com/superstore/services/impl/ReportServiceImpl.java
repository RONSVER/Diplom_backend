package com.superstore.services.impl;

import com.superstore.dto.ProductAwaitingPaymentReportDto;
import com.superstore.dto.ProductReportDto;
import com.superstore.dto.ProfitReportDto;
import com.superstore.repository.OrderItemRepository;
import com.superstore.services.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final OrderItemRepository dao;

    @Override
    public List<ProductReportDto> getTop10SoldProducts() {
        return dao.findTop10SoldProducts();
    }

    @Override
    public List<ProductReportDto> getTop10CancelledProducts() {
        return dao.findTop10CancelledProducts();
    }

    @Override
    public List<ProfitReportDto> getProfitReport(String interval, LocalDateTime startDate, LocalDateTime endDate) {
        List<Object[]> reportData = dao.findProfitByInterval(interval, startDate, endDate);
        // Преобразование в DTO
        return reportData.stream()
                .map(data -> new ProfitReportDto((LocalDateTime) data[0], (BigDecimal) data[1]))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductAwaitingPaymentReportDto> finOrdersProcessingBeforeDate(int days) {
        LocalDateTime dateThreshold = LocalDateTime.now().minusDays(days);

        return dao.findProductsAwaitingPaymentForMoreThanNDays(dateThreshold);
    }
}
