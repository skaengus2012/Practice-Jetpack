package nlab.practice.jetpack.util

import android.view.ViewGroup
import org.jetbrains.anko.AnkoContext

/**
 * Anko 기능에 대한 확장 함수 정의
 *
 * @author Doohyun
 * @since 2018. 12. 10
 */

fun AnkoContext.Companion.fromViewGroup(viewGroup: ViewGroup) : AnkoContext<ViewGroup> =
        AnkoContext.create(viewGroup.context, viewGroup)