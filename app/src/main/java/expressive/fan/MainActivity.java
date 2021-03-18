package expressive.fan;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    private Steps steps = new Steps();

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
                        ((TextView) findViewById(R.id.textViewStep)).setText(steps.getStepAt(0));
                        ((TextView) findViewById(R.id.textViewTimer)).setText(formatTime(0));
                    } else {
                        float curPos = mediaPlayer.getCurrentPosition();
                        float total = mediaPlayer.getDuration();
                        float progress = curPos / total * 100;
                        ((SeekBar) findViewById(R.id.seekBar)).setProgress((int) progress);
                        ((TextView) findViewById(R.id.textViewStep)).setText(steps.getStepAt((int) curPos));
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