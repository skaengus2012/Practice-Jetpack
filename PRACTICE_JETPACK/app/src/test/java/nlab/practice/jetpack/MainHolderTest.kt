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

    private val _activityLifeCycleBinder = ActivityLifeCycleBinder(CompositeDisposable())
    private val _activityCallbackDelegate = ActivityCallback()

    private lateinit var _mainBottomNavUsecase: MainBottomNavUsecase

    @Before
    fun setup() {
        _mainBottomNavUsecase = mock(MainBottomNavUsecase::class.java)
    }

    @Test
    fun testSimpleMainViewModel() {
        val mainViewModel =
                MainHolderViewModel(_activityLifeCycleBinder, _activityCallbackDelegate, _mainBottomNavUsecase)

        _activityLifeCycleBinder.apply(ActivityLifeCycle.ON_CREATE)
    }
}