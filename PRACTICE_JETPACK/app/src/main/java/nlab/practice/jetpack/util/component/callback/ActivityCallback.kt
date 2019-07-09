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

package nlab.practice.jetpack.util.component.callback

import android.os.Bundle

/**
 * Activity 의 Callback 에 대한 연결자 정의
 *
 * @author Doohyun
 */
class ActivityCallback {

    var onBackPressedCommand: (()-> Boolean)? = null
        private set

    var onRestoreInstanceStateCommand: ((savedInstanceState: Bundle?) -> Unit)? = null
        private set

    var onSaveInstanceStateCommand: ((outState: Bundle?) -> Unit)? = null

    fun onBackPressed(action: () -> Boolean) {
       onBackPressedCommand = action
    }

    fun onRestoreInstanceState(action: (savedInstanceState: Bundle?) -> Unit) {
        onRestoreInstanceStateCommand = action
    }

    fun onSaveInstanceState(action: (outState: Bundle?) -> Unit) {
        onSaveInstanceStateCommand = action
    }
}


