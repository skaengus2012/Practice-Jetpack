package nlab.practice.jetpack.ui.paging

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import nlab.practice.jetpack.util.nav.FragmentNavUsecase
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author Doohyun
 */
class PagingViewModel @Inject constructor(
        private val _disposables: CompositeDisposable,
        private val _fragmentNavUsecase: FragmentNavUsecase,
        private val _androidScheduler: Scheduler) {

    init {
        Observable.timer(5, TimeUnit.SECONDS)
                .observeOn(_androidScheduler)
                .doOnNext { _fragmentNavUsecase.navIntroduce() }
                .subscribe()
                .addTo(_disposables)
    }
}