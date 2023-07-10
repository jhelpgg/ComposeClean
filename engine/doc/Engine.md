# 3D engine for Android

A 3D engine is a library for help to create 3D scenes/animations.

The aims of this 3D engine are :
* Consume few battery.
* Be compatible with a maximum of Android devices.
* Easy to start with.
* Integrable like any other graphics component.
* In Kotlin 100%.
* Have a classic and DSL way to do things, so developer can choose the way he prefers or the situation implies.

The features are :
* Create a 3D scene.
* Create objects.
* Have some effects.
* Have pre-build objects.
* Have a composable version.
* Create animations. They have lot of interpolation to make animation more smooth and dynamic.
* Particles effect.

# Menu

* [3D version of hello world](helloWorld/HelloWorld3D.md) : 3D hello world
  * [Classic way](helloWorld/HelloWorld3D.md#classic-way) : XML layout
    * [Standard way](helloWorld/HelloWorld3D.md#standard-way) : Strand way to manipulate objects
    * [DSL way](helloWorld/HelloWorld3D.md#dsl-way) : DSL version
  * [Composable way](helloWorld/HelloWorld3D.md#composable-way) : Use composable component
* [Material and texture](material/Material.md) : Apply image and tint on object
  * [Material](material/Material.md#material) : Material description and creation
  * [Texture](material/Material.md#texture) : Create a texture
  * [Image source](material/Material.md#image-source) : Image source description
* [Position in 3D](position/PositionIn3D.md) : Locate objects in coordinate space
  * [Location with coordinates](position/PositionIn3D.md#location-with-coordinates) : Location in the screen
  * [Rotations around main axis](position/PositionIn3D.md#rotations-around-main-axis) : To rotate object
  * [Scale along main axis](position/PositionIn3D.md#scale-along-main-axis) : To change object sizes
  * [Move an object](position/PositionIn3D.md#move-an-object) : Move an object in coordinate space
    * [Absolute change](position/PositionIn3D.md#absolute-change) : Change/set object position
    * [Relative change](position/PositionIn3D.md#relative-change) : Change object position relative to it's current position
      * [Translation](position/PositionIn3D.md#translation) : Translate an object
      * [Rotation](position/PositionIn3D.md#rotation) : Rotate an object
      * [Scale](position/PositionIn3D.md#scale) : Scale an object
