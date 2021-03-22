package expressive.fan.core

import expressive.fan.states.State

interface MainActivityController {
    fun playClicked()
    fun pauseClicked()
    fun stopClicked()
    fun createPlayer()
    fun play()
    fun pause()
    fun stop()
    fun setPlayButtonEnabled(b: Boolean)
    fun setPauseButtonEnabled(b: Boolean)
    fun setStopButtonEnabled(b: Boolean)
    fun setNextState(nextState: State)
    fun progressChanged(progress: Int, fromUser: Boolean)
    fun startScheduler()
    fun updateProgress()
    fun resetProgress()
    fun seekToNewPos(progress: Int)
    fun completed()
}