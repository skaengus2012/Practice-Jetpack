package nlab.practice.jetpack.common.di

import dagger.Module
import dagger.Provides
import nlab.practice.jetpack.ui.tutorial.AnkoFirstDataBundle
import javax.inject.Singleton

/**
 * 데이터 저장을 메모리 위에서 처리하기 위해 사용하는 번들 모듈 정의
 *
 * @author Doohyun
 */
@Module
class BundleModule {

    @Singleton
    @Provides
    fun provideAnkoFistDataBundle(): AnkoFirstDataBundle = AnkoFirstDataBundle()
}
