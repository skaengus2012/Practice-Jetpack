package nlab.practice.jetpack.ui.viewmodel

import android.app.Application
import io.reactivex.disposables.CompositeDisposable
import nlab.practice.jetpack.repository.SimpleRepository
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2018. 11. 23
 */
class DITestViewModel {

    @Inject
    lateinit var application: Application

    @Inject
    lateinit var simpleRepository: SimpleRepository

    @Inject
    lateinit var disposable: CompositeDisposable

}