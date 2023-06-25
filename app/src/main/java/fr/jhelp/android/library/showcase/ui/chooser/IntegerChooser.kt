package fr.jhelp.android.library.showcase.ui.chooser

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.jhelp.android.library.math.extensions.bounds
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/**
 * Chooser of integer between two values.
 * The progression defines by a step
 */
class IntegerChooser(minimum: Int = 0,
                     maximum: Int = 16,
                     start: Int = (minimum + maximum) / 2,
                     step: Int = 1)
{
    private val minimum = min(minimum, maximum)
    private val maximum = max(minimum, maximum)
    private val step = step.bounds(1, abs(maximum - minimum))
    private val valueMutable = mutableStateOf(start.bounds(minimum, maximum))

    /**
     * Selected value
     */
    val value: State<Int> = this.valueMutable


    /**
     * Show the selector
     */
    @Composable
    fun Show(modifier: Modifier = Modifier)
    {
        val self = this
        Row(modifier = modifier.wrapContentSize()) {
            // Minus button
            Button(onClick = {
                self.valueMutable.value = max(self.minimum, self.valueMutable.value - self.step)
            },
                   enabled = self.value.value > self.minimum,
                   shape = RoundedCornerShape(8.dp),
                   colors = ButtonDefaults.textButtonColors(containerColor = Color.White)
                  ) {
                Text(text = "-",
                     color = Color.Black,
                     fontSize = 24.sp,
                     textAlign = TextAlign.Center,
                     fontFamily = FontFamily.Monospace)
            }

            // Value text
            Text(text = self.value.value.toString(),
                 color = Color.Black,
                 fontSize = 24.sp,
                 fontFamily = FontFamily.Monospace,
                 textAlign = TextAlign.Center,
                 modifier = Modifier
                     .background(Color.White)
                     .widthIn(64.dp, 128.dp))

            // Add button
            Button(onClick = {
                self.valueMutable.value = min(self.maximum, self.valueMutable.value + self.step)
            },
                   enabled = self.value.value < self.maximum,
                   shape = RoundedCornerShape(8.dp),
                   colors = ButtonDefaults.textButtonColors(containerColor = Color.White)
                  ) {
                Text(text = "+",
                     color = Color.Black,
                     fontSize = 24.sp,
                     textAlign = TextAlign.Center,
                     fontFamily = FontFamily.Monospace)
            }
        }
    }
}