package inventory.repository;

import inventory.model.Part;
import inventory.model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.Objects;
import java.util.StringTokenizer;

public class ProductRepository implements IRepository<Product> {

    private static String filename = "data/products.txt";
    private ObservableList<Product> allProducts;
    private int autoProductId;
    private IRepository<Part> partsRepo;

    public ProductRepository(IRepository<Part> parts) {
        this.allProducts = FXCollections.observableArrayList();
        this.autoProductId = 0;
        this.partsRepo = parts;
        readProducts();
    }

    @Override
    public void addElement(Product product) {
        allProducts.add(product);
        writeAll();
    }

    @Override
    public ObservableList<Product> getAllElements() {
        return allProducts;
    }

    @Override
    public Product lookupElement(String search) {
        boolean isFound = false;
        for(Product p: allProducts) {
            if(p.getName().contains(search) || (p.getProductId()+"").equals(search)) return p;
            isFound = true;
        }
        if(!isFound) {
            return new Product(0, null, 0.0, 0, 0, 0, null);
        }
        return null;
    }

    @Override
    public void updateElement(int index, Product product) {
        allProducts.set(index, product);
        writeAll();
    }

    @Override
    public void deleteElement(Product product) {
        allProducts.remove(product);
        writeAll();
    }

    @Override
    public int getAutoID() {
        autoProductId++;
        return autoProductId;
    }

    @Override
    public void setAutoID(int id) {

    }

    @Override
    public void setAllElements(ObservableList<Product> list) {

    }

    private void readProducts(){
        ClassLoader classLoader = InventoryRepository.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(filename)).getFile());

        ObservableList<Product> listP = FXCollections.observableArrayList();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;
            while((line=br.readLine())!=null){
                Product product=getProductFromString(line);
                if (product!=null)
                    listP.add(product);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.allProducts = listP;
    }


    private Product getProductFromString(String line){
        Product product=null;
        if (line==null|| line.equals("")) return null;
        StringTokenizer st=new StringTokenizer(line, ",");
        String type=st.nextToken();
        if (type.equals("P")) {
            int id= Integer.parseInt(st.nextToken());
            this.setAutoID(id);
            String name= st.nextToken();
            double price = Double.parseDouble(st.nextToken());
            int inStock = Integer.parseInt(st.nextToken());
            int minStock = Integer.parseInt(st.nextToken());
            int maxStock = Integer.parseInt(st.nextToken());
            String partIDs=st.nextToken();

            StringTokenizer ids= new StringTokenizer(partIDs,":");
            ObservableList<Part> list= FXCollections.observableArrayList();
            while (ids.hasMoreTokens()) {
                String idP = ids.nextToken();
                Part part = partsRepo.lookupElement(idP);
                if (part != null)
                    list.add(part);
            }
            product = new Product(id, name, price, inStock, minStock, maxStock, list);
            product.setAssociatedParts(list);
        }
        return product;
    }

    private void writeAll() {

        ClassLoader classLoader = InventoryRepository.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(filename)).getFile());

        BufferedWriter bw = null;
        ObservableList<Part> parts=partsRepo.getAllElements();
        ObservableList<Product> products=allProducts;

        try {
            bw = new BufferedWriter(new FileWriter(file));

            for (Product pr:products) {
                String line=pr.toString()+",";
                ObservableList<Part> list= pr.getAssociatedParts();
                int index=0;
                while(index<list.size()-1){
                    line=line+list.get(index).getPartId()+":";
                    index++;
                }
                if (index==list.size()-1)
                    line=line+list.get(index).getPartId();
                bw.write(line);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
