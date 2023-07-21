# Colors

In [fr.jhelp.android.library.math.Colors.kt](../../src/main/java/fr/jhelp/android/library/math/Colors.kt) 
file can find lot of defined colors constants.

The constants with name start with `COLOR_ALPHA_`   re predefined transparency.

For example to have amber 600 with transparency for hint :

```kotlin
import fr.jhelp.android.library.math.COLOR_ALPHA_HINT
import fr.jhelp.android.library.math.COLOR_AMBER_0600
import fr.jhelp.android.library.math.COLOR_MASK

/**
 * Sample for colors
 */
fun sampleColors()
{
    val amber600Hint = COLOR_ALPHA_HINT or (COLOR_AMBER_0600 and COLOR_MASK)
    //...
}
```

The `and COLOR_MASK` is here to remove initial transparency
