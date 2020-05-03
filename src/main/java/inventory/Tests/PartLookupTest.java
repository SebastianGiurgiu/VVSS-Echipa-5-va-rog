package inventory.Tests;

import inventory.model.Part;
import inventory.repository.IRepository;
import inventory.repository.PartRepository;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assumptions.assumeTrue;


class PartServiceTest {

    private static IRepository<Part> partRepository = new PartRepository();

    @BeforeEach
    void setup()
    {
    }

    @Test
    void lookupExistingPart(){
        String partName = "Screw";

        Part part = partRepository.lookupElement(partName);

        assumeTrue(part != null);


    }

    @Test
    void lookupNonExistingPart()
    {
        String partName = "nonExistentPart";

        Part part = partRepository.lookupElement(partName);

        assumeTrue(part == null);
    }

}