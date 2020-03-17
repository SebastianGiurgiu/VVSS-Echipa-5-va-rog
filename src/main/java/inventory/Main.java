package inventory;

import inventory.model.Part;
import inventory.model.Product;
import inventory.repository.IRepository;
import inventory.repository.InventoryRepository;
import inventory.repository.PartRepository;
import inventory.repository.ProductRepository;
import inventory.controller.MainScreenController;
import inventory.service.PartService;
import inventory.service.ProductService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        //System.out.println(service.getAllProducts());
        //System.out.println(service.getAllParts());
        IRepository<Part> partRepo = new PartRepository();
        IRepository<Product> productRepo = new ProductRepository(partRepo);

        PartService partService = new PartService(partRepo);
        ProductService productService = new ProductService(productRepo);

        FXMLLoader loader= new FXMLLoader(getClass().getResource("/fxml/MainScreen.fxml"));

        Parent root=loader.load();
        MainScreenController ctrl=loader.getController();
        ctrl.setServices(partService,productService);

        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
