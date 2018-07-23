package org.akvo.mockcaddisfly.data

import timber.log.Timber
import java.util.*

class RandomTestResultGenerator {

    fun getResultValue(ranges: String?): Double {
        Timber.i("ranges $ranges")
        if (ranges == null || ranges.isEmpty()) {
            return DEFAULT_VALUE
        } else {
            val split = ranges.split(",")
            return when {
                split.isEmpty() -> DEFAULT_VALUE
                split.size == 1 -> java.lang.Double.parseDouble(split[0])
                else -> {
                    val max = java.lang.Double.parseDouble(split[split.size - 1])
                    val bound = Math.max(max, 1.0).toInt() //cannot be less than 1
                    var randomDouble = Random().nextInt(bound).toDouble()
                    val min = java.lang.Double.parseDouble(split[0])
                    randomDouble = Math.min(Math.max(randomDouble, min), max)
                    randomDouble
                }
            }
        }
    }

    companion object {

        const val DEFAULT_VALUE = 0.0
    }
}