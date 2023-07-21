# Random

In [fr.jhelp.android.library.math.Random](../../src/main/java/fr/jhelp/android/library/math/Random.kt) 
file their tools for choose something randomly.

Method `random` with two number (`Int` or `Float`) choose randomly a value between the given ones.

Method `random` with an array choose an element of the array randomly

Method `random` with a class of an `enum` choose an enum instance randomly.

```kotlin
import fr.jhelp.android.library.math.random

/**
 * Just samples of random
 */
fun sampleRandom() {
    // Choose an integer in 42 and 73
    val integer = random(42, 73)
    
    // Choose a float in 3.14 and 4.56789
    val real = random(3.14f, 4.56789f)
    
    val array = arrayOf("First", "Second", "Third")
    // Choose randomly an element of an array
    val element = random(array)
    
    // Choose randomly an enum instance
    val instance = random(Sample::class.java)
}

/**
 * Just a sample enum
 */
enum class Sample
{
    /** First */
    FIRST,
    /** Second */
    SECOND,
    /** Third */
    THIRD
}
```
