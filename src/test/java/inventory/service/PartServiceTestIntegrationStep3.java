package inventory.service;

import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.repository.IRepository;
import inventory.repository.PartRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;

import java.util.ArrayList;


public class PartServiceTestIntegrationStep3 {

    private IRepository<Part> partRepository;
    private PartService partService;
    private ArrayList<Part> list;
    private final Integer minValue = 0;
    private final Integer maxValue = 5;
    private final Integer machineID = 3;
    private final Double price = 5.0;
    private final Integer stock = -1;

    @BeforeEach
    void setup()
    {
        list = new ArrayList<>();
        partRepository = new PartRepository();
        partRepository.setAllElements(FXCollections.observableArrayList(list));
        partService = new PartService(partRepository);
    }

    @Test
    void getAllParts() {

        // arrange
        ObservableList<Part> observableList = partService.getAllParts();
        int nrOfParts = observableList.size();

        Part p1 = new InhousePart(1,"p1",price,stock,minValue,maxValue,machineID);
        Part p2 = new InhousePart(2,"p2",price,stock,minValue,maxValue,machineID);
        Part p3 = new InhousePart(3,"p3",price,stock,minValue,maxValue,machineID);

        // act
        partRepository.addElement(p1);
        partRepository.addElement(p2);
        partRepository.addElement(p3);

        // assert
        assert(partService.getAllParts().get(nrOfParts).getName().equals("p1"));
        assert(partService.getAllParts().get(nrOfParts + 1).getName().equals("p2"));
        assert(partService.getAllParts().get(nrOfParts + 2).getName().equals("p3"));
    }


    @Test
    void lookupPart() {

        // arrange
        Part p1 = new InhousePart(1,"p1",price,stock,minValue,maxValue,machineID);
        Part p2 = new InhousePart(2,"p2",price,stock,minValue,maxValue,machineID);
        Part p3 = new InhousePart(3,"p3",price,stock,minValue,maxValue,machineID);

        // act
        partRepository.addElement(p1);
        partRepository.addElement(p2);
        partRepository.addElement(p3);

        // assert
        assert(partService.lookupPart("p1") == p1);
        assert(partService.lookupPart("2") == p2);
        assert(partService.lookupPart("4") == null);
        assert(partService.lookupPart("p4") == null);
    }

}
