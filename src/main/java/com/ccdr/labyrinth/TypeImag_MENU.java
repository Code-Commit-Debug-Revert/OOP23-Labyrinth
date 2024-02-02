package com.ccdr.labyrinth;

import java.util.PrimitiveIterator;

import javafx.scene.image.Image;

public enum TypeImag_MENU {
    //path is redirected through src/main/resources
    //if the file could not be found, jfx will throw an exception
    //(it doesn't do that when you use the 'file:' protocol)
    ICON("/menu/Icon.png");
    //TEXT("/menu/Text.png");

    private String path;
    private Image picture;

    private TypeImag_MENU(String path){
        this.path = path;
        this.picture = new Image(path, 0, 0, true, true);
    }

    public Image getImage(){
        return picture;
    }
}
