package dat14aatkea.bazarplatformer;

/**
 * Created by BigD on 29/02/16.
 */
public interface TouchHandler {

    public boolean isTouchDown(int pointer);
    public int getTouchX(int pointer);
    public int getTouchY(int pointer);

}
