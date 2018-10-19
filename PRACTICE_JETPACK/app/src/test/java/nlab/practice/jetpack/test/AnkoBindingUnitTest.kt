package nlab.practice.jetpack.test


import nlab.practice.jetpack.model.UnitTestAnkoComponent
import nlab.practice.jetpack.ui.viewmodel.CompanyViewModel
import org.junit.Test

/**
 * Anko 의 데이터 바인딩을 지원하기 위한 확장 함수 테스트.
 *
 * @author Doohyun
 * @since 2018. 11. 08
 */
class AnkoBindingUnitTest {

    /**
     * anko 로 mvvm 을 하고자하는 drive 기능 테스
     */
    @Test
    fun testDriveMVVM() {
        val companyViewModel = CompanyViewModel()
        companyViewModel.name = "마이다스아이티"

        // Data 변화 감지를 위해 해당 뷰를 참조관계로 유지 -> 물론 바로 gc 가 일어나지 않으니 없어도 의도한 결과는 도출됨
        val testView = UnitTestAnkoComponent(companyViewModel).createView()
        companyViewModel.name = "네이버"

        companyViewModel.notifyChange()
    }

    /**
     * Anko 의 확장함수 drive 의 메모리누수 방지 테스트
     */
    @Test
    fun testDriveMemoryLeakDefence() {
        val companyViewModel = CompanyViewModel()
        companyViewModel.name = "마이다스아이티"

        UnitTestAnkoComponent(companyViewModel).createView()
        System.gc()

        companyViewModel.name = "네이버"
    }

}