package dangkhoa.dmt.dapchau;


import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.provider.MediaStore;

public class SoundPlayer {

    private static SoundPool soundPool;
    private static int hitSound;
    private static int thuaSound;
    private static int thangSound;

    public SoundPlayer(Context context)
    {
        soundPool = new SoundPool(2, AudioManager.STREAM_MUSIC,0);

        hitSound = soundPool.load(context, R.raw.blop, 1);

        thuaSound = soundPool.load(context, R.raw.mission_failed_sound_effect, 1);

        thangSound = soundPool.load(context, R.raw.game_show_correct_answer, 1);
    }

    public void playHitSound()
    {
        soundPool.play(hitSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playThuaSound()
    {
        soundPool.play(thuaSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }

    public void playThangSound()
    {
        soundPool.play(thangSound, 1.0f, 1.0f, 1, 0, 1.0f);
    }
}
