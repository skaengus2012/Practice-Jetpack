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

/**
 * @author Doohyun
 * @since 2019. 04. 18
 */
class ContextInjectionType {
    companion object {
        const val APPLICATION = "CONTEXT_TYPE_APPLICATION"
        const val ACTIVITY = "CONTEXT_TYPE_ACTIVITY"
        const val FRAGMENT = "CONTEXT_TYPE_FRAGMENT"
    }
}