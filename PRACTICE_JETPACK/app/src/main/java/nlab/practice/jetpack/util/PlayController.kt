package nlab.practice.jetpack.util

import io.reactivex.Single
import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.PlayerRepository
import javax.inject.Inject

class PlayController @Inject constructor(
        private val _playerRepository: PlayerRepository, private val _toastHelper: ToastHelper) {

    fun getCurrentTrack() = Single.fromCallable { _playerRepository.getRandomTrack() }

    fun play() {
        _toastHelper.showToast(R.string.slide_up_panel_play_message)
    }

    fun prev() {
        _toastHelper.showToast(R.string.slide_up_panel_prev_message)
    }

    fun next() {
        _toastHelper.showToast(R.string.slide_up_panel_next_message)
    }
}
