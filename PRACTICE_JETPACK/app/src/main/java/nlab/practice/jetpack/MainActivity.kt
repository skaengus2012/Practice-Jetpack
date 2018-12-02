package nlab.practice.jetpack

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import nlab.practice.jetpack.ui.layout.ActivityMainUI
import nlab.practice.jetpack.ui.viewmodel.MainTestViewModel
import org.jetbrains.anko.setContentView

class MainActivity : AppCompatActivity() {

    private lateinit var _viewModel: MainTestViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _viewModel = ViewModelProviders.of(this).get(MainTestViewModel::class.java)
        ActivityMainUI(_viewModel).setContentView(this)
    }
}
