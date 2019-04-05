package nlab.practice.jetpack.ui.collapsingtoolbar

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.BehaviorSubject
import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.CoverRepository
import nlab.practice.jetpack.repository.PagingItemRepository
import nlab.practice.jetpack.ui.common.viewmodel.ListErrorPageViewModel
import nlab.practice.jetpack.util.ResourceProvider
import nlab.practice.jetpack.util.SchedulerFactory
import nlab.practice.jetpack.util.SnackBarHelper
import nlab.practice.jetpack.util.ToastHelper
import nlab.practice.jetpack.util.component.ActivityCommonUsecase
import nlab.practice.jetpack.util.component.lifecycle.ActivityLifeCycle
import nlab.practice.jetpack.util.component.lifecycle.ActivityLifeCycleBinder
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemListAdapter
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewModel
import javax.inject.Inject

/**
 * @author Doohyun
 */
class CollapsingToolbarViewModel @Inject constructor(
        lifeCycleBinder : ActivityLifeCycleBinder,
        private val _activityUsecase: ActivityCommonUsecase,
        private val _toolbarUsecase: ToolbarItemVisibilityUsecase,
        private val _disposables: CompositeDisposable,
        private val _schedulerFactory: SchedulerFactory,
        private val _coverRepository: CoverRepository,
        private val _pagingItemRepository: PagingItemRepository,
        private val _resourceProvider: ResourceProvider,
        private val _toastHelper: ToastHelper,
        private val _snackBarHelper: SnackBarHelper,
        private val _itemViewModelFactory: CollapsingPagingItemViewModelFactory) : ListErrorPageViewModel  {

    val coverImage: ObservableField<String> = ObservableField()

    val coverText = ObservableField<String>()

    val loadFinished = ObservableBoolean()

    val isShowCollapsingScrim = ObservableBoolean(false)

    val listAdapter = BindingItemListAdapter<BindingItemViewModel>()

    private val _listUpdateSubject = BehaviorSubject.create<List<BindingItemViewModel>>()

    private val _showErrorState = ObservableBoolean()

    init {
        lifeCycleBinder.bindUntil(ActivityLifeCycle.ON_CREATE) {
            refresh()
            observeEvent()
            _toolbarUsecase.initialize(false)
        }
    }

    private fun observeEvent() {
        _listUpdateSubject
                .subscribe { listAdapter.submitList(it) }
                .addTo(_disposables)

        _toolbarUsecase.scrimVisibilityChangeSubject
                .subscribe { isShowCollapsingScrim.set(it) }
                .addTo(_disposables)
    }

    private fun loadCover() = _coverRepository.getCover()
            .subscribeOn(_schedulerFactory.io())
            .observeOn(_schedulerFactory.ui())
            .map {{
                coverImage.set(it.imageUrl)
                coverText.set(it.title)
            }}

    private fun loadItems() = _pagingItemRepository.getItems(0, 100)
            .subscribeOn(_schedulerFactory.io())
            .observeOn(_schedulerFactory.ui())
            .map { it.withIndex().map {
                (index, item)
                ->
                _itemViewModelFactory.create(item) {
                    val messageRes = _resourceProvider.getString(R.string.collapsing_toolbar_ex_item_click)
                    _toastHelper.showToast(String.format(messageRes.toString(), index))
                }
            } }
            .map {{_listUpdateSubject.onNext(it)}}

    override fun isShowErrorView(): ObservableBoolean = _showErrorState

    override fun refresh() {
        loadFinished.set(false)

        Single.merge(loadCover(), loadItems())
                .toList()
                .observeOn(_schedulerFactory.ui())
                .doOnSuccess {
                    executors
                    ->
                    executors.forEach { it() }
                }
                .doOnSuccess {
                    _showErrorState.set(false)
                    loadFinished.set(true)
                }
                .doOnError { _showErrorState.set(true) }
                .subscribe()
                .addTo(_disposables)
    }

    fun onBackPress() = _activityUsecase.onBackPressed()

    fun onFabClickEvent() {
        _snackBarHelper.showSnackBar(R.string.collapsing_toolbar_fab_message, duration = 1500)
    }
}