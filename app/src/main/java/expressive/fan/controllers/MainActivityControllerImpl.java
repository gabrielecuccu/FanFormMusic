package expressive.fan.controllers;

import expressive.fan.core.MainActivityController;
import expressive.fan.core.MainActivityView;
import expressive.fan.states.InitState;
import expressive.fan.states.State;

public class MainActivityControllerImpl implements MainActivityController {

    private MainActivityView view;

    private State state;

    public MainActivityControllerImpl(MainActivityView view) {
        this.view = view;
        this.state = new InitState(this);
    }

    @Override
    public void playClicked() {
        state.playClicked();
    }

    @Override
    public void pauseClicked() {
        state.pauseClicked();
    }

    @Override
    public void stopClicked() {
        state.stopClicked();
    }

    @Override
    public void createPlayer() {
        view.createPlayer();
    }

    @Override
    public void play() {
        view.play();
    }

    @Override
    public void pause() {
        view.pause();
    }

    @Override
    public void stop() {
        view.stop();
    }

    @Override
    public void setPlayButtonEnabled(boolean b) {
        view.setPlayButtonEnabled(b);
    }

    @Override
    public void setPauseButtonEnabled(boolean b) {
        view.setPauseButtonEnabled(b);
    }

    @Override
    public void setStopButtonEnabled(boolean b) {
        view.setStopButtonEnabled(b);
    }

    @Override
    public void setNextState(State nextState) {
        state = nextState;
    }

    @Override
    public void progressChanged(int progress, boolean fromUser) {
        if (!fromUser) {
            return;
        }

        state.progressChanged(progress);
    }

    @Override
    public int getTotalAudioDuration() {
        return view.getTotalAudioDuration();
    }

    @Override
    public void seekTo(int newPos) {
        view.seekTo(newPos);
    }
}
