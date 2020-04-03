package inventory.repository;

import inventory.model.Part;
import javafx.collections.ObservableList;

public class PartRepositoryMock implements IRepository<Part>{

    private static int autoid = 0;


    @Override
    public void addElement(Part element) {

    }

    @Override
    public ObservableList<Part> getAllElements() {
        return null;
    }

    @Override
    public Part lookupElement(String search) {
        return null;
    }

    @Override
    public void updateElement(int index, Part element) {

    }

    @Override
    public void deleteElement(Part element) {

    }

    @Override
    public int getAutoID() {
        return autoid++;
    }

    @Override
    public void setAutoID(int id) {

    }

    @Override
    public void setAllElements(ObservableList<Part> list) {

    }
}
