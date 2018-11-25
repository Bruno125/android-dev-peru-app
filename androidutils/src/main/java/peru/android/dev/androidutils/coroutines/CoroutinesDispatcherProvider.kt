package peru.android.dev.androidutils.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

interface CoroutinesDispatcherProvider {
    val main: CoroutineDispatcher
    val computation: CoroutineDispatcher
    val io: CoroutineDispatcher
}

fun provideDefaultDispatcher() = object: CoroutinesDispatcherProvider {
    override val main: CoroutineDispatcher get() = Dispatchers.Main
    override val computation: CoroutineDispatcher get() = Dispatchers.Default
    override val io: CoroutineDispatcher get() = Dispatchers.IO
}

@ExperimentalCoroutinesApi
fun provideFakeDispatcher() = object: CoroutinesDispatcherProvider {
    override val main: CoroutineDispatcher get() = Dispatchers.Unconfined
    override val computation: CoroutineDispatcher get() = Dispatchers.Unconfined
    override val io: CoroutineDispatcher get() = Dispatchers.Unconfined
}
