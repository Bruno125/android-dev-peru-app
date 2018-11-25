package peru.android.dev.androidutils.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import peru.android.dev.androidutils.coroutines.CoroutinesDispatcherProvider

abstract class CoroutineViewModel(dispatcher: CoroutinesDispatcherProvider): ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(dispatcher.main + viewModelJob)


    protected fun execute(predicate: suspend ()->Unit) = uiScope.launch { predicate() }
}