package nlab.practice.jetpack.common.di.activity

import dagger.Module
import nlab.practice.jetpack.ui.tutorial.AnkoFirstActivityBinder

/**
 * @author Doohyun
 * @since 2018. 12. 18
 */
@Module(includes = [
    AnkoFirstActivityBinder::class
])
interface ActivityBinder