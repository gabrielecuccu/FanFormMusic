package expressive.fan

import java.util.*

class Steps : HashMap<Int?, String?>() {
    fun getStepAt(millis: Int): String? {
        return get(keys.stream().filter { s: Int? -> s!! <= millis }.max { obj: Int?, anotherInteger: Int? -> obj!!.compareTo(anotherInteger!!) }.get())
    }

    fun getProgressOfStepAt(millis: Int): Int {
        val start = keys.stream().filter { s: Int? -> s!! <= millis }.max { obj: Int?, anotherInteger: Int? -> obj!!.compareTo(anotherInteger!!) }.get()
        val end = keys.stream().filter { s: Int? -> s!! > millis }.min { obj: Int?, anotherInteger: Int? -> obj!!.compareTo(anotherInteger!!) }.get()
        val stepDuration = end - start
        val positionInStep = millis - start
        return (positionInStep.toFloat() / stepDuration.toFloat() * 100).toInt()
    }

    init {
        put(0, "Wu Gi stance")
        put(28000, "Opening the form")
        put(49000, "Embrace the moon")
        put(58000, "Toss")
        put(63000, "Fan pointing back diagonal")
        put(68000, "Open the fan")
        put(77000, "Swallow swoops on the water")
        put(80000, "Open the fan")
        put(84000, "Dr. Hua Tuo lowers the blind")
        put(88000, "Yellow nightingale's descent")
        put(90000, "Phoenix dances in a circle")
        put(98000, "Black dragon shakes its tail")
        put(103000, "Turn around and strike the tiger")
        put(105000, "Open the fan")
        put(106000, "Close the fan")
        put(107000, "Spirit dragon turns its head")
        put(111000, "Pluck the lotus from the lake (DOWN)")
        put(113000, "Pluck the lotus from the lake (UP)")
        put(120000, "Wild goose flies south")
        put(128000, "Low hanging stance")
        put(130000, "Zhoajun catches butterflies (golden cockerel)")
        put(134000, "(right fighting stance)")
        put(138000, "(right lady stance) -> Flood dragon plays with the pearl")
        put(142000, "(catch the fan) -> Roc spreads its wings")
        put(150000, "(Open the fan)")
        put(160000, "Waiting for Mayumi :)")
        put(306000, "END")
    }
}