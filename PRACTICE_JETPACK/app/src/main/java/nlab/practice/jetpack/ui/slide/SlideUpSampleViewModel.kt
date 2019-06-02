package nlab.practice.jetpack.ui.slide

import androidx.databinding.ObservableBoolean
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.PagingItemRepository
import nlab.practice.jetpack.ui.common.viewmodel.ListErrorPageViewModel
import nlab.practice.jetpack.ui.listadapter.ListAdapterExampleItemDecoration
import nlab.practice.jetpack.ui.listadapter.ListAdapterExampleItemViewModel
import nlab.practice.jetpack.ui.listadapter.ListAdapterExampleItemViewModelFactory
import nlab.practice.jetpack.util.SchedulerFactory
import nlab.practice.jetpack.util.component.ActivityCommonUsecase
import nlab.practice.jetpack.util.component.callback.ActivityCallback
import nlab.practice.jetpack.util.component.lifecycle.ActivityLifeCycle
import nlab.practice.jetpack.util.component.lifecycle.ActivityLifeCycleBinder
import nlab.practice.jetpack.util.recyclerview.LayoutManagerFactory
import nlab.practice.jetpack.util.recyclerview.RecyclerViewConfig
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemListAdapter
import nlab.practice.jetpack.util.slidingpanel.SlidingUpPanelLayoutUsecase
import nlab.practice.jetpack.util.slidingpanel.isExpanded
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2019. 04. 18
 */
class SlideUpSampleViewModel @Inject constructor(
        private val _disposable: CompositeDisposable,
        private val _listAdapterExampleFactory: ListAdapterExampleItemViewModelFactory,
        private val _pagingRepository: PagingItemRepository,
        private val _schedulerFactory: SchedulerFactory,
        layoutManagerFactory: LayoutManagerFactory,
        itemDecoration: ListAdapterExampleItemDecoration,
        lifeCycleBinder: ActivityLifeCycleBinder,
        activityCallback: ActivityCallback,
        activityCommonUsecase: ActivityCommonUsecase,
        slidingUpPanelLayoutUsecase: SlidingUpPanelLayoutUsecase): ListErrorPageViewModel {

    companion object {
        const val SPAN_COUNT = 2
    }

    val listAdapter = BindingItemListAdapter<ListAdapterExampleItemViewModel>()

    val recyclerViewConfig = RecyclerViewConfig().apply {
        layoutManager = layoutManagerFactory.createGridLayoutManager(SPAN_COUNT)
        itemDecorations.add(itemDecoration)
    }

    private val _isShowErrorView = ObservableBoolean(false)

    private val _listUpdateSubject: BehaviorSubject<List<ListAdapterExampleItemViewModel>> = BehaviorSubject.createDefault(ArrayList())

    init {
        lifeCycleBinder.bindUntil(ActivityLifeCycle.ON_CREATE) {
            slidingUpPanelLayoutUsecase.initialize()
            refresh()

            activityCommonUsecase.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        lifeCycleBinder.bindUntil(ActivityLifeCycle.FINISH) {
            activityCommonUsecase.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        activityCallback.onBackPressed { when {
            slidingUpPanelLayoutUsecase.currentPanelState.isExpanded() -> {
                slidingUpPanelLayoutUsecase.collapse()
                true
            }

            else -> false
        }}

        subscribeItemChanged()
    }

    private fun subscribeItemChanged() {
        _listUpdateSubject
                .doOnNext { listAdapter.submitList(it) }
                .subscribe()
                .addTo(_disposable)
    }

    override fun isShowErrorView(): ObservableBoolean = _isShowErrorView

    override fun refresh() {
        _pagingRepository.getItems(0, 100)
                .subscribeOn(_schedulerFactory.io())
                .observeOn(_schedulerFactory.ui())
                .doOnSuccess {
                    _isShowErrorView.set(false)

                    it.map(_listAdapterExampleFactory::create).run { _listUpdateSubject.onNext(this) }
                }
                .doOnError {
                    _isShowErrorView.set(true)
                }
                .subscribe()
                .addTo(_disposable)
    }
}