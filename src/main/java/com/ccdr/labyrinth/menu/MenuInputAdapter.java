package com.ccdr.labyrinth.menu;

import com.ccdr.labyrinth.jfx.JFXInputSource;

import javafx.scene.input.KeyEvent;

public class MenuInputAdapter implements JFXInputSource.Receiver{
    private MenuController menuController;

    public MenuInputAdapter(MenuController controller){
        this.menuController = controller;
    }

    public void onKeyPressed(KeyEvent event){
        switch(event.getCode()){
            case UP:
                menuController.moveUp();
                break;
            case DOWN:
                menuController.moveDown();
                break;
            case ENTER:
                menuController.select();
                break;
            case ESCAPE:
            case BACK_SPACE:
                menuController.back();
                break;
            default:
                break;
        }
    }

    public void onKeyReleased(KeyEvent event){

    }
}
