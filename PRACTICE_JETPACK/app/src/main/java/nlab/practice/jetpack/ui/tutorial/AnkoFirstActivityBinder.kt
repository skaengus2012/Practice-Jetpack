package nlab.practice.jetpack.ui.tutorial

import android.app.Activity
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import nlab.practice.jetpack.common.di.activity.ActivityScope
import nlab.practice.jetpack.common.di.activity.ActivityCommonModule

/**
 * @author Doohyun
 */
@ActivityScope
@Subcomponent(modules = [ActivityCommonModule::class])
interface AnkoFirstActivityComponent : AndroidInjector<AnkoFirstActivity>{

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<AnkoFirstActivity>()
}

@Module(subcomponents = [AnkoFirstActivityComponent::class])
abstract class AnkoFirstActivityBinder {
    @Binds
    @IntoMap
    @ActivityKey(AnkoFirstActivity::class)
    abstract fun ankoFirstActivity(builder: AnkoFirstActivityComponent.Builder) : AndroidInjector.Factory<out Activity>
}