# Formal functions

The idea is to create formal writing of equation. 
For example :

```kotlin
import fr.jhelp.android.library.math.formal.X
import fr.jhelp.android.library.math.formal.Y
import fr.jhelp.android.library.math.formal.minus
import fr.jhelp.android.library.math.formal.plus
import fr.jhelp.android.library.math.formal.times

/**
 * Sample for formal
 */
fun main()
{
    val equation1 = X + 3 * Y
    val equation2 = Y - X
    val result = equation1 + equation2
    println("result=$result")
    // Here we want that result is 4 * Y. But its more complex, we have to simplify it:
    val simplifiedResult = result.simplifyMax()
    println("simplifiedResult=$simplifiedResult")
    // Here we have what we want. We can spy the simplification steps :
    val simplifiedResultSpied = result.simplifyMax { function -> println(" -> $function") }
    println("simplifiedResultSpied=$simplifiedResultSpied")
    // Result is the same but we have also print the simplification steps
}
```

Console result

```text
result=X + 3.0 * Y + (Y - X)
simplifiedResult=4.0 * Y
 -> X + 3.0 * Y + (Y - X)
 -> 0.0 + Y + 3.0 * Y
 -> 4.0 * Y
 -> 4.0 * Y
simplifiedResultSpied=4.0 * Y
```

The formal functions are classes that extends [fr.jhelp.android.library.math.formal.MathFunction](../../../src/main/java/fr/jhelp/android/library/math/formal/MathFunction.kt) 

* [Constant](#constant) : Defines constants
* [Variable](#variable) : Define variables
* [Cosinus](#cosinus) : Cosinus function
* [Sinus](#sinus) : Sinus function
* [Unary minus](#unary-minus) : Opposite of a function
* [Addition](#addition) : Add two functions
* [Subtraction](#subtraction) : Subtract two functions
* [Multiplication](#multiplication) : Multiply two functions
* [Division](#division) : Divide two functions
* [Operations can be made on formal functions](#operations-can-be-made-on-formal-functions) : Operations can be do with function

## Constant

The class [fr.jhelp.android.library.math.formal.Constant](../../../src/main/java/fr/jhelp/android/library/math/formal/Constant.kt)
represents a constant. That is to say a value like 4.0, 5.9, ... never change.

A constant can be `invalid`  by example the result of division by zero.

Its possible to create them by using explicitly the class constructor. 
But it is more elegant to benefits Kotlin defined `DSL` or the extension `const` when ever `DSL` can't be applied.

Like in main example, just use `3` without explicit conversion.

```kotlin
import fr.jhelp.android.library.math.formal.X
import fr.jhelp.android.library.math.formal.Y
import fr.jhelp.android.library.math.formal.plus
import fr.jhelp.android.library.math.formal.times

// ...
val equation1 = X + 3 * Y
// ...
```

Some situation force to use the `const` extension, like if want use a constant alone.

```kotlin
import fr.jhelp.android.library.math.formal.const

// ...
val constant = 5.const
// ...
```

## Variable

The class [fr.jhelp.android.library.math.formal.Variable](../../../src/main/java/fr/jhelp/android/library/math/formal/Variable.kt)
Represents a variable.
That is to say something we don't know the value.

Its is represented by a String. Here we only accept String that follow the condition :
* Start with a letter `a` to `z` or `A` to `Z` (accent are not allowed)
* Follow by zero or more letter `a` to `z` or `A` to `Z` (accent are not allowed) or digit `0` to `9` or `_`

In another word, they have to follow the regular expression : `[a-zA-Z][a-zA-Z0-9_]*`

Variable name are case sensitive, that is to say `X` and `x` are different variables. 

Usually in Mathematics we use the letters : `x`, `y`, `z`, ...

Predefined variables :
* `fr.jhelp.android.library.math.formal.X`
* `fr.jhelp.android.library.math.formal.Y`
* `fr.jhelp.android.library.math.formal.Z`
* `fr.jhelp.android.library.math.formal.T`
* `fr.jhelp.android.library.math.formal.A`
* `fr.jhelp.android.library.math.formal.alpha`
* `fr.jhelp.android.library.math.formal.epsilon`

Kotlin DSL can resolve some situation on using only String.

```kotlin
import fr.jhelp.android.library.math.formal.X
import fr.jhelp.android.library.math.formal.minus
import fr.jhelp.android.library.math.formal.plus

// ...
val equation = X + "b" - "F"
// ...
```

But sometime the `variable extension is necessary`

```kotlin
import fr.jhelp.android.library.math.formal.variable

// ...
val equation = "F".variable
// ...
```

## Cosinus

The class [fr.jhelp.android.library.math.formal.Cosinus](../../../src/main/java/fr/jhelp/android/library/math/formal/Cosinus.kt)
represents the cosinus of a function.

To create one, can use the `fr.jhelp.android.library.math.formal.cos` method

```kotlin
import fr.jhelp.android.library.math.formal.alpha
import fr.jhelp.android.library.math.formal.cos
import fr.jhelp.android.library.math.formal.minus
import kotlin.math.PI

/**
 * Sample for formal
 */
fun main()
{
    val equation = cos(PI - alpha)
}
```


## Sinus

The class [fr.jhelp.android.library.math.formal.Sinus](../../../src/main/java/fr/jhelp/android/library/math/formal/Sinus.kt)
represents the sinus of a function.

To create one, can use the `fr.jhelp.android.library.math.formal.sin` method

```kotlin
import fr.jhelp.android.library.math.formal.alpha
import fr.jhelp.android.library.math.formal.div
import fr.jhelp.android.library.math.formal.sin

/**
 * Sample for formal
 */
fun main()
{
    val equation = sin(alpha / 2.0)
}

```

## Unary minus

The class [fr.jhelp.android.library.math.formal.UnaryMinus](../../../src/main/java/fr/jhelp/android/library/math/formal/UnaryMinus.kt)
represents the opposite of a function

Usually we prefer use the `unaryMinus` operator

```kotlin
import fr.jhelp.android.library.math.formal.X
import fr.jhelp.android.library.math.formal.unaryMinus

/**
 * Sample for formal
 */
fun main()
{
    val equation = -X
}
```

## Addition

The class [fr.jhelp.android.library.math.formal.Addition](../../../src/main/java/fr/jhelp/android/library/math/formal/Addition.kt)
represents the the addition of two functions

Usually we prefer use the `plus` operator

```kotlin
import fr.jhelp.android.library.math.formal.Y
import fr.jhelp.android.library.math.formal.Z
import fr.jhelp.android.library.math.formal.plus

/**
 * Sample for formal
 */
fun main()
{
    val equation = Y + Z
}
```

## Subtraction

The class [fr.jhelp.android.library.math.formal.Subtraction](../../../src/main/java/fr/jhelp/android/library/math/formal/Subtraction.kt)
represents the the subtraction of two functions

Usually we prefer use the `minus` operator

```kotlin
import fr.jhelp.android.library.math.formal.Y
import fr.jhelp.android.library.math.formal.Z
import fr.jhelp.android.library.math.formal.minus

/**
 * Sample for formal
 */
fun main()
{
    val equation = Y - Z
}
```

## Multiplication

The class [fr.jhelp.android.library.math.formal.Multiplication](../../../src/main/java/fr/jhelp/android/library/math/formal/Multiplication.kt)
represents the the multiplication of two functions

Usually we prefer use the `times` operator

```kotlin
import fr.jhelp.android.library.math.formal.Y
import fr.jhelp.android.library.math.formal.Z
import fr.jhelp.android.library.math.formal.times

/**
 * Sample for formal
 */
fun main()
{
    val equation = Y * Z
}
```

## Division

The class [fr.jhelp.android.library.math.formal.Division](../../../src/main/java/fr/jhelp/android/library/math/formal/Division.kt)
represents the the division of two functions

Usually we prefer use the `div` operator

```kotlin
import fr.jhelp.android.library.math.formal.Y
import fr.jhelp.android.library.math.formal.Z
import fr.jhelp.android.library.math.formal.div

/**
 * Sample for formal
 */
fun main()
{
    val equation = Y / Z
}
```

## Operations can be made on formal functions

Those operations are common to all previous presented classes since they extends `MathFunction`: 

|                         Method                          | Description                                                                                                                       |
|:-------------------------------------------------------:|:----------------------------------------------------------------------------------------------------------------------------------|
|               simplify(): MathFunction<*>               | Compute (if possible) a simplier version of the function                                                                          |
|   replace(Variable, MathFunction<*>): MathFunction<*>   | Compute a function with replace the given variable by the given function                                                          |
|           collectVariables(TreeSet<Variable>)           | Collect all function variables inside the given `TreeSet`                                                                         |
|          derivative(Variable): MathFunction<*>          | Compute the derivative of the function on given variable                                                                          |
|             simplifyMax(): MathFunction<*>              | Simplify the function as much as possible and return the result                                                                   |
| simplifyMax((MathFunction<*>) -> Unit): MathFunction<*> | Simplify the function as much as possible and return the result. It sends to given function each steps take during simplification |
