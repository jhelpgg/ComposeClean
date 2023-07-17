# Scene3D

[fr.jhelp.android.library.engine.scene.Scene3D](../../src/main/java/fr/jhelp/android/library/engine/scene/Scene3D.kt)
represents the scene draw on 3D.

As we saw in [Node hierarchy](../nodeHierarchy/NodeHierarchy.md) it contains a root node that use 
as main node. All nodes and objects draw are decedent of that root node. 

`Scene3D` have also some other capacities.

`Scene3D` fields:

|    Field name     | Description                                                                                                  |
|:-----------------:|:-------------------------------------------------------------------------------------------------------------|
|  backgroundColor  | The scene background color                                                                                   |
|       root        | The main node of the entire scene                                                                            |
| textureBackground | Texture draw on background, if not `null`.</br>It draws over the background color.                           |
|   textureOver3D   | Texture draw over the 3D, if not `null`.</br>To see the 3D behind, use transparent or semi-transparent part. |

`Scene3D` methods

|    Method signature    | Description                                                                                                                                                                                                                                                                                             |
|:----------------------:|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|    play(Animation)     | Play an animation util it stopped by it self or manually.</br>See [Animations](../animation/Animation.md)                                                                                                                                                                                               |
| play((Float)->Boolean) | Play a function until it returns `false`.</br>The method will reserve the number of frames played since the method `play` was called.</br>The `Boolean` returns indicates if have to continue to call the lambda (`true`) or if it is finished (`false`).</br> Don't do heavy operation in that lambda. |
|  play(ParticleEffect)  | Launch a particle effect. See [Particle effect](../particleEffect/ParticleEffect.md)                                                                                                                                                                                                                    |
|    stop(Animation)     | Stop an animation. See [Animations](../animation/Animation.md)                                                                                                                                                                                                                                          |
|  stop(ParticleEffect)  | Stop a particle effect. See [Particle effect](../particleEffect/ParticleEffect.md)                                                                                                                                                                                                                      |

