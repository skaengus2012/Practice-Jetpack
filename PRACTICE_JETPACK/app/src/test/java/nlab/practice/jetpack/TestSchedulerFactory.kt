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

package nlab.practice.jetpack

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import nlab.practice.jetpack.util.SchedulerFactory

/**
 * Unit Test 용 SchedulerFactory 생산
 *
 * PRESET
 *
 * single = single
 * io = trampoline
 * ui = trampoline
 * computation = trampoline
 * trampoline = trampoline
 */
fun testSchedulerFactoryOf(
    single: Scheduler = Schedulers.single(),
    io: Scheduler = Schedulers.trampoline(),
    ui: Scheduler = Schedulers.trampoline(),
    computation: Scheduler = Schedulers.trampoline(),
    trampoline: Scheduler = Schedulers.trampoline()
) = object : SchedulerFactory {

    override fun single() = single

    override fun io() = io

    override fun ui() = ui

    override fun computation() = computation

    override fun trampoline() = trampoline
}