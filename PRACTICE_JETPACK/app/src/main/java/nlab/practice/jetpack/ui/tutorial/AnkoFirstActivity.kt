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

package nlab.practice.jetpack.ui.tutorial

import android.os.Bundle
import nlab.practice.jetpack.util.di.activity.InjectableActivity
import org.jetbrains.anko.setContentView
import javax.inject.Inject

/**
 * Anko 로 구성된 첫번째 페이지 구성
 *
 * @author Doohyun
 * @since 2018. 11. 23
 */
class AnkoFirstActivity : InjectableActivity() {

    @Inject
    lateinit var viewModel: AnkoFirstViewModel

    override fun onCreateBinding(savedInstanceState: Bundle?) {
        ActivityAnkoFirstUI().let {
            it.setContentView(this)

            // 일부로 viewModel 을 나중에 세팅함 -> viewModel set 에 대한 테스트 진행을 위해
            it.setViewModel(viewModel)
        }
    }
}