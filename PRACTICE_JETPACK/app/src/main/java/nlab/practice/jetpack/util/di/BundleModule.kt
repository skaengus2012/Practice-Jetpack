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

package nlab.practice.jetpack.util.di

import dagger.Module
import dagger.Provides
import nlab.practice.jetpack.ui.tutorial.AnkoFirstDataBundle
import javax.inject.Singleton

/**
 * 데이터 저장을 메모리 위에서 처리하기 위해 사용하는 번들 모듈 정의
 *
 * @author Doohyun
 */
@Module
class BundleModule {

    @Singleton
    @Provides
    fun provideAnkoFistDataBundle(): AnkoFirstDataBundle = AnkoFirstDataBundle()
}
