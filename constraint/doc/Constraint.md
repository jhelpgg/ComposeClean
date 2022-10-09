# Constraint

Set of tools or short cut for place component in compose constraint layout

[Margin](../src/main/java/fr/jhelp/compose/constraint/theme/Margin.kt)
contains distances used in other tools. It defines the meaning of near, next and far. It defines
standard distances from parent

[ConstrainScopeExtensions](../src/main/java/fr/jhelp/compose/constraint/extensions/ConstrainScopeExtensions.kt)
defines short cut to link a component to parent with standard distance

```kotlin
import  fr.jhelp.compose.constraint.extensions.topParent
import  fr.jhelp.compose.constraint.extensions.startParent
import  fr.jhelp.compose.constraint.extensions.bottomParent
import  fr.jhelp.compose.constraint.extensions.endParent
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.Dimension

// ...

ConstraintLayout(modifier = Modifier.fillMaxSize()) {
    val text = createRef()

    Text(text = "Text",
         modifier = Modifier.constrainAs(text) {
             width = Dimension.wrapContent
             height = Dimension.wrapContent
             topParent
             startParent
             endParent
             bottomParent
         })
}
// ...
```

[HorizontalAnchorableExtensions](../src/main/java/fr/jhelp/compose/constraint/extensions/HorizontalAnchorableExtensions.kt)
defines short cut to link a component with an component in horizontal way

```kotlin
import  fr.jhelp.compose.constraint.extensions.topParent
import  fr.jhelp.compose.constraint.extensions.startParent
import  fr.jhelp.compose.constraint.extensions.far
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.Dimension

// ...

ConstraintLayout(modifier = Modifier.fillMaxSize()) {
    val (text1, text2) = createRefs()

    Text(text = "Text",
         modifier = Modifier.constrainAs(text1) {
             width = Dimension.wrapContent
             height = Dimension.wrapContent
             topParent
             startParent
         })

    Text(text = "Text2",
         modifier = Modifier.constrainAs(text2) {
             width = Dimension.wrapContent
             height = Dimension.wrapContent
             topParent
             start far text1.end
         })
}
// ...
```

[VerticalAnchorableExtensions](../src/main/java/fr/jhelp/compose/constraint/extensions/VerticalAnchorableExtensions.kt)
defines short cut to link a component with an component in vertical way

```kotlin
import  fr.jhelp.compose.constraint.extensions.topParent
import  fr.jhelp.compose.constraint.extensions.startParent
import  fr.jhelp.compose.constraint.extensions.near
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.Dimension

// ...

ConstraintLayout(modifier = Modifier.fillMaxSize()) {
    val (text1, text2) = createRefs()

    Text(text = "Text",
         modifier = Modifier.constrainAs(text1) {
             width = Dimension.wrapContent
             height = Dimension.wrapContent
             topParent
             startParent
         })

    Text(text = "Text2",
         modifier = Modifier.constrainAs(text2) {
             width = Dimension.wrapContent
             height = Dimension.wrapContent
             top near text1.bottom
             startParent
         })
}
// ...
```


