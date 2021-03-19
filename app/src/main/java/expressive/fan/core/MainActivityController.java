package expressive.fan.core;

import expressive.fan.states.State;

public interface MainActivityController {
    void playClicked();
    void pauseClicked();
    void stopClicked();

    void createPlayer();
    void play();
    void pause();
    void stop();

    void setPlayButtonEnabled(boolean b);
    void setPauseButtonEnabled(boolean b);
    void setStopButtonEnabled(boolean b);

    void setNextState(State nextState);

    void progressChanged(int progress, boolean fromUser);

    void startScheduler();

    void updateProgress();

    void resetProgress();

    void seekToNewPos(int progress);

    void completed();
}
