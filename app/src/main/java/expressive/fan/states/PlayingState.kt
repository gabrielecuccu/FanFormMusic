package expressive.fan.states

import expressive.fan.core.MainActivityController

class PlayingState(private val controller: MainActivityController) : AbstractState() {
    override fun pauseClicked() {
        controller.pause()
        controller.setNextState(PausedState(controller))
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

    override fun completed() {
        controller.setNextState(StoppedState(controller))
        controller.stop()
    }

    init {
        controller.setPlayButtonEnabled(false)
        controller.setPauseButtonEnabled(true)
        controller.setStopButtonEnabled(true)
    }
}