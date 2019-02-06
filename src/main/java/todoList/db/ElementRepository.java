package todoList.db;

import todoList.model.Element;
import org.springframework.data.repository.CrudRepository;

public interface ElementRepository extends CrudRepository<Element, Integer> {

}
