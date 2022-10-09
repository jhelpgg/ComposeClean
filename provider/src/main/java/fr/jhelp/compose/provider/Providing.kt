package fr.jhelp.compose.provider

/**
 * Provide/Change an instance producer for every code interested by an instance.
 *
 * The producer is called the first time the instance is called after this method.
 * Then the produced instance is used for all others until this method is called again (to modify the association)
 *
 * To provide instance for an interface five suggestions :
 *
 * ````kotlin
 * provideSingle<MyInterface>{ MyInstance() }
 * provideSingle { MyInstance() as MyInterface }
 * provideSingle { createMyInterface() }
 * provideSingle<MyInterface> { createMyInstance() }
 * provideSingle { createMyInstance() as MyInterface }
 * ````
 */
inline fun <reified T : Any> provideSingle(noinline producer: () -> T) =
    provideSingle("", producer)

/**
 * Provide/Change an instance producer for every code interested by an instance.
 * It give an additional qualifier to be able register several instance and choose the appropriate one later.
 *
 * The producer is called the first time the instance is called after this method.
 * Then the produced instance is used for all others until this method is called again with same qualifier (to modify the association)
 *
 * To provide instance for an interface five suggestions :
 *
 * ````kotlin
 * provideSingle<MyInterface>("Name") { MyInstance() }
 * provideSingle("Name") { MyInstance() as MyInterface }
 * provideSingle("Name") { createMyInterface() }
 * provideSingle<MyInterface>("Name") { createMyInstance() }
 * provideSingle("Name") { createMyInstance() as MyInterface }
 * ````
 */
inline fun <reified T : Any> provideSingle(qualifier: String, noinline producer: () -> T)
{
    ProviderManager.provide(producer, T::class, qualifier, true)
}

/**
 * Provide/Change an instance producer for every code interested by an instance.
 *
 * The producer is called each time an instance is required, so it may be an different instance each times,
 * depends on the producer itself
 *
 *
 * To provide instance for an interface five suggestions :
 *
 * ````kotlin
 * provideMultiple<MyInterface>{ MyInstance() }
 * provideMultiple { MyInstance() as MyInterface }
 * provideMultiple { createMyInterface() }
 * provideMultiple<MyInterface> { createMyInstance() }
 * provideMultiple { createMyInstance() as MyInterface }
 * ````
 */
inline fun <reified T : Any> provideMultiple(noinline producer: () -> T) =
    provideMultiple("", producer)


/**
 * Provide/Change an instance producer for every code interested by an instance.
 * It give an additional qualifier to be able register several instance and choose the appropriate one later.
 *
 * The producer is called each time an instance is required, so it may be an different instance each times,
 * depends on the producer itself
 *
 *
 * To provide instance for an interface five suggestions :
 *
 * ````kotlin
 * provideMultiple<MyInterface>("Name"){ MyInstance() }
 * provideMultiple("Name") { MyInstance() as MyInterface }
 * provideMultiple("Name") { createMyInterface() }
 * provideMultiple<MyInterface>("Name") { createMyInstance() }
 * provideMultiple("Name") { createMyInstance() as MyInterface }
 * ````
 *
 */
inline fun <reified T : Any> provideMultiple(qualifier: String, noinline producer: () -> T)
{
    ProviderManager.provide(producer, T::class, qualifier, false)
}

/**
 * Forget an association, any instance used after this call will failed, unless [provideSingle] or [provideMultiple]
 * is called again
 */
inline fun <reified T : Any> forget(qualifier: String = "")
{
    ProviderManager.forget(T::class, qualifier)
}

/**
 * Say that instance will be provided by external before the instance will be used (or already provided)
 *
 * ````kotlin
 * class MyClass
 * {
 *    val myInterface by provided<MyInterface>()
 *
 *    // ...
 *
 *    fun myFunction()
 *    {
 *        // ...
 *        this.myInterface.callMethod()
 *        // ...
 *    }
 * }
 * ````
 *
 * And somewhere (before `myFunction` is called) :
 *
 * ````kotlin
 * provideSingle<MyInterface>{ MyInstance() }
 * ````
 */
inline fun <reified T> provided(qualifier: String = "") =
    Provided<T>("${T::class.qualifiedName}:$qualifier")

/**
 * Indicate if something is provided for the class, and qualifier
 */
inline fun <reified T> isProvided(qualifier: String = "") =
    ProviderManager.isProvided("${T::class.qualifiedName}:$qualifier")