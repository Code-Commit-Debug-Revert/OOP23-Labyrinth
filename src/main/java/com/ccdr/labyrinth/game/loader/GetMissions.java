package com.ccdr.labyrinth.game.loader;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;

import com.ccdr.labyrinth.Category;
import com.ccdr.labyrinth.Material;

public class GetMissions {
    private final int MIN_REQUIRED = 3;
    private final int MAX_REQUIRED = 9;
    private int material_temp = 0;
    private int idCat = 0;
    private int idMat = 0;
    private Set<Category> category = new HashSet<>(Set.of(Category.values()));
    private Set<Material> material = new HashSet<>(Set.of(Material.values()));
    private Random quantityGenerator = new Random();


    public Item generateMission(){
        Item item = new Item();
        if(material_temp == 1 || material_temp == 0){
            if(material_temp == 1){
                category.remove(Category.values()[idCat]);
            }

            do{
                idCat = quantityGenerator.nextInt(0,5);
            }while(!category.contains(Category.values()[idCat]));
        }

        if(material_temp == 2 || material_temp == 0){
            if(material_temp == 2){
                material.remove(Material.values()[idMat]);
                material_temp = 0;
            }

            do{
                idMat = quantityGenerator.nextInt(0,6);
            }while(!material.contains(Material.values()[idMat]));
        }

        item.setCategory(Category.values()[idCat]);
        item.setMaterial(Material.values()[idMat]);
        material_temp++;
        item.setQuantity(quantityGenerator.nextInt(MIN_REQUIRED,MAX_REQUIRED));;

        return item;

    }
}
