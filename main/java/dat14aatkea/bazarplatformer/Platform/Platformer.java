package dat14aatkea.bazarplatformer.Platform;

import dat14aatkea.bazarplatformer.Game;
import dat14aatkea.bazarplatformer.Screen;

/**
 * Created by Alex on 04-04-2016.
 */
public class Platformer extends Game {

    @Override
    public Screen createStartScreen() {
        return new MainMenu(this);
    }
}
