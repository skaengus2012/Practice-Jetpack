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
 */
class SlidingMainViewModel @Inject constructor(
        private val playController: PlayController,
        private val disposables: CompositeDisposable,
        private val slidingUpPanelLayoutUsecase: SlidingUpPanelLayoutUsecase?,
        lifeCycleBinder: FragmentLifeCycleBinder) {

    val currentTrack = ObservableField<Track>()

    val artist = ObservableField<String>()

    init {
        lifeCycleBinder.bindUntil(FragmentLifeCycle.ON_VIEW_CREATED) {
            loadCurrentTrack()
        }
    }

    private fun loadCurrentTrack() {
        playController.trackChangeSubject
                .doOnNext {
                    currentTrack.set(it)
                    artist.set("Suzy")
                }
                .subscribe()
                .addTo(disposables)
    }

    fun onPanelCollapse() {
        slidingUpPanelLayoutUsecase?.collapse()
    }

    fun onPlay() {
        playController.play()
    }

    fun onPrev() {
        playController.prev()
    }

    fun onNext() {
        playController.next()
    }
}