# Animations

[fr.jhelp.android.library.animations.Animation](../src/main/java/fr/jhelp/android/library/animations/Animation.kt)
represents a generic animation. It can be play in 3D scene.

Standard way

```kotlin
import fr.jhelp.android.library.animations.Animation
import fr.jhelp.android.library.engine.scene.Scene3D

//...
scene3D.play(animation)
//...
```

// DSL way

```kotlin
import fr.jhelp.android.library.animations.Animation
import fr.jhelp.android.library.engine.extensions.tree
import fr.jhelp.android.library.engine.view.View3D

// ...
view3D.tree {
    this.scene3D.play(animation)
}
// ...
```

* [Generic animation description](#generic-animation-description) : How to implements animation
* [Animation loop](#animation-loop) : Loop on animation until a method is called
* [Animation based a function](#animation-based-a-function) : Use a function to describe the animation
* [Animation list](#animation-list) : List of animations played one after other
* [Pause animation](#pause-animation) : Make a pause in animation
* [Animations played in parallel](#animations-played-in-parallel) : Animations launched and played in same time
* [Animation that launch a task](#animation-that-launch-a-task) : Launch a task in animation
* [Animation key frame](#animation-key-frame) : Animations bases on key frame
  * [Generic animation based on frame description](#generic-animation-based-on-frame-description) : Describes how implements a generic key frame animation
  * [Animate a 3D node](#animate-a-3d-node) : Animate a node/object in 3D
  * [Robot animation](#robot-animation) : Animates a robot

## Generic animation description

Animation have a FPS in specification.

If you extend it, remember to not do heavy/long operation inside override methods, 
else you will experience slow down in 3D.

The mandatory to override method `animate` is called when animation is played.
The received parameter is the number of frame that passed since the animation started.
It returns a boolean to indicates if animation should continue or not :
* `true` : Animation should continue
* `false` : Animation is finished. 

Their two optional method to override. If they are not override, they will do nothing :
* `initialize` : Called just before animation start. Useful for some initialization, register listeners, ...
* `finished` : Called just after animation stopped (by call stop fro scene or `animate` return false).
               Useful for free some memory, unregister some listeners ...

The method `millisecondsToFrame` is a tool to convert a number of milliseconds to a number of frame 
for the animation FPS.

## Animation loop

[fr.jhelp.android.library.animations.AnimationLoop](../src/main/java/fr/jhelp/android/library/animations/AnimationLoop.kt)
is an implementation of [generic animation](#generic-animation-description), that permits to loop on an animation.

It is composed of three animations :
* `startAnimation` : Animation play once at start before the loop animation. Once it is finished, the loop starts
* `loopAnimation` : Animation repeat in loop until the method `endLoop` is called.
* `endAnimation` : Animation called after loop is finished.

Their also a constructor with only specify the animation loop. Use it and start and end animation does nothing.

## Animation based a function

[fr.jhelp.android.library.animations.AnimationFunction](../src/main/java/fr/jhelp/android/library/animations/AnimationFunction.kt)
is an implementation of [generic animation](#generic-animation-description), bases on a function.

The function in construction is call inside `animate` of [generic animation](#generic-animation-description).
So the received parameter is the number of frame that passed since the animation started.
The returned boolean indicates if animation should continue or not :
* `true` : Animation should continue
* `false` : Animation is finished. 
 
## Animation list

[fr.jhelp.android.library.animations.AnimationList](../src/main/java/fr/jhelp/android/library/animations/AnimationList.kt)
is an implementation of [generic animation](#generic-animation-description), that contains a list of animation to play one after other.

To add animation to the list just call the `add` method.

Animations are played in added order. 
Once the first is finished, the second start.
Once second finished, the third start ...

## Pause animation

[fr.jhelp.android.library.animations.AnimationPause](../src/main/java/fr/jhelp/android/library/animations/AnimationPause.kt)
is an implementation of [generic animation](#generic-animation-description), that does nothing for a specified time.

Useful if want mark a pause in an [animation list](#animation-list)

## Animations played in parallel

[fr.jhelp.android.library.animations.AnimationParallel](../src/main/java/fr/jhelp/android/library/animations/AnimationParallel.kt)
is an implementation of [generic animation](#generic-animation-description), that contains a list of animation to play in same time.

To add animation play in same time, call the `add` method.

Combined with [animation list](#animation-list) it can result a rich animation

## Animation that launch a task

[fr.jhelp.android.library.animations.AnimationTask](../src/main/java/fr/jhelp/android/library/animations/AnimationTask.kt)
is an implementation of [generic animation](#generic-animation-description), that launch a task when it is played.

Useful if use in [animation list](#animation-list) or [animation parallel](#animations-played-in-parallel).

In [animation list](#animation-list) task launch it played after the previous animation is finished.

In [animation parallel](#animations-played-in-parallel) task launch in same time other animations starts.

## Animation key frame

[fr.jhelp.android.library.animations.keyFrame.AnimationKeyFrame](../src/main/java/fr/jhelp/android/library/animations/keyFrame/AnimationKeyFrame.kt)
is an implementation of [generic animation](#generic-animation-description), that represents a generic animation based on frames

The engine provides some implementations.

* [Generic animation based on frame description](#generic-animation-based-on-frame-description) : Describes how implements a generic key frame animation
* [Animate a 3D node](#animate-a-3d-node) : Animate a node/object in 3D
* [Robot animation](#robot-animation) : Animates a robot

### Generic animation based on frame description

The idea is to change an object property during the time. 
For this, defines value of the property for specific frames moment, and between frames interpolates the value.

The method `obtainValue` to override extract the current property value from the object.

The method `setValue` to override give a value for the changed property to the object.

The method `interpolateValue` to override is called to interpolate the property value between two values.
It changes the object's property value with the interpolated result.
The interpolation should be linear. 

For a simple `Float` value :

```kotlin
    override fun interpolateValue(animated: MyObject, before: Float, after: Float,
                                  percent: Float)
    {
        val anti = 1f - percent
        val interpolated = before * anti + after * percent
        animated.property = interpolated
    }
```

The `percent` represents the percent of progression between `before` and `after`.
It is always in [[0, 1]].
That is to say, more `percent` is near `0` more the value should be near `before`.
And more `percent` is near `1` more the value should be near `after`.

That's explains the formula in previous example.

For more examples we suggest to look pre-build implementation code.

The method `time` defines the property value for a time passed since animation started.
It specifies the interpolation to use for reach the value. 

The method `frame` defines the property value for a number of frames passed since animation started.
It specifies the interpolation to use for reach the value.

Interpolations [fr.jhelp.android.library.animations.interpolation.Interpolation](../src/main/java/fr/jhelp/android/library/animations/interpolation/Interpolation.kt)
are function that transform a value in [[0, 1]]. 

The constraints is that all values for [[0, 1]] should returns a value.
For `0` must returns `0`. For `1` must returns `1`. 
Remember if function not continue, a "teleport" effect may happen.

List of prebuilt interpolation.

|                                                                                            Class                                                                                             | Description                                                                                                                                                                            |
|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|        [fr.jhelp.android.library.animations.interpolation.AccelerationInterpolation](../src/main/java/fr/jhelp/android/library/animations/interpolation/AccelerationInterpolation.kt)        | Acceleration effect.</br>More the `factor` is great, more the effect is amplified                                                                                                      |
|          [fr.jhelp.android.library.animations.interpolation.AnticipateInterpolation](../src/main/java/fr/jhelp/android/library/animations/interpolation/AnticipateInterpolation.kt)          | Anticipation effect.</br>That is to say it look goes reverse and then go to the good way, like if it take a run-up.</br>More the `factor` is great, more the effect is amplified       |
| [fr.jhelp.android.library.animations.interpolation.AnticipateOvershootInterpolation](../src/main/java/fr/jhelp/android/library/animations/interpolation/AnticipateOvershootInterpolation.kt) | Anticipate and overshoot effect.</br>Anticipate : Like if it take a run-up.</br> Overshoot : Goes to far and return back.</br>More the `factor` is great, more the effect is amplified |
|              [fr.jhelp.android.library.animations.interpolation.BounceInterpolation](../src/main/java/fr/jhelp/android/library/animations/interpolation/BounceInterpolation.kt)              | Bounce effect.</br>Bounce three times at destination point                                                                                                                             |
|            [fr.jhelp.android.library.animations.interpolation.BouncingInterpolation](../src/main/java/fr/jhelp/android/library/animations/interpolation/BouncingInterpolation.kt)            | Bounce effect.</br>Bounce a number of specified times at destination point                                                                                                             |
|             [fr.jhelp.android.library.animations.interpolation.CosinusInterpolation](../src/main/java/fr/jhelp/android/library/animations/interpolation/CosinusInterpolation.kt)             | Cosine interpolation                                                                                                                                                                   |
|               [fr.jhelp.android.library.animations.interpolation.CubicInterpolation](../src/main/java/fr/jhelp/android/library/animations/interpolation/CubicInterpolation.kt)               | Cubic interpolation.</br>The interpolation will follow variation like cubic in path</br>See [Cubic](../../images/doc/images/path/Path.md#cubic)                                        |
|        [fr.jhelp.android.library.animations.interpolation.DecelerationInterpolation](../src/main/java/fr/jhelp/android/library/animations/interpolation/DecelerationInterpolation.kt)        | Deceleration effect.</br>More the `factor` is great, more the effect is amplified                                                                                                      |
|         [fr.jhelp.android.library.animations.interpolation.ExponentialInterpolation](../src/main/java/fr/jhelp/android/library/animations/interpolation/ExponentialInterpolation.kt)         | Exponential interpolation                                                                                                                                                              |
|            [fr.jhelp.android.library.animations.interpolation.HesitateInterpolation](../src/main/java/fr/jhelp/android/library/animations/interpolation/HesitateInterpolation.kt)            | Hesitation effect.</br>The value slow dow to the middle trajectory, the stop a little adn final goes.</br>Like if it hesitates to goes to destination.                                 |
|              [fr.jhelp.android.library.animations.interpolation.LinearInterpolation](../src/main/java/fr/jhelp/android/library/animations/interpolation/LinearInterpolation.kt)              | Linear interpolation.</br>The value just regularly progress.                                                                                                                           |
|           [fr.jhelp.android.library.animations.interpolation.LogarithmInterpolation](../src/main/java/fr/jhelp/android/library/animations/interpolation/LogarithmInterpolation.kt)           | Logarithm interpolation                                                                                                                                                                |
|           [fr.jhelp.android.library.animations.interpolation.OvershootInterpolation](../src/main/java/fr/jhelp/android/library/animations/interpolation/OvershootInterpolation.kt)           | Overshoot effect.</br>Goes to far and return back.</br>More the `factor` is great, more the effect is amplified                                                                        |
|           [fr.jhelp.android.library.animations.interpolation.QuadraticInterpolation](../src/main/java/fr/jhelp/android/library/animations/interpolation/QuadraticInterpolation.kt)           | Quadratic interpolation.</br>The interpolation will follow variation like quadratic in path</br>See [Quadratic](../../images/doc/images/path/Path.md#quadratic)                        |
|              [fr.jhelp.android.library.animations.interpolation.RandomInterpolation](../src/main/java/fr/jhelp/android/library/animations/interpolation/RandomInterpolation.kt)              | Random interpolation.</br>The value do chaotic teleports progression.                                                                                                                  |
|               [fr.jhelp.android.library.animations.interpolation.SinusInterpolation](../src/main/java/fr/jhelp/android/library/animations/interpolation/SinusInterpolation.kt)               | Sine interpolation                                                                                                                                                                     |
|              [fr.jhelp.android.library.animations.interpolation.SquareInterpolation](../src/main/java/fr/jhelp/android/library/animations/interpolation/SquareInterpolation.kt)              | Percentage square interpolation                                                                                                                                                        |
|          [fr.jhelp.android.library.animations.interpolation.SquareRootInterpolation](../src/main/java/fr/jhelp/android/library/animations/interpolation/SquareRootInterpolation.kt)          | Percentage square root interpolation                                                                                                                                                   |

### Animate a 3D node

[fr.jhelp.android.library.engine.animation.keyFrame.AnimationNode3D](../../engine/src/main/java/fr/jhelp/android/library/engine/animation/keyFrame/AnimationNode3D.kt)
is a [generic animation based on frame](#generic-animation-based-on-frame-description) for animates node/object

See [Animation node 3D](../../engine/doc/animation/Animation.md) for more details

### Robot animation

[fr.jhelp.android.library.engine.scene.geometry.robot.RobotAnimation](../../engine/src/main/java/fr/jhelp/android/library/engine/scene/geometry/robot/RobotAnimation.kt)
is a [generic animation based on frame](#generic-animation-based-on-frame-description) for animates a
[fr.jhelp.android.library.engine.scene.geometry.robot.Robot](../../engine/src/main/java/fr/jhelp/android/library/engine/scene/geometry/robot/Robot.kt)

It use a [fr.jhelp.android.library.engine.scene.geometry.robot.RobotPosition](../../engine/src/main/java/fr/jhelp/android/library/engine/scene/geometry/robot/RobotPosition.kt)
for defines each robot's joint angles for a given frame.

To know more about robot : [Robot](../../engine/doc/nodesAndObjects/geometry/Robot.md)
