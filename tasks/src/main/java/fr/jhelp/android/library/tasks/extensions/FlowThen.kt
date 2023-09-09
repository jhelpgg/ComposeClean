package fr.jhelp.android.library.tasks.extensions

import fr.jhelp.android.library.tasks.TaskType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

internal class FlowThen<P, R>(source: Flow<P>, taskType: TaskType, transform: (P) -> R) : Flow<R>
{
    private var collector: FlowCollector<R>? = null

    init
    {
        taskType.launch { source.collect { value -> this.collector?.emit(transform(value)) } }
    }

    override suspend fun collect(collector: FlowCollector<R>)
    {
        this.collector = collector
    }
}