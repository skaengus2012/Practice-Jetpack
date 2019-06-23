package nlab.practice.jetpack.ui.centerscroll

import androidx.databinding.ObservableArrayList
import nlab.practice.jetpack.repository.LyricsRepository
import nlab.practice.jetpack.ui.common.viewmodel.SimpleTextItemViewModel
import nlab.practice.jetpack.util.recyclerview.CenteringRecyclerViewUsecase
import nlab.practice.jetpack.util.recyclerview.LayoutManagerFactory
import nlab.practice.jetpack.util.recyclerview.RecyclerViewConfig
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

/**
 * @author Doohyun
 */
class CenterScrollViewModel @Inject constructor(
        lyricsRepository: LyricsRepository,
        layoutManagerFactory: LayoutManagerFactory,
        private val recyclerViewUsecase: CenteringRecyclerViewUsecase) {

    private var currentScrollIndex = 0

    val items = ObservableArrayList<SimpleTextItemViewModel>()

    val recyclerViewConfig = RecyclerViewConfig().apply {
        layoutManager = layoutManagerFactory.createLinearLayoutManager()
    }

    init {
        lyricsRepository.getLyrics()
                .asSequence()
                .map { SimpleTextItemViewModel(it) }
                .run { items.addAll(this) }

    }

    fun scrollToPrevIndex() {
        currentScrollIndex = max(0, currentScrollIndex - 1)

        scrollToIndexInternal(currentScrollIndex)
    }

    fun scrollToNextIndex() {
        currentScrollIndex = min(items.size - 1, currentScrollIndex + 1)

        scrollToIndexInternal(currentScrollIndex)
    }

    private fun scrollToIndexInternal(currentIndex: Int) {
        for ((index, value) in items.withIndex()) {
            value.selected = (index == currentIndex)
        }

        recyclerViewUsecase.center(currentIndex)
    }
}