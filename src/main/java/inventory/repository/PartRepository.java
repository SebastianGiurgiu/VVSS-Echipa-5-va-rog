package inventory.repository;

import inventory.model.InhousePart;
import inventory.model.OutsourcedPart;
import inventory.model.Part;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.Objects;
import java.util.StringTokenizer;

public class PartRepository implements IRepository<Part> {

    private static String filename = "data/parts.txt";
    private ObservableList<Part> allParts;
    private int autoPartId;

    public PartRepository(){
        this.allParts = FXCollections.observableArrayList();
        this.autoPartId = 0;
        readParts();
    }

    @Override
    public void addElement(Part part) {
        allParts.add(part);
        writeAll();
    }

    @Override
    public ObservableList<Part> getAllElements() {
        return allParts;
    }

    @Override
    public Part lookupElement(String search) {
        for(Part p:allParts) {
            if(p.getName().contains(search) || (p.getPartId()+"").equals(search)) return p;
        }
        return null;
    }

    @Override
    public void updateElement(int index, Part part) {
        allParts.set(index, part);
        writeAll();
    }

    @Override
    public void deleteElement(Part part) {
        allParts.remove(part);
        writeAll();
    }

    @Override
    public int getAutoID() {
        autoPartId++;
        return autoPartId;
    }

    @Override
    public void setAutoID(int id) {
        autoPartId=id;
    }

    @Override
    public void setAllElements(ObservableList<Part> list) {
            allParts=list;
    }

    private void readParts(){
        ClassLoader classLoader = PartRepository.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(filename)).getFile());
        ObservableList<Part> listP = FXCollections.observableArrayList();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line = null;
            while((line=br.readLine())!=null){
                Part part=getPartFromString(line);
                if (part!=null)
                    listP.add(part);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setAllElements(listP);
    }

    private void writeAll() {

        ClassLoader classLoader = PartRepository.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource(filename)).getFile());

        BufferedWriter bw = null;

        try {
            bw = new BufferedWriter(new FileWriter(file));

            for( Part part : allParts)
            {
                String line = part.toString();
                bw.write(line);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Part getPartFromString(String line){
        Part item=null;

        if (line==null|| line.equals("")) return null;
        try {
            StringTokenizer st = new StringTokenizer(line, ",");
            String type = st.nextToken();
            if (type.equals("I")) {
                int id = Integer.parseInt(st.nextToken());
                setAutoID(id);
                String name = st.nextToken();
                double price = Double.parseDouble(st.nextToken());
                int inStock = Integer.parseInt(st.nextToken());
                int minStock = Integer.parseInt(st.nextToken());
                int maxStock = Integer.parseInt(st.nextToken());
                int idMachine = Integer.parseInt(st.nextToken());
                item = new InhousePart(id, name, price, inStock, minStock, maxStock, idMachine);
            }
            if (type.equals("O")) {
                int id = Integer.parseInt(st.nextToken());
                setAutoID(id);
                String name = st.nextToken();
                double price = Double.parseDouble(st.nextToken());
                int inStock = Integer.parseInt(st.nextToken());
                int minStock = Integer.parseInt(st.nextToken());
                int maxStock = Integer.parseInt(st.nextToken());
                String company = st.nextToken();
                item = new OutsourcedPart(id, name, price, inStock, minStock, maxStock, company);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return item;
    }
}
