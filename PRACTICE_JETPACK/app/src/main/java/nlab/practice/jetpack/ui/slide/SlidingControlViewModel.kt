package nlab.practice.jetpack.ui.slide

import androidx.databinding.ObservableField
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.PlayerRepository
import nlab.practice.jetpack.repository.model.Track
import nlab.practice.jetpack.util.SchedulerFactory
import nlab.practice.jetpack.util.ToastHelper
import nlab.practice.jetpack.util.component.lifecycle.FragmentLifeCycle
import nlab.practice.jetpack.util.component.lifecycle.FragmentLifeCycleBinder
import nlab.practice.jetpack.util.slidingpanel.SlidingUpPanelLayoutUsecase
import nlab.practice.jetpack.util.slidingpanel.isHidden
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2019. 04. 18
 */
class SlidingControlViewModel @Inject constructor(
        fragmentLifeCycleBinder: FragmentLifeCycleBinder,
        private val _slidingUpPanelLayoutUsecase: SlidingUpPanelLayoutUsecase?,
        private val _toastHelper: ToastHelper,
        private val _playerRepository: PlayerRepository,
        private val _schedulerFactory: SchedulerFactory,
        private val _disposables: CompositeDisposable) {

    val currentTrack = ObservableField<Track>()

    init {
        fragmentLifeCycleBinder.bindUntil(FragmentLifeCycle.ON_VIEW_CREATED) {
            loadCurrentTrack()
        }
    }

    private fun loadCurrentTrack() {
        Single.fromCallable { _playerRepository.getRandomTrack() }
                .subscribeOn(_schedulerFactory.io())
                .observeOn(_schedulerFactory.ui())
                .doOnSuccess {
                    showPanelIfHidden()
                    currentTrack.set(it)
                }
                .subscribe()
                .addTo(_disposables)
    }

    private fun showPanelIfHidden() = _slidingUpPanelLayoutUsecase?.let {
        val isHidden = it.currentPanelState.isHidden()
        if (isHidden) {
            it.collapse()
        }
    }

    fun onClickPlayed() {
        _toastHelper.showToast(R.string.slide_up_panel_play_message)
    }

    fun onClickPrev() {
        _toastHelper.showToast(R.string.slide_up_panel_prev_message)
    }

    fun onClickNext() {
        _toastHelper.showToast(R.string.slide_up_panel_next_message)
    }

    fun onPanelLayoutClick() {
        _slidingUpPanelLayoutUsecase?.expand()
    }
}