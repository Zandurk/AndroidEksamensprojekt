package dat14aatkea.bazarplatformer.Platform;

import android.graphics.Bitmap;

import dat14aatkea.bazarplatformer.Game;
import dat14aatkea.bazarplatformer.Screen;

/**
 * Created by Alex on 04-04-2016.
 */
public class LevelScreen extends Screen {

    Bitmap background;
    Bitmap levels;
    Bitmap text;
    Bitmap levels2;
    int levelCounter = 1;



    public LevelScreen(Game game) {
        super(game);
         background = game.loadBitmap("background.png");
         levels = game.loadBitmap("o.png");
         text = game.loadBitmap("levelText.png");
        levels2 = game.loadBitmap("o2.png");
        game.drawBitmap(background, 0, 0);
    }

    @Override
    public void update(float deltaTime) {

        game.drawBitmap(text, 320-111/2, 80);
        game.drawBitmap(levels, 320 - 230 / 2, 150);

        // der skal laves if statement på de specefikke koordinater til level 1 hvorefter den skal henvise til Level_1+
        if (game.getTouchX(0) > 280 - 230/2 && game.getTouchX(0) < 360 - 230/2 && game.getTouchY(0) > 150 - levels.getHeight() && game.getTouchY(0) < 150 + levels.getHeight() ) {
            LevelChosen(1);
             }



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

    public void enableNextLevel(int x){
        game.drawBitmap(levels2, 320-230/2 + levels.getWidth()+10, 150);
       // LevelChosen(2);
    }

    private void LevelChosen(int choice)
    {


        switch(choice)
        {
            case 1: game.setScreen(new Level_1(game));
                break;
            case 2: game.setScreen(new Level_2(game));
        }
    }
}
