package org.launchcode.models.Forms;

import org.launchcode.models.Forms.Cheese;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
public class Menu {

    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3,max=15)
    private String name;

    @ManyToMany
    private List<Cheese> cheeses;

    public Menu () {}

    public void addItem(Cheese item) { cheeses.add(item);}

}
