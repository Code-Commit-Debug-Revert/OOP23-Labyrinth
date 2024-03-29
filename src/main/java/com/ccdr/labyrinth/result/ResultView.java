package com.ccdr.labyrinth.result;

import java.util.List;
import java.util.Map;

import com.ccdr.labyrinth.game.player.Player;

/**
 * Interface that describes how a view for the result screen is structured.
 */
public interface ResultView {
    /**
     * This method gets called first, when the menu must be shown in the first place.
     */
    void onEnable();

    /**
     * Draw the entire Result screen.
     * @param players list of players, to draw their scores and other data
     * @param playersToIndex map used to tell the index of a player
     */
    void draw(List<Player> players, Map<Player, Integer> playersToIndex);
}
