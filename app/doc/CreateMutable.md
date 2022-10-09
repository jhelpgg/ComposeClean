# Create mutable

In [MutableCreate](../src/main/java/fr/jhelp/compose/MutableCreate.kt) the method `mutable` is a 
shortcut to create an holder mutable form composable context to share to model 

Example extracted from [MainActivityComposable](../src/main/java/fr/jhelp/composeclean/ui/composables/MainActivityComposable.kt)

```kotlin
package fr.jhelp.composeclean.ui.composables

// ...
import fr.jhelp.compose.mutable
// ...

/**
 * Main activity composable
 */
class MainActivityComposable
{
    private val applicationContext: Context by provided<Context>()
    private val mainModel: MainModel by provided<MainModel>()

    @Composable
    fun Show()
    {
        // Create holders and initialize the model
        val textChoice = mutable<TextChoice>(TextChoice.CHOICE1)
        this.mainModel.initialize(textChoice)
        //  ....
    }
    // ....
}
```
