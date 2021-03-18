package expressive.fan;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    private HashMap<Integer, String> steps = new HashMap<Integer, String>() {{
        put(0, "Wu Gi stance");
        put(28000, "Opening the form");
        put(49000, "Embrace the moon");
        put(58000, "Toss");
        put(63000, "Fan pointing back diagonal");
        put(68000, "Open the fan");
        put(77000, "Swallow swoops on the water");
        put(80000, "Open the fan");
        put(84000, "Dr. Hua Tuo lowers the blind");
        put(88000, "Yellow nightingale's descent");
        put(90000, "Phoenix dances in a circle");
        put(98000, "Black dragon shakes its tail");
        put(103000, "Turn around and strike the tiger");
        put(105000, "Open the fan");
        put(106000, "Close the fan");
        put(107000, "Spirit dragon turns its head");
        put(111000, "Pluck the lotus from the lake (DOWN)");
        put(113000, "Pluck the lotus from the lake (UP)");
        put(120000, "Wild goose flies south");

        put(128000, "Low hanging stance");
        put(130000, "Zhoajun catches butterflies (golden cockerel)");
        put(134000, "(right fighting stance)");
        put(138000, "(right lady stance) -> Flood dragon plays with the pearl");
        put(142000, "(catch the fan) -> Roc spreads its wings");
        put(150000, "(Open the fan)");
        put(160000, "Waiting for Mayumi :)");
    }};

    @RequiresApi(api = Build.VERSION_CODES.N)
    private String getStep(int millis) {
        Set<Integer> keys = steps.keySet();
        int max = keys.stream().filter(s -> s <= millis).max(Integer::compareTo).get();
        return steps.get(max);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_play).setOnClickListener(view -> play());
        findViewById(R.id.btn_pause).setOnClickListener(view -> pause());
        findViewById(R.id.btn_stop).setOnClickListener(view -> stop());

        findViewById(R.id.btn_play).setEnabled(true);
        findViewById(R.id.btn_pause).setEnabled(false);
        findViewById(R.id.btn_stop).setEnabled(false);

        ((SeekBar) findViewById(R.id.seekBar)).setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!fromUser) {
                    return;
                }

                if (mediaPlayer == null) {
                    return;
                }

                int total = mediaPlayer.getDuration();
                int newPos = total / 100 * progress;
                mediaPlayer.seekTo(newPos);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(() -> {
                    if (mediaPlayer == null) {
                        ((SeekBar) findViewById(R.id.seekBar)).setProgress(0);
                        ((TextView) findViewById(R.id.textViewStep)).setText(getStep(0));
                        ((TextView) findViewById(R.id.textViewTimer)).setText(formatTime(0));
                    } else {
                        float curPos = mediaPlayer.getCurrentPosition();
                        float total = mediaPlayer.getDuration();
                        float progress = curPos / total * 100;
                        ((SeekBar) findViewById(R.id.seekBar)).setProgress((int) progress);
                        ((TextView) findViewById(R.id.textViewStep)).setText(getStep((int) curPos));
                        ((TextView) findViewById(R.id.textViewTimer)).setText(formatTime((long) curPos));
                    }
                });
            }
        };

        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 200);
    }

    @SuppressLint("DefaultLocale")
    private String formatTime(long millis) {
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
    }

    private void play() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.longing);
            mediaPlayer.setVolume(0.75f, 0.75f);
            mediaPlayer.start();

            findViewById(R.id.btn_play).setEnabled(false);
            findViewById(R.id.btn_pause).setEnabled(true);
            findViewById(R.id.btn_stop).setEnabled(true);
        } else {
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();

                findViewById(R.id.btn_play).setEnabled(false);
                findViewById(R.id.btn_pause).setEnabled(true);
                findViewById(R.id.btn_stop).setEnabled(true);
            }
        }
    }

    private void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            findViewById(R.id.btn_play).setEnabled(true);
            findViewById(R.id.btn_pause).setEnabled(false);
            findViewById(R.id.btn_stop).setEnabled(true);
        }
    }

    private void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;

            findViewById(R.id.btn_play).setEnabled(true);
            findViewById(R.id.btn_pause).setEnabled(false);
            findViewById(R.id.btn_stop).setEnabled(false);
        }
    }
}