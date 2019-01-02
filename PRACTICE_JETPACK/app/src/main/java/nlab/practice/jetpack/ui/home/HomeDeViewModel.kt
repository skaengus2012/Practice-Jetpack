package nlab.practice.jetpack.ui.home

import android.app.Application
import io.reactivex.disposables.CompositeDisposable
import nlab.practice.jetpack.R
import nlab.practice.jetpack.di.android.InjectableAndroidViewModel
import nlab.practice.jetpack.repository.TestMenuRepository
import nlab.practice.jetpack.ui.common.MainContainerViewModel
import nlab.practice.jetpack.util.databinding.model.RecyclerViewConfig
import nlab.practice.jetpack.util.recyclerview_de.anko.AnkoViewBindingItem
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2018. 12. 12
 */
class HomeDeViewModel(application: Application): InjectableAndroidViewModel(application), MainContainerViewModel {

    @Inject
    lateinit var disposables: CompositeDisposable

    @Inject
    lateinit var testMenuRepository: TestMenuRepository

    @Inject
    lateinit var homeItemDecoration: HomeItemDelDecoration

    private val homeHeaderViewModel = HomeHeaderDeViewModel(injector)

    init {
        injector.inject(this)
    }

    override fun onCleared() {
        stopHeaderTimer()
        disposables.clear()
    }

    override fun getHeader(): AnkoViewBindingItem? = homeHeaderViewModel

    override fun getItems(): List<AnkoViewBindingItem>? = listOf(
            createAnkoFirstViewModel(),
            createPagingTestViewModel()
    )

    override fun getRecyclerViewConfig(): RecyclerViewConfig? = RecyclerViewConfig().apply {
        addItemDecoration(homeItemDecoration)
    }

    private fun createAnkoFirstViewModel(): HomeItemDeViewModel = testMenuRepository.getAnkoFirstViewMenu().run {
        HomeItemDeViewModel(injector, this, R.id.nav_anko_first_activity)
    }

    private fun createPagingTestViewModel(): HomeItemDeViewModel = testMenuRepository.getPagingTestMenu().run {
        HomeItemDeViewModel(injector, this, R.id.nav_paging_test_fragment)
    }

    fun startHeaderTimer() {
        homeHeaderViewModel.startTimer()
    }

    fun stopHeaderTimer() {
        homeHeaderViewModel.stopTimer()
    }
}