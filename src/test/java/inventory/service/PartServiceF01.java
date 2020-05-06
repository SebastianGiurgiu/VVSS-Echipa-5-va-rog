package inventory.service;

import inventory.model.Part;
import inventory.repository.IRepository;
import inventory.repository.PartRepositoryMock;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class PartServiceF01 {

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

    @Tag("ECP")
    @Test
    void ECP_4_AddPartWithPriceLessThanZero() {
        String partName = "part4";
        double price = -1.0;
        int stock = 1;
        String result = partService.addInhousePart(partName,price,stock,minValue,maxValue,machineID);
        assumeTrue(!result.isEmpty());
    }

    @Tag("ECP")
    @Test
    void ECP_5_AddPartWithEmptyName() {
        String partName = "";
        double price = 5.0;
        int stock = 3;
        String result = partService.addInhousePart(partName,price,stock,minValue,maxValue,machineID);
        assumeTrue(!result.isEmpty());
    }


    @Tag("BVA")
    @Test
    void BVA_1_AddPartWithStockBiggerThanMinVal() {
        String partName = "part1";
        double price = 5.0;
        int stock = 1;
        String result = partService.addInhousePart(partName,price,stock,minValue,maxValue,machineID);
        assumeTrue(result.isEmpty());
    }

    @Tag("BVA")
    @Test
    void BVA_2_AddPartWithStockSmallerThanMaxVal() {
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
        int stock = maxValue+1;
        String result = partService.addInhousePart(partName,price,stock,minValue,maxValue,machineID);
        assumeTrue(!result.isEmpty());
    }

    @Tag("BVA")
    @Test
    void BVA_4_AddPartWithStockValueLessThanMinVal() {
        String partName = "part4";
        double price = 5.0;
        int stock = minValue ;
        String result = partService.addInhousePart(partName,price,stock,minValue,maxValue,machineID);
        assumeTrue(!result.isEmpty());
    }



    @Tag("BVA")
    @Test
    void BVA_5_AddPartWithPriceLessThanZero() {
        String partName = "part5";
        double price = -1.0;
        int stock = 3;
        String result = partService.addInhousePart(partName,price,stock,minValue,maxValue,machineID);
        assumeTrue(!result.isEmpty());
    }

    @Tag("BVA")
    @Test
    void BVA_6_AddPartWithPriceCloseToZero() {
        String partName = "part6";
        double price = 0.1;
        int stock = 3 ;
        String result = partService.addInhousePart(partName,price,stock,minValue,maxValue,machineID);
        assumeTrue(result.isEmpty());
    }

    @Tag("BVA")
    @Test
    void BVA_7_AddPartWithPriceSlightlyBiggerThanZero() {
        String partName = "part7";
        double price = 0.2;
        int stock = 3 ;
        String result = partService.addInhousePart(partName,price,stock,minValue,maxValue,machineID);
        assumeTrue(result.isEmpty());
    }

    @Tag("BVA")
    @Test
    void BVA_8_AddPartWithEmptyName() {
        String partName = "";
        double price = 1.0;
        int stock = 3;
        String result = partService.addInhousePart(partName,price,stock,minValue,maxValue,machineID);
        assumeTrue(!result.isEmpty());
    }

    @Tag("BVA")
    @Test
    void BVA_9_AddPartWithNameLengthBeingOne() {
        String partName = "1";
        double price = 5.0;
        int stock = 3 ;
        String result = partService.addInhousePart(partName,price,stock,minValue,maxValue,machineID);
        assumeTrue(result.isEmpty());
    }

}
