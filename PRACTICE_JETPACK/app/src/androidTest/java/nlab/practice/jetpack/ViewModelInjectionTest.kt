package nlab.practice.jetpack

import android.app.Application
import android.util.Log
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import nlab.practice.jetpack.ui.ankomvvm.AnkoFirstActivity
import nlab.practice.jetpack.ui.viewmodel.DITestViewModel
import nlab.practice.jetpack.di.component.DaggerAppComponent
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith


const val LOG_NAME = "TEST_LOG"

/**
 * ViewModel Injection 에 대한 테스트 케이스 정의
 */
@RunWith(AndroidJUnit4::class)
class ViewModelInjectionTest {

    private lateinit var _application: Application

    @get:Rule
    val activityTest: ActivityTestRule<AnkoFirstActivity> = ActivityTestRule(AnkoFirstActivity::class.java)

    @Before
    fun init() {
        _application = activityTest.activity.application
    }

    @Test
    fun useAppContext() {

        val appComponent = DaggerAppComponent.builder()
                .application(_application)
                .build()

        val component = appComponent.viewModelComponent().build()
        val component2 = appComponent.viewModelComponent().build()

        val aViewModel = DITestViewModel().apply { component.inject(this) }
        val bViewModel = DITestViewModel().apply { component.inject(this) }


        Log.d(LOG_NAME, "equals component injection test : ${aViewModel.simpleRepository === bViewModel.simpleRepository}")

        component2.inject(bViewModel)
        Log.d(LOG_NAME, "another component injection test : ${aViewModel.simpleRepository === bViewModel.simpleRepository}")
    }
}
