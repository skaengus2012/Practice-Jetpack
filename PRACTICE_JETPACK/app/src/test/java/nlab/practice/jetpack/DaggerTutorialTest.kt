package nlab.practice.jetpack

import android.view.View
import nlab.practice.jetpack.util.di.itemview.DaggerItemViewUsecaseFactory
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

/**
 * Dagger 의 학습 테스트를 위한 유닛 테스트 정의
 *
 * @author Doohyun
 * @since 2018. 12. 28
 */
class DaggerTutorialTest {

    private lateinit var mockView: View

    @Before
    fun setup() {
        mockView = mock(View::class.java)
    }

    @Test
    fun testScopeForItemViewUsecaseComponent() {
        val aFactory = DaggerItemViewUsecaseFactory
                .builder()
                .setView(mockView)
                .build()

        // 같은 컴포넌트에서 나온 Usecase 는 같은가?
        Assert.assertEquals(aFactory.navigateViewUsecase(), aFactory.navigateViewUsecase())

        val bFactory = DaggerItemViewUsecaseFactory
                .builder()
                .setView(mockView)
                .build()

        // 다른 컴포넌트에서 나온 Usecase 는 다른가?
        Assert.assertNotEquals(aFactory.navigateViewUsecase(), bFactory.navigateViewUsecase())
    }


}