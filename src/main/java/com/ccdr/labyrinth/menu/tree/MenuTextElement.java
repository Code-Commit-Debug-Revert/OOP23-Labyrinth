package com.ccdr.labyrinth.menu.tree;

//menu element that only displays text. Meant to be used only as a leaf.
//it runs the runnable provided in the constructor once selected
public class MenuTextElement extends MenuElement {
    private Runnable action;

    public MenuTextElement(String text, Runnable action){
        super(text);
        this.action = action;
    }

    @Override
    public void up() {}

    @Override
    public void down() {}

    @Override
    public MenuElement nextState() {
        if(this.action != null){
            this.action.run();
        }
        return getParent();
    }

    @Override
    public String toString() {
        return getName();
    }

}