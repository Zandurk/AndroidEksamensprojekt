package dat14aatkea.bazarplatformer.Platform;

import android.graphics.Bitmap;
import android.util.Log;



import dat14aatkea.bazarplatformer.Game;
import dat14aatkea.bazarplatformer.Screen;
import dat14aatkea.bazarplatformer.State;

/**
 * Created by Alex on 08-04-2016.
 */
public class PlayerHandler {

    Game game;

    public PlayerHandler(Game game)
    {
        this.game = game;
    }

    Floater floater = new Floater();
    float startOfJump;
    float JUMP_DISTANCE = 160;

    Player player = new Player();

    public void update(float deltaTime) {

        player.x = player.x + player.velocityX * deltaTime;
        System.out.println(player.y);
        System.out.println(player.x);
        System.out.println(player.jumpingState);

        if(game.isTouchDown(0)&& player.jumpingState.equals("none")&& player.y>=810)
        {
            startOfJump = player.x;

            player.jumpingState="jumping";
            player.jumpMax = player.y - 200;
        }
        if(player.jumpingState.equals("jumping"))
        {
            player.y = player.y -player.velocityY * deltaTime;
            float mathX = player.x - startOfJump;
            //player.x = player.x- (player.velocityX*player.velocityX )+(3*player.velocityX) * deltaTime;

           // player.y = player.y + (player.velocityY * mathX * -mathX) * deltaTime;
            //a*x^2 + b*x + c



            // x = -x^2+3x

            if (player.y<player.getJumpMax())
            {
                player.jumpingState="falling";
            }
        }
        if (player.jumpingState.equals("falling"))
        {
            player.y = player.y+ player.velocityY * deltaTime;

            //player.y = player.y+ (float)0.01*player.velocityY*player.velocityY * deltaTime;

            //player.y = player.y+((float)0.2*player.velocityY*player.getPlayerVelocityY()) * deltaTime;
            if (player.y >810)
            {
                player.jumpingState ="none";
            }else{

            }
        }
    }

}



