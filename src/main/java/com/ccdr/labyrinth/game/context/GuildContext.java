package com.ccdr.labyrinth.game.context;

import com.ccdr.labyrinth.game.generator.MissionGenerator;
import com.ccdr.labyrinth.game.player.Player;
import com.ccdr.labyrinth.game.util.Item;
import com.ccdr.labyrinth.game.util.Material;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Class for the context of the guild and the implements of the menu missions.
 */
public class GuildContext implements Context {

    private final List<Item> missions = new ArrayList<>();
    private final List<Item> missionsCom = new ArrayList<>();
    private final List<Material> materialpresents = new ArrayList<>();
    private final MissionGenerator getM = new MissionGenerator();
    private int menuIndex;
    private PlayersContext players;
    private boolean done;
    private Context following;

    /**
     * Class costructor.
     * @param nPlayer for the number of missions to generate
     */
    public GuildContext(final int nPlayer) {
        for (int i = 0; i < nPlayer * 2; i++) {
            missions.add(getM.generateMission());
        }

        materialpresents.addAll(getM.materialPresents());
    }
    /**
     * Set the PlayerManager.
     * @param pm
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public final void setPlayerManager(final PlayersContext pm) {
        this.players = pm;
    }
    /**
     *
     * @return List of missions
     */
    public final List<Item> getListOfMissions() {
        return Collections.unmodifiableList(this.missions);
    }
    /**
     *
     * @return materials presents in game
     */
    public final List<Material> getMaterialPresents() {
        return Collections.unmodifiableList(this.materialpresents);
    }
    /**
     *
     * @return Index of the menu Guild missions
     */
    public final int getMenuIndex() {
        return this.menuIndex;
    }
    /**
     *
     * @return Missions completed
     */
    public final List<Item> getMissionCompl() {
        return Collections.unmodifiableList(this.missionsCom);
    }
    /**
     *
     * @return GetMissions
     */
    public final MissionGenerator getMissions() {
        return this.getM;
    }

    /**
     * Set the following context.
     * @param next next context
     */
    public final void setNextContext(final Context next) {
        this.following = next;
    }

    @Override
    public final void up() {
        if (this.menuIndex > 0) {
            this.menuIndex--;
        }
    }

    @Override
    public final void down() {
        if (this.menuIndex < missions.size() - 1) {
            this.menuIndex++;
        }
    }

    @Override
    public final void left() {
        up();
    }
    @Override
    public final void right() {
        down();
    }
    @Override
    public final void primary() {
        if (players != null) {
            final Player player = players.getActivePlayer();
            if (player.getQuantityMaterial(missions.get(menuIndex).getMaterial()) >= missions.get(menuIndex).getQuantity()) {
                player.decreaseQuantityMaterial(missions.get(menuIndex).getMaterial(),
                missions.get(menuIndex).getQuantity());
                player.increasePoints(missions.get(menuIndex).getPoints());
                missionsCom.add(missions.get(menuIndex));
                missions.remove(menuIndex);
            }
        }
    }

    @Override
    public final void secondary() {
    }

    @Override
    public final void back() {
        this.done = true;
    }

    @Override
    public final boolean done() {
        return this.done;
    }

    @Override
    public final Context getNextContext() {
        this.done = false;
        return this.following;
    }

}
