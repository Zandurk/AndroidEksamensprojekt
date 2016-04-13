package dat14aatkea.bazarplatformer.Platform;

import android.graphics.Bitmap;
import android.util.Log;

import dat14aatkea.bazarplatformer.Game;
import dat14aatkea.bazarplatformer.Screen;
import dat14aatkea.bazarplatformer.State;

/**
 * Created by Alex on 05-04-2016.
 */
public class GameScreen extends Screen {

    enum State{
        Paused,
        Running,
        gameOver
    }


    Bitmap background;
    Bitmap floater;
    //Bitmap player;
    int floaterPos = 435;
    State state = State.Running;
    PlayerHandler p;
    PlayerRenderer pr;



    public GameScreen(Game game) {
        super(game);
        background = game.loadBitmap("background.png");
        floater = game.loadBitmap("floater4.png");

        p = new PlayerHandler(game);
        pr = new PlayerRenderer(game, p);

    }



    @Override
    public void update(float deltaTime) {
    game.drawBitmap(background, 0, 0);
    game.drawBitmap(floater, 0, floaterPos);
    game.drawBitmap(floater, floater.getWidth(), floaterPos);
    game.drawBitmap(floater, floater.getWidth() + floater.getWidth(), floaterPos);


      if (state == State.Running) p.update(deltaTime);
        if (p.player.x > 480-20) {
            game.drawBitmap(background, 0, 0);
            game.drawBitmap(floater, 0, floaterPos);
            game.drawBitmap(floater, floater.getWidth(), floaterPos);
            game.drawBitmap(floater, floater.getWidth() + floater.getWidth(), floaterPos);
        }
        pr.render();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
