package nlab.practice.jetpack

import android.app.Application
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import nlab.practice.jetpack.common.di.DaggerAppComponent
import nlab.practice.jetpack.ui.tutorial.AnkoFirstActivity
import nlab.practice.jetpack.ui.viewmodel.DITestViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith


/**
 * ViewModel Injection 에 대한 테스트 케이스 정의
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class ViewModelInjectionTest {

    private lateinit var _application: Application

    @get:Rule
    val activityTest: ActivityTestRule<AnkoFirstActivity> = ActivityTestRule(AnkoFirstActivity::class.java)

    @Before
    fun init() {
        _application = activityTest.activity.application
    }

    @Test
    fun testSingletonInjection() {

        val appComponent = DaggerAppComponent.builder()
                .application(_application)
                .build()

        val component = appComponent.viewModelComponent().build()
        val component2 = appComponent.viewModelComponent().build()

        val aViewModel = DITestViewModel().apply { component.inject(this) }
        val bViewModel = DITestViewModel().apply { component.inject(this) }

        Assert.assertEquals(aViewModel.simpleRepository, bViewModel.simpleRepository)

        component2.inject(bViewModel)
        Assert.assertEquals(true, aViewModel.simpleRepository === bViewModel.simpleRepository)
    }
}
