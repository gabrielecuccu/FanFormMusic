package expressive.fan.core

interface MainActivityView {
    fun createPlayer()
    fun play()
    fun pause()
    fun stop()
    fun setPlayButtonEnabled(b: Boolean)
    fun setPauseButtonEnabled(b: Boolean)
    fun setStopButtonEnabled(b: Boolean)
    fun getTotalAudioDuration(): Int
    fun seekTo(newPos: Int)
    fun updateProgress(position: Int, progress: Int)
    fun getCurrentPosition(): Int
}