package fr.jhelp.compose.ui.recycler.adapter

import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.RecyclerView
import fr.jhelp.compose.collection.IntList
import fr.jhelp.compose.ui.recycler.RecyclerModel
import kotlin.math.min
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

internal class RecyclerAdapter<T : Any>(private val applyTheme: @Composable (@Composable () -> Unit) -> Unit,
                                        private val drawItem: @Composable (T) -> Unit) :
        RecyclerView.Adapter<RecyclerItemHolder<T>>(),
        RecyclerModel<T>,
        View.OnAttachStateChangeListener
{
    private val mainScope = MainScope()
    private var filter: (T) -> Boolean = { true }
    private val completeList = ArrayList<T>()
    private val visibleIndices = IntList()
    private var attached = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemHolder<T> =
        RecyclerItemHolder<T>(ComposeView(parent.context))

    override fun onBindViewHolder(holder: RecyclerItemHolder<T>, position: Int)
    {
        holder.drawComposable(this.applyTheme,
                              this.drawItem,
                              this.completeList[this.visibleIndices[position]])
    }

    override fun getItemCount(): Int =
        this.visibleIndices.size

    override fun iterator(): Iterator<T> =
        this.completeList.iterator()

    override val size: Int
        get() = this.completeList.size

    override val empty: Boolean
        get() = this.completeList.isEmpty()

    override val notEmpty: Boolean
        get() = this.completeList.isNotEmpty()

    override fun get(index: Int): T =
        this.completeList[index]

    override fun set(index: Int, value: T)
    {
        val wasFiltered = this.filter(this.completeList[index])
        val willBeFiltered = this.filter(value)

        this.completeList[index] = value

        if (wasFiltered == willBeFiltered)
        {
            if (wasFiltered)
            {
                val visibleIndex = this.visibleIndices.firstIndexOf(index)
                this.launch { this@RecyclerAdapter.notifyItemChanged(visibleIndex) }
            }

            return
        }

        if (wasFiltered)
        {
            val visibleIndex = this.visibleIndices.firstIndexOf(index)
            this.visibleIndices.remove(visibleIndex)
            this.launch { this@RecyclerAdapter.notifyItemRemoved(visibleIndex) }
            return
        }

        var indexInsert = 0

        while (indexInsert < this.visibleIndices.size && this.visibleIndices[indexInsert] <= index)
        {
            indexInsert++
        }

        this.visibleIndices.insert(indexInsert, index)
        this.launch { this@RecyclerAdapter.notifyItemInserted(indexInsert) }
    }

    override operator fun plusAssign(element: T)
    {
        this.completeList += element

        if (this.filter(element))
        {
            this.visibleIndices += this.completeList.size - 1
            this.launch { this@RecyclerAdapter.notifyItemInserted(this@RecyclerAdapter.visibleIndices.size - 1) }
        }
    }

    override operator fun plusAssign(elements: Collection<T>)
    {
        for (element in elements)
        {
            this += element
        }
    }

    override operator fun plusAssign(elements: Array<T>)
    {
        for (element in elements)
        {
            this += element
        }
    }

    override operator fun minusAssign(element: T)
    {
        val index = this.completeList.indexOf(element)

        if (index < 0)
        {
            return
        }

        this.completeList.removeAt(index)
        val indexVisible = this.visibleIndices.firstIndexOf(index)

        if (indexVisible < 0)
        {
            return
        }

        this.visibleIndices.remove(indexVisible)
        this.launch { this@RecyclerAdapter.notifyItemRemoved(indexVisible) }
    }

    override operator fun minusAssign(elements: Collection<T>)
    {
        for (element in elements)
        {
            this -= element
        }
    }

    override operator fun minusAssign(elements: Array<T>)
    {
        for (element in elements)
        {
            this -= element
        }
    }

    override fun clear()
    {
        this.completeList.clear()
        val size = this.visibleIndices.size

        if (size == 0)
        {
            return
        }

        this.visibleIndices.clear()

        if (size == 1)
        {
            this.launch { this@RecyclerAdapter.notifyItemRemoved(0) }
            return
        }

        this.launch { this@RecyclerAdapter.notifyItemRangeRemoved(0, size) }
    }

    override fun sort(comparator: Comparator<T>)
    {
        this.completeList.sortWith(comparator)
        this.applyFilter()
    }

    override fun filter(filter: (T) -> Boolean)
    {
        this.filter = filter
        this.applyFilter()
    }

    override fun removeFilter()
    {
        this.filter = { true }
        this.applyFilter()
    }

    private fun applyFilter()
    {
        val previousSize = this.visibleIndices.size
        this.visibleIndices.clear()

        for ((index, element) in this.completeList.withIndex())
        {
            if (this.filter(element))
            {
                this.visibleIndices += index
            }
        }

        val size = this.visibleIndices.size

        this.launch {
            if (previousSize > size)
            {
                this@RecyclerAdapter.notifyItemRangeRemoved(size, previousSize - size)
            }

            val number = min(previousSize, size)

            if (number > 0)
            {
                this@RecyclerAdapter.notifyItemRangeChanged(0, number)
            }

            if (previousSize < size)
            {
                this@RecyclerAdapter.notifyItemRangeInserted(previousSize, size - previousSize)
            }
        }
    }

    private fun launch(action: () -> Unit)
    {
        if (this.attached)
        {
            this.mainScope.launch { action() }
        }
    }

    override fun onViewAttachedToWindow(v: View?)
    {
        this.attached = true
    }

    override fun onViewDetachedFromWindow(v: View?)
    {
        this.attached = false
    }
}