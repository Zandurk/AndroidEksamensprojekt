package dat14aatkea.bazarplatformer;

/**
 * Created by Alexander on 07-03-2016.
 */
public class MyKeyEventPool extends Pool<MyKeyEvent>{

    @Override
    protected MyKeyEvent newItem() {
        return new MyKeyEvent();
    }
}
