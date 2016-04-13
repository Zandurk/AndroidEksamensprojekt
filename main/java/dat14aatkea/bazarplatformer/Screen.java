package dat14aatkea.bazarplatformer;

/**
 * Created by BigD on 22/02/16.
 */
public abstract class Screen
{
    protected final Game game;

    public Screen(Game game)
    {
        this.game = game;
    }

    public int xPixels = 0;
    public int deathLine = -10;
    public int yPixels = 920;

    public abstract void update(float deltaTime);
    public abstract void pause();
    public abstract void resume();
    public abstract void dispose();
}
