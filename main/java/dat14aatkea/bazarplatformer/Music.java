package dat14aatkea.bazarplatformer;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;

/**
 * Created by Alexander on 14-03-2016.
 */
public class Music implements MediaPlayer.OnCompletionListener {

    private MediaPlayer mediaPlayer;
    private boolean isPrep = false;

    public Music(AssetFileDescriptor afd){
        mediaPlayer = new MediaPlayer();
        try{
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.prepare();
            isPrep = true;
            mediaPlayer.setOnCompletionListener(this);
        } catch (IOException e){
            throw new RuntimeException("Could not load music");
        }
    }

    public void dispose(){
        if (mediaPlayer.isPlaying()) mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

        synchronized (this){
            isPrep = false;
        }
    }

    public boolean isLooping(){
        return mediaPlayer.isLooping();
    }

    public boolean isPlaying(){
        return mediaPlayer.isPlaying();
    }

    public boolean isStopped(){
        return !isPrep;
    }

    public void play(){
        if (mediaPlayer.isPlaying()) return;

        try {
            synchronized (this){
                if (!isPrep)mediaPlayer.prepare();
                mediaPlayer.start();
            }
        } catch (IOException e){
             e.printStackTrace();
        }
    }
    public void pause() {
        if(mediaPlayer.isPlaying()) mediaPlayer.pause();
    }

    public void setLooping(boolean looping){
        mediaPlayer.setLooping(looping);
    }

    public void stop(){
            synchronized (this){
                if (!isPrep) return;

                mediaPlayer.stop();
                isPrep = false;
            }
    }

    public void setVolume(float volume){
        mediaPlayer.setVolume(volume, volume);
    }

}
