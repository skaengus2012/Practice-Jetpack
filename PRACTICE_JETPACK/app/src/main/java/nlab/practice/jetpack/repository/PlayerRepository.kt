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

package nlab.practice.jetpack.repository

import nlab.practice.jetpack.repository.model.Track
import nlab.practice.jetpack.util.RandomTestExecutor
import kotlin.random.Random

/**
 * @author Doohyun
 */
class PlayerRepository(private val imageRepository: ImagePoolRepository) {

    private val randomIndex = Random.nextInt(imageRepository.getSize())

    fun getRandomTrack(): Track {
        RandomTestExecutor.delay(maxTime = 1500)
        return Track(image = imageRepository.get(randomIndex), title = "The $randomIndex`s Track Playing...")
    }
}