package nlab.practice.jetpack.util.recyclerview_de.anko

import android.view.ViewGroup
import androidx.databinding.BaseObservable
import org.jetbrains.anko.AnkoComponent

/**
 * RecyclerView 에 들어갈 ViewModel 요소
 *
 * @author Doohyun
 */
abstract class AnkoViewBindingItem : BaseObservable() {

    /**
     * 정의된 Anko Component
     *
     * @return View 로 사용할 Layout Component 정보
     */
    abstract fun getViewComponent() : AnkoComponent<ViewGroup>
}