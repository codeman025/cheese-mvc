package org.launchcode.controllers;

import org.launchcode.models.Forms.Cheese;
import org.launchcode.models.Forms.Menu;
import org.launchcode.models.data.CheeseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.Entity;
import javax.validation.Valid;

import static com.sun.corba.se.spi.activation.IIOP_CLEAR_TEXT.value;

@Controller
@RequestMapping(value="menu")
public class MenuController {
    @Autowired
    private MenuDoa menuDoa;
    @Autowired
    private CheeseDao cheeseDao;

    @RequestMapping(value"")
    public String index(Model model){
        model.addAttribute("title","Menus");
        model.addAttribute("menus", menuDoa.findAll());
        return "menu/index";
    }
    @RequestMapping(value="add",method= RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute("title","Add Menu");
        model.addAttribute(new Menu());
        return "menu/add";
    }
    @RequestMapping(value="add",method RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid Menu newMenu, Errors errors){
        if (errors.hasErrors()){
            model.addAttribute("title","Add Menu");
            return "menu/add";
        }
        menuDoa.save(newMenu);
        return "redirect:view/" + newMenu.getId();
    }
    @RequestMapping(value="view/{id}", method = RequestMethod.GET)
    public String view(Model model, @PathVariable int id){
        Menu menu = menuDao.findOne(id);
        model.addAttribute("title", menu.getName());
        model.addAttribute("menu", menu);
        return "menu/view";
    }
    @RequestMapping(value="add-item/{menuId}",method = RequestMethod.GET)
    public String addItem(Model model, @ModelAttribute @Valid AddMenuItemForm itemForm,
                          Errors errors, @PathVariable int menuId){
        if(errors.hasErrors()){
            model.addAttribute("title","Add Item");
            return "menu/add-item/" + menuId;
        }
        Menu menu = menuDoa.findOne(itemForm.getMenuId());
        Cheese cheese = cheeseDao.findOne(itemForm.getCheeseId());

        menu.addItem(cheese);
        menuDoa.save(menu);
        return "redirect:../view/" + menu.getId());
    }

}
