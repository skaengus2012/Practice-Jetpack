package nlab.practice.jetpack.ui.slide

import androidx.databinding.ObservableField
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import nlab.practice.jetpack.repository.model.Track
import nlab.practice.jetpack.util.PlayController
import nlab.practice.jetpack.util.component.lifecycle.FragmentLifeCycle
import nlab.practice.jetpack.util.component.lifecycle.FragmentLifeCycleBinder
import nlab.practice.jetpack.util.slidingpanel.SlidingUpPanelLayoutUsecase
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2019. 04. 18
 */
class SlidingControlViewModel @Inject constructor(
        fragmentLifeCycleBinder: FragmentLifeCycleBinder,
        private val _slidingUpPanelLayoutUsecase: SlidingUpPanelLayoutUsecase?,
        private val _playController: PlayController,
        private val _disposables: CompositeDisposable) {

    val currentTrack = ObservableField<Track>()

    init {
        fragmentLifeCycleBinder.bindUntil(FragmentLifeCycle.ON_VIEW_CREATED) {
            loadCurrentTrack()
        }
    }

    private fun loadCurrentTrack() {
        _playController.trackChangeSubject
                .doOnNext { currentTrack.set(it) }
                .subscribe()
                .addTo(_disposables)
    }

    fun onClickPlayed() {
        _playController.play()
    }

    fun onClickPrev() {
        _playController.prev()
    }

    fun onClickNext() {
        _playController.next()
    }

    fun onPanelLayoutClick() {
        _slidingUpPanelLayoutUsecase?.expand()
    }
}