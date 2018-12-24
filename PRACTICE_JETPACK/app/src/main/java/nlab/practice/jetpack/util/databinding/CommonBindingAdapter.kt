package nlab.practice.jetpack.util.databinding

import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import androidx.databinding.ObservableList
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import nlab.practice.jetpack.util.databinding.callback.SimpleOnListChangedCallback
import nlab.practice.jetpack.util.databinding.callback.WeakPropertyChangedCallback
import nlab.practice.jetpack.util.databinding.model.RecyclerViewConfig
import nlab.practice.jetpack.util.recyclerview_de.anko.AnkoViewBindingAdapter
import nlab.practice.jetpack.util.recyclerview_de.anko.AnkoViewBindingItem
import java.lang.ref.WeakReference

/**
 * [observable] 의 변화를 감지하여, [TARGET] 에 변경사실을 전달하여, [observableConsumer] 를 동작시키는 함수
 *
 * @param observable 적용할 데이터
 * @param propertyIds 감시할 propertyId 목록
 * @param observableConsumer 데이터에 대한 활용 함수
 * @return 요청자 본인
 */
inline fun <TARGET, OBS: BaseObservable> TARGET.drive(
        observable: OBS,
        vararg propertyIds: Int,
        crossinline observableConsumer: TARGET.(viewModel: OBS) -> Unit) : TARGET {
    // observableConsumer 를 그대로 사용하면, memory leak 이 발생, 객체로 변환처리
    val convertBiConsumer: (TARGET, OBS) -> Unit = {
        target, obs -> target.observableConsumer(obs)
    }

    convertBiConsumer(this, observable)
    observable.addOnWeakPropertyChangedCallback(WeakReference(this), propertyIds, convertBiConsumer)

    return this
}

/**
 * [OBS] 에 OnPropertyChangedCallback 을 추가한다.
 *
 * 메모리 Leak 을 방지하고자, [TARGET] 을 [weakTargetRef] 형태로 받는다.
 *
 * @param weakTargetRef TARGET::drive 로 부터 약한참조로 받아야 메모리 릭에서 벗어날 수 있음
 * @param propertyIds   감시할 propertyId 목록
 * @param biConsumer    데이터 변경에 대한 감지 메소드
 */
fun <TARGET, OBS: BaseObservable> OBS.addOnWeakPropertyChangedCallback(
        weakTargetRef: WeakReference<TARGET>,
        propertyIds: IntArray,
        biConsumer: (target: TARGET, observable: OBS) -> Unit) {

    val obs = this
    addOnPropertyChangedCallback(object: WeakPropertyChangedCallback<TARGET>(weakTargetRef) {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
            targetRef.get()?.run {
                if (propertyIds.isEmpty() or (propertyId == BR._all) or (propertyId in propertyIds)) {
                    biConsumer(this, obs)
                }
            }
        }
    })
}

/**
 * [items], [headerItem], [footerItem] 을 RecyclerView 에 적용 시킨다.
 */
fun <TARGET: RecyclerView, LIST: List<ITEM>, ITEM: AnkoViewBindingItem> TARGET.driveList(
        items: LIST? = null,
        headerItem: ITEM? = null,
        footerItem: ITEM? = null,
        config: RecyclerViewConfig? = null,
        callback: ObservableList.OnListChangedCallback<ObservableList<ITEM>>? = null) {

    (config ?: RecyclerViewConfig()).bindRecyclerView(this)

    AnkoViewBindingAdapter<ITEM>().apply {
        header = headerItem
        footer = footerItem
        this.items = items?.toMutableList()
    }.let {
        adapter
        ->
        this.adapter = adapter
        adapter.notifyItemViewTypeChanged()
        adapter.notifyDataSetChanged()

        if (adapter.items != null) {
            @Suppress("UNCHECKED_CAST")
            (adapter.items as? ObservableList<ITEM>)?.run { addOnWeakListChangedCallback(WeakReference(adapter), callback) }
        }
    }
}

/**
 * 아이템 변경에 대한 리스너 적용
 *
 * [callback] 이 Null 이라면 자동으로 SimpleOnListChangedCallback 의 구현체가 생성
 */
private fun <TARGET: AnkoViewBindingAdapter<ITEM>, OBS: ObservableList<ITEM>, ITEM: AnkoViewBindingItem>
        OBS.addOnWeakListChangedCallback(
        weakTargetRef: WeakReference<TARGET>,
        callback: ObservableList.OnListChangedCallback<ObservableList<ITEM>>? = null) {

    val obs = this
    val applyCallback = callback ?: object: SimpleOnListChangedCallback<ITEM>() {

        override fun onChanged(sender: ObservableList<ITEM>?) {
            weakTargetRef.get()?.run {
                items?.run {
                    clear()
                    addAll(obs)
                }

                notifyItemViewTypeChanged()
                notifyDataSetChanged()
            }
        }
    }

    // 콜백 추가
    addOnListChangedCallback(applyCallback)
}





