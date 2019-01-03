package nlab.practice.jetpack.util.lifecycle

/**
 * Fragment LifeCycle 종류 정의
 *
 * @author Doohyun
 */
enum class FragmentLifeCycle {
    ON_ATTACH,
    ON_CREATE,
    ON_CREATE_VIEW,
    ON_ACTIVITY_CREATED,
    ON_VIEW_CREATED,
    ON_START,
    ON_RESUME,
    ON_PAUSE,
    ON_STOP,
    ON_DESTROY_VIEW,
    ON_DESTROY,
    ON_DETACH
}

typealias FragmentLifeCycleBinder = LifeCycleBinder<FragmentLifeCycle>