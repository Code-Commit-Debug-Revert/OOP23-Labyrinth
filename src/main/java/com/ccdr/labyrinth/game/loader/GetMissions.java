package com.ccdr.labyrinth.game.loader;

import java.util.Random;
import java.util.Set;

import com.ccdr.labyrinth.Category;
import com.ccdr.labyrinth.Material;

public class GetMissions {
    private final int MIN_REQUIRED = 1;
    private final int MAX_REQUIRED = 9;
    private int value = 0;
    private Item item;
    private Category[] category = Category.values();
    private Material[] material = Material.values();
    private Random quantityGenerator = new Random();

    public void setTypeItem(){
        value = quantityGenerator.nextInt(0,4);
        item.setCategory(category[value]);
    }

    public void setMatItem(){
        value = quantityGenerator.nextInt(0,4);
        item.setMaterial(material[value]);
    }

    public void setRandomRequiredQuantities(Set<Material> materialsToSet) {
        for(Material material : materialsToSet) {
            switch (material) {
                case WOOD:
                    item.getRequiredMaterials().setRequiredWood(quantityGenerator.nextInt(MIN_REQUIRED, MAX_REQUIRED));
                case COAL:
                    item.getRequiredMaterials().setRequiredCoal(quantityGenerator.nextInt(MIN_REQUIRED, MAX_REQUIRED));
                case IRON:
                    item.getRequiredMaterials().setRequiredIron(quantityGenerator.nextInt(MIN_REQUIRED, MAX_REQUIRED));
                case COPPER:
                    item.getRequiredMaterials().setRequiredCopper(quantityGenerator.nextInt(MIN_REQUIRED, MAX_REQUIRED));
                case DIAMOND:
                    item.getRequiredMaterials().setRequiredDiamond(quantityGenerator.nextInt(MIN_REQUIRED, MAX_REQUIRED));
                case SILK:
                    item.getRequiredMaterials().setRequiredSilk(quantityGenerator.nextInt(MIN_REQUIRED, MAX_REQUIRED));
            }
        }
    }
}
