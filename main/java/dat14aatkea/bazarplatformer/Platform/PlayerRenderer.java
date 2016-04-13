package dat14aatkea.bazarplatformer.Platform;

import android.graphics.Bitmap;

import dat14aatkea.bazarplatformer.Game;

/**
 * Created by Alex on 08-04-2016.
 */
public class PlayerRenderer {
    Game game;
    PlayerHandler playerHandler;
    Bitmap player;

    public PlayerRenderer(Game game, PlayerHandler playerHandler) {
        this.game = game;
        this.playerHandler = playerHandler;
        this.player = game.loadBitmap("droid.png");
    }

    public void render(){
        game.drawBitmap(player, (int)playerHandler.player.x, (int) playerHandler.player.y);

    }
}
