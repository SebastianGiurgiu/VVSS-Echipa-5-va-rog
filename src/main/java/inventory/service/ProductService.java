package inventory.service;

import inventory.model.Part;
import inventory.model.Product;
import inventory.repository.IRepository;
import javafx.collections.ObservableList;


public class ProductService {

    private IRepository<Product> repo;

    public ProductService(IRepository<Product> products)
    {
        this.repo = products;
    }

    public ObservableList<Product> getAllProducts() {
        return repo.getAllElements();
    }

    public Product lookupProduct(String search) {
        return repo.lookupElement(search);
    }

    public void updateProduct(int productIndex, int productId, String name, double price, int inStock, int min, int max, ObservableList<Part> addParts){
        Product product = new Product(productId, name, price, inStock, min, max, addParts);
        repo.updateElement(productIndex, product);
    }

    public void deleteProduct(Product product){
        repo.deleteElement(product);
    }

    public void addProduct(String name, double price, int inStock, int min, int  max, ObservableList<Part> addParts){
        Product product = new Product(repo.getAutoID(), name, price, inStock, min, max, addParts);
        repo.addElement(product);
    }

}
