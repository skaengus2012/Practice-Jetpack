package nlab.practice.jetpack.ui.slide

import androidx.databinding.ObservableField
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import nlab.practice.jetpack.repository.model.Track
import nlab.practice.jetpack.util.PlayController
import nlab.practice.jetpack.util.SchedulerFactory
import nlab.practice.jetpack.util.component.lifecycle.FragmentLifeCycle
import nlab.practice.jetpack.util.component.lifecycle.FragmentLifeCycleBinder
import nlab.practice.jetpack.util.slidingpanel.SlidingUpPanelLayoutUsecase
import javax.inject.Inject

/**
 * @author Doohyun
 */
class SlidingMainViewModel @Inject constructor(
        private val _playController: PlayController,
        private val _disposables: CompositeDisposable,
        private val _schedulerFactory: SchedulerFactory,
        private val _slidingUpPanelLayoutUsecase: SlidingUpPanelLayoutUsecase?,
        lifeCycleBinder: FragmentLifeCycleBinder) {

    val currentTrack = ObservableField<Track>()

    val artist = ObservableField<String>()

    init {
        lifeCycleBinder.bindUntil(FragmentLifeCycle.ON_VIEW_CREATED) {
            loadCurrentTrack()
        }
    }

    private fun loadCurrentTrack() {
        _playController.getCurrentTrack()
                .subscribeOn(_schedulerFactory.io())
                .observeOn(_schedulerFactory.ui())
                .doOnSuccess {
                    currentTrack.set(it)
                    artist.set("Suzy")
                }
                .subscribe()
                .addTo(_disposables)
    }

    fun onPanelCollapse() {
        _slidingUpPanelLayoutUsecase?.collapse()
    }

    fun onPlay() {
        _playController.play()
    }

    fun onPrev() {
        _playController.prev()
    }

    fun onNext() {
        _playController.next()
    }
}