package dat14aatkea.bazarplatformer;

/**
 * Created by Alexander on 07-03-2016.
 */
public class MyKeyEvent {
    public enum KeyEventType{
        Up,
        Down
    }

    public KeyEventType type;
    public int keyCode;
    public char keyCharacter;

}
