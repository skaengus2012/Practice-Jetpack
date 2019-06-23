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

package nlab.practice.jetpack.util.anko

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import org.jetbrains.anko.AnkoContext

/**
 * Anko 기능에 대한 확장 함수 정의
 *
 * @author Doohyun
 * @since 2018. 12. 10
 */

fun AnkoContext.Companion.fromViewGroup(viewGroup: ViewGroup) : AnkoContext<ViewGroup> =
        AnkoContext.create(viewGroup.context, viewGroup)

@ColorInt
fun AnkoContext<*>.color(@ColorRes colorRes: Int): Int = ContextCompat.getColor(ctx, colorRes)
fun View.color(@ColorRes colorRes: Int): Int = ContextCompat.getColor(context, colorRes)
fun Context.color(@ColorRes colorRes: Int): Int = ContextCompat.getColor(this, colorRes)

fun AnkoContext<*>.string(@StringRes stringRes: Int): String = ctx.getString(stringRes)
fun View.string(@StringRes stringRes: Int): String = context.getString(stringRes)
fun Context.string(@StringRes stringRes: Int): String = getString(stringRes)