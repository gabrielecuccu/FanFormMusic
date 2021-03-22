package expressive.fan

import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import expressive.fan.core.MainActivityController

class SeekBarChangeListener(private val controller: MainActivityController) : OnSeekBarChangeListener {
    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        controller.progressChanged(progress, fromUser)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {}
    override fun onStopTrackingTouch(seekBar: SeekBar?) {}

}