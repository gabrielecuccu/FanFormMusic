package expressive.fan.states;

import expressive.fan.core.MainActivityController;

public class InitState extends AbstractState {

    private MainActivityController controller;

    public  InitState(MainActivityController controller) {
        this.controller = controller;
        this.controller.setPlayButtonEnabled(true);
        this.controller.setPauseButtonEnabled(false);
        this.controller.setStopButtonEnabled(false);
    }

    @Override
    public void playClicked() {
        controller.createPlayer();
        controller.play();
        controller.setNextState(new PlayingState(controller));
    }

    @Override
    public void progressChanged(int progress) {
        controller.createPlayer();
        int total = controller.getTotalAudioDuration();
        int newPos = total / 100 * progress;
        controller.seekTo(newPos);
        controller.setNextState(new PausedState(controller));
    }
}
