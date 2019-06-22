package nlab.practice.jetpack

import android.app.Application
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import nlab.practice.jetpack.ui.tutorial.AnkoFirstActivity
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
class AndroidExampleTest {

    private lateinit var application: Application

    @get:Rule
    val activityTest: ActivityTestRule<AnkoFirstActivity> = ActivityTestRule(AnkoFirstActivity::class.java)

    @Before
    fun init() {
        application = activityTest.activity.application
    }

    @Test
    fun testExample() {
        Assert.assertEquals(true, true)
    }
}
