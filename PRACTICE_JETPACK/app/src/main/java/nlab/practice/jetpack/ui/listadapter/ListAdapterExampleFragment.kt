package nlab.practice.jetpack.ui.listadapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nlab.practice.jetpack.databinding.FragmentListAdapterExampleBinding
import nlab.practice.jetpack.util.di.fragment.InjectableFragment

/**
 * ListAdapter 예제 구현을 위한 Fragment
 *
 * @author Doohyun
 */
class ListAdapterExampleFragment: InjectableFragment() {

    lateinit var binding: FragmentListAdapterExampleBinding

    override fun onCreateBindingView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentListAdapterExampleBinding.inflate(inflater, container, false)
                .apply { binding = this }
                .root
    }

}