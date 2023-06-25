# Math additional tools

We add some math tools
in [fr.jhelp.android.library.math.Math.kt](../../src/main/java/fr/jhelp/android/library/math/Math.kt)
file.

Some constants :

|      Name       | Definition                                                           |
|:---------------:|:---------------------------------------------------------------------|
|   `PI_FLOAT`    | `PI` number in `Float` precision                                     |
|    `E_FLOAT`    | `e` (Euler) number in `Float` precision                              |
|    `TWO_PI`     | `PI` number multiply by 2 (That is to say theta)                     |
| `TWO_PI_FLOAT`  | `PI` number multiply by 2 (That is to say theta) in `Float`precision |
|    `EPSILON`    | Value enough near zero to consider it is zero                        |
| `EPSILON_FLOAT` | Value enough near zero to consider it is zero in `Float` precision   |
|    `AXIS_X`     | Vector for X axis                                                    |
|    `AXIS_Y`     | Vector for Y axis                                                    |
|    `AXIS_Z`     | Vector for Z axis                                                    |

List of methods

|       Name       | Definition                                                                                                                            |
|:----------------:|:--------------------------------------------------------------------------------------------------------------------------------------|
|     `square`     | Returns the square of given number                                                                                                    |
|      `sqrt`      | Returns the square root of given number                                                                                               |
|    `distance`    | Returns the distance between two points (2D or 3D)                                                                                    |
|    `maximum`     | Returns the maximum of several numbers                                                                                                |
|    `minimum`     | Returns the minimum of several numbers                                                                                                |
|      `log2`      | Returns the logarithm base 2 of given number                                                                                          |
|     `cubic`      | There two versions:<br/>- Compute cubic interpolation for one particular factor<br/>- Compute all cubic for a given precision         |
|   `quadratic`    | There two versions:<br/>- Compute quadratic interpolation for one particular factor<br/>- Compute all quadratic for a given precision |
|     `modulo`     | Compute a number modulo an other one                                                                                                  |
| `moduloInterval` | Compute a number modulo inside a given interval                                                                                       |
| `degreeToGrade`  | Transform degree angle to grade angle                                                                                                 |
| `radianToGrade`  | Transform radian angle to grade angle                                                                                                 |
| `gradeToDegree`  | Transform grade angle to degree angle                                                                                                 |
| `gradeToRadian`  | Transform grade angle to radian angle                                                                                                 |
| `degreeToRadian` | Transform degree angle to radian angle                                                                                                |
| `radianToDegree` | Transform radian angle to degree angle                                                                                                |

