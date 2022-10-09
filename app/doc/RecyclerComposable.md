# Recycler composable

The aim is to have list/grid simple without have to manage empty holder and solve sort and filter issue
in easy way.

To use it creates a [RecyclerComposable](../src/main/java/fr/jhelp/compose/ui/recycler/RecyclerComposable.kt)

It requires a composable function to draw a cell and data element type.
By example to draw a text with String element :

```kotlin
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class ExampleListComposable()
{
    @Composable
    fun Draw()
    {
        val recyclerComposable = RecyclerComposable<String> {string -> Text(text = string)}
        recyclerComposable.Draw()
    }
}
```

You can change the number of column to look like a grid with `gridSpan` property

It provides a model to manipulates the data inside the list

```kotlin
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

class ExampleListComposable()
{
    @Composable
    fun Draw()
    {
        val recyclerComposable = RecyclerComposable<String> { string -> Text(text = string) }
        recyclerComposable.Draw()
        val recyclerModel = recyclerComposable.recyclerModel
        recyclerModel += listOf("Hello", "world", "in", "list")
    }
}
```

To have more detail about the model see [Common](../../common/doc/Common.md)

To have a complete list of all available operations on model see [RecyclerModel](../../common/src/main/java/fr/jhelp/compose/ui/recycler/RecyclerModel.kt)

An other example can be found in [ContactListActivityComposable](../src/main/java/fr/jhelp/composeclean/ui/composables/ContactListActivityComposable.kt)
