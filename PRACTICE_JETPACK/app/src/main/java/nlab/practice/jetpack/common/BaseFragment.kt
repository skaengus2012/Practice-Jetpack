package nlab.practice.jetpack.common

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import nlab.practice.jetpack.LeakCanaryWatcher
import javax.inject.Inject

/**
 * @author Doohyun
 * @since 2018. 12. 11
 */
abstract class BaseFragment : Fragment(), HasSupportFragmentInjector {

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onAttach(context: Context?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AndroidSupportInjection.inject(this)
        }

        super.onAttach(context)
    }

    @SuppressWarnings("deprecation")
    override fun onAttach(activity: Activity?) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            AndroidSupportInjection.inject(this)
        }

        super.onAttach(activity)
    }

    override fun onDestroyView() {
        LeakCanaryWatcher.watch(this)

        super.onDestroyView()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = childFragmentInjector
}