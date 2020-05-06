package inventory.service;

import inventory.model.Part;
import inventory.repository.IRepository;
import inventory.repository.PartRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.mockito.Mockito;
import org.junit.jupiter.api.*;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;


public class PartServiceTestIntegrationStep2 {

    private IRepository<Part> partRepository;
    private PartService partService;
    private ArrayList<Part> list;

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
        Part part1 = mock(Part.class);
        Part part2 = mock(Part.class);
        Part part3 = mock(Part.class);
        Mockito.when(part3.getName()).thenReturn("Name");

        // act
        partRepository.addElement(part1);
        partRepository.addElement(part2);
        partRepository.addElement(part3);
        ObservableList<Part> observableList = partService.getAllParts();

        // assert
        int newNrOfParts = observableList.size();
        assert(partService.getAllParts().get(newNrOfParts -1 ).getName().equals("Name"));
    }

    @Test
    void getNumberOfParts() {

        // arrange
        Part part1 = mock(Part.class);
        Part part2 = mock(Part.class);
        Part part3 = mock(Part.class);

        // act
        partRepository.addElement(part1);
        partRepository.addElement(part2);
        partRepository.addElement(part3);

        // assert
        assert (partService.getNumberOfParts().equals(3));
    }

}
