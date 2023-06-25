# Math tools for 3D

Those tools are use in the 3D engine. (See [3D engine for Android](../../../engine/doc/Engine.md))

They behaviors are actually limited to the 3D engine needs, may more comes later.

* [Point2D](#frjhelpandroidlibrarymathpoint2dsrcmainjavafrjhelpandroidlibrarymathpoint2dkt) : 2D point
* [Point3D](#frjhelpandroidlibrarymathpoint3dsrcmainjavafrjhelpandroidlibrarymathpoint3dkt) : 3D point
* [Vector3D](#frjhelpandroidlibrarymathvector3dsrcmainjavafrjhelpandroidlibrarymathvector3dkt) : 3D vector
* [Rotation3D](#frjhelpandroidlibrarymathrotation3dsrcmainjavafrjhelpandroidlibrarymathrotation3dkt) : 3D rotation
* [Matrix](#frjhelpandroidlibrarymathmatrixsrcmainjavafrjhelpandroidlibrarymathmatrixkt) : 3x3 matrix

## [fr.jhelp.android.library.math.Point2D](../../src/main/java/fr/jhelp/android/library/math/Point2D.kt)

Represents a point in a 2D plane.

## [fr.jhelp.android.library.math.Point3D](../../src/main/java/fr/jhelp/android/library/math/Point3D.kt)

Represents a point in a 3D space.

## [fr.jhelp.android.library.math.Vector3D](../../src/main/java/fr/jhelp/android/library/math/Vector3D.kt)

Represents a vector in a 3D space.

Actual capacities :

* Have vector length
* Multiply by a factor
* Compute the cross product with an other vector
* Add two vectors
* Compute the dot product of the vector

## [fr.jhelp.android.library.math.Rotation3D](../../src/main/java/fr/jhelp/android/library/math/Rotation3D.kt)

Represents a rotation in a 3D space.
The representation choose is a quaternion ([Quaternion](https://en.wikipedia.org/wiki/Quaternion)).
Here only those of length 1 interesting us, because they also can be see as a direction vector and
an angle.

Actual methods :

* `identity()` : Makes the rotation as identity. That is to say a rotation of angle 0, so move
  nothing.
* `get(Vector3D):AngleFloat` : Compute the rotation direction and fill the given vector with
  computed direction.
  It returns the rotation angle.
* `-` : returns the opposite rotation. (Same direction but opposite angle)
* `opposite()` : Make the rotation turn in opposite angle
* `multiply(Rotation3D, Rotation3D)` : Combine two rotations (first then second), compute the result
  rotation and set this rotation with the result.
* `rotateVector(Vector3D) : Vector3D` : Apply rotation to vector and return the rotated vector
* `rotateVector(Vector3D, Vector3D) : Vector3D` : Apply rotation to fisrt vector fill the second
  with rotated vector and return the second
* `copy(Rotation3D)` : Copy the given rotation, so the rotation becomes the same as given one.
* `set(Vector3D, AngleFloat)` : Change the rotation to become rotation around given vector with
  given vector.
* `set(Vector3D, Vector3D)` : Change the rotation to be one that transform the first vector to
  second one.
* `*` : Combine two rotations (first then second), and return the result rotation.

## [fr.jhelp.android.library.math.Matrix](../../src/main/java/fr/jhelp/android/library/math/Matrix.kt)

Represents a 3x3 matrix. Here the usage is limited to represents rotation in 2D space.

To create instance, the constructor creates the identity matrix at start (matrix that change
nothing. T

Other way to create matrix is to use the "flying" method : `obtainRotateMatrix`

```kotlin
import fr.jhelp.android.library.math.obtainRotateMatrix

// ...
fun compute()
{
    // ... 
    val rotationMatrix = obtainRotateMatrix(centerX, centerY, angle)
    // ...
}
```

Available methods :

* `toIdentity()` : Put the matrix to identity. That is to say rotation that does nothing, since
  angle of 0
* `toRotate(Flot, Float, AngleFloat)` : Make the matrix be the rotation center at given coordinates
  with given angle.
* `transform(Flaot, Float):Point2D` : Apply the transformation to given coordinates and returns the
  result point.
