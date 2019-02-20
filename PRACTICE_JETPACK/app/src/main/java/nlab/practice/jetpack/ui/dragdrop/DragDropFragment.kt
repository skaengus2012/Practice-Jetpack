package nlab.practice.jetpack.ui.dragdrop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import nlab.practice.jetpack.databinding.FragmentDragDropBinding
import nlab.practice.jetpack.util.di.fragment.InjectableFragment

/**
 * Drag N Drop 을 수행하는 화면 정의
 *
 * @author Doohyun
 */
class DragDropFragment : InjectableFragment() {

    override fun onCreateBindingView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return FragmentDragDropBinding.inflate(inflater, container, false)
                .root
    }
}