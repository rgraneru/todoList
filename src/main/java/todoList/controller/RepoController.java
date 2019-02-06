package todoList.controller;

import com.sun.media.jfxmedia.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import todoList.db.CategoryRepository;
import todoList.db.ElementRepository;
import todoList.model.Category;
import todoList.model.Element;
import todoList.service.TodoService;

import java.util.stream.StreamSupport;

@RestController
public class RepoController {
    @Autowired
    private ElementRepository elementRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    TodoService todoService;

    @PostMapping(path="/addElement")
    public RedirectView addNewElement(@ModelAttribute Element element) {
        boolean illegalNewElement = StreamSupport.stream(elementRepository.findAll().spliterator(), false)
                .anyMatch(element1 -> element1.getCategory().isEmpty() && element.getTextField().equals(element1.getTextField()));

        if (illegalNewElement){
            return todoService.showErrorToUser("Det er ikke lov å legge til to helt like elementer. "+element.getTextField() + " ekisterer fra før uten kategorier");
        }
        else {
            elementRepository.save(element);
            return new RedirectView("/");
        }
    }

    @PostMapping(path = "/addCategory")
    public RedirectView addNewCategory(@ModelAttribute Category category) {
        try {
            categoryRepository.save(category);
        } catch (Exception e) {
            Logger.logMsg(Logger.ERROR, e.getMessage());
            return todoService.showErrorToUser("Du kan ikke legge til den samme kategorien to ganger");
        }
        return new RedirectView("/");
    }

    @GetMapping(path="/removeElement")
    public RedirectView removeElement (@RequestParam Integer id) {
        try {
            elementRepository.deleteById(id);
        }
        catch (Exception e) {
            Logger.logMsg(Logger.ERROR, e.getMessage());
            return todoService.showErrorToUser("Klarte ikke slette element. Mystisk");
        }
        return new RedirectView("/");
    }

    @GetMapping(path="/removeCategory")
    public RedirectView removeCategory (@RequestParam Integer id) {
        try {
            categoryRepository.deleteById(id);
        }
        catch (Exception e) {
            Logger.logMsg(Logger.ERROR, e.getMessage());
            return todoService.showErrorToUser("Klarte ikke slette kategori. Den er i bruk");
        }
        return new RedirectView("/");
    }
    @GetMapping(path="/setCategoryOnElement")
    public RedirectView addSetCategoryOnElement (@RequestParam Integer elementId, @RequestParam Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).get();
        Element element = elementRepository.findById(elementId).get();
        if (element.getCategory().contains(category)) {
            return todoService.showErrorToUser("Det er ikke lov å legge til to helt like elementer. " + element.getTextField() + " eksisterer allerede med kategorien " + category.getName());
        }
        else {
            element.getCategory().add(category);
            elementRepository.save(element);
            return new RedirectView("/");
        }
    }
}
