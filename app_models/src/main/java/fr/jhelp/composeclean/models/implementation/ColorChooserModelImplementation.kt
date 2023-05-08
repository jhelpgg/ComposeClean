package fr.jhelp.composeclean.models.implementation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import fr.jhelp.composeclean.models.shared.ColorChooserModel

internal class ColorChooserModelImplementation : ColorChooserModel
{
    private var colorSelected = 0xFF_CA_FE_00.toInt()
    override var colorChangeListener: (Int) -> Unit = {}
    private val colorMutable = mutableStateOf<Int>(0xFF_CA_FE_00.toInt())
    override val color: State<Int> = this.colorMutable
    private val redMutable = mutableStateOf<Int>(0xCA)
    override val red: State<Int> = this.redMutable
    private val greenMutable = mutableStateOf<Int>(0xFE)
    override val green: State<Int> = this.greenMutable
    private val blueMutable = mutableStateOf<Int>(0x00)
    override val blue: State<Int> = this.blueMutable

    override fun color(color: Int)
    {
        this.colorSelected = 0xFF_00_00_00.toInt() or (color and 0x00_FF_FF_FF)
        this.redMutable.value = (this.colorSelected shr 16) and 0xFF
        this.greenMutable.value = (this.colorSelected shr 8) and 0xFF
        this.blueMutable.value = this.colorSelected and 0xFF
        this.colorMutable.value = this.colorSelected
        this.colorChangeListener(this.colorSelected)
    }

    override fun red(red: Int)
    {
        this.colorSelected =
            (this.colorSelected and 0xFF_00_FF_FF.toInt()) or ((red and 0xFF) shl 16)
        this.redMutable.value = red and 0xFF
        this.colorChangeListener(this.colorSelected)
        this.colorMutable.value = this.colorSelected
    }

    override fun green(green: Int)
    {
        this.colorSelected =
            (this.colorSelected and 0xFF_FF_00_FF.toInt()) or ((green and 0xFF) shl 8)
        this.greenMutable.value = green and 0xFF
        this.colorChangeListener(this.colorSelected)
        this.colorMutable.value = this.colorSelected
    }

    override fun blue(blue: Int)
    {
        this.colorSelected =
            (this.colorSelected and 0xFF_FF_FF_00.toInt()) or (blue and 0xFF)
        this.blueMutable.value = blue and 0xFF
        this.colorChangeListener(this.colorSelected)
        this.colorMutable.value = this.colorSelected
    }
}