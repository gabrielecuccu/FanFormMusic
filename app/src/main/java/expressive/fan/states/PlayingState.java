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
    public void progressChangedByUser(int progress) {
        controller.seekToNewPos(progress);
    }

    @Override
    public void progressChangedByScheduler() {
        controller.updateProgress();
    }

    @Override
    public void completed() {
        controller.setNextState(new StoppedState(controller));
        controller.stop();
    }
}
