package inventory.service;

import inventory.model.InhousePart;
import inventory.model.Part;
import inventory.repository.IRepository;
import inventory.repository.PartRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.Mockito.mock;

public class PartServieTestWithMockConstructor {

    private IRepository<Part> partRepository;
    private PartService partService;
    private final Integer minValue = 0;
    private final Integer maxValue = 5;
    private final Integer machineID = 3;
    private final String partName = "part1";
    private final  Double price = 5.0;

    @BeforeEach
    void setup()
    {
        partRepository = mock(PartRepository.class);
        partService = new PartService(partRepository);
    }

    @Test
    void AddPartWithStockValueLessThanMinVal() {
        int stock = -1;
        String result = partService.addInhousePart(partName,price,stock,minValue,maxValue,machineID);
        assumeTrue(!result.isEmpty());
    }

    @Test
    void AddValidPart() {
        int stock = 3;
        String result = partService.addInhousePart(partName,price,stock,minValue,maxValue,machineID);
        assumeTrue(result.isEmpty());
    }


}
