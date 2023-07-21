# Number ans String extensions

Some extensions for simplify the life.
Get them for package `fr.jhelp.android.library.math.extensions`

* [Double extensions](#double-extensions) : Extensions for Double
* [Float extensions](#float-extensions) : Extensions for Float
* [Long extensions](#long-extensions) : Extensions for Long
* [Int extensions](#int-extensions) : Extensions for Int
* [String extensions](#string-extensions) : Extensions for String

## Double extensions

Double extensions are defines in the file [fr.jhelp.android.library.math.extensions.DoubleExtensions.kt](../../../src/main/java/fr/jhelp/android/library/math/extensions/DoubleExtensions.kt).

|  Extension signature   | Description                                                                                                                                                                                                                                                                                                |
|:----------------------:|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| bounds(Double, Double) | Limit a double to given interval.<br/>If the double lower or equals to the minimum of given doubles, the returns values is the minimum..<br/>If the double upper or equals to the maximum of given doubles, the returns values is the maximum.<br/>If value between the limit, the value is returns as is. |
|      nul:Boolean       | Indicates if double can be consider as zero                                                                                                                                                                                                                                                                |
|      sign(): Int       | Give the sigh of the double                                                                                                                                                                                                                                                                                |
| compare(Double) : Int  | Compare with an other double                                                                                                                                                                                                                                                                               |
| same(Double): Boolean  | Indicates if given double can be consider equals to given one                                                                                                                                                                                                                                              |

## Float extensions

Float extensions are defines in the file [fr.jhelp.android.library.math.extensions.FloatExtensions.kt](../../../src/main/java/fr/jhelp/android/library/math/extensions/FloatExtensions.kt).

| Extension signature  | Description                                                                                                                                                                                                                                                                                           |
|:--------------------:|:------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| bounds(Float, Float) | Limit a float to given interval.<br/>If the float lower or equals to the minimum of given floats, the returns values is the minimum..<br/>If the float upper or equals to the maximum of given floats, the returns values is the maximum.<br/>If value between the limit, the value is returns as is. |
|     nul:Boolean      | Indicates if float can be consider as zero                                                                                                                                                                                                                                                            |
|     sign(): Int      | Give the sigh of the float                                                                                                                                                                                                                                                                            |
| compare(Float) : Int | Compare with an other float                                                                                                                                                                                                                                                                           |
| same(Float): Boolean | Indicates if given float can be consider equals to given one                                                                                                                                                                                                                                          |
|      floor:Int       | Compute the maximum integer lower or equals to this number                                                                                                                                                                                                                                            |
|       ciel:Int       | Compute the minimum integer greater or equals to this number                                                                                                                                                                                                                                          |
|      round:Int       | Compute the nearest integer to this number                                                                                                                                                                                                                                                            |

## Long extensions

Long extensions are defines in the file [fr.jhelp.android.library.math.extensions.LongExtensions.kt](../../../src/main/java/fr/jhelp/android/library/math/extensions/LongExtensions.kt).

Some methods are infix, that is to say they can be use with syntax like they are operator like `*`, `+`, ...

To better understanding, see the example bellow the extensions table

| Extension signature | Description                                                            |
|:-------------------:|:-----------------------------------------------------------------------|
|   GCD(Long):Long    | Infix extension that returns the Greater Common Divisor of the numbers |
|   LCM(Long):Long    | Infix extension that returns the Lower Common Multiple of the numbers  |
|    seconds:Long     | The value is a number of seconds in milliseconds                       |
|    minutes:Long     | The value is a number of minutes in milliseconds                       |
|     hours:Long      | The value is a number of hours in milliseconds                         |
|      days:Long      | The value is a number of days in milliseconds                          |

```kotlin
import fr.jhelp.android.library.math.extensions.GCD
import fr.jhelp.android.library.math.extensions.LCM

/**
 * Sample for NumberExtensions
 */
fun sampleAngle()
{
    val value1 = 142731L
    val value2 = 333L
    val gcd = value1 GCD value2
    val lcm = value1 LCM value2
    // ...
}
```

## Int extensions

Int extensions are defines in the file [fr.jhelp.android.library.math.extensions.IntExtensions.kt](../../../src/main/java/fr/jhelp/android/library/math/extensions/IntExtensions.kt).

Some methods are infix, that is to say they can be use with syntax like they are operator like `*`, `+`, ...

To better understanding, see the example bellow the extensions table

| Extension signature  | Description                                                                                                                                                                                                                                                                                                      |
|:--------------------:|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| bounds(Int, Int):Int | Limit an integer to given interval.<br/>If the integer lower or equals to the minimum of given integers, the returns values is the minimum..<br/>If the integer upper or equals to the maximum of given integers, the returns values is the maximum.<br/>If value between the limit, the value is returns as is. |
|  useAlpha(Int):Int   | Apply an alpha to a color                                                                                                                                                                                                                                                                                        |
|     GCD(Int):Int     | Infix extension that returns the Greater Common Divisor of the numbers                                                                                                                                                                                                                                           |
|     LCM(Int):Int     | Infix extension that returns the Lower Common Multiple of the numbers                                                                                                                                                                                                                                            |
|     seconds:Int      | The value is a number of seconds in milliseconds                                                                                                                                                                                                                                                                 |
|     minutes:Int      | The value is a number of minutes in milliseconds                                                                                                                                                                                                                                                                 |
|      hours:Int       | The value is a number of hours in milliseconds                                                                                                                                                                                                                                                                   |
|       days:Int       | The value is a number of days in milliseconds                                                                                                                                                                                                                                                                    |

```kotlin
import fr.jhelp.android.library.math.extensions.GCD
import fr.jhelp.android.library.math.extensions.LCM

/**
 * Sample for NumberExtensions
 */
fun sampleAngle()
{
    val value1 = 142731
    val value2 = 333
    val gcd = value1 GCD value2
    val lcm = value1 LCM value2
    // ...
}
```

## String extensions

String extensions are defines in the file [fr.jhelp.android.library.math.extensions.StringExtensions.kt](../../../src/main/java/fr/jhelp/android/library/math/extensions/StringExtensions.kt).

The method `compareAlphabet(String):Int` compare the String with given one.
First it compare them in case insensitive mode.
* If the are different, returns the comparison result.
* If consider equals, return comparison in sensitive case mode.

