# Common

Common generic objects shared between application modules.

[Mutable](../src/main/java/fr/jhelp/compose/mutable/Mutable.kt)
Represents a modifiable value.

In compose it will be used to crete a handler in composable view but need to be manipulate by the
model. Those values must be create in composable method from same module, that's why we use this to
create them and give the hand to the model.

[RecyclerModel](../src/main/java/fr/jhelp/compose/ui/recycler/RecyclerModel.kt)
Interface to interact with recycler composable,
see [RecyclerComposable](../../app/doc/RecyclerComposable.md)

The recycler composable is created by th UI, tht is shared this recycler model to activity model. So
that the activity model can manipulate it and drive the activity embed list.
