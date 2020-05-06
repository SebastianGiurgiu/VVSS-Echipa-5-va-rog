package inventory.service;

import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.repository.IRepository;
import inventory.repository.PartRepository;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

public class PartServiceF02 {

    private IRepository<Part> partRepository;
    private PartService partService;
    private final Integer minValue = 0;
    private final Integer maxValue = 5;
    private final Integer machineID = 3;
    private final Double price = 5.0;
    private final Integer stock = -1;

    @BeforeEach
    void setup()
    {
        ArrayList<Part> list = new ArrayList<>();
        partRepository = new PartRepository();
        partRepository.setAllElements(FXCollections.observableArrayList(list));
        partService = new PartService(partRepository);
    }


    @Test
    void lookupPartWhenNameIsEqualWIthSeach() {
        Part p1 = new InhousePart(1,"p1",price,stock,minValue,maxValue,machineID);
        partRepository.addElement(p1);
        assert(partService.lookupPart("p1") == p1);
    }

    @Test
    void lookupPartWhenIdIsEqualWIthSeach() {
        Part p1 = new InhousePart(1,"p1",price,stock,minValue,maxValue,machineID);
        partRepository.addElement(p1);
        assert(partService.lookupPart("1") == p1);
    }

    @Test
    void lookupPartWhenIdAndNameIsEqualWIthSeach() {
        Part p1 = new InhousePart(1,"1",price,stock,minValue,maxValue,machineID);
        partRepository.addElement(p1);
        assert(partService.lookupPart("1") == p1);
    }


    @Test
    void lookupPartWhenIdOrNameIsNotEqualWIthSeach() {
        Part p1 = new InhousePart(1,"p1",price,stock,minValue,maxValue,machineID);
        partRepository.addElement(p1);
        assert(partService.lookupPart("2") == null);
    }


    @Test
    void lookupPartWhenListIsEmpty() {
        assert(partService.lookupPart("2") == null);
    }



}
