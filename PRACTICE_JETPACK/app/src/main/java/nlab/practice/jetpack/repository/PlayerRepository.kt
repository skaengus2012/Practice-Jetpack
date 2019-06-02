package nlab.practice.jetpack.repository

import nlab.practice.jetpack.repository.model.Track
import nlab.practice.jetpack.util.RandomTestExecutor
import kotlin.random.Random

/**
 * @author Doohyun
 */
class PlayerRepository(private val _imageRepository: ImagePoolRepository) {

    private val _randomIndex = Random.nextInt(_imageRepository.getSize())

    fun getRandomTrack(): Track {
        RandomTestExecutor.delay(maxTime = 1500)
        return Track(image = _imageRepository.get(_randomIndex), title = "The $_randomIndex`s Track Playing...")
    }
}