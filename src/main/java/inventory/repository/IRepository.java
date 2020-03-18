package inventory.repository;

import javafx.collections.ObservableList;

public interface IRepository<E> {

void addElement(E element);
 ObservableList<E> getAllElements();
 E lookupElement(String search);
 void updateElement(int index, E element);
 void deleteElement(E element);
 int getAutoID();
 void setAutoID(int id);
 void setAllElements(ObservableList<E> list);
}
