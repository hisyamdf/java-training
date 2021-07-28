package com.training.controllers;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.training.data.entity.Category;
import com.training.data.repos.CategoryRepo;
import com.training.dto.CategoryData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private HttpSession session;

    @GetMapping
    public String main(Model model) {
        Iterable<Category> categories = categoryRepo.findAll();
        model.addAttribute("categories", categories);
        return "categories";
    }

    @GetMapping("/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        Optional<Category> categories = categoryRepo.findById(id);
        if (categories.isPresent()) {
            model.addAttribute("categories", categories.get());
        }
        return "detailcategory";
    }

    @GetMapping("/add")
    public String add(Model model) {
        if(session.getAttribute("CURRENT_USER")==null){
            return "redirect:/users/login";
        }
        model.addAttribute("categories", new CategoryData());
        return "inputcategory";
    }

    @PostMapping("/save")
    public String save(CategoryData categoryData) {
        if(session.getAttribute("CURRENT_USER")==null){
            return "redirect:/users/login";
        }
        Category categories = new Category();
        categories.setName(categoryData.getName());
        categoryRepo.save(categories);
        return "redirect:/categories";
    }

    @GetMapping("/remove/{id}")
    public String remove(Model model, @PathVariable("id") Long id) {
        if(session.getAttribute("CURRENT_USER")==null){
            return "redirect:/users/login";
        }
        categoryRepo.deleteById(id);
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        if(session.getAttribute("CURRENT_USER")==null){
            return "redirect:/users/login";
        }
        Optional<Category> categories = categoryRepo.findById(id);
        if(categories.isPresent()) {
            model.addAttribute("categories", categories);
            return "editcategory";
        }
        return "redirect:/categories";
    }

    @PostMapping("/update")
    public String update(Category categories) {
        if(session.getAttribute("CURRENT_USER")==null){
            return "redirect:/users/login";
        }
        categoryRepo.save(categories);
        return "redirect:/categories";
    }

}