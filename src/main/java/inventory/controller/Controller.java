package inventory.controller;

//import inventory.service.InventoryService;
import inventory.service.PartService;
import inventory.service.ProductService;

public interface Controller {
    void setServices(PartService partService, ProductService productService);
}
