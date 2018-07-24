/*
 * Copyright (C) 2018 Stichting Akvo (Akvo Foundation)
 *
 * This file is part of Akvo Flow.
 *
 * Akvo Flow is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Akvo Flow is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Akvo Flow.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.akvo.mockcaddisfly.data

import timber.log.Timber
import java.util.*

class RandomTestResultGenerator {

    fun getResultValue(ranges: String): Double {
        Timber.i("ranges $ranges")
        if (ranges.isEmpty()) {
            return DEFAULT_VALUE
        } else {
            val split = ranges.split(",")
            return when {
                split.isEmpty() -> DEFAULT_VALUE
                split.size == 1 -> split[0].toDouble()
                else -> {
                    val max = split[split.size - 1].toDouble()
                    val bound = Math.max(max, 1.0).toInt() // cannot be less than 1
                    val randomDouble = Random().nextInt(bound).toDouble()
                    val min = split[0].toDouble()
                    Math.min(Math.max(randomDouble, min), max)
                }
            }
        }
    }

    companion object {

        const val DEFAULT_VALUE = 0.0
    }
}