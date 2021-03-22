package expressive.fan.states

abstract class AbstractState : State {
    override fun playClicked() {}
    override fun pauseClicked() {}
    override fun stopClicked() {}
    override fun progressChangedByUser(progress: Int) {}
    override fun progressChangedByScheduler() {}
    override fun completed() {}
}