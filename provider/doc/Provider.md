# Provider

Provider is a light tool for provide/inject instances.

The principe is you provide an instance somewhere in the code and any where it can be used just by
asking it.

Thr providing instance can be changed, it is not necessary unique. We have a mechanism for able to
give a different instance for each. It can be also labeled, so give an instance for some, an other
for others.

A provide instance can be forget, useful in situation where instance need to be released and no more
necessary.

It is also possible to know if an instance is provided or not.

For explanation bellow we will consider to have the following classes and interface :

```kotlin
package sample

interface ObjectInterface
{
    fun method()
}
```

```kotlin
package sample

import java.util.concurrent.atomic.AtomicInteger

class ObjectInstance : ObjectInterface
{
    companion object
    {
        private val nextId = AtomicInteger(0)
    }

    val id : Int = ObjectInstance.nextId.getAndIncrement()

    override fun method()
    {
        println("ObjectInstance # ${this.id}")
    }
}
```

```kotlin
package sample

import java.util.concurrent.atomic.AtomicInteger

class ObjectInstance2 : ObjectInterface
{
    companion object
    {
        private val nextId = AtomicInteger(0)
    }

    val id : Int = ObjectInstance2.nextId.getAndIncrement()

    override fun method()
    {
        println("ObjectInstance2 # ${this.id}")
    }
}
```

## Provide an object instance

To provide an object instance, just call `fr.jhelp.android.library.provider.provideSingle` and when to use
it `fr.jhelp.android.library.provider.provided`

By example :

```kotlin
package sample

import fr.jhelp.android.library.provider.provideSingle

class Provider
{
    fun provideInstance()
    {
        provideSingle { ObjectInstance() }
    }
}
```

```kotlin
package sample

import fr.jhelp.android.library.provider.provided

class UsageObject
{
    private val objectInstance by provided<ObjectInstance>()

    fun methodThatNotUseObjectProvided()
    {
        // ...    
    }

    fun methodThatUseObjectProvided()
    {
        this.objectInstance.method()
    }
}
```

Methods that not use the provided instance can be use at anytime like before the instance is
provided. The provided instance is trying to resolved the first usage time. So in our example,
before call `methodThatUseObjectProvided`
of `UsageObject` the method `provideInstance` of `Provider` should be called before .

Since we provide a single instance the message print by `methodThatUseObjectProvided` will be always
the same and if an other class get the provided instance, the message will still the same, since the
instance is the same.

## Provide an interface implementation

The most common usage is to provide an interface instance, the object that use it manipulates the
real object through an interface. For this just do some little change :

```kotlin
package sample

import fr.jhelp.android.library.provider.provideSingle

class Provider
{
    fun provideInstance()
    {
        provideSingle<ObjectInterface> { ObjectInstance() }
    }
}
```

```kotlin
package sample

import fr.jhelp.android.library.provider.provided

class UsageObject
{
    private val objectInterface by provided<ObjectInterface>()

    fun methodThatNotUseObjectProvided()
    {
        // ...    
    }

    fun methodThatUseObjectProvided()
    {
        this.objectInterface.method()
    }
}
```

You notice that in providing we specify the interface we want to share and the implementation. It
must be explicit, it can guess that you want expose it as an interface by itself

In usage, we just the type we request by the interface since now it is an interface implementation it
is shared.

## Provide with a label

In some situation, we need have an instance for one usage and an other for an other usage. For this
case can use the labelling

```kotlin
package sample

import fr.jhelp.android.library.provider.provideSingle

class Provider
{
    fun provideInstance()
    {
        provideSingle<ObjectInterface>("case1") { ObjectInstance() }
        provideSingle<ObjectInterface>("case2") { ObjectInstance2() }
    }
}
```

```kotlin
package sample

import fr.jhelp.android.library.provider.provided

class UsageObject
{
    private val objectInterface by provided<ObjectInterface>("case1")

    fun methodThatNotUseObjectProvided()
    {
        // ...    
    }

    fun methodThatUseObjectProvided()
    {
        this.objectInterface.method()
    }
}
```

```kotlin
package sample

import fr.jhelp.android.library.provider.provided

class UsageObject2
{
    private val objectInterface by provided<ObjectInterface>("case2")

    fun methodThatNotUseObjectProvided()
    {
        // ...    
    }

    fun methodThatUseObjectProvided()
    {
        this.objectInterface.method()
    }
}
```

Same labeling will provide same instance. If an other class use `"case1"` labeling, it will have
same instance that received by `UsageObject`

## Provide different instance

It is also possible to provide a different instance each time the provided value is used.

```kotlin
package sample

import fr.jhelp.android.library.provider.provideMultiple

class Provider
{
    fun provideInstance()
    {
        provideMultiple<ObjectInterface> { ObjectInstance() }
    }
}
```

```kotlin
package sample

import fr.jhelp.android.library.provider.provided

class UsageObject
{
    private val objectInterface by provided<ObjectInterface>()

    fun methodThatNotUseObjectProvided()
    {
        // ...
    }

    fun methodThatUseObjectProvided()
    {
        this.objectInterface.method()
    }
}
```

```kotlin
fun main()
{
    val provider = Provider()
    provider.provideInstance()

    val usageObject = UsageObject()

    for (i in 0 until 5)
    {
        usageObject.methodThatUseObjectProvided()
    }
}
```

Will print :

```text
ObjectInstance # 0
ObjectInstance # 1
ObjectInstance # 2
ObjectInstance # 3
ObjectInstance # 4
```

## Provide different instance with labeling

Like before its possible to provide a label, here we will provide two different ways to create an
instance

```kotlin
package sample

import fr.jhelp.android.library.provider.provideMultiple

class Provider
{
    fun provideInstance()
    {
        provideMultiple<ObjectInterface>("case1") { ObjectInstance() }
        provideMultiple<ObjectInterface>("case2") { ObjectInstance2() }
    }
}
```

## Determine if a class/interface is provided

Just use `isProvided` method

```kotlin
import fr.jhelp.android.library.provider.isProvided

// ...

if (!isProvided<ObjectInterface>())
{
    // Do special treatment if instance not provided
}

if (!isProvided<ObjectInterface>("case1"))
{
    // Do special treatment if instance with specific label not provided
}
```

The first if check if instance without label is provided, the second check if instance with label
is provided

## Forget association

In some case it good for memory to forget instances if we will no more need of them, to do it,
use `forget method`

```kotlin
import fr.jhelp.android.library.provider.forget

//...

forget<ObjectInterface>()

//...

forget<ObjectInterface>("case2")
```

First forget without label, second with label
