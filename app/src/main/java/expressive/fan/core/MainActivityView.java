package expressive.fan.core;

public interface MainActivityView {
    void createPlayer();
    void play();
    void pause();
    void stop();

    void setPlayButtonEnabled(boolean b);
    void setPauseButtonEnabled(boolean b);
    void setStopButtonEnabled(boolean b);

    int getTotalAudioDuration();

    void seekTo(int newPos);

    void updateProgress(int position, int progress);

    int getCurrentPosition();
}
