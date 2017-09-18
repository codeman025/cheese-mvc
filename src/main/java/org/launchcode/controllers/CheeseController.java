package org.launchcode.controllers;

import org.launchcode.models.Forms.Category;
import org.launchcode.models.Forms.Cheese;
import org.launchcode.models.Forms.CheeseType;
import org.launchcode.models.data.CategoryDao;
import org.launchcode.models.data.CheeseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping("cheese")
public class CheeseController {

    @Autowired
    private CheeseDao cheeseDao;

    @Autowired
    private CategoryDao categoryDao;



    // Request path: /cheese
    @RequestMapping(value = "")
    public String index(Model model) {

        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "My Cheeses");

        return "cheese/index";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String displayAddCheeseForm(Model model) {
        model.addAttribute("title", "Add Cheese");
        model.addAttribute(new Cheese());
        model.addAttribute("categories", categoryDao.findAll());
        return "cheese/add";
    }

    @RequestMapping(value="add", method = RequestMethod.GET)
    public String displayAddCheeseForm(Model model){
        model.addAttribute("title","Add Cheese");
        model.addAttribute(new Cheese());
        model.addAttribute("categories",categoryDao.findAll());
        return "cheese/add";
    }


    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String processAddCheeseForm(@ModelAttribute  @Valid Cheese newCheese,
                                       Errors errors, @RequestParam int categoryId, Model model) {


        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Cheese");
            return "cheese/add";
        }
        Category cat = categoryDao.findOne(categoryId);//in order to get a category that a user can select
        newCheese.setCategory(cat);//findONe is a method speicified by the crudreposityr interface
        cheeseDao.save(newCheese);//springboot will make a class that implements this method for us
        //as long as this corresponds to an actual id
        //
        return "redirect:";
    }
//the displayremove is get
    @RequestMapping(value = "remove", method = RequestMethod.GET)
    public String displayRemoveCheeseForm(Model model) {
        model.addAttribute("cheeses", cheeseDao.findAll());
        model.addAttribute("title", "Remove Cheese");
        return "cheese/remove";
    }

    @RequestMapping(value = "remove", method = RequestMethod.POST)
    public String processRemoveCheeseForm(@RequestParam int[] cheeseIds) {

        for (int cheeseId : cheeseIds) {
            cheeseDao.delete(cheeseId);
        }

        return "redirect:";
    }
    @RequestMapping(value="edit/{cheseID}", method = RequestMethod.POST)
    public String processEditForm(Model model, @PathVariable int cheeseId,
                                  @ModelAttribute @Valid Cheese newCheese,
                                  @RequestParam int categoryId,
                                  Errors errors){
        if(errors.hasErrors()){
            model.addAttribute("title", "Add Cheese");
            return "cheese/edit";
        }

        Cheese editedCheese = cheeseDao.findOne(cheeseId);
        editedCheese.setName(newCheese.getName());
        editedCheese.setDescription(newCheese.getDescription());
        editedCheese.setCategory(categoryDao.findOne(categoryId));
        cheeseDao.save(editedCheese);

        return "redirect:/cheese";
    }
}
/**
