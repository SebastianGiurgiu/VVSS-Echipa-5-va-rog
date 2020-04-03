package inventory.service;

import inventory.model.Part;
import inventory.repository.IRepository;
import inventory.repository.PartRepositoryMock;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

class PartServiceTest {

    private IRepository<Part> partRepository;
    private PartService partService;
    private static int minValue = 0;
    private static int maxValue = 5;
    private static int machineID = 3;

    @BeforeEach
    void setup()
    {
        partRepository = new PartRepositoryMock();
        partService = new PartService(partRepository);
    }

    @AfterEach
    void teardown(){
    }

    @Tag("ECP")
    @Test
    void ECP_1_AddPartWithStockValueLessThanMinVal() {
        String partName = "part1";
        double price = 5.0;
        int stock = -1;
        String result = partService.addInhousePart(partName,price,stock,minValue,maxValue,machineID);
        assumeTrue(!result.isEmpty());
    }

    @Tag("ECP")
    @Test
    void ECP_2_AddValidPart() {
        String partName = "part2";
        double price = 5.0;
        int stock = 3;
        String result = partService.addInhousePart(partName,price,stock,minValue,maxValue,machineID);
        assumeTrue(result.isEmpty());

    }

    @Tag("ECP")
    @Test
    void ECP_3_AddPartWithStockValueBiggerThanMaxVal() {
        String partName = "part3";
        double price = 5.0;
        int stock = 50;
        String result = partService.addInhousePart(partName,price,stock,minValue,maxValue,machineID);
        assumeTrue(!result.isEmpty());
    }

    @Tag("BVA")
    @Test
    void BVA_1_AddPartWithStockAsMinVal() {
        String partName = "part1";
        double price = 5.0;
        int stock = 1;
        String result = partService.addInhousePart(partName,price,stock,minValue,maxValue,machineID);
        assumeTrue(result.isEmpty());
    }

    @Tag("BVA")
    @Test
    void BVA_2_AddPartWithStockAsMaxVal() {
        String partName = "part2";
        double price = 5.0;
        int stock = 4;
        String result = partService.addInhousePart(partName,price,stock,minValue,maxValue,machineID);
        assumeTrue(result.isEmpty());

    }

    @Tag("BVA")
    @Test
    void BVA_3_AddPartWithStockValueBiggerThanMaxVal() {
        String partName = "part3";
        double price = 5.0;
        int stock = maxValue + 1;
        String result = partService.addInhousePart(partName,price,stock,minValue,maxValue,machineID);
        assumeTrue(!result.isEmpty());
    }

    @Tag("BVA")
    @Test
    void BVA_4_AddPartWithStockValueLessThanMinVal() {
        String partName = "part4";
        double price = 5.0;
        int stock = minValue - 1 ;
        String result = partService.addInhousePart(partName,price,stock,minValue,maxValue,machineID);
        assumeTrue(!result.isEmpty());
    }

    @Disabled
    @Test
    void whatever()
    {
        assumeTrue(false);
    }

}