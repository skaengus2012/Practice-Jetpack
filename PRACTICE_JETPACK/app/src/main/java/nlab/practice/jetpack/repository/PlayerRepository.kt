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