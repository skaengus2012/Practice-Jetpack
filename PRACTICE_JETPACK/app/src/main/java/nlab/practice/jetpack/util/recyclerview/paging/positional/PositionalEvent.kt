/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nlab.practice.jetpack.util.recyclerview.paging.positional

import androidx.annotation.StringDef
import androidx.paging.PositionalDataSource

/**
 * Positional 데이터 조회에 대한 Event
 *
 * @author Doohyun
 */
data class PositionalEvent(
    @PositionalDataLoadState val state: String,
    val initParams: PositionalDataSource.LoadInitialParams? = null,
    val rangeParams: PositionalDataSource.LoadRangeParams? = null
)


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
