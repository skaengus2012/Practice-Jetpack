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
import nlab.practice.jetpack.ui.slide.SlideUpSampleActivity
import nlab.practice.jetpack.ui.tutorial.AnkoFirstActivity
import nlab.practice.jetpack.util.IntentUtil

/**
 * @author Doohyun
 * @since 2019. 08. 01
 */
interface ActivityNavUsecase {
    fun navAnko()
    fun navSlide()
}

class ActivityNavUsecaseImpl(private val activity: Activity) : ActivityNavUsecase {

    override fun navAnko() {
        activity.startActivity(IntentUtil.createActivityIntent<AnkoFirstActivity>(activity))
    }

    override fun navSlide() {
        activity.startActivity(IntentUtil.createActivityIntent<SlideUpSampleActivity>(activity))
    }
}