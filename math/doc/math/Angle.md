# Angles

Angles are defines with a value and a unit.

Managed units are stored in the
enumeration : [fr.jhelp.android.library.math.AngleUnit](../../src/main/java/fr/jhelp/android/library/math/AngleUnit.kt)

The angle it self if represents
by [fr.jhelp.android.library.math.AngleFloat](../../src/main/java/fr/jhelp/android/library/math/AngleFloat.kt)

At construction choose the value and the unit.

Its possible two add or subtract two angles.
The add or subtract angle is converted to the current angle unit, then the operation is done, and
return the result angle with the original unit.

By example :

```kotlin
import fr.jhelp.android.library.math.AngleFloat
import fr.jhelp.android.library.math.AngleUnit

/**
 * Sample for angle
 */
fun sampleAngle()
{
    val firstAngle = AngleFloat(30.0f, AngleUnit.DEGREE)
    val secondAngle = AngleFloat(1.23f, AngleUnit.GRADE)
    val thirdAngle = AngleFloat(0.123f, AngleUnit.RADIAN)

    val additionFirstSecond = firstAngle + secondAngle
    // additionFirstSecond unit will be DEGREE since firstAngle is in DEGREE

    val subtractSecondThird = secondAngle - thirdAngle
    // subtractSecondThird unit will be GRADE since secondAngle unit is GRADE
}
```

Its also possible to multiply or divide by a factor. The result is an angle with the same unit.

Can take the opposite ange with same unit

The method `convert` convert the angle to an angle with a given unit.
If the unit is the same as the angle, the angle is returned without modification.

The `cos` method returns the angle cosinus

The `sin` method returns the angle sinus
