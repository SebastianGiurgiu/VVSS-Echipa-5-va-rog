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

    public void addInhousePart(String name, double price, int inStock, int min, int  max, int partDynamicValue){
        InhousePart inhousePart = new InhousePart(repo.getAutoID(), name, price, inStock, min, max, partDynamicValue);
        repo.addElement(inhousePart);
    }

    public void addOutsourcePart(String name, double price, int inStock, int min, int  max, String partDynamicValue){
        OutsourcedPart outsourcedPart = new OutsourcedPart(repo.getAutoID(), name, price, inStock, min, max, partDynamicValue);
        repo.addElement(outsourcedPart);
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
