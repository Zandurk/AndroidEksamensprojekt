package dat14aatkea.bazarplatformer.Platform;


import android.graphics.Bitmap;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import dat14aatkea.bazarplatformer.Game;
import dat14aatkea.bazarplatformer.Platform.PlayerHandler;
import dat14aatkea.bazarplatformer.Platform.PlayerRenderer;
import dat14aatkea.bazarplatformer.Platform2.*;
import dat14aatkea.bazarplatformer.Screen;

/**
 * Created by Hidancore on 08-04-2016.
 */
public class Level_1 extends Screen {
    enum State {
        Paused,
        Running,
        gameOver
    }

    Bitmap background;
    Bitmap goal;
    Bitmap gameover;
    Bitmap vic;
    int floaterPos = 875;

    State state = State.Running;
    // der skal nok importeres spil klassen med logic?

    LevelScreen levelScreen;
    PlayerHandler p;
    PlayerRenderer pr;
    Floater floater = new Floater();
    int count = 0;



    public Level_1(Game game) {
        super(game);
        background = game.loadBitmap("background.png");
        goal = game.loadBitmap("stang.png");
        gameover = game.loadBitmap("gameover.png");
        vic = game.loadBitmap("vic.png");
        p = new PlayerHandler(game);
        pr = new PlayerRenderer(game, p);
        levelScreen = new LevelScreen(game);


        //Hjæælp?
        if (game.getTouchY(0) < 36 && game.getTouchX(0) > 320 - 36) {
            game.setScreen(new LevelScreen(game));
        }
        //////////////////////////////////////////////////////////////////////


    }

    @Override
    public void update(float deltaTime) {

       // game.drawBitmap(goal, 200-floater.getHeight(), 150);
        if (count == 0) {
            game.drawBitmap(background, 0, 0);
            floater.drawFloater(floaterPos, 0, game);
            floater.drawFloater(floaterPos, floater.getFloaterWidth(), game);
            floater.drawFloater(floaterPos, floater.getFloaterWidth()*2, game);
            floater.drawFloater(floaterPos, floater.getFloaterWidth()*3,game);
            floater.drawFloater(floaterPos, floater.getFloaterWidth()*4, game);

        } else if(count == 1){
            game.drawBitmap(background, 0, 0);
            floater.drawFloater(floaterPos, 0, game);
            floater.drawFloater(floaterPos, floater.getFloaterWidth()*2, game);
            floater.drawFloater(floaterPos, floater.getFloaterWidth() * 3, game);
            floater.drawFloater(floaterPos, floater.getFloaterWidth()*4, game);
            game.drawBitmap(goal, floater.getFloaterWidth()*4, (floaterPos - floater.getFloaterHeight()*3)+30);

        }
        // 1144 slutter banen.. muligvis noget lang men det ser vi på.
        // der skal tilføjes et flag et godt 30 pix fra slut hvor spilleren vinder vis han passere.

        if (p.player.x >= 640-20) {
            game.drawBitmap(background,0, 0);
            drawMap(count);

            p.player.x = 0;
            count++;
        }


        if (p.player.x >= floater.getFloaterWidth()*4 && count==1){
            game.drawBitmap(background,0,0);
            game.drawBitmap(vic, 320-292/2, 420 );
            p.player.y = 940;
            state = State.gameOver;
            if (game.isTouchDown(0)){
                game.setScreen(new LevelScreen(game));
                levelScreen.enableNextLevel(count+1);
            }

        }


 // FALD NED OG DØØØØØØØØØØØØØØØØØØØØØØ
        if(p.player.x >= 143 && 286>=p.player.x && p.player.y>= 810 && count == 1){
            for (int i = 50; i < 100 ; i++) {
                p.player.x = 50-i;
            }
            //gameover
        }

/*
        for (Map.Entry<Bitmap, Map<Float, Float>> entry : floater.floaters.entrySet()) {
           if(floater.floaters.values().contains(entry.getValue().keySet() )){
              //  if ((float)p.player.x = entry.getValue().values()){

                //}
            } else{
                p.player.y = 850;
            }
        }
*/

        pr.render();

        if (state == State.Running) p.update(deltaTime);


        if (p.player.x < -20){
            game.drawBitmap(background, 0,0);
            game.drawBitmap(gameover, 320-gameover.getWidth()/2, 420);
            state = State.gameOver;
            if (game.isTouchDown(0)){
                game.setScreen(new LevelScreen(game));
            }
        }



    }








    @Override
    public void pause() {
        if (state == State.Running && game.getTouchY(0) < 36 && game.getTouchX(0) > 320 - 36) {
            state = State.Paused;
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }




    public void drawMap(int a) {
       if (a == 0) {


       } else if(count == 1){
           floater.drawFloater(floaterPos, 0, game);
           floater.drawFloater(floaterPos, floater.getFloaterWidth() * 2, game);
       }
    }
}
