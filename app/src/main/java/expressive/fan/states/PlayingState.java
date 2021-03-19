package expressive.fan.states;

import expressive.fan.core.MainActivityController;

public class PlayingState extends AbstractState {

    private MainActivityController controller;

    public PlayingState(MainActivityController controller) {
        this.controller = controller;
        this.controller.setPlayButtonEnabled(false);
        this.controller.setPauseButtonEnabled(true);
        this.controller.setStopButtonEnabled(true);
    }

    @Override
    public void pauseClicked() {
        controller.pause();
        controller.setNextState(new PausedState(controller));
    }

    @Override
    public void stopClicked() {
        controller.stop();
        controller.setNextState(new StoppedState(controller));
    }

    @Override
    public void progressChanged(int progress) {
        int total = controller.getTotalAudioDuration();
        int newPos = total / 100 * progress;
        controller.seekTo(newPos);
    }
}
