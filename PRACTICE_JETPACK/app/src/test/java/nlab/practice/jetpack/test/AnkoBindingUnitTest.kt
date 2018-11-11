package nlab.practice.jetpack.test


import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView
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
        val companyViewModel = CompanyViewModel().apply { name = "마이다스아이티" }

        //  물론 바로 gc 가 일어나지 않으니 없어도 의도한 결과는 도출됨
        UnitTestAnkoComponent(companyViewModel).createView()
        companyViewModel.name = "네이버"

        companyViewModel.notifyChange()
    }

    /**
     * Anko 의 확장함수 drive 의 메모리누수 방지 테스트
     */
    @Test
    fun testDriveMemoryLeakDefence() {
        val companyViewModel = CompanyViewModel().apply { name = "마이다스아이티" }
        companyViewModel.name = "마이다스아이티"

        UnitTestAnkoComponent(companyViewModel).createView()
        System.gc()

        companyViewModel.name = "네이버"
    }

    /**
     * BaseObservable 안의 BaseObservable 이 있을 경우의 테스트
     */
    @Test
    fun testDriveSubViewModel() {
        val companyViewModel = CompanyViewModel().apply { name = "네이버" }
        val subCompanyViewModel = CompanyViewModel().apply { name = "네이버 웹툰" }

        UnitTestAnkoComponent(companyViewModel).createView()
        companyViewModel.subCompany = subCompanyViewModel

        // 자식 쪽에 바인딩된 요소는 없기 때문에 해당 변경은 무시됨
        companyViewModel.subCompany?.name = "네이버 뮤직"
    }

    /**
     * 리스트 항목의 변경에 대한 UnitTest 정
     */
    @Test
    fun testObservableListCallback() {
        var observableItems = ObservableArrayList<Int>().apply {
            addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<Int>>() {
                override fun onChanged(sender: ObservableList<Int>?) {
                    println("onChanged")
                }

                override fun onItemRangeRemoved(sender: ObservableList<Int>?, positionStart: Int, itemCount: Int) {
                    println("onItemRangeRemoved")
                }

                override fun onItemRangeMoved(
                        sender: ObservableList<Int>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
                    println("onItemRangeMoved")
                }

                override fun onItemRangeInserted(sender: ObservableList<Int>?, positionStart: Int, itemCount: Int) {
                    println("onItemRangeInserted")
                }

                override fun onItemRangeChanged(sender: ObservableList<Int>?, positionStart: Int, itemCount: Int) {
                    println("onItemRangeChanged")
                }
            })
        }

        observableItems.add(0)
        observableItems.set(0, 1)


    }

}