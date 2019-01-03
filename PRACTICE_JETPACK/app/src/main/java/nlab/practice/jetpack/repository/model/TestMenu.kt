package nlab.practice.jetpack.repository.model

import androidx.annotation.ColorRes

/**
 * @author Doohyun
 * @since 2018. 12. 13
 */
data class TestMenu(
        val title: String,
        @ColorRes val cardColorRes: Int,
        val cardTitle: String? = null
)