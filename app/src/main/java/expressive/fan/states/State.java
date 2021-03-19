package expressive.fan.states;

public interface State {

    void playClicked();
    void pauseClicked();
    void stopClicked();

    void progressChangedByUser(int progress);

    void progressChangedByScheduler();

    void completed();
}
