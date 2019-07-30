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

package nlab.practice.jetpack.ui.centerscroll

import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import nlab.practice.jetpack.repository.LyricsRepository
import nlab.practice.jetpack.util.SchedulerFactory
import nlab.practice.jetpack.util.lifecycle.FragmentLifecycle
import nlab.practice.jetpack.util.lifecycle.FragmentLifecycleBinder
import nlab.practice.jetpack.util.recyclerview.LayoutManagerFactory
import nlab.practice.jetpack.util.recyclerview.RecyclerViewConfig
import nlab.practice.jetpack.util.recyclerview.RecyclerViewUsecase
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * @author Doohyun
 */
class CenterScrollViewModel @Inject constructor(
    fragmentLifeCycleBinder: FragmentLifecycleBinder,
    layoutManagerFactory: LayoutManagerFactory,
    lyricsSmoothScrollHelperFactory: LyricsSmoothScrollerHelper.Factory,
    private val schedulerFactory: SchedulerFactory,
    private val lyricsRepository: LyricsRepository,
    private val recyclerViewUsecase: RecyclerViewUsecase
) {

    val items = ObservableArrayList<LyricsItemViewModel>()

    val recyclerViewConfig = RecyclerViewConfig().apply {
        layoutManager = layoutManagerFactory.createCenterScrollerLayoutManager(300f)
    }

    private val disposables = CompositeDisposable()

    private var isScrolling = false

    private var isSkip = false

    private val lyricsSmoothScrollerHelper = lyricsSmoothScrollHelperFactory.create(this)

    init {
        initializeLyrics()

        fragmentLifeCycleBinder.bindUntil(FragmentLifecycle.ON_VIEW_CREATED) {
            subscribeScrollEvent()
            subscribeLyricsIndexChanged()
        }

        fragmentLifeCycleBinder.bindUntil(FragmentLifecycle.ON_DESTROY_VIEW) {
            disposables.clear()
        }
    }

    private fun initializeLyrics() {
        with(lyricsRepository.getLyrics()) {
            val result = mutableListOf<LyricsItemViewModel>()

            for (index in 0 until size) {
                result += LyricsItemViewModel(index, get(index)) { updateSelectedWithScroll(it) }
            }

            if (result.isNotEmpty()) {
                result[0].selected = true
            }

            result
        }.run { items.addAll(this) }
    }

    private fun subscribeScrollEvent() {
        recyclerViewUsecase.scrollStateChanges()
            .subscribe { isScrolling = (it != RecyclerView.SCROLL_STATE_IDLE) }
            .addTo(disposables)
    }

    private fun subscribeLyricsIndexChanged() {
        Observable.interval(3, TimeUnit.SECONDS)
            .observeOn(schedulerFactory.ui())
            .subscribe {
                if (isSkip) {
                    isSkip = false
                } else {
                    updateProgress()
                }
            }
            .addTo(disposables)
    }

    private fun updateProgress() {
        val updateIndex = getCurrentSelectedIndex()
            ?.let { it + 1 }
            ?.takeIf { it < items.size }
            ?: 0

        updateSelectedWithScroll(updateIndex)
    }

    private fun updateSelectedWithScroll(position: Int) {
        val isValid = (position in 0 until items.size)
        if (isValid) {
            for (index in 0 until items.size) {
                items[index].selected = (index == position)
            }

            if (!isScrolling) {
                lyricsSmoothScrollerHelper.scrolling(position)
            }
        }
    }

    fun getCurrentSelectedIndex(): Int? = items.find { it.selected }?.index

    fun scrollToFirst() {
        isSkip = true
        updateSelectedWithScroll(0)
    }

    fun scrollToLast() {
        isSkip = true
        updateSelectedWithScroll(items.size - 1)
    }
}