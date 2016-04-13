package dat14aatkea.bazarplatformer;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public abstract class Game extends Activity implements Runnable, View.OnKeyListener, SensorEventListener
{




    private Thread mainLoopThread;
    private State state = State.Paused;
    private List<State> stateChanges = new ArrayList<>();
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Screen screen;
    private Bitmap offscreenSurface;
    private Canvas canvas = null;

    private boolean pressedKeys[] = new boolean[256];

    private TouchHandler touchHandler;
    private TouchEventPool touchEventPool = new TouchEventPool();
    private List<TouchEvent> touchEvents = new ArrayList<>();
    private List<TouchEvent> touchEventBuffer = new ArrayList<>();
    private MyKeyEventPool myKeyEventPool = new MyKeyEventPool();
    private List<MyKeyEvent> myKeyEvents = new ArrayList<>();
    private List<MyKeyEvent> myKeyEventBuffer = new ArrayList<>();


    private float[] accelerometer = new float[3];

    private SoundPool soundPool;

    private int fps = -1;

    public abstract Screen createStartScreen();

    protected void onCreate(Bundle instanceBundle)
    {
        super.onCreate(instanceBundle);
        //Log.d("onCreate", "1 *********************************");
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //***** slide 48: next two lines
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        //        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //Log.d("onCreate", "2 *********************************");
        surfaceView = new SurfaceView(this);
        //Creates ana actual view in activity class
        setContentView(surfaceView);

        surfaceHolder = surfaceView.getHolder();
        Log.d("onCreate", "1 *********************************");

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        this.soundPool = new SoundPool(20, AudioManager.STREAM_MUSIC, 0);

        screen = createStartScreen();
        Log.d("onCreate", "3 *********************************");


            setOffscreenSurface(640, 920);

        surfaceView.requestFocus();
        surfaceView.setFocusableInTouchMode(true);
        touchHandler = new MultiTuchHandler(surfaceView, touchEventBuffer, touchEventPool);

        SensorManager manager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        if(manager.getSensorList(Sensor.TYPE_ACCELEROMETER).size() != 0)
        {
         Sensor accelerometer = manager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
            manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);

        }
    }

    public void setOffscreenSurface(int width, int height)
    {

        if (offscreenSurface != null) offscreenSurface.recycle();
        offscreenSurface = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        canvas = new Canvas(offscreenSurface);
        //rationScreenToLogicalX = (float) offscreenSurface.getWidth() / (float) surfaceView.getWidth();
        //rationScreenToLogicalY = (float) offscreenSurface.getHeight() / (float) surfaceView.getHeight();

    }


    public void setScreen(Screen screen)
    {

        if (this.screen != null) this.screen.dispose();
        this.screen = screen;
    }

    public Bitmap loadBitmap(String fileName)
    {
        InputStream in = null;
        Bitmap bitmap = null;
        try
        {
            in = getAssets().open(fileName);
            bitmap = BitmapFactory.decodeStream(in);
            if (bitmap == null)
                throw new RuntimeException("Could get a bitmap from the file " + fileName);
            return bitmap;
        } catch (IOException e)
        {
            throw new RuntimeException("Could not load the file " + fileName);
        } finally
        {
            if (in != null)
            {
                try
                {
                    in.close();
                } catch (IOException e)
                {
                    Log.d("closing inputstream", "Shit");
                }
            }
        }
    }


        public Music loadMusic(String fileName)
        {
         try{
             AssetFileDescriptor afd = getAssets().openFd(fileName);
             return new Music(afd);
         } catch (IOException e){
             throw new RuntimeException("Could not load music: " + fileName);
         }
        }

        public Sound loadSound(String fileName)
        {
            try{
                AssetFileDescriptor afd = getAssets().openFd(fileName);
                int soundId = soundPool.load(afd, 0);
                Sound sound = new Sound(soundId, soundPool);
                return sound;
            }catch (IOException e){
                throw new RuntimeException("Could not load sound file: "+ fileName);
            }

        }

    public void clearFramebuffer(int color)
    {
        //*****
        if (canvas != null) canvas.drawColor(color);
    }

    public int getFramebufferWidth()
    {
        return surfaceView.getWidth();//*****
    }

    public int getOffScreenWidth(){return offscreenSurface.getWidth();}
    public int getOffScreenHeight(){return offscreenSurface.getHeight();}


    public int getFramebufferHeight()
    {
        return surfaceView.getHeight();//*****
    }

    public void drawBitmap(Bitmap bitmap, int x, int y)
    {
        if (canvas != null) canvas.drawBitmap(bitmap, x, y, null);

    }

    Rect src = new Rect();
    Rect dst = new Rect();


    public void drawBitmap(Bitmap bitmap, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight)
    {

        if (canvas == null) return;
        src.left = srcX;
        src.top = srcY;
        src.right = srcX + srcWidth;
        src.bottom = srcY + srcHeight;

        dst.left = x;
        dst.top = y;
        dst.right = x + srcWidth;
        dst.bottom = y + srcHeight;

        canvas.drawBitmap(bitmap, src, dst, null);


    }


    public boolean onKey(View v, int keyCode, KeyEvent event)
    {
        if (event.getAction() == KeyEvent.ACTION_DOWN)
        {
            pressedKeys[keyCode] = true;
        } else if (event.getAction() == KeyEvent.ACTION_UP)
        {
            pressedKeys[keyCode] = false;
        }
        return false;
    }

    public boolean isKeyPressed(int keyCode)
    {
        return pressedKeys[keyCode];
    }

    public boolean isTouchDown(int pointer)
    {
        return touchHandler.isTouchDown(pointer);
    }

    public int getTouchX(int pointer)
    {
        float ratioX = (float) offscreenSurface.getWidth() / (float) surfaceView.getWidth();
        int x = touchHandler.getTouchX(pointer);
        x = (int)(x * ratioX);
        return x;
    }

    public int getTouchY(int pointer)
    {

        float ratioY = (float) offscreenSurface.getHeight() / (float) surfaceView.getHeight();
        int y = touchHandler.getTouchY(pointer);
        y = (int)(y * ratioY);
        return y;
    }

    private void fillEvents(){
     synchronized (myKeyEventBuffer) {
         for (int i = 0; i < myKeyEventBuffer.size(); i++) {
             myKeyEvents.add(myKeyEventBuffer.get(i));
         }
         myKeyEventBuffer.clear();
     }
        synchronized (touchEventBuffer){
        for (int i = 0; i < touchEvents.size(); i++) {
            touchEvents.add(touchEventBuffer.get(i));
        }
        touchEventBuffer.clear();
    }
    }
    private void freeEvents() {
        synchronized (myKeyEvents) {
            for (int i = 0; i < myKeyEventBuffer.size(); i++) {
                myKeyEventPool.free(myKeyEventBuffer.get(i));
            }
        }
        synchronized (touchEventBuffer) {
            for (int i = 0; i < touchEvents.size(); i++) {
                touchEventPool.free(touchEventBuffer.get(i));
            }

        }
    }

    public List<MyKeyEvent> getKeyEvents() {
        return myKeyEvents;
    }

    public List<TouchEvent> getTouchEvents(){
        return touchEvents;
    }

    public float[] getAccelerometer()
    {
        return accelerometer;
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy){

    }

    public void onSensorChanged(SensorEvent event){
        System.arraycopy(event.values, 0, accelerometer, 0, 3);
    }

    //This the the main method for the game Looooop
    public void run()
    {
        int frames = 0;
        long startTime = System.nanoTime();
        long currTime = System.nanoTime();
        while (true)
        {
            synchronized (stateChanges)
            {
                for (int i = 0; i < stateChanges.size(); i++)
                {
                    state = stateChanges.get(i);
                    if (state == State.Disposed)
                    {
                        //***** slide 55: one line
                        if (screen != null) screen.dispose();
                        Log.d("Game", "State is Disposed");
                    }  if (state == State.Paused)
                    {
                        //***** slide 55: one line
                        if (screen != null) screen.pause();
                        Log.d("Game", "State is Paused");
                    }  if (state == State.Resumed)
                    {
                        //***** slide 55: one line
                        if (screen != null) screen.resume();
                        state = State.Running;
                        Log.d("Game", "State is Resumed -> Running");
                    }
                }
                stateChanges.clear();
            }
            if (state == State.Running)
            {

                if (!surfaceHolder.getSurface().isValid()) continue;
                Canvas physicalCanvas = surfaceHolder.lockCanvas();
                // here we should do some drawing on the screen
                //canvas.drawColor(Color.YELLOW);
                //***** slide 57: one line

                fillEvents();
                currTime = System.nanoTime();
                if (screen != null) screen.update(((currTime-startTime)/1000000000.0f));
                startTime = currTime;
                freeEvents();
                src.left = 0;
                src.top = 0;
                src.right = offscreenSurface.getWidth() - 1;
                src.bottom = offscreenSurface.getHeight() - 1;
                dst.left = 0;
                dst.top = 0;
                dst.right = surfaceView.getWidth();
                dst.bottom = surfaceView.getHeight();
                physicalCanvas.drawBitmap(offscreenSurface, src, dst, null);
                surfaceHolder.unlockCanvasAndPost(physicalCanvas);
                //physicalCanvas = null;

            }
            frames = frames + 1;
            if (System.nanoTime() - startTime > 1000000000) {
                fps = frames;
                frames = 0;
                startTime = System.nanoTime();
            }

        }
    }

    public int getFrameRate(){
        return fps;
    }


    public void onPause()
    {
        super.onPause();
        synchronized (stateChanges)
        {
            if (isFinishing())
            {
                stateChanges.add(stateChanges.size(), State.Disposed);
                ((SensorManager)getSystemService(Context.SENSOR_SERVICE)).unregisterListener(this);
            } else
            {
                stateChanges.add(stateChanges.size(), State.Paused);
            }
        }
        try
        {
            mainLoopThread.join();
        } catch (InterruptedException e)
        {
        }
        if (isFinishing()){
            soundPool.release();
        }
    }

    public void onResume() {

        super.onResume();
        mainLoopThread = new Thread(this);
        mainLoopThread.start();
        synchronized (stateChanges)
        {
            stateChanges.add(stateChanges.size(), State.Resumed);
        }
    }
}










