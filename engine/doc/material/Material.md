# Material and texture

In [3D version of hello world](../helloWorld/HelloWorld3D.md) we drew a grey cube.
Here we will learn how to change the color and put image on it.

* [Material](#material) : Material description and creation
* [Texture](#texture) : Create a texture
* [Image source](#image-source) : Image source description

## Material

A material describes how render a 3D object. 
For this we use the class [fr.jhelp.android.library.engine.scene.Material](../../src/main/java/fr/jhelp/android/library/engine/scene/Material.kt)

It have 3 fields :

| Field name | Description                                                                                                                                                                      |
|:----------:|:---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
|   alpha    | It defines the opacity level of the object in [[0, 1]].<br/>More the value is near 1, more object will be opaque.<br/>More the value is near 0, more object will be transparent. |
|  diffuse   | Diffuse color on object.<br/>Diffuse color defines the base color of the object.<br/>Mix with texture it will tint it.                                                           |
|  texture   | Texture to use.<br/>A texture is an image applies to an object.                                                                                                                  |

By default each object have its own `Material` instance, but it may b a good idea to create one instance and share it to several object.

So the material's object can be directly change :

```kotlin
import fr.jhelp.android.library.engine.scene.RED
import fr.jhelp.android.library.engine.scene.geometry.Box

// ...
    val box = Box()
    // ...
    box.material.diffuse = RED
// ...
```

Material can be create and share to several object.

Classic way :

```kotlin
import fr.jhelp.android.library.engine.scene.GREEN
import fr.jhelp.android.library.engine.scene.Material
import fr.jhelp.android.library.engine.scene.geometry.Box
import fr.jhelp.android.library.engine.scene.geometry.Sphere

// ...
        val material = Material()
        material.diffuse = GREEN
        
        val box = Box()
        val sphere = Sphere()
        // ...
        box.material = material
        sphere.material = material
// ...
```

For **DSL** have to create a reference, fill the material, then share the reference

```kotlin
import fr.jhelp.android.library.engine.dsl.material
import fr.jhelp.android.library.engine.dsl.materialReference
import fr.jhelp.android.library.engine.extensions.material
import fr.jhelp.android.library.engine.extensions.tree
import fr.jhelp.android.library.engine.scene.BLUE
import fr.jhelp.android.library.engine.view.View3D

// ...
        view3D.tree {
            // Create a material reference
            val materialReference = materialReference()
        
            // Fill the material
            material(materialReference) {
                this.diffuse = BLUE
            }
        
            this.root {
                this.box {
                    this.material(materialReference)
                }
            }
        }
// ...

```

It is possible to create several material references in one time :

```kotlin
import fr.jhelp.android.library.engine.dsl.materialReferences
import fr.jhelp.android.library.engine.extensions.material
import fr.jhelp.android.library.engine.scene.BLUE

// ...
        // Create some material references
        val (materialReference1, materialReference2, materialReference3) = materialReferences()
        
        // Fill the material
        material(materialReference1) {
            this.diffuse = BLUE
        }
// ...

```

the method `materialReferences` can create until 16 materials in one time. For more, have to separate the creation.

```kotlin
import fr.jhelp.android.library.engine.dsl.materialReferences

// ...
val (materialReference1, materialReference2, materialReference3, materialReference4,
    materialReference5, materialReference6, materialReference7, materialReference8,
    materialReference9, materialReference10, materialReference11, materialReference12,
    materialReference13, materialReference14, materialReference15, materialReference16) = materialReferences()
val (materialReference17, materialReference18, materialReference19, materialReference20) = materialReferences()
// ...

```

## Texture

Texture is an image applied to a 3D object, like :

![Cube with a texture on it](engine_material_texture.png)

The image source can be the drawable folder, the assets, a `android.graphics.Bitmap`, draw by the developer or get from any stream.

A texture can be sealed or not. 
If the texture is not seal it is possible to modify it, by drawing on it.
To save RAM memory, a texture can be seal, bu after that it can't be modified. 

To have a [fr.jhelp.android.library.engine.scene.Texture](../../src/main/java/fr/jhelp/android/library/engine/scene/Texture.kt) 
instance there different ways.

One is ti use functions store in [fr.jhelp.android.library.engine.resources.Textures.kt](../../src/main/java/fr/jhelp/android/library/engine/resources/Textures.kt)
file :

```kotlin
import fr.jhelp.android.library.engine.resources.defaultTexture
import fr.jhelp.android.library.engine.resources.texture

// ...
    // Initialized with default texture
    var texture = defaultTexture()
// ...
    // Get from drawable
    texture = texture(resources, R.drawable.image, sealed = true)
// ..
    // Empty texture to draw on it
    texture = texture(512, 512)
// ...
    // Texture from stream
    texture = texture({url.openStram()}, sealed = false)
// ...
    // Texture form bitmap 
    texture = texture(bitmap, sealec = false)
// ...
    // Texture from image source (See further in this documentation for what is an image source)
    texture = texture(imageSource, seale = true)
// ...
```

If texture is not sealed it is possible to draw on it :

```kotlin
import fr.jhelp.android.library.engine.scene.draw

// ...
    texture.draw { bitmap, canvas, paint -> 
        // ...
    }
// ...
```

Inside the lambda 
* `bitmap` is the bitmap that contains the texture image, 
* `canvas` is the canvas tool to draw/fill line, rectangle, oval, ... 
* `paint` is the paint tool for choose color, stroke width, ...

You can use their also our image manipulation tools see [Images documentation](../../../images/doc/ImagesDocumentation.md)

Another way tho create texture is to use [fr.jhelp.android.library.engine.resources.ResourcesAccess](../../src/main/java/fr/jhelp/android/library/engine/resources/ResourcesAccess.kt)

It is possible to use it if application context is provided or `View3D` (or its composable version) is used and was show at least one time

```kotlin
import fr.jhelp.android.library.engine.resources.ResourcesAccess


// ...
    val textureDrawable = ResourcesAccess.obtainTexture(R.drawable.image, sealed = true)
    val textureAsset = ResourcesAccess.obtainTexture("asset/path/image.png", sealed = false)
```

For **DSL** way for create texture first create reference, associate it a description and can be applied to a material.

Creates one reference :

```kotlin
import fr.jhelp.android.library.engine.dsl.textureReference
import fr.jhelp.android.library.engine.extensions.tree
import fr.jhelp.android.library.engine.view.View3D

    view3D.tree {
        val textureReference = textureReference()
    }
```

Can create several textures references in one time :

```kotlin
import fr.jhelp.android.library.engine.dsl.textureReferences
import fr.jhelp.android.library.engine.extensions.tree
import fr.jhelp.android.library.engine.view.View3D

    view3D.tree {
        val (textureReference1, textureReference2, textureReference3) = textureReferences()
    }
```

Like material, the number of references can be created in one time is limited to 16, for more cut the creation

```kotlin
import fr.jhelp.android.library.engine.dsl.textureReferences
import fr.jhelp.android.library.engine.extensions.tree
import fr.jhelp.android.library.engine.view.View3D

    view3D.tree {
        val (textureReference1, textureReference2, textureReference3, textureReference4,
            textureReference5, textureReference6, textureReference7, textureReference8,
            textureReference9, textureReference10, textureReference11, textureReference12,
            textureReference13, textureReference14, textureReference15, textureReference16) = textureReferences()
        val (textureReference17, textureReference18, textureReference19, textureReference20) = textureReferences()
    }
```

For associate reference to a texture, use one of `texture` methode version :

```kotlin
import fr.jhelp.android.library.engine.dsl.texture
import fr.jhelp.android.library.engine.dsl.textureReferences
import fr.jhelp.android.library.engine.extensions.tree
import fr.jhelp.android.library.engine.view.View3D

    view3D.tree {
        val (textureReferenceDrawable, textureReferenceAsset, textureReferenceDraw, textureReferenceImageSource) = textureReferences()

        // Associate to a drawable resource
        texture(textureReferenceDrawable, R.drawable.body_costume)
        // Associate to an asset
        texture(textureReferenceAsset, assetPath = "images/background.png")
        // Associate to image draw
        texture(textureReferenceDraw, 512, 512) { bitmap, canvas, paint ->
            // ...
        }
        // Associate to an image source
        texture(textureReferenceImageSource, imageSource)
    }
```

Once reference is associated, it can be used with a material

```kotlin
import fr.jhelp.android.library.engine.dsl.material
import fr.jhelp.android.library.engine.dsl.materialReference
import fr.jhelp.android.library.engine.dsl.texture
import fr.jhelp.android.library.engine.dsl.textureReference
import fr.jhelp.android.library.engine.extensions.tree
import fr.jhelp.android.library.engine.scene.WHITE
import fr.jhelp.android.library.engine.view.View3D

    view3D.tree {
        val textureReference = textureReference()
        texture(textureReference, R.drawable.body_costume)
        val materialReference = materialReference()
        material(materialReference) {
            this.diffuse = WHITE
            this.textureReference = textureReference
        }
    }
```

The texture reference can be used in several materials :

```kotlin
import fr.jhelp.android.library.engine.dsl.material
import fr.jhelp.android.library.engine.dsl.materialReferences
import fr.jhelp.android.library.engine.dsl.texture
import fr.jhelp.android.library.engine.dsl.textureReference
import fr.jhelp.android.library.engine.extensions.tree
import fr.jhelp.android.library.engine.scene.RED
import fr.jhelp.android.library.engine.scene.WHITE
import fr.jhelp.android.library.engine.view.View3D

    view3D.tree {
        val textureReference = textureReference()
        texture(textureReference, R.drawable.body_costume)
        val (materialReference, materialReferenceRedTint) = materialReferences()
        material(materialReference) {
            this.diffuse = WHITE
            this.textureReference = textureReference
        }
        material(materialReferenceRedTint) {
            this.diffuse = RED
            this.textureReference = textureReference
        }
    }
```

## Image source

Previously we used image source without explain what iit is. An image source is a source of image.
It can be simple source like drawable, asset or draw. But it can be an image operation on an other image source, 
or a combination of image sources.

Bellow classes for create an image source :

|                                                                                  Class                                                                                  | Description                                   |
|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------:|:----------------------------------------------|
|     [fr.jhelp.android.library.engine.resources.image.ImageSourceDefault](../../src/main/java/fr/jhelp/android/library/engine/resources/image/ImageSourceDefault.kt)     | Image source with default image               |
|    [fr.jhelp.android.library.engine.resources.image.ImageSourceDrawable](../../src/main/java/fr/jhelp/android/library/engine/resources/image/ImageSourceDrawable.kt)    | Imge source based on drawable resource        |
|       [fr.jhelp.android.library.engine.resources.image.ImageSourceAsset](../../src/main/java/fr/jhelp/android/library/engine/resources/image/ImageSourceAsset.kt)       | Image source based on image in assets         |
|        [fr.jhelp.android.library.engine.resources.image.ImageSourceDraw](../../src/main/java/fr/jhelp/android/library/engine/resources/image/ImageSourceDraw.kt)        | Image source based n a drawing                |
|        [fr.jhelp.android.library.engine.resources.image.ImageSourceGrey](../../src/main/java/fr/jhelp/android/library/engine/resources/image/ImageSourceGrey.kt)        | Gray version of an image source               |
|      [fr.jhelp.android.library.engine.resources.image.ImageSourceDarker](../../src/main/java/fr/jhelp/android/library/engine/resources/image/ImageSourceDarker.kt)      | Darker version of an image source             |
| [fr.jhelp.android.library.engine.resources.image.ImageSourceCutWithPath](../../src/main/java/fr/jhelp/android/library/engine/resources/image/ImageSourceCutWithPath.kt) | Result of cut an image source along a path    |
|       [fr.jhelp.android.library.engine.resources.image.ImageSourceStack](../../src/main/java/fr/jhelp/android/library/engine/resources/image/ImageSourceStack.kt)       | Cumulate several images sources one ver other |
