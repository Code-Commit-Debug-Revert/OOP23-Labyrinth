package com.ccdr.labyrinth.game.tiles;

import java.util.Optional;

import com.ccdr.labyrinth.game.player.Player;
import com.ccdr.labyrinth.game.util.Material;

/**
 * This tile type is the one that players normally walk on to move through the labyrinth.
 * It may contain a bonus inside that will be rewarded to the player.
 */
public final class StandardTile extends GenericTile {
    private Optional<Material> bonusMaterial;
    private int bonusAmount;

    /**
     * Creates a tile with absolutely zero bonus inside.
     */
    public StandardTile() {
        this.bonusMaterial = Optional.empty();
        this.bonusAmount = 0;
    }

    /**
     * Creates a tile with a bonus amount of materials inside.
     * @param bonusMaterial type of material to give as bonus
     * @param amount quantity of material to give
     */
    public StandardTile(final Material bonusMaterial, final int amount) {
        this.bonusMaterial = Optional.of(bonusMaterial);
        this.bonusAmount = amount;
    }

    @Override
    public void onEnter(final Player player) {
        super.discover();
        if (this.bonusMaterial.isPresent()) {
            player.increaseQuantityMaterial(this.bonusMaterial.get(), this.bonusAmount);
            this.bonusMaterial = Optional.empty();
            this.bonusAmount = 0;
        }
    }

    @Override
    public void onExit(final Player player) { }

    /**
     * @return Full optional if the tile has some bonus materials, empty otherwise
     */
    public Optional<Material> getBonusMaterial() {
        return bonusMaterial;
    }
}
