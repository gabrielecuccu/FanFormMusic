package expressive.fan.states

import expressive.fan.core.MainActivityController

class PausedState(private val controller: MainActivityController) : AbstractState() {
    override fun playClicked() {
        controller.play()
        controller.setNextState(PlayingState(controller))
    }

    override fun stopClicked() {
        controller.stop()
        controller.setNextState(StoppedState(controller))
    }

    override fun progressChangedByUser(progress: Int) {
        controller.seekToNewPos(progress)
    }

    override fun progressChangedByScheduler() {
        controller.updateProgress()
    }

    init {
        controller.setPlayButtonEnabled(true)
        controller.setPauseButtonEnabled(false)
        controller.setStopButtonEnabled(true)
    }
}