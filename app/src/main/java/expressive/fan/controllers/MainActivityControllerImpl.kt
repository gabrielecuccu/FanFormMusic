package expressive.fan.controllers

import expressive.fan.core.MainActivityController
import expressive.fan.core.MainActivityView
import expressive.fan.states.InitState
import expressive.fan.states.InitialisedState
import expressive.fan.states.State
import java.util.*

class MainActivityControllerImpl(private val view: MainActivityView) : MainActivityController {

    private var state: State = InitState()

    override fun initialise() {
        setNextState(InitialisedState(this))
    }

    override fun playClicked() {
        state.playClicked()
    }

    override fun pauseClicked() {
        state.pauseClicked()
    }

    override fun stopClicked() {
        state.stopClicked()
    }

    override fun createPlayer() {
        view.createPlayer()
    }

    override fun play() {
        view.play()
    }

    override fun pause() {
        view.pause()
    }

    override fun stop() {
        view.stop()
    }

    override fun setPlayButtonEnabled(b: Boolean) {
        view.setPlayButtonEnabled(b)
    }

    override fun setPauseButtonEnabled(b: Boolean) {
        view.setPauseButtonEnabled(b)
    }

    override fun setStopButtonEnabled(b: Boolean) {
        view.setStopButtonEnabled(b)
    }

    override fun setNextState(nextState: State) {
        state = nextState
    }

    override fun progressChanged(progress: Int, fromUser: Boolean) {
        if (!fromUser) {
            return
        }
        state.progressChangedByUser(progress)
    }

    override fun startScheduler() {
        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                state.progressChangedByScheduler()
            }
        }
        val timer = Timer()
        timer.schedule(timerTask, 0, 200)
    }

    override fun updateProgress() {
        val curPos = view.getCurrentPosition().toFloat()
        val total = view.getTotalAudioDuration().toFloat()
        val progress = curPos / total * 100
        view.updateProgress(curPos.toInt(), progress.toInt())
    }

    override fun resetProgress() {
        view.updateProgress(0, 0)
    }

    override fun seekToNewPos(progress: Int) {
        val total = view.getTotalAudioDuration()
        val newPos = total / 100 * progress
        view.seekTo(newPos)
    }

    override fun completed() {
        state.completed()
    }
}