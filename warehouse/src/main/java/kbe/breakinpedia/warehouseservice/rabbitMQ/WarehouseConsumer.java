package kbe.breakinpedia.warehouseservice.rabbitMQ;

import kbe.breakinpedia.warehouseservice.dto.ProductListRequest;
import kbe.breakinpedia.warehouseservice.model.Product;
import kbe.breakinpedia.warehouseservice.service.WarehouseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WarehouseConsumer {
    private final WarehouseService warehouseService;

    @Autowired
    public WarehouseConsumer(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    //listen to order service
    @RabbitListener(queues = "warehouse-request-queue")
    public void handleWarehouseRequest(ProductListRequest productListRequest) {
        log.info("Received WarehouseRequest: {}", productListRequest);
        for (Product product : productListRequest.getProductList()) {
            // Check if the request contains product ID or product name
            if (product.getId() != null) {
                // Reduce counts by product ID
                warehouseService.reduceProductCountByProductId(product.getId(), product.getCounts());
            } else {
                log.warn("Invalid WarehouseRequest: Missing product ID");
            }
        }
    }
}
