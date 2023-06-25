package fr.jhelp.android.library.showcase.ui.recycler.adapter

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.recyclerview.widget.RecyclerView

/**
 * Holder of list/grid cell
 */
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