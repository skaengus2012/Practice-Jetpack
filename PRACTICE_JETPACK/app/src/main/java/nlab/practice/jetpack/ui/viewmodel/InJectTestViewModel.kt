package nlab.practice.jetpack.ui.viewmodel

import android.app.Application
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * @author Doohyun
 */
class InjectTestViewModel @Inject constructor(
        val application: Application, val compositeDisposable: CompositeDisposable
)