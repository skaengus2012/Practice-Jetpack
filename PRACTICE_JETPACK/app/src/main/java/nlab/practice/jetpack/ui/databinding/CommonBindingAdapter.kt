package nlab.practice.jetpack.ui.databinding

import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import androidx.databinding.ObservableList
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import nlab.practice.jetpack.ui.databinding.callback.SimpleOnListChangedCallback
import nlab.practice.jetpack.ui.databinding.callback.WeakPropertyChangedCallback
import nlab.practice.jetpack.ui.databinding.recyclerview.ViewComponentBindingAdapter
import nlab.practice.jetpack.ui.databinding.recyclerview.ViewComponentBindingItem
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

fun <TARGET: RecyclerView, OBS: ObservableList<ITEM>, ITEM: ViewComponentBindingItem> TARGET.driveList(
        observableList: OBS,
        headerItems: ITEM? = null,
        footerItems: ITEM? = null,
        callback: ObservableList.OnListChangedCallback<ObservableList<ITEM>>? = null) {

    // 레이아웃 매니저가 없다면, Linear 기본으로 세팅
    if (layoutManager == null) {
        layoutManager = LinearLayoutManager(context)
    }

    // 어댑터 생성 및 주입
    val adapter = ViewComponentBindingAdapter(observableList).apply {
        header = headerItems
        footer = footerItems
    }
    setAdapter(adapter)

    observableList.addOnWeakListChangedCallback(WeakReference(adapter), callback)
}

fun <TARGET: ViewComponentBindingAdapter<ITEM>, OBS: ObservableList<ITEM>, ITEM: ViewComponentBindingItem>
        OBS.addOnWeakListChangedCallback(
        weakTargetRef: WeakReference<TARGET>,
        callback: ObservableList.OnListChangedCallback<ObservableList<ITEM>>? = null) {

    val obs = this
    var applyCallback = callback ?: object: SimpleOnListChangedCallback<ITEM>() {

        override fun onChanged(sender: ObservableList<ITEM>?) {
            weakTargetRef.get()?.let {
                it.items.clear()
                it.items.addAll(obs)
                it.initializeViewTypeMapper()
                it.notifyDataSetChanged()
            }
        }
    }

    // 콜백 추가
    addOnListChangedCallback(applyCallback)
}





