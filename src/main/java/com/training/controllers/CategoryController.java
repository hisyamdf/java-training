package com.training.controllers;

import java.util.Optional;

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

    @GetMapping
    public String main(Model model) {
        Iterable<Category> categories = categoryRepo.findAll();
        model.addAttribute("categories", categories);
        return "categories";
    }

    @GetMapping("/categories/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        Optional<Category> categories = categoryRepo.findById(id);
        if (categories.isPresent()) {
            model.addAttribute("categories", categories.get());
        }
        return "detailcategory";
    }

    @GetMapping("/categories/add")
    public String add(Model model) {
        model.addAttribute("categories", new CategoryData());
        return "inputcategory";
    }

    @PostMapping("/categories/save")
    public String save(CategoryData categoryData) {
        Category categories = new Category();
        categories.setName(categoryData.getName());
        categoryRepo.save(categories);
        return "redirect:/categories";
    }

    @GetMapping("/categories/remove/{id}")
    public String remove(Model model, @PathVariable("id") Long id) {
        categoryRepo.deleteById(id);
        return "redirect:/categories";
    }

    @GetMapping("/categories/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        Optional<Category> categories = categoryRepo.findById(id);
        if(categories.isPresent()) {
            model.addAttribute("categories", categories);
            return "editcategory";
        }
        return "redirect:/categories";
    }

    @PostMapping("/categories/update")
    public String update(Category categories) {
        categoryRepo.save(categories);
        return "redirect:/categories";
    }

}