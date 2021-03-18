package expressive.fan.states;

import expressive.fan.core.MainActivityController;

public class PausedState extends AbstractState {

    private MainActivityController controller;

    public PausedState(MainActivityController controller) {
        this.controller = controller;
        this.controller.setPlayButtonEnabled(true);
        this.controller.setPauseButtonEnabled(false);
        this.controller.setStopButtonEnabled(true);
    }

    @Override
    public void playClicked() {
        controller.play();
        controller.setNextState(new PlayingState(controller));
    }

    @Override
    public void stopClicked() {
        controller.stop();
        controller.setNextState(new StoppedState(controller));
    }
}
