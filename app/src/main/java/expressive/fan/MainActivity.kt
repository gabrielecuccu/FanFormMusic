package expressive.fan

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import expressive.fan.controllers.MainActivityControllerImpl
import expressive.fan.core.MainActivityController
import expressive.fan.core.MainActivityView
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), MainActivityView {
    private var controller: MainActivityController? = null
    private val steps: Steps = Steps()
    private var mediaPlayer: MediaPlayer? = null
    private var duration: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)
        controller = MainActivityControllerImpl(this)
        findViewById<View?>(R.id.btn_play).setOnClickListener { controller!!.playClicked() }
        findViewById<View?>(R.id.btn_pause).setOnClickListener { controller!!.pauseClicked() }
        findViewById<View?>(R.id.btn_stop).setOnClickListener { controller!!.stopClicked() }
        (findViewById<View?>(R.id.seekBar) as SeekBar).setOnSeekBarChangeListener(SeekBarChangeListener(controller!!))
        controller!!.startScheduler()
    }

    @SuppressLint("DefaultLocale")
    private fun formatTime(millis: Long): String? {
        return String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)))
    }

    @Synchronized
    override fun createPlayer() {
        mediaPlayer = MediaPlayer.create(this, R.raw.longing)
        mediaPlayer?.setVolume(0.75f, 0.75f)
        mediaPlayer?.setOnCompletionListener { controller!!.completed() }
    }

    @Synchronized
    override fun play() {
        mediaPlayer?.start()
    }

    @Synchronized
    override fun pause() {
        mediaPlayer?.pause()
    }

    @Synchronized
    override fun stop() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun setPlayButtonEnabled(b: Boolean) {
        findViewById<View?>(R.id.btn_play).isEnabled = b
    }

    override fun setPauseButtonEnabled(b: Boolean) {
        findViewById<View?>(R.id.btn_pause).isEnabled = b
    }

    override fun setStopButtonEnabled(b: Boolean) {
        findViewById<View?>(R.id.btn_stop).isEnabled = b
    }

    override fun getTotalAudioDuration(): Int {
        if (duration == null) {
            synchronized(this) { duration = mediaPlayer?.duration }
        }
        return duration!!
    }

    @Synchronized
    override fun seekTo(newPos: Int) {
        mediaPlayer?.seekTo(newPos)
    }

    override fun updateProgress(position: Int, progress: Int) {
        runOnUiThread {
            (findViewById<View?>(R.id.seekBar) as SeekBar).progress = progress
            (findViewById<View?>(R.id.progressBar) as ProgressBar).progress = steps.getProgressOfStepAt(position)
            (findViewById<View?>(R.id.textViewStep) as TextView).text = steps.getStepAt(position)
            (findViewById<View?>(R.id.textViewTimer) as TextView).text = formatTime(position.toLong())
        }
    }

    @Synchronized
    override fun getCurrentPosition(): Int {
        return mediaPlayer?.currentPosition ?: 0
    }
}