package nlab.practice.jetpack.ui.history

import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.HistoryRepository
import nlab.practice.jetpack.ui.common.viewmodel.SimpleItemViewModel
import javax.inject.Inject

/**
 * @author Doohyun
 */
class HistoryViewModel @Inject constructor(
        historyRepository: HistoryRepository,
        private val historyItemViewModelFactory: HistoryItemViewModelFactory) {

    val headers = SimpleItemViewModel(R.layout.view_history_header).run { listOf(this) }

    val items = historyRepository.items.map { historyItemViewModelFactory.create(it) }
}