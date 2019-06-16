package nlab.practice.jetpack.ui.centerscroll

import androidx.databinding.ObservableArrayList
import nlab.practice.jetpack.repository.LyricsRepository
import nlab.practice.jetpack.ui.common.viewmodel.SimpleTextItemViewModel
import nlab.practice.jetpack.util.recyclerview.LayoutManagerFactory
import nlab.practice.jetpack.util.recyclerview.RecyclerViewConfig
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewModel
import javax.inject.Inject

/**
 * @author Doohyun
 */
class CenterScrollViewModel @Inject constructor(
        lyricsRepository: LyricsRepository,
        layoutManagerFactory: LayoutManagerFactory) {

    val items = ObservableArrayList<BindingItemViewModel>()

    val recyclerViewConfig = RecyclerViewConfig().apply {
        layoutManager = layoutManagerFactory.createLinearLayoutManager()
    }

    init {
        lyricsRepository.getLyrics()
                .asSequence()
                .map { SimpleTextItemViewModel(it) }
                .run { items.addAll(this) }

    }
}