package nlab.practice.jetpack.ui.paging

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nlab.practice.jetpack.ui.main.ChildFragmentModule
import nlab.practice.jetpack.util.di.fragment.InjectableFragment
import nlab.practice.jetpack.util.recyclerview.paging.positional.PositionalPagingModule

/**
 * @author Doohyun
 * @since 2019. 01. 29
 */
class UnboundedPagingFragment : InjectableFragment() {

    override fun onCreateBindingView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @dagger.Module(includes = [
        ChildFragmentModule::class,
        PositionalPagingModule::class
    ])
    class Module
}