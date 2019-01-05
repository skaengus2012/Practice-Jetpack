package nlab.practice.jetpack.util.component.lifecycle

/**
 * Activity LifeCycle 종류 정의
 *
 * @author Doohyun
 */
enum class ActivityLifeCycle {
    ON_CREATE,
    ON_START,
    ON_RESUME,
    ON_PAUSE,
    ON_STOP,
    ON_DESTROY,
    FINISH
}

typealias ActivityLifeCycleBinder = LifeCycleBinder<ActivityLifeCycle>