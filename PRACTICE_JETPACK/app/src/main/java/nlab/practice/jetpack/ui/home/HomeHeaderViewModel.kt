package nlab.practice.jetpack.ui.home

import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable
import nlab.practice.jetpack.util.recyclerview.anko.AnkoViewBindingItem
import org.jetbrains.anko.AnkoComponent
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2018. 12. 12
 */
class HomeHeaderViewModel : AnkoViewBindingItem() {

    @Inject lateinit var disposables: CompositeDisposable

    override fun getViewComponent(): AnkoComponent<ViewGroup> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}