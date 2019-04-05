package nlab.practice.jetpack.util

import com.bumptech.glide.request.RequestOptions

/**
 * @author Doohyun
 * @since 2019. 01. 23
 */
object GlideOptionProvider {

    @JvmStatic
    fun forPagingItem() = RequestOptions()
            .centerCrop()
            .override(200)

    @JvmStatic
    fun forBigSizeCenterCrop() = RequestOptions()
            .centerCrop()
}