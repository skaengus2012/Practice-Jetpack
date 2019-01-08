package nlab.practice.jetpack.ui.paging

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import androidx.recyclerview.widget.DiffUtil
import nlab.practice.jetpack.databinding.FragmentPagingBinding
import nlab.practice.jetpack.repository.model.PagingItem
import nlab.practice.jetpack.util.di.fragment.InjectableFragment
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2018. 12. 10
 */
class PagingFragment : InjectableFragment() {

    @Inject
    lateinit var viewModel: PagingViewModel

    @Inject
    lateinit var dataSource: PagingItemPositionalDataSource

    lateinit var binding: FragmentPagingBinding

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
                .setEnablePlaceholders(true)
                .build()

        val builder = RxPagedListBuilder<Int, PagingItem>(object: DataSource.Factory<Int, PagingItem>() {
            override fun create(): DataSource<Int, PagingItem> {
                return dataSource
            }
        }, config)

        val callback = object: DiffUtil.ItemCallback<PagingItem>() {
            override fun areItemsTheSame(oldItem: PagingItem, newItem: PagingItem): Boolean {
                return oldItem.itemId == newItem.itemId
            }

            override fun areContentsTheSame(oldItem: PagingItem, newItem: PagingItem): Boolean {
                return oldItem.itemId == newItem.itemId && oldItem.title == newItem.title
            }
        }
/**
        lv_content.adapter = BindingPagedListAdapter<PageableItemViewModel<PagingItem>>(callback)
        */
    }
}