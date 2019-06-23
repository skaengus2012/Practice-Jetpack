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

package nlab.practice.jetpack.util

import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar
import nlab.practice.jetpack.R

/**
 * @author Doohyun
 */
class SnackBarHelper(private val resourceProvider: ResourceProvider, viewSupplier: ()-> View?) {

    private val targetView: View? by lazyPublic(viewSupplier)

    fun showSnackBar(
            @StringRes message: Int,
            duration: Int = Snackbar.LENGTH_SHORT,
            @StringRes actionMessage: Int? = null,
            actionBehavior: (() -> Unit)? = null) = targetView?.run {
        Snackbar.make(this, message, duration)
                .decorate()
                .setActionInternal(actionMessage, actionBehavior)
                .show()
    }

    private fun Snackbar.setActionInternal(@StringRes actionMessage: Int?, actionBehavior: (() -> Unit)?): Snackbar {
        if ((actionMessage != null) && (actionBehavior != null)) {
            setAction(actionMessage) { actionBehavior() }
        }

        return this
    }

    private fun Snackbar.decorate(): Snackbar {
        decorateTextView(view.findViewById(R.id.snackbar_text))

        return this
    }

    private fun decorateTextView(view: TextView?) = view?.run {
        minHeight = resourceProvider.getDimensionPixelSize(R.dimen.main_bottom_navigation_height)
        gravity = Gravity.CENTER_VERTICAL
        setPadding(0, 0, 0, 0)
    }
}