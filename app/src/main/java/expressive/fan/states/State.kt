package expressive.fan.states

interface State {
    fun playClicked()
    fun pauseClicked()
    fun stopClicked()
    fun progressChangedByUser(progress: Int)
    fun progressChangedByScheduler()
    fun completed()
}