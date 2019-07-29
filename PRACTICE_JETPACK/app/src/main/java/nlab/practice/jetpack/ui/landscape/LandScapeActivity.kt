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

package nlab.practice.jetpack.ui.landscape

import android.os.Bundle
import dagger.Provides
import nlab.practice.jetpack.R
import nlab.practice.jetpack.ui.main.MainHolderNavController
import nlab.practice.jetpack.util.di.activity.ActivityScope
import nlab.practice.jetpack.util.di.activity.InjectableActivity

/**
 * @author Doohyun
 * @since 2019. 07. 08
 */
class LandScapeActivity : InjectableActivity() {

    override fun onCreateBinding(savedInstanceState: Bundle?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    @dagger.Module
    class Module {

        @ActivityScope
        @Provides
        fun provideFragmentNavController(activity: LandScapeActivity): MainHolderNavController {
            return MainHolderNavController(activity.supportFragmentManager, R.id.layout_container)
        }
    }

}