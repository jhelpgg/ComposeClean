# Nodes and objects

The engine have nodes, that have no drawing, but can be place in the space and use has group parent.

Other 3D objects are node with mesh and material to be draw in space.

In addition of generic object and clone notion, the engine provides some pre-build objects for "simple"
geometry and two "complex" object.

* [Node 3D](geometry/Node3D.md) : More about 3D node
* [Object 3D](geometry/Object3D.md) : Generic object 3D
* [Clone 3D](geometry/Clone3D.md) : Copy any object 3D to save memory
* [Plane](geometry/Plane.md) : Pre-build object : A simple plane
* [Box](geometry/Box.md) : Pre-build object : A box wih capacity to define how texture is applies on each face
* [Sphere](geometry/Sphere.md) : Pre-build object : A sphere with can choose the precision
* [Revolution](geometry/Revolution.md) : Pre-build object : A revolution, that is to say a path rotated around Y axis.
* [Field 3D](geometry/Field3D.md) : Pre-build object : Simple filed defines by an equation
* [Object based on paths](geometry/ObjectPath.md) : Pre-build object : A path repeat along another path.
* [Dice](geometry/Dice.md) : Complex object that represents a dice
* [Robot](geometry/Robot.md) : Complex set of objects attached in a node, it represents an humanoid robot here can move each body part.

