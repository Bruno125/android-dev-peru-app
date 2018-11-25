package peru.android.dev.androidutils.coroutines

import kotlinx.coroutines.ExperimentalCoroutinesApi

object AppDispatcher {

    @JvmStatic
    public val Default: CoroutinesDispatcherProvider = provideDefaultDispatcher()

    @ExperimentalCoroutinesApi
    @JvmStatic
    public val Fake: CoroutinesDispatcherProvider = provideFakeDispatcher()
}