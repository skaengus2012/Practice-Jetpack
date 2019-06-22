package nlab.practice.jetpack.util

import io.reactivex.subjects.BehaviorSubject
import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.model.Track
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayController @Inject constructor(private val toastHelper: ToastHelper) {

    val trackChangeSubject: BehaviorSubject<Track> = BehaviorSubject.create()

    fun play() {
        toastHelper.showToast(R.string.slide_up_panel_play_message)
    }

    fun prev() {
        toastHelper.showToast(R.string.slide_up_panel_prev_message)
    }

    fun next() {
        toastHelper.showToast(R.string.slide_up_panel_next_message)
    }
}
