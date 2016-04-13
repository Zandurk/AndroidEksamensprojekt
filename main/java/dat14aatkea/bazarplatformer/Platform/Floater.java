package dat14aatkea.bazarplatformer.Platform;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;

import dat14aatkea.bazarplatformer.Game;

/**
 * Created by Alex on 09-04-2016.
 */
public class Floater {

   HashMap<Bitmap, Map<Float, Float>> floaters = new HashMap<>();
    HashMap<Float, Float> floatInts = new HashMap<>();
    Game game;
    Bitmap floater;
    public static final int WIDTH = 143;
    public static final int HEIGHT = 45;
    float y = 875;
    float x = WIDTH;


 public void drawFloater(float y, float x, Game game) {
  this.y = y;
  this.x = x;
  this.game = game;
  floater = game.loadBitmap("floater4.png");
  game.drawBitmap(floater, (int)x, (int)y);
  floatInts.put(x, y);
  floaters.put(floater,floatInts);
   }

 public boolean getFloatPosition(int posX, int id){
    for (int i = 0; i < floaters.size(); i++) {
        if (floaters.keySet().contains(posX)){
            floaters.get(posX);

            }

    }
    return false;
    }

    public int getFloaterWidth(){
       return floater.getWidth();
    }

    public int getFloaterHeight(){
        return floater.getHeight();
    }
}
