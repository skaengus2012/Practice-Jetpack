package nlab.practice.jetpack

import io.reactivex.disposables.CompositeDisposable
import nlab.practice.jetpack.ui.main.MainBottomNavUsecase
import nlab.practice.jetpack.ui.main.MainHolderViewModel
import nlab.practice.jetpack.util.component.callback.ActivityCallback
import nlab.practice.jetpack.util.component.lifecycle.ActivityLifeCycle
import nlab.practice.jetpack.util.component.lifecycle.ActivityLifeCycleBinder
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

/**
 * @author Doohyun
 */
class MainHolderTest {

    private val activityLifeCycleBinder = ActivityLifeCycleBinder(CompositeDisposable())
    private val activityCallbackDelegate = ActivityCallback()

    private lateinit var mainBottomNavUsecase: MainBottomNavUsecase

    @Before
    fun setup() {
        mainBottomNavUsecase = mock(MainBottomNavUsecase::class.java)
    }

    @Test
    fun testSimpleMainViewModel() {
        val mainViewModel =
                MainHolderViewModel(activityLifeCycleBinder, activityCallbackDelegate, mainBottomNavUsecase)

        activityLifeCycleBinder.apply(ActivityLifeCycle.ON_CREATE)
    }
}