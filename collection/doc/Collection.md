# Collection

Set of additional collections and tools to manipulate them.

[fr.jhelp.android.library.collection.IntList](../src/main/java/fr/jhelp/android/library/collection/IntList.kt)
list of `Int`, more efficient in memory and rapidity than `ArrayList<Int>`

[IterableExtensions](../src/main/java/fr/jhelp/android/library/collection/extensions/IterableExtensions.kt)
extensions for `Iterable`

`select` : Creates iterable with elements filtered by a given criteria

```kotlin
import jhelp.compose.collection.extensions.select

val list: List<String> = createList()

for (element in list.select { element -> element.startWith("A", true) })
{
    // ....
}
```

`transform` :  Creates iterable that each elements are transformed by given transformation

```kotlin
import jhelp.compose.collection.extensions.transform

val list: List<Contact> = obtainContacs()


for (firstName in list.transfrom { contact -> contact.firstName })
{
    // ....    
}
```

[IteratorExtensions](../src/main/java/fr/jhelp/android/library/collection/extensions/IteratorExtensions.kt)
extensions for `Iterable`

`select` : Creates iterator with elements filtered by a given criteria

`transform` :  Creates iterator that each elements are transformed by given transformation

[fr.jhelp.android.library.collection.SortedArray](../src/main/java/fr/jhelp/android/library/collection/SortedArray.kt)
is a list of elements sorted by a given comparator or in "natural" order for `Comparable` classes.

[fr.jhelp.android.library.collection.Queue](../src/main/java/fr/jhelp/android/library/collection/Queue.kt)
is a queue of elements
