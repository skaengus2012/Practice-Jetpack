package nlab.practice.jetpack.util.anko

import androidx.databinding.BaseObservable
import androidx.databinding.Observable
import androidx.databinding.library.baseAdapters.BR
import nlab.practice.jetpack.util.anko.callback.WeakPropertyChangedCallback
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