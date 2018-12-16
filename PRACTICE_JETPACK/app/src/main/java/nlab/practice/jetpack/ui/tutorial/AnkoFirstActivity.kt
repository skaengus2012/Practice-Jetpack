package nlab.practice.jetpack.ui.tutorial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import org.jetbrains.anko.setContentView

/**
 * Anko 로 구성된 첫번째 페이지 구성
 *
 * @author Doohyun
 * @since 2018. 11. 23
 */
class AnkoFirstActivity : AppCompatActivity() {

    private lateinit var _viewModel: AnkoFirstViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _viewModel = ViewModelProviders.of(this).get(AnkoFirstViewModel::class.java)

        ActivityAnkoFirstUI().let {
            it.setContentView(this)

            // 일부로 viewModel 을 나중에 세팅함 -> viewModel set 에 대한 테스트 진행을 위해
            it.setViewModel(_viewModel)
        }
    }


}