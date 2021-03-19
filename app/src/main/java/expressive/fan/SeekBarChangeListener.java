package expressive.fan;

import android.widget.SeekBar;

import expressive.fan.core.MainActivityController;

public class SeekBarChangeListener implements SeekBar.OnSeekBarChangeListener {

    private MainActivityController controller;

    public SeekBarChangeListener(MainActivityController controller) {
        this.controller = controller;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        controller.progressChanged(progress, fromUser);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
