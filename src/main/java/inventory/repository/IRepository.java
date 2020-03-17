package inventory.repository;

import javafx.collections.ObservableList;

public interface IRepository<Element> {

void addElement(Element element);
 ObservableList<Element> getAllElements();
 Element lookupElement(String search);
 void updateElement(int index, Element element);
 void deleteElement(Element element);
 int getAutoID();
 void setAutoID(int id);
 void setAllElements(ObservableList<Element> list);
}
