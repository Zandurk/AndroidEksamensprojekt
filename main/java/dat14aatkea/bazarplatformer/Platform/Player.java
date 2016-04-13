package dat14aatkea.bazarplatformer.Platform;

/**
 * Created by Alex on 08-04-2016.
 */
public class Player {

    Floater floater = new Floater();
    public static final float WIDTH = 51;
    public static final float HEIGHT = 66;
    float x = 0;
    String jumpingState ="none";
    float y = 810;
    float velocityX = 150;
    float jumpMax = 100;
    float velocityY = 250;

    public float getJumpMax()
    {
        return jumpMax;
    }

    public void setJumpMax(float jumpMax)
    {
        this.jumpMax = jumpMax;
    }


    public float getPlayerPosY(){
        return y;
    }

    public float getPlayerPosX(){
        return x;
    }

    public float getPlayerVelocityX(){
        return velocityX;
    }

    public float getPlayerVelocityY() { return velocityY; }
}
