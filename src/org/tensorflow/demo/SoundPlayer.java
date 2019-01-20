package org.tensorflow.demo;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import org.tensorflow.demo.env.Logger;


public class SoundPlayer {
    private static final Logger LOGGER = new Logger();

    private static SoundPool soundPool;

    private static int dontLookAtMe;
    private static int getOutOfTheWay;
    private static int newHuman;

    public SoundPlayer(Context context) {
        //soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC, 0);
        soundPool = new SoundPool.Builder().setMaxStreams(10).build();
        dontLookAtMe = soundPool.load(context, R.raw.dontlookatme, 1);
    }

    public void playDontLookAtMe() {
        LOGGER.i("corgdomination");
        soundPool.play(dontLookAtMe, 1.0f, 1.0f, 0, 0, 1.0f);
    }
}
