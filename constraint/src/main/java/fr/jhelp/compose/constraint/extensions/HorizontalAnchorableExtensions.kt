package fr.jhelp.compose.constraint.extensions

import androidx.constraintlayout.compose.ConstraintLayoutBaseScope
import androidx.constraintlayout.compose.HorizontalAnchorable
import fr.jhelp.compose.constraint.theme.FAR
import fr.jhelp.compose.constraint.theme.NEAR
import fr.jhelp.compose.constraint.theme.NEXT

infix fun HorizontalAnchorable.next(horizontalAnchor: ConstraintLayoutBaseScope.HorizontalAnchor) =
    this.linkTo(anchor = horizontalAnchor, margin = NEXT)

infix fun HorizontalAnchorable.near(horizontalAnchor: ConstraintLayoutBaseScope.HorizontalAnchor) =
    this.linkTo(anchor = horizontalAnchor, margin = NEAR)

infix fun HorizontalAnchorable.far(horizontalAnchor: ConstraintLayoutBaseScope.HorizontalAnchor) =
    this.linkTo(anchor = horizontalAnchor, margin = FAR)