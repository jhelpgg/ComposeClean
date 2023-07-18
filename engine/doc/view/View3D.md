# View 3D

[fr.jhelp.android.library.engine.view.View3D](../../src/main/java/fr/jhelp/android/library/engine/view/View3D.kt)
is the graphic component where the 3D is draw.

See [Hello world](../helloWorld/HelloWorld3D.md) to learn how add it in your project as any other 
graphical component (text, button, image, ...)

It carries the scene with its `scene3D` field.

With [fr.jhelp.android.library.engine.extensions.tree](../../src/main/java/fr/jhelp/android/library/engine/extensions/View3DExtensions.kt)
extension used in DSL way, have directly access to the scene inside the lambda.

By default the view rotate the root node when user move finger on screen.
And it zoom when user use two fingers with pinch movement.
This behavior can be configured or completely changed.

* [Customize the default manipulation](#customize-the-default-manipulation) : Customize the default manipulation
* [Change the default manipulation](#change-the-default-manipulation) : Change the default manipulation.
  * [Customize manipulation](#customize-manipulation)  : Completely customize the touch reaction
  * [Block all interaction](#block-all-interaction) : No more interaction
  * [Touch to move](#touch-to-move) : Move manipulate object instead of rotate it
  * [Sensitive touch](#sensitive-touch) : Sensitive touch the screen
      * [Customizable sensitive](#customizable-sensitive) : Customize the sensitive reaction
      * [Virtual joystick](#virtual-joystick) : Virtual joystick over the screen

## Customize the default manipulation

To change the manipulate node, just change the value of the field `manipulateNode` with the node/object to manipulate.

To manipulate the node again, the method `manipulateRoot()` is a shor cut to 
```kotlin
view3D.manipulateNode = view3D.scene3D.root
```

Their way to limit/change limitation of manipulation with fields:

|     Field     | Description                          |      Initial value      |
|:-------------:|:-------------------------------------|:-----------------------:|
| minimumAngleX | Minimum angle possible around X axis | Float.NEGATIVE_INFINITY |
| maximumAngleX | Maximum angle possible around X axis | Float.POSITIVE_INFINITY |
| minimumAngleY | Minimum angle possible around Y axis | Float.NEGATIVE_INFINITY |
| maximumAngleY | Maximum angle possible around Y axis | Float.POSITIVE_INFINITY |
|   minimumZ    | Minimum value possible for Z         |           -9f           |
|   maximumZ    | Maximum value possible for Z         |           -1f           |


## Change the default manipulation

Their other possible manipulation.  

In fact view touch is specified by `view3DTouchAction` field.
It is a [fr.jhelp.android.library.engine.view.touch.View3DTouchAction](../../src/main/java/fr/jhelp/android/library/engine/view/touch/View3DTouchAction.kt) 

Have to extends this abstract class `View3DTouchAction` to create own interaction, or use one other 
prebuilt one. The default one is [fr.jhelp.android.library.engine.view.touch.View3DTouchManipulation](../../src/main/java/fr/jhelp/android/library/engine/view/touch/View3DTouchManipulation.kt)

* [Customize manipulation](#customize-manipulation)  : Completely customize the touch reaction
* [Block all interaction](#block-all-interaction) : No more interaction
* [Touch to move](#touch-to-move) : Move manipulate object instead of rotate it
* [Sensitive touch](#sensitive-touch) : Sensitive touch the screen
  * [Customizable sensitive](#customizable-sensitive) : Customize the sensitive reaction
  * [Virtual joystick](#virtual-joystick) : Virtual joystick over the screen

### Customize manipulation

To completely customize the interaction extends [fr.jhelp.android.library.engine.view.touch.View3DTouchAction](../../src/main/java/fr/jhelp/android/library/engine/view/touch/View3DTouchAction.kt)

|  Method name   | Description                                                                                                     |
|:--------------:|:----------------------------------------------------------------------------------------------------------------|
|    attachTo    | Called when the touch action is attached to view 3D. Can be used for initialize things, register listeners ...  |
|   detachFrom   | Called when the touch action is detached from view 3D. Can be used to free memory, unregister some liteners ... |
| oneFingerDown  | Called when one finger touch the screen.                                                                        |
|  oneFingerUp   | Called when one finger removed from th screen                                                                   |
| oneFingerMove  | Called when one finger slide on the screen                                                                      |
| twoFingersDown | Called when two fingers touch the screen                                                                        |
|  twoFingersUp  | Called when two fingers removed from the screen                                                                 |
| twoFingersMove | Called when two fingers slides on the screen                                                                    |

### Block all interaction

[fr.jhelp.android.library.engine.view.touch.View3DTouchNothing](../../src/main/java/fr/jhelp/android/library/engine/view/touch/View3DTouchNothing.kt)
is a
[fr.jhelp.android.library.engine.view.touch.View3DTouchAction](../../src/main/java/fr/jhelp/android/library/engine/view/touch/View3DTouchAction.kt)
that block all interaction. That can be useful during an animation to block eporary the interaction.

### Touch to move

[fr.jhelp.android.library.engine.view.touch.View3DTouchMove](../../src/main/java/fr/jhelp/android/library/engine/view/touch/View3DTouchMove.kt)
is a
[fr.jhelp.android.library.engine.view.touch.View3DTouchAction](../../src/main/java/fr/jhelp/android/library/engine/view/touch/View3DTouchAction.kt)
it manipulates the `manipulateNode` but this time the manipulate not move in the screen with one finger.

### Sensitive touch

[fr.jhelp.android.library.engine.view.touch.View3DTouchOverSensitive](../../src/main/java/fr/jhelp/android/library/engine/view/touch/View3DTouchOverSensitive.kt)
is a
[fr.jhelp.android.library.engine.view.touch.View3DTouchAction](../../src/main/java/fr/jhelp/android/library/engine/view/touch/View3DTouchAction.kt)
with something draw over the 3D and can react to user touch.

It use a [fr.jhelp.android.library.engine.view.touch.View3DTouchOverListener](../../src/main/java/fr/jhelp/android/library/engine/view/touch/View3DTouchOverListener.kt)
to determines how to react to touch with one finger.

Since it draws a texture sized 512x512 over the 3D, all touch coordinates are convert in texture coordinates.

Their pre-build instance of `View3DTouchOverListener`

* [Customizable sensitive](#customizable-sensitive) : Customize the sensitive reaction
* [Virtual joystick](#virtual-joystick) : Virtual joystick over the screen

#### Customizable sensitive

Implements 
[fr.jhelp.android.library.engine.view.touch.View3DTouchOverListener](../../src/main/java/fr/jhelp/android/library/engine/view/touch/View3DTouchOverListener.kt)
to create customized reaction.

By default the 512x512 texture is fully transparent.

|    Method     | Description                                                                                                                           |
|:-------------:|:--------------------------------------------------------------------------------------------------------------------------------------|
|   onRefresh   | Called when texture refresh is nedd.</br>Use the given `android.graphics.Canvas` and `android.graphics.Paintè to draw on the texture. |
|    onDown     | Called when on finger touch the texture                                                                                               |
|     onUp      | Called each time one finger removed from texture                                                                                      |
|    onClick    | Called when a click is detected.</br>Click is defined by a touch follow by remove finger in a short time.                             |
|    onMove     | Called when one finger slides on the texture                                                                                          |
| dirtyListener | Gives the callback to call if you want request to refresh the texture                                                                 |

#### Virtual joystick

[fr.jhelp.android.library.engine.view.joystick.VirtualJoystick](../../src/main/java/fr/jhelp/android/library/engine/view/joystick/VirtualJoystick.kt)
is a
[fr.jhelp.android.library.engine.view.touch.View3DTouchOverListener](../../src/main/java/fr/jhelp/android/library/engine/view/touch/View3DTouchOverListener.kt)
that draws a virtual joystick over the screen when user slide on screen.

It is possible to react to joystick position change and to a click on screen.

The [fr.jhelp.android.library.engine.view.joystick.VirtualJoystickType](../../src/main/java/fr/jhelp/android/library/engine/view/joystick/VirtualJoystickType.kt)
required at construction permits to choose the joystick color.

Give a lambda to the `click` field to react when user click on screen.

Register to the state flow `direction` to have the actual joystick direction and react when it changes.

```kotlin
import fr.jhelp.android.library.engine.view.View3D
import fr.jhelp.android.library.engine.view.joystick.VirtualJoystick
import fr.jhelp.android.library.engine.view.joystick.VirtualJoystickDirection
import fr.jhelp.android.library.engine.view.joystick.VirtualJoystickType
import fr.jhelp.android.library.engine.view.touch.View3DTouchOverSensitive
import fr.jhelp.android.library.tasks.TaskType
import fr.jhelp.android.library.tasks.extensions.observedBy

// ...
    val virtualJoystick = VirtualJoystick(VirtualJoystickType.GREEN)
    view3D.view3DTouchAction = View3DTouchOverSensitive(virtualJoystick)

    virtualJoystick.click = ::clickAction
    virtualJoystick.direction.observedBy(TaskType.SHORT_TASK, ::directionChanged)
// ...

private fun clickAction()
{
    // ...
}

private fun directionChanged(direction: VirtualJoystickDirection)
{
    when(direction)
    {
        // ...
    }
}
```
