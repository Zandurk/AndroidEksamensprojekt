package dat14aatkea.bazarplatformer;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;

import java.util.Random;

public class SimpleScreen extends Screen
{
    Bitmap visual;
    int x = 0;
    int y = 0;
    Random rand = new Random();
    int clearColor = Color.BLUE;
    Sound sound;
    Music music;
    boolean userWantsMusic;


    public SimpleScreen(Game game)
    {
        super(game);
        visual = game.loadBitmap("bob.png");
        sound = game.loadSound("explosion.ogg");
        music = game.loadMusic("music.ogg");
    }

    public void update(float deltaTime) {


        Log.d("FPS", "Fps: "+ game.getFrameRate());
        game.clearFramebuffer(clearColor);
        if (game.isTouchDown(0)) {
            if (userWantsMusic) {
                music.pause();
                userWantsMusic = false;
            }else {
                music.play();
                userWantsMusic = true;
            }
        }

        for (int pointer = 0; pointer < 5; pointer++)
        {
            if (game.isTouchDown(pointer)){
                game.drawBitmap(visual, game.getTouchX(pointer), game.getTouchY(pointer));
                sound.play(1);
            }
        }

        /*
        game.drawBitmap(bitmap, 10, 10);
        game.drawBitmap(bitmap, 100, 140, 0, 0, 64, 64);
        if (game.isKeyPressed(MyKeyEvent.KEYCODE_MENU))
        {
            clearColor = rand.nextInt();
        }*/
        float x = - game.getAccelerometer()[0];
        float y =  game.getAccelerometer()[1];
        x = (x/100) * game.getFramebufferWidth()/2 + game.getOffScreenWidth()/2;
        y = (y/100) * game.getFramebufferHeight()/2 + game.getOffScreenHeight()/2;
        game.drawBitmap(visual, (int)x-64, (int)y-64);


    }
    public void pause(){
        Log.d("SimpleScreen" , " we ar pausing");
        music.pause();
    }
    public void resume(){
        Log.d("SimpleScreen" , " we ar resuming");
        if (userWantsMusic) music.play();
    }
    public void dispose(){
        Log.d("SimpleScreen" , " we ar disposing the game");
        sound.dispose();
        music.dispose();
    }

}


