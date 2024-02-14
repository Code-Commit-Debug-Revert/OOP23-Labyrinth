package com.ccdr.labyrinth.game;

import com.ccdr.labyrinth.game.loader.tiles.SourceTile;
import com.ccdr.labyrinth.game.loader.tiles.Tile;
import com.ccdr.labyrinth.game.player.PlayersManager;

/**
 * This context has the only purpose of updating the source tiles in the board.
 * This update action is written as a context in order to set when it becomes active through the player's turn
 */
public final class UpdateBoardContext implements Context {

    private final Board board;
    private Context following;
    private boolean enter;
    private PlayersManager playerManager;

    /**
     * @param board Board object to get the source tiles from
     * @param following reference to context object that should activate after this one
     */
    public UpdateBoardContext(final Board board) {
        this.board = board;
    }

    public void bindNextContext(final Context following){
        this.following = following;
    }

    public void bindPlayerManager(final PlayersManager pm){
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
        for (final Tile tile : this.board.getMap().values()) {
            if (tile instanceof SourceTile) {
                ((SourceTile) tile).updateTile();
                this.enter = true;
            }
        }
    }

    @Override
    public void secondary() { }

    @Override
    public void back() { }

    @Override
    public boolean done() {
        return enter;
    }

    @Override
    public Context getNextContext() {
        this.enter = false;
        return this.following;
    }

    /**
     * This method is here just so that the view can access it to display it
     * @return
     */
    public int getActivePlayerIndex(){
        return this.playerManager.getActivePlayerIndex();
    }
}
