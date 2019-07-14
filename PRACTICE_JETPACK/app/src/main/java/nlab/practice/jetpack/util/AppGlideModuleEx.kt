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

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

const val DISK_CACHE_MAX_SIZE: Long = 1024 * 1024 * 100

/**
 * @author Doohyun
 * @since 2019. 01. 21
 */
@GlideModule
class AppGlideModuleEx : AppGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        GlidePreLollipopTlsSetupDelegate.registerComponents(registry)
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)

        val memoryCalculator = MemorySizeCalculator
            .Builder(context)
            .build()

        val memoryCacheSize = memoryCalculator.memoryCacheSize
            .let {
                val result: Float = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    it / 1.5f
                } else {
                    it / 3f
                }

                result.toLong()
            }

        val bitmapPullSize = memoryCalculator.bitmapPoolSize
            .let {
                val result: Float = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    it / 1.5f
                } else {
                    it / 3f
                }

                result.toLong()
            }


        builder.setDefaultTransitionOptions(Bitmap::class.java, BitmapTransitionOptions.withCrossFade())
            .setDefaultRequestOptions(RequestOptions().format(DecodeFormat.PREFER_ARGB_8888).disallowHardwareConfig())
            .setMemoryCache(LruResourceCache(memoryCacheSize))
            .setBitmapPool(LruBitmapPool(bitmapPullSize))
            .setDiskCache { DiskLruCacheWrapper.create(Glide.getPhotoCacheDir(context), DISK_CACHE_MAX_SIZE) }
            .setDefaultTransitionOptions(Drawable::class.java, DrawableTransitionOptions.withCrossFade())
            .setDefaultTransitionOptions(Bitmap::class.java, BitmapTransitionOptions.withCrossFade())
    }
}