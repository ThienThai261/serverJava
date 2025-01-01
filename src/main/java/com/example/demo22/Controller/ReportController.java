package com.example.demo22.Controller;

import com.example.demo22.Model.InventoryReportDTO;
import com.example.demo22.Model.SalesReportDTO;
import com.example.demo22.Model.UserActivityReportDTO;
import com.example.demo22.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // Lấy danh sách các báo cáo chung
    @GetMapping
    public ResponseEntity<List<String>> getAllReports() {
        List<String> reports = List.of("Doanh thu", "Sản phẩm bán chạy", "Khách hàng thân thiết");
        return ResponseEntity.ok(reports);
    }

    // Báo cáo doanh thu theo thời gian
//    @GetMapping("/sales")
//    public ResponseEntity<SalesReportDTO> getSalesReport(
//            @RequestParam(required = false) String startDate,
//            @RequestParam(required = false) String endDate) {
//        SalesReportDTO report = reportService.getSalesReport(startDate, endDate);
//        return ResponseEntity.ok(report);
//    }

    // Báo cáo tồn kho sản phẩm
    @GetMapping("/inventory")
    public ResponseEntity<List<InventoryReportDTO>> getInventoryReport() {
        List<InventoryReportDTO> inventoryReports = reportService.getInventoryReport();
        return ResponseEntity.ok(inventoryReports);
    }

    // Báo cáo hoạt động của người dùng
    @GetMapping("/users")
    public ResponseEntity<List<UserActivityReportDTO>> getUserActivityReport() {
        List<UserActivityReportDTO> userReports = reportService.getUserActivityReport();
        return ResponseEntity.ok(userReports);
    }
}
