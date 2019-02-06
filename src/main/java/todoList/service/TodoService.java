package todoList.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriUtils;
import todoList.db.CategoryRepository;
import todoList.db.ElementRepository;
import todoList.model.Category;
import todoList.model.Element;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public class TodoService {
    @Autowired
    private ElementRepository elementRepository;

    @Autowired
    CategoryRepository categoryRepository;

    public List<Element> findAllTodoElements(Integer categoryFilterId) {
        Iterable<Element> all = elementRepository.findAll();
        return StreamSupport.stream(all.spliterator(), false)
                .filter(element -> element.hasCategory(categoryFilterId)).collect(Collectors.toList());

    }

    public Iterable<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public RedirectView showErrorToUser(String message) {
        return new RedirectView(UriUtils.encodeQuery("/?errorMessage=" + message, "UTF-8"));
    }
}
