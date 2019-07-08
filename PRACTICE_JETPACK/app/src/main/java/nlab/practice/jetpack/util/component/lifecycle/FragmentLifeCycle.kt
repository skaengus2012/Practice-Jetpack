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

package nlab.practice.jetpack.util.component.lifecycle

/**
 * Fragment LifeCycle 종류 정의
 *
 * @author Doohyun
 */
enum class FragmentLifeCycle {
    ON_ATTACH,
    ON_CREATE,
    ON_CREATE_VIEW,
    ON_ACTIVITY_CREATED,
    ON_VIEW_CREATED,
    ON_START,
    ON_RESUME,
    ON_PAUSE,
    ON_STOP,
    ON_DESTROY_VIEW,
    ON_DESTROY,
    ON_DETACH
}

typealias FragmentLifeCycleBinder = LifeCycleBinder<FragmentLifeCycle>