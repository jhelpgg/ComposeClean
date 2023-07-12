# Node 3D

A node 3D
is [fr.jhelp.android.library.engine.scene.Node3D](../../../src/main/java/fr/jhelp/android/library/engine/scene/Node3D.kt)

Node is a generic way to group nodes, all objects extends its so they have same property.

Node fields :

| Field name | Description                                                                                                 |
|:----------:|:------------------------------------------------------------------------------------------------------------|
|    `id`    | The node unique id                                                                                          |
|   `name`   | An optional name (useful to retrieve it)                                                                    |
|  `parent`  | An optional parent. When node draw in scene only the root scene don't have parent.                          |
| `position` | Node position (See [Position](../../position/PositionIn3D.md) to locate it in space relative to its parent. |
| `visible`  | Indicates if node is visible. Invisible node will hide all it's children, grand children ...                |
| `sound3D`  | An optional attached 3D sound that follow its movement (See [Sound 3D](../../sound/Sound3D.md))             |

Node methods :

|            Method signature             | Description                                                                                                                                   |
|:---------------------------------------:|:----------------------------------------------------------------------------------------------------------------------------------------------|
|               `center()`                | Compute node's center.</br>For a node it is his location.</br>For an object it is the center node of it's mesh (See [Object 3D](Object3D.md)) |
|        `relativeRoot(): Node3D`         | Node's root parent. If node is in the scen, it will be the scene root.                                                                        |
|   `firstChildByName(String): Node3D?`   | Search node by name inside node decedent hierarchy. If two or more nodes have the requested name, it gives the first found                    |
|            `copy(): Node3D`             | Copy the node                                                                                                                                 |
|              `add(Node3D)`              | Add node as children. The added node must not be the child of another node.                                                                   |
|            `remove(Node3D)`             | Remove a node child                                                                                                                           |
|          `removeAllChildren()`          | Remove all children                                                                                                                           |
|         `childrenCount(): Int`          | Number of children                                                                                                                            |
|         `children(Int): Node3D`         | Get a children at indicated index                                                                                                             |
| `applyMaterialHierarchically(Material)` | Apply given material to all it's decedant hierarchy                                                                                           |

Node 3D is `Iterable` on its children, so can be used in `for` the iterate all children :

```kotlin
for (child in node3D)
{
    // ...
}
```
