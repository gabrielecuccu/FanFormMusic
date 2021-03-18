package expressive.fan.controllers;

import expressive.fan.core.MainActivityController;
import expressive.fan.core.MainActivityView;

public class MainActivityControllerImpl implements MainActivityController {

    private MainActivityView view;

    public MainActivityControllerImpl(MainActivityView view) {
        this.view = view;
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
}
