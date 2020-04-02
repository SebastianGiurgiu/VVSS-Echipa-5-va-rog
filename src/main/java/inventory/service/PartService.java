package inventory.service;

import inventory.model.InhousePart;
import inventory.model.OutsourcedPart;
import inventory.model.Part;
import inventory.repository.IRepository;
import javafx.collections.ObservableList;

public class PartService {
    private IRepository<Part> repo;

    public PartService(IRepository<Part> parts){
        this.repo = parts;
    }

    public String addInhousePart(String name, double price, int inStock, int min, int  max, int partDynamicValue){
        String emptyString = "";
        String errMsg = Part.isValidPart(name,price,inStock,min,max,emptyString);
        if(errMsg.equals("")){
            InhousePart inhousePart = new InhousePart(repo.getAutoID(), name, price, inStock, min, max, partDynamicValue);
            repo.addElement(inhousePart);
        }
        return errMsg;
    }

    public String addOutsourcePart(String name, double price, int inStock, int min, int  max, String partDynamicValue){
        String emptyString = "";
        String errMsg = Part.isValidPart(name,price,inStock,min,max,emptyString);
        if(errMsg.equals("")){
            OutsourcedPart outsourcedPart = new OutsourcedPart(repo.getAutoID(), name, price, inStock, min, max, partDynamicValue);
            repo.addElement(outsourcedPart);
        }
        return errMsg;
    }

    public ObservableList<Part> getAllParts() {
        return repo.getAllElements();
    }

    public Part lookupPart(String search) {
        return repo.lookupElement(search);
    }

    public void updateInhousePart(int partIndex, int partId, String name, double price, int inStock, int min, int max, int partDynamicValue){
        InhousePart inhousePart = new InhousePart(partId, name, price, inStock, min, max, partDynamicValue);
        repo.updateElement(partIndex, inhousePart);
    }

    public void updateOutsourcedPart(int partIndex, int partId, String name, double price, int inStock, int min, int max, String partDynamicValue){
        OutsourcedPart outsourcedPart = new OutsourcedPart(partId, name, price, inStock, min, max, partDynamicValue);
        repo.updateElement(partIndex, outsourcedPart);
    }

    public void deletePart(Part part){
        repo.deleteElement(part);
    }
}
