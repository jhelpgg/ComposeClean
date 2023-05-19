package fr.jhelp.compose.engine.view.overlay.component

import fr.jhelp.compose.math.extensions.bounds
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Create a group button.
 *
 * Buttons becomes toggle buttons and selection managed by the group.
 *
 * To not experience strange behavior, not change on managed buttons:
 * * [OverlayButtonImage.selected]
 * * [OverlayButtonImage.toggleBehavior]
 * * [OverlayButtonImage.selectListener]
 */
class OverlayButtonGroup(selectedIndex: Int,
                         firstButton: OverlayButtonImage,
                         vararg otherButtons: OverlayButtonImage)
{
    private val buttons = ArrayList<OverlayButtonImage>()
    private val selectedMutable = MutableStateFlow<Int>(selectedIndex.bounds(0, otherButtons.size))

    /** Selected button */
    val selected: StateFlow<Int> = this.selectedMutable.asStateFlow()

    init
    {
        firstButton.insideGroup = true
        firstButton.selectListener = { selected ->
            if (selected)
            {
                this.selectionChanged(0)
            }
        }
        firstButton.toggleBehavior = true
        firstButton.selected = this.selectedMutable.value == 0
        this.buttons.add(firstButton)
        var index = 1

        for (button in otherButtons)
        {
            val selectIndex = index
            button.insideGroup = true
            button.selectListener = { selected ->
                if (selected)
                {
                    this.selectionChanged(selectIndex)
                }
            }
            button.toggleBehavior = true
            button.selected = this.selectedMutable.value == index
            this.buttons.add(button)
            index++
        }
    }

    /**
     * Add button to the group
     */
    fun add(button: OverlayButtonImage)
    {
        val selectIndex = this.buttons.size
        button.insideGroup = true
        button.selectListener = { selected ->
            if (selected)
            {
                this.selectionChanged(selectIndex)
            }
        }
        button.toggleBehavior = true
        button.selected = false
        this.buttons.add(button)
    }

    /**
     * Select a button
     */
    fun select(indexButton: Int)
    {
        val button = indexButton.bounds(0, this.buttons.size - 1)

        if (this.buttons[button].enabled)
        {
            this.selectionChanged(button)
        }
    }

    private fun selectionChanged(index: Int)
    {
        if (index == this.selectedMutable.value)
        {
            return
        }

        this.buttons[this.selectedMutable.value].selected = false
        this.buttons[index].selected = true
        this.selectedMutable.value = index
    }
}