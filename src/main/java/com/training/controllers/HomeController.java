package com.training.controllers;

import java.util.Optional;

import com.training.data.entity.Product;
import com.training.data.repos.CategoryRepo;
import com.training.data.repos.ProductRepo;
import com.training.dto.ProductData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private ProductRepo repo;

    @Autowired
    private CategoryRepo categoryRepo;


    @GetMapping
    public String main(Model model) {
        Iterable<Product> products = repo.findAll();
        model.addAttribute("products", products);
        return "index";
    }

    @GetMapping("/product/{id}")
    public String detail(Model model, @PathVariable("id") Long id) {
        Optional<Product> product = repo.findById(id);
        if (product.isPresent()) {
            model.addAttribute("product", product.get());
        }
        return "detail";
    }

    @GetMapping("/product/add")
    public String add(Model model) {
        model.addAttribute("product", new ProductData());
        model.addAttribute("categories", categoryRepo.findAll());
        return "input";
    }

    @PostMapping("/product/save")
    public String save(ProductData productData) {
        Product product = new Product();
        product.setCode(productData.getCode());
        product.setName(productData.getName());
        product.setPrice(productData.getPrice());
        product.setDescription(productData.getDescription());
        product.setCategory(categoryRepo.findById(productData.getCategoryId()).get());
        repo.save(product);
        return "redirect:/";
    }

    @GetMapping("/product/remove/{id}")
    public String remove(Model model, @PathVariable("id") Long id) {
        repo.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/product/edit/{id}")
    public String edit(Model model, @PathVariable("id") Long id) {
        Optional<Product> product = repo.findById(id);
        if(product.isPresent()) {
            model.addAttribute("product", product);
            return "edit";
        }
        return "redirect:/";
    }

    @PostMapping("/product/update")
    public String update(Product product) {
        repo.save(product);
        return "redirect:/";
    }

}