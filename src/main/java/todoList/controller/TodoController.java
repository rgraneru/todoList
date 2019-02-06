package todoList.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import todoList.model.Category;
import todoList.model.Element;
import todoList.service.TodoService;

@Controller
public class TodoController {

    @Autowired
    TodoService todoService;

    @GetMapping("/")
    public String availability(Model model, @RequestParam(required=false) Integer categoryFilterId, @RequestParam(required=false) String errorMessage) {
        model.addAttribute("elements", todoService.findAllTodoElements(categoryFilterId));
        model.addAttribute("categories", todoService.findAllCategories());
        model.addAttribute("category", new Category());
        model.addAttribute("element", new Element());
        model.addAttribute("errorMessage", errorMessage);
        return "todoList";
    }
}