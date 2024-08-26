package kbe.breakinpedia.warehouseservice.controller;

import kbe.breakinpedia.warehouseservice.dto.ProductResponse;
import kbe.breakinpedia.warehouseservice.service.WarehouseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/warehouse")
@RequiredArgsConstructor
public class WarehouseController {
    private final WarehouseService warehouseService;

    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProduct() {
        return warehouseService.getAllProduct();
    }

    @GetMapping("/products/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse getProductById(@PathVariable String id) {
        return warehouseService.getProductById(id);
    }

}
