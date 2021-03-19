package expressive.fan;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import expressive.fan.controllers.MainActivityControllerImpl;
import expressive.fan.core.MainActivityController;
import expressive.fan.core.MainActivityView;

public class MainActivity extends AppCompatActivity implements MainActivityView {

    private MainActivityController controller;

    private final Steps steps = new Steps();

    private MediaPlayer mediaPlayer;

    private Integer duration = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        controller = new MainActivityControllerImpl(this);

        findViewById(R.id.btn_play).setOnClickListener(view -> controller.playClicked());
        findViewById(R.id.btn_pause).setOnClickListener(view -> controller.pauseClicked());
        findViewById(R.id.btn_stop).setOnClickListener(view -> controller.stopClicked());

        ((SeekBar) findViewById(R.id.seekBar)).setOnSeekBarChangeListener(new SeekBarChangeListener(controller));

        controller.startScheduler();
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

    synchronized public void createPlayer() {
        mediaPlayer = MediaPlayer.create(this, R.raw.longing);
        mediaPlayer.setVolume(0.75f, 0.75f);
    }

    synchronized public void play() {
        mediaPlayer.start();
    }

    synchronized public void pause() {
        mediaPlayer.pause();
    }

    synchronized public void stop() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    @Override
    public void setPlayButtonEnabled(boolean b) {
        findViewById(R.id.btn_play).setEnabled(b);
    }

    @Override
    public void setPauseButtonEnabled(boolean b) {
        findViewById(R.id.btn_pause).setEnabled(b);
    }

    @Override
    public void setStopButtonEnabled(boolean b) {
        findViewById(R.id.btn_stop).setEnabled(b);
    }

    @Override
    public int getTotalAudioDuration() {
        if (duration == null) {
            synchronized (this) {
                duration = mediaPlayer.getDuration();
            }
        }

        return duration;
    }

    @Override
    synchronized public void seekTo(int newPos) {
        mediaPlayer.seekTo(newPos);
    }

    @Override
    public void updateProgress(int position, int progress) {
        runOnUiThread(() -> {
            ((SeekBar) findViewById(R.id.seekBar)).setProgress(progress);
            ((TextView) findViewById(R.id.textViewStep)).setText(steps.getStepAt(position));
            ((TextView) findViewById(R.id.textViewTimer)).setText(formatTime(position));
        });
    }

    @Override
    synchronized public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }
}