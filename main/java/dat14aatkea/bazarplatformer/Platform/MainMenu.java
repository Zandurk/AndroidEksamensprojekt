package dat14aatkea.bazarplatformer.Platform;

import android.graphics.Bitmap;
import android.util.Log;

import dat14aatkea.bazarplatformer.Game;
import dat14aatkea.bazarplatformer.Screen;
import dat14aatkea.bazarplatformer.State;

/**
 * Created by Alexander on 04-04-2016.
 */
public class MainMenu extends Screen {

    enum State {
        Paused,
        Running
    }


    Bitmap mainmenu;
    Bitmap levelmanager;
    Bitmap exit;
    Bitmap logo;

    State state = State.Running;

    public MainMenu(Game game) {
        super(game);

        mainmenu = game.loadBitmap("background.png");
        levelmanager = game.loadBitmap("button_play.png");
        exit = game.loadBitmap("quit_button.png");
        logo = game.loadBitmap("lille_logo.png");
    }


    @Override
    public void update(float deltaTime) {



        game.drawBitmap(mainmenu, 0, 0);
        game.drawBitmap(logo, 320-200/2, 200);
        game.drawBitmap(levelmanager, 320-160/2, 500-(logo.getHeight()-levelmanager.getHeight()+70));
        game.drawBitmap(exit, 320-160/2, 500-logo.getHeight()+70);





        if (game.getTouchX(0) > 320 - 160/2 - levelmanager.getWidth() && game.getTouchX(0) < 320-160/2 + levelmanager.getWidth() && game.getTouchY(0) > 500-(logo.getHeight()-levelmanager.getHeight()+70)- levelmanager.getHeight() && game.getTouchY(0) <(500-(logo.getHeight()-levelmanager.getHeight()+70) + levelmanager.getHeight())){
            game.setScreen(new LevelScreen(game));
            return;
        }
        if (game.getTouchX(0) > 320-160/2 - exit.getWidth() && game.getTouchX(0) < 320-160/2 + exit.getWidth() && game.getTouchY(0) > 500-logo.getHeight()+70 - exit.getHeight() && game.getTouchY(0) < 500-logo.getHeight()+70 + exit.getHeight()){
            System.exit(0);
            state = State.Paused;

        }



                    }

    @Override
    public void pause() {
        if(state == State.Running){
            state = State.Paused;
                        }


    }

    @Override
    public void resume() {
        if (state == State.Paused){
            state = State.Running;
            game.setScreen(new MainMenu(game));
        }

    }

    @Override
    public void dispose() {

    }
}
