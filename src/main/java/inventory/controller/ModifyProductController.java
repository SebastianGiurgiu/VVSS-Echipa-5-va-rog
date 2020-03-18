package inventory.controller;

import inventory.model.Part;
import inventory.model.Product;
import inventory.service.PartService;
import inventory.service.ProductService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static inventory.controller.MainScreenController.getModifyProductIndex;


public class ModifyProductController implements Initializable, Controller {
    
    // Declare fields
    private Stage stage;
    private Parent scene;
    private ObservableList<Part> parts = FXCollections.observableArrayList();
    private int productId;
    private int productIndex = getModifyProductIndex();

    private PartService partService;
    private ProductService productService;


    @FXML
    private TextField minTxt;

    @FXML
    private TextField maxTxt;

    @FXML
    private TextField productIdTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField inventoryTxt;

    @FXML
    private TextField priceTxt;

    @FXML
    private TextField productSearchTxt;

    @FXML
    private TableView<Part> addProductTableView;

    @FXML
    private TableColumn<Part, Integer> addProductIdCol;

    @FXML
    private TableColumn<Part, String> addProductNameCol;

    @FXML
    private TableColumn<Part, Integer> addProductInventoryCol;

    @FXML
    private TableColumn<Part, Double> addProductPriceCol;

    @FXML
    private TableView<Part> deleteProductTableView;

    @FXML
    private TableColumn<Part, Integer> deleteProductIdCol;

    @FXML
    private TableColumn<Part, String> deleteProductNameCol;

    @FXML
    private TableColumn<Part, Integer> deleteProductInventoryCol;

    @FXML
    private TableColumn<Part, Double> deleteProductPriceCol;

    public ModifyProductController(){}

    public void setServices(PartService partService, ProductService productService){
        this.partService = partService;
        this.productService = productService;
        fillWithData();
    }

    private void fillWithData(){
        // Populate add product table view
        addProductTableView.setItems(partService.getAllParts());

        addProductIdCol.setCellValueFactory(new PropertyValueFactory<>("partId"));
        addProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        addProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Populate modify product form
        Product product = productService.getAllProducts().get(productIndex);

        productId = productService.getAllProducts().get(productIndex).getProductId();
        productIdTxt.setText(Integer.toString(product.getProductId()));
        nameTxt.setText(product.getName());
        inventoryTxt.setText(Integer.toString(product.getInStock()));
        priceTxt.setText(Double.toString(product.getPrice()));
        maxTxt.setText(Integer.toString(product.getMax()));
        minTxt.setText(Integer.toString(product.getMin()));

        // Populate delete product table view
        parts = product.getAssociatedParts();
        updateDeleteProductTableView();
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }


    /**
     * Method to add to button handler to switch to scene passed as source
     * @param event
     * @param source
     * @throws IOException
     */
    @FXML
    private void displayScene(ActionEvent event, String source) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        FXMLLoader loader= new FXMLLoader(getClass().getResource(source));
        scene = loader.load();
        Controller ctrl=loader.getController();
        ctrl.setServices(partService,productService);
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    /**
     * Method to add values of parts to the bottom table view of the scene.
     */
    public void updateDeleteProductTableView() {
        deleteProductTableView.setItems(parts);
        
        deleteProductIdCol.setCellValueFactory(new PropertyValueFactory<>("partId"));
        deleteProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        deleteProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("inStock"));
        deleteProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Ask user for confirmation before deleting selected part from current product.
     * @param event 
     */
    @FXML
    void handleDeleteProduct(ActionEvent event) {
        Part part = deleteProductTableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm Part Deletion!");
        alert.setContentText("Are you sure you want to delete part " + part.getName() + " from parts?");
        Optional<ButtonType> result = alert.showAndWait();

        if(result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                System.out.println("Part deleted.");
                parts.remove(part);
            } else {
                System.out.println("Canceled part deletion.");
            }
        }
    }
    
    /**
     * Add selected part from top table view to bottom table view in order to create
     * new product
     * @param event 
     */
    @FXML
    void handleAddProduct(ActionEvent event) {
        Part part = addProductTableView.getSelectionModel().getSelectedItem();
        parts.add(part);
        updateDeleteProductTableView();   
    }

    /**
     * Ask user for confirmation before canceling product modification
     * and switching scene to Main Screen
     * @param event
     * @throws IOException
     */
    @FXML
    void handleCancelProduct(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation Needed");
        alert.setHeaderText("Confirm Cancelation");
        alert.setContentText("Are you sure you want to cancel modifying product?");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent()) {
            if (result.get() == ButtonType.OK) {
                System.out.println("Ok selected. Product modification canceled.");
                displayScene(event, "/fxml/MainScreen.fxml");
            } else {
                System.out.println("Cancel clicked.");
            }
        }
    }

    /**
     * Validate given product parameters.  If valid, update product in inventory,
     * else give user an error message explaining why the product is invalid.
     * @param event
     * @throws IOException
     */
    @FXML
    void handleSaveProduct(ActionEvent event) throws IOException {
        String name = nameTxt.getText();
        String price = priceTxt.getText();
        String inStock = inventoryTxt.getText();
        String min = minTxt.getText();
        String max = maxTxt.getText();
        String errorMessage = "";
        
        try {
            errorMessage = Product.isValidProduct(name, Double.parseDouble(price), Integer.parseInt(inStock), Integer.parseInt(min), Integer.parseInt(max), parts, errorMessage);
            if(errorMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Adding Part!");
                alert.setHeaderText("Error!");
                alert.setContentText(errorMessage);
                alert.showAndWait();
            } else {
                productService.updateProduct(productIndex, productId, name, Double.parseDouble(price), Integer.parseInt(inStock), Integer.parseInt(min), Integer.parseInt(max), parts);
                displayScene(event, "/fxml/MainScreen.fxml");
            }
        } catch (NumberFormatException e) {
            System.out.println("Form contains blank field.");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Adding Product!");
            alert.setHeaderText("Error!");
            alert.setContentText("Form contains blank field.");
            alert.showAndWait();
        }
    }

    /**
     * Gets search text and inputs into lookupAssociatedPart method to highlight desired part
     * @param event 
     */
    @FXML
    void handleSearchProduct(ActionEvent event) {
        String x = productSearchTxt.getText();
        addProductTableView.getSelectionModel().select(partService.lookupPart(x));
    }


}
