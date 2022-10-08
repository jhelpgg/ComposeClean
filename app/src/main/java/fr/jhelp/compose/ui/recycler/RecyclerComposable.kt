package fr.jhelp.compose.ui.recycler

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.jhelp.compose.provider.isProvided
import fr.jhelp.compose.provider.provideSingle
import fr.jhelp.compose.provider.provided
import fr.jhelp.compose.ui.recycler.adapter.RecyclerAdapter
import kotlin.math.max

class RecyclerComposable<T : Any>(drawItem: @Composable (T) -> Unit)
{
    val recyclerModel: RecyclerModel<T> = RecyclerAdapter<T>(drawItem)
    private lateinit var recyclerView: RecyclerView
    private var recyclerViewInitialized = false
    private val applicationContext by provided<Context>()
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

    @Composable
    fun Draw(modifier: Modifier = Modifier)
    {
        AndroidView<RecyclerView>(
            modifier = modifier,
            factory = { context ->
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