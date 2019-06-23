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

package nlab.practice.jetpack.util.nav

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment

/**
 * @author Doohyun
 */
interface ActivityNavUsecase {
    fun startActivity(intent: Intent)
}

class DefaultActivityNavUsecase(private val context: Context) : ActivityNavUsecase {
    override fun startActivity(intent: Intent) = context.startActivity(intent)
}

class FragmentOwnerActivityNavUsecase(private val fragment: Fragment): ActivityNavUsecase {
    override fun startActivity(intent: Intent) = fragment.startActivity(intent)
}

inline fun <reified T: Activity> ActivityNavUsecase.startActivity(intentProvider: IntentProvider) {
    intentProvider.createActivityIntent(T::class.java).run { startActivity(this) }
}