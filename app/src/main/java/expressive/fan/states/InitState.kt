package expressive.fan.states

import expressive.fan.core.MainActivityController

class InitState(private val controller: MainActivityController) : AbstractState() {
    override fun playClicked() {
        controller.createPlayer()
        controller.play()
        controller.setNextState(PlayingState(controller))
    }

    override fun progressChangedByUser(progress: Int) {
        controller.createPlayer()
        controller.seekToNewPos(progress)
        controller.setNextState(PausedState(controller))
    }

    override fun progressChangedByScheduler() {
        controller.resetProgress()
    }

    init {
        controller.setPlayButtonEnabled(true)
        controller.setPauseButtonEnabled(false)
        controller.setStopButtonEnabled(false)
    }
}