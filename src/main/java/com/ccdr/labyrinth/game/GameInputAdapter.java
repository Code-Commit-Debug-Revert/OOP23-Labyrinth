package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.jfx.JFXInputSource;

import javafx.scene.input.KeyEvent;

public class GameInputAdapter implements JFXInputSource.Receiver {
    private GameController controller;

    public GameInputAdapter(GameController controller){
        this.controller = controller;
    }

    public void onKeyPressed(KeyEvent event){
        switch(event.getCode()){
            case W:
                this.controller.movePlayerUp();
                break;
            case D:
                this.controller.movePlayerRight();
                break;
            case A:
                this.controller.movePlayerLeft();
                break;
            case S:
                this.controller.movePlayerDown();
                break;
            case ENTER:
                break;
            default:
                break;
        }
    }

    public void onKeyReleased(KeyEvent event){

    }
}
