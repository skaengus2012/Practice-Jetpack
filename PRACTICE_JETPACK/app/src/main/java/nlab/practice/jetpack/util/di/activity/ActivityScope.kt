package nlab.practice.jetpack.util.di.activity

import javax.inject.Scope

/**
 * Activity 에 대한 생명주기를 가지는 Scope
 *
 * @author Doohyun
 * @since 2018. 12. 18
 */
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@Scope
annotation class ActivityScope