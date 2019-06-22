package nlab.practice.jetpack.ui.history

import androidx.databinding.Bindable
import com.google.auto.factory.AutoFactory
import com.google.auto.factory.Provided
import nlab.practice.jetpack.R
import nlab.practice.jetpack.repository.model.History
import nlab.practice.jetpack.util.ResourceProvider
import nlab.practice.jetpack.util.recyclerview.binding.BindingItemViewModel

/**
 * @author Doohyun
 */
@AutoFactory
class HistoryItemViewModel(
        @Provided resourceProvider: ResourceProvider,
        private val history: History): BindingItemViewModel() {

    private val resultMessage = when(history.isSuccess) {
        true -> R.string.history_success
        false -> R.string.history_failed
    }.run { resourceProvider.getString(this).toString() }

    override fun getLayoutRes(): Int = R.layout.view_history_item

    @Bindable
    fun getTitle(): String = history.title

    @Bindable
    fun getSubTitle(): String = history.subTitle

    @Bindable
    fun getResultMessage(): String = resultMessage
}