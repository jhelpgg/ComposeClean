package fr.jhelp.compose.ui.recycler

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.jhelp.android.library.common.ui.recycler.RecyclerModel
import fr.jhelp.android.library.provider.isProvided
import fr.jhelp.android.library.provider.provideSingle
import fr.jhelp.android.library.provider.provided
import fr.jhelp.compose.ui.recycler.adapter.RecyclerAdapter
import kotlin.math.max

/**
 * Recycler view embed in composable
 * @param drawItem Describes how to draw a cell element.
 *                 It receives the value to draw on the cell.
 */
class RecyclerComposable<T : Any>(drawItem: @Composable (T) -> Unit)
{
    /** Linked model for manipulates list/grid data */
    val recyclerModel: RecyclerModel<T> = RecyclerAdapter<T>(drawItem)

    private lateinit var recyclerView: RecyclerView
    private var recyclerViewInitialized = false
    private val applicationContext by provided<Context>()

    /**
     * The grid span.
     * If 1 it shows a list, for mor than one
     * If more than 1 shows a grid and the value is the number of columns
     */
    var gridSpan: Int = 1
        set(value)
        {
            val newValue = max(value, 1)
            val changed = field != newValue
            field = newValue

            if (changed && this.recyclerViewInitialized)
            {
                this.recyclerView.layoutManager =
                    if (newValue == 1)
                    {
                        LinearLayoutManager(this.applicationContext)
                    }
                    else
                    {
                        GridLayoutManager(this.applicationContext, newValue)
                    }
            }
        }

    /**
     * Draw the recycle view in composable
     */
    @Composable
    fun Draw(modifier: Modifier = Modifier)
    {
        AndroidView<RecyclerView>(
            modifier = modifier,
            factory = { context ->
                // Auto provide application context if not already provided to be sure there if grid
                // span changed after recycler view is shown
                if (!isProvided<Context>())
                {
                    val applicationContext = context.applicationContext
                    provideSingle<Context> { applicationContext }
                }

                this.recyclerView = RecyclerView(context)
                this.recyclerViewInitialized = true
                this.recyclerView.layoutManager =
                    if (this.gridSpan == 1)
                    {
                        LinearLayoutManager(context)
                    }
                    else
                    {
                        GridLayoutManager(context, this.gridSpan)
                    }
                val adapter = this.recyclerModel as RecyclerAdapter
                this.recyclerView.adapter = adapter
                this.recyclerView.addOnAttachStateChangeListener(adapter)
                this.recyclerView
            })
    }
}