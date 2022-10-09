package fr.jhelp.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import fr.jhelp.compose.mutable.Mutable

@Composable
inline fun <reified T : Any> mutable(initialValue: T): Mutable<T>
{
    var value: T by remember { mutableStateOf(initialValue) }
    return Mutable<T>({ value }, { newValue -> value = newValue })
}
