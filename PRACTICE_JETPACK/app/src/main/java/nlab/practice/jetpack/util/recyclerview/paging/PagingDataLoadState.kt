package nlab.practice.jetpack.util.recyclerview.paging

import androidx.annotation.StringDef

/**
 * Paging 방식의 데이터 로드 상태 정의
 *
 * @author Doohyun
 */
@StringDef(value= [
    PagingDataLoadState.INIT_LOAD_START,
    PagingDataLoadState.INIT_LOAD_FINISH,
    PagingDataLoadState.INIT_LOAD_ERROR,
    PagingDataLoadState.LOAD_START,
    PagingDataLoadState.LOAD_FINISH,
    PagingDataLoadState.LOAD_ERROR
])
annotation class PagingDataLoadState {
    companion object {
        private const val TAG = "PagingDataLoadState"
        const val INIT_LOAD_START = "${TAG}_init_start"
        const val INIT_LOAD_FINISH = "${TAG}_init_load_finish"
        const val INIT_LOAD_ERROR = "${TAG}_init_load_error"
        const val LOAD_START = "${TAG}_start"
        const val LOAD_FINISH = "${TAG}_finish"
        const val LOAD_ERROR = "${TAG}_error"
    }
}