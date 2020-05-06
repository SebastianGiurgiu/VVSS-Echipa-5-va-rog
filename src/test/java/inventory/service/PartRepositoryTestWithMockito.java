package inventory.service;

import inventory.model.Part;
import inventory.repository.PartRepository;
import javafx.collections.FXCollections;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;

public class PartRepositoryTestWithMockito {
    private ArrayList<Part> list;
    private PartRepository partRepository;

    @BeforeEach
    void setUp()
    {
        list = new ArrayList<>();
        partRepository = new PartRepository();
        partRepository.setAllElements(FXCollections.observableArrayList(list));
    }

    @Test
    void addElement() {
        Part p1 = mock(Part.class);
        partRepository.addElement(p1);
        assert (partRepository.getAllElements().size() == 1);
    }

    @Test
    void deleteElement() {
        Part p1 = mock(Part.class);
        partRepository.addElement(p1);
        partRepository.deleteElement(p1);
        assert (partRepository.getAllElements().size() == 0);
    }

    @Test
    void lookupElementExisting() {
        assert (partRepository.lookupElement("abc") == null);
    }

    @Test
    void lookupElementNonExisting() {
        Part p1 = mock(Part.class);
        Mockito.when(p1.getName()).thenReturn("p1");
        partRepository.addElement(p1);
        assert (partRepository.lookupElement("p1") == p1);
    }

}
