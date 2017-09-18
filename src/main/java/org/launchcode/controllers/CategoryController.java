package org.launchcode.controllers;

import org.launchcode.models.Forms.Category;
import org.launchcode.models.data.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryDao categoryDao;
    //base is /cheese
    @RequestMapping(value="")
    public String index(Model model) {
        model.addAttribute("categories", categoryDao.findAll());
        model.addAttribute("title", "Categories");

        return "category/index";
    }
    @RequestMapping(value= "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("title","Category");
        model.addAttribute("category", new Category());
        return "category/add";
    }
    @RequestMapping(value="add", method = RequestMethod.GET)
    public String add(Model model) {
        @ModelAttribute @Valid Category category, Errors errors) {
        if (errors.hasErrors()) {
            return "category/add";
        }
        categoryDao.save(category);
        return "redirect:";//sends user back to base page after
        }
    }

}

