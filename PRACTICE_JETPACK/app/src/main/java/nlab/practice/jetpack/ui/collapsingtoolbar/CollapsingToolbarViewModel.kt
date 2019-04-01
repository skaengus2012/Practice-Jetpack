package nlab.practice.jetpack.ui.collapsingtoolbar

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import nlab.practice.jetpack.repository.CoverRepository
import nlab.practice.jetpack.repository.PagingItemRepository
import nlab.practice.jetpack.ui.common.viewmodel.ListErrorPageViewModel
import nlab.practice.jetpack.util.SchedulerFactory
import nlab.practice.jetpack.util.component.lifecycle.ActivityLifeCycle
import nlab.practice.jetpack.util.component.lifecycle.ActivityLifeCycleBinder
import javax.inject.Inject

/**
 * @author Doohyun
 */
class CollapsingToolbarViewModel @Inject constructor(
        lifeCycleBinder : ActivityLifeCycleBinder,
        private val _disposables: CompositeDisposable,
        private val _schedulerFactory: SchedulerFactory,
        private val _coverRepository: CoverRepository,
        private val _pagingItemRepository: PagingItemRepository,
        private val _itemViewModelFactory: CollapsingPagingItemViewModelFactory) : ListErrorPageViewModel  {

    val coverImage: ObservableField<String> = ObservableField()

    val coverText = ObservableField<String>()

    val loadFinished = ObservableBoolean()

    private val _showErrorState = ObservableBoolean()

    init {
        lifeCycleBinder.bindUntil(ActivityLifeCycle.ON_CREATE) {
            refresh()
        }

    }

    private fun loadCover() = _coverRepository.getCover()
            .subscribeOn(_schedulerFactory.io())
            .observeOn(_schedulerFactory.ui())
            .doOnSuccess {
                coverImage.set(it.imageUrl)
                coverText.set(it.title)
            }

    private fun loadItems() = _pagingItemRepository.getItems(0, 100)
            .subscribeOn(_schedulerFactory.io())
            .observeOn(_schedulerFactory.ui())
            .doOnSuccess {  }

    override fun isShowErrorView(): ObservableBoolean = _showErrorState

    override fun refresh() {
        loadFinished.set(false)

        Single.merge(loadCover(), loadItems())
                .toList()
                .observeOn(_schedulerFactory.ui())
                .doOnSuccess {
                    _showErrorState.set(false)
                    loadFinished.set(true)
                }
                .doOnError { _showErrorState.set(true) }
                .subscribe()
                .addTo(_disposables)
    }
}