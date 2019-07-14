/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nlab.practice.jetpack.util

import android.os.SystemClock
import java.lang.RuntimeException
import kotlin.random.Random

/**
 * @author Doohyun
 * @since 2019. 02. 01
 */
object RandomTestExecutor {

    fun error(percent: Int = 50) = Random.nextInt(100).takeIf { it <= percent }?.run {
        throw RuntimeException()
    }

    fun delay(minTime: Long = 100, maxTime: Long = 1000) = Random.nextLong(minTime, maxTime + 1).run {
        SystemClock.sleep(this)
    }
}