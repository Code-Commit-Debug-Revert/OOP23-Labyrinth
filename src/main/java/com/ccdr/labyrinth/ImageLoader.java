package com.ccdr.labyrinth;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javafx.scene.image.Image;
/**
 * Enum for loading from file path and getting images.
 */
public enum ImageLoader {
    /**path is redirected through src/main/resources.
    *if the file could not be found, jfx will throw an exception
    *(it doesn't do that when you use the 'file:' protocol)
    */
    /**
     * Menu.
     */
    LOGO("/menu/Logo-v3.png"),
    /**
     * Game.
     */
    WALL("/game/Wall.png"),
    /**
     * 
     */
    PATH_VERTICAL("/game/PathVertical.png"),
    /**
     * 
     */
    PATH_HORIZONTAL("/game/PathHorizontal.png"),
    /**
     * 
     */
    PATH_CENTRAL("/game/PathCentral.png"),
    /**
     * 
     */
    PATH("/game/Path.png"),
    /**
     * 
     */
    GUILD("/game/Guild.png"),
    /**
     * 
     */
    POINT("/game/Point.png"),
    /**
     * Materials.
     */
    COAL("/game/materials/Coal.png"),
    /**
     * 
     */
    COPPER("/game/materials/Copper.png"),
    /**
     * 
     */
    DIAMOND("/game/materials/Diamond.png"),
    /**
     * 
     */
    IRON("/game/materials/Iron.png"),
    /**
     * 
     */
    SILK("/game/materials/Silk.png"),
    /**
     * 
     */
    WOOD("/game/materials/Wood.png"),
    /**
     * Categorys.
     */
    ARMOR("/game/category/Armor.png"),
    /**
     * 
     */
    CLOTHING("/game/category/Clothing.png"),
    /**
     * 
     */
    JEWEL("/game/category/Jewel.png"),
    /**
     * 
     */
    TOOL("/game/category/Tool.png"),
    /**
     * 
     */
    WEAPON("/game/category/Weapon.png");

    private Image picture;
    /**
     * 
     * @param path
     */
    ImageLoader(final String path) {
        this.picture = new Image(path, 0, 0, true, true);
    }
    /**
     * 
     * @return image
     * 
     */
    @SuppressFBWarnings
    public Image getImage() {
        return picture;
    }
}
