package com.ccdr.labyrinth.game.context;

import com.ccdr.labyrinth.game.context.PlayersContext.Subphase;
import com.ccdr.labyrinth.game.tiles.Board;
import com.ccdr.labyrinth.game.tiles.SourceTile;
import com.ccdr.labyrinth.game.tiles.Tile;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * This context has the only purpose of updating the source tiles in the board.
 * This update action is written as a context in order to set when it becomes active through the player's turn
 */
public final class UpdateBoardContext implements Context {

    /**
     * Board and PlayersContext must point to the objects created inside GameController.
     * There can only be one instance of these, but using singletons is discouraged
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    private final Board board;
    private Context following;
    private boolean enter;
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    private PlayersContext playerManager;
    private boolean advancePlayer;

    /**
     * @param board Board object to get the source tiles from
     */
    public UpdateBoardContext(final Board board) {
        this.board = board;
    }

    /**
     * @param following reference to context object that should activate after this one
     */
    public void setNextContext(final Context following) {
        this.following = following;
    }

    /**
     * @param pm reference to the PlayersManager instance that actually contains the players for the game
     */
    public void setPlayerManager(final PlayersContext pm) {
        this.playerManager = pm;
    }

    @Override
    public void up() { }

    @Override
    public void down() { }

    @Override
    public void left() { }

    @Override
    public void right() { }

    @Override
    public void primary() {
        //advance to the next player
        if (this.playerManager != null) {
            if (this.advancePlayer) {
                final int nextPlayerIndex = this.playerManager.getActivePlayerIndex() + 1;
                this.playerManager.setActivePlayer(nextPlayerIndex % this.playerManager.getPlayers().size());
                this.playerManager.setTurnSubphase(Subphase.DICE);
            }
            for (final Tile tile : this.board.getMap().values()) {
                if (tile instanceof SourceTile) {
                    ((SourceTile) tile).updateTile();
                }
            }
            this.advancePlayer = true;
            this.enter = true;
        }
    }

    @Override
    public void secondary() { }

    @Override
    public void back() { }

    @Override
    public boolean done() {
        return this.enter;
    }

    @Override
    public Context getNextContext() {
        this.enter = false;
        return this.following;
    }

    /**
     * This method is here just so that the view can access it to display it.
     * @return index of the active player
     */
    public int getVisualPlayerIndex() {
        if (this.playerManager != null) {
            if (this.advancePlayer) {
                return (this.playerManager.getActivePlayerIndex() + 1) % this.playerManager.getPlayers().size();
            } else {
                return this.playerManager.getActivePlayerIndex();
            }
        }
        return 0;
    }
}
