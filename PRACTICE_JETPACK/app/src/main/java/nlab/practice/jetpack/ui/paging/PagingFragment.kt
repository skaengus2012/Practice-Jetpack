package nlab.practice.jetpack.ui.paging

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import androidx.recyclerview.widget.LinearLayoutManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_paging.*
import nlab.practice.jetpack.databinding.FragmentPagingBinding
import nlab.practice.jetpack.util.di.fragment.InjectableFragment
import nlab.practice.jetpack.util.recyclerview.paging.BindingPagedListAdapter
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2018. 12. 10
 */
class PagingFragment : InjectableFragment() {

    @Inject
    lateinit var viewModel: PagingViewModel

    @Inject
    lateinit var dataSourceFactory: PagingItemPositionalDataSourceFactory

    lateinit var binding: FragmentPagingBinding

    var pagedList: PagedList<PagingItemViewModel>? = null

    override fun onCreateBindingView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPagingBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val config = PagedList.Config.Builder()
                .setInitialLoadSizeHint(20)
                .setPageSize(10)
                .setPrefetchDistance(5)
                .setEnablePlaceholders(false)
                .build()

        val builder = RxPagedListBuilder<Int, PagingItemViewModel>(object: DataSource.Factory<Int, PagingItemViewModel>() {
            override fun create(): DataSource<Int, PagingItemViewModel> {
                return dataSourceFactory.create().map { PagingItemViewModel(it) }
            }
        }, config)

        Observable.timer(6, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                .doOnNext { pagedList?.dataSource?.invalidate() }.subscribe()


        val pagedListAdapter: BindingPagedListAdapter<PagingItemViewModel> = BindingPagedListAdapter.create()

        lv_content.layoutManager = LinearLayoutManager(context)
        lv_content.adapter = pagedListAdapter

        builder.buildObservable()
                .doOnNext {
                    pagedList = it
                    pagedListAdapter.submitList(it) }
                .subscribe()
    }
}