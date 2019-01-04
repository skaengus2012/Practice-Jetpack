package nlab.practice.jetpack.util.nav

import androidx.annotation.StringDef
import androidx.fragment.app.FragmentManager

/**
 * Fragment 를 Navigation 할 수 있는 컨트롤러
 *
 * @author Doohyun
 */
class NavController(private val _fragmentManager: FragmentManager) {

    @StringDef(value = [Named.DEFAULT, Named.CHILD])
    annotation class Named {
        companion object {
            private const val TAG = "NavController"

            const val DEFAULT = "${TAG}_default"
            const val CHILD = "${TAG}_child"
        }
    }

    fun replace() {

    }

    fun add() {

    }
}