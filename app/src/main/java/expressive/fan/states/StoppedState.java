package expressive.fan.states;

import expressive.fan.core.MainActivityController;

public class StoppedState extends AbstractState {

    private MainActivityController controller;

    public StoppedState(MainActivityController controller) {
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
}
