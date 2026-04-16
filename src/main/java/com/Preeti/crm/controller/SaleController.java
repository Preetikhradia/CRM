package com.Preeti.crm.controller;

import com.Preeti.crm.model.Sale;
import com.Preeti.crm.repository.SaleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
@CrossOrigin(origins = "http://localhost:5173")
public class SaleController {

    private final SaleRepository saleRepository;

    public SaleController(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @GetMapping
    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    @PostMapping
    public Sale createSale(@RequestBody Sale sale) {
        if (sale.getDate() == null) {
            sale.setDate(LocalDate.now());
        }
        return saleRepository.save(sale);
    }
}