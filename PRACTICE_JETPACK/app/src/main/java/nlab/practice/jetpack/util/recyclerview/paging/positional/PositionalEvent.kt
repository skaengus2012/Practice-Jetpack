package nlab.practice.jetpack.util.recyclerview.paging.positional

import androidx.annotation.StringDef
import androidx.paging.PositionalDataSource

/**
 * Positional 데이터 조회에 대한 Event
 *
 * @author Doohyun
 */
data class PositionalEvent internal constructor(
        @PositionalDataLoadState val state: String,
        val initParams: PositionalDataSource.LoadInitialParams? = null,
        val rangeParams: PositionalDataSource.LoadRangeParams? = null)


/**
 * 데이터의 조회 상태 열람
 */
@StringDef(value = [
    PositionalDataLoadState.INIT_LOAD_START,
    PositionalDataLoadState.INIT_LOAD_FINISH,
    PositionalDataLoadState.INIT_LOAD_ERROR,
    PositionalDataLoadState.INIT_LOAD_DATA_SIZE_CHANGED,
    PositionalDataLoadState.LOAD_START,
    PositionalDataLoadState.LOAD_FINISH,
    PositionalDataLoadState.LOAD_ERROR,
    PositionalDataLoadState.LOAD_DATA_SIZE_CHANGED
])
annotation class PositionalDataLoadState {
    companion object {
        private const val TAG = "PositionalDataLoadState"
        const val INIT_LOAD_START = "${TAG}_init_start"
        const val INIT_LOAD_FINISH = "${TAG}_init_load_finish"
        const val INIT_LOAD_ERROR = "${TAG}_init_load_error"
        const val INIT_LOAD_DATA_SIZE_CHANGED = "${TAG}_init_load_size_changed"
        const val LOAD_START = "${TAG}_start"
        const val LOAD_FINISH = "${TAG}_finish"
        const val LOAD_ERROR = "${TAG}_error"
        const val LOAD_DATA_SIZE_CHANGED = "${TAG}_load_size_changed"
    }
}
