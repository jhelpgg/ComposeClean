package fr.jhelp.compose.ui.recycler.adapter

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.recyclerview.widget.RecyclerView

internal class RecyclerItemHolder<T : Any>(view: ComposeView) : RecyclerView.ViewHolder(view)
{
    private val composeView = this.itemView as ComposeView

    init
    {
        this.composeView.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
    }

    fun drawComposable(drawItem: @Composable (T) -> Unit,
                       value: T)
    {
        this.composeView.setContent { drawItem(value) }
    }
}