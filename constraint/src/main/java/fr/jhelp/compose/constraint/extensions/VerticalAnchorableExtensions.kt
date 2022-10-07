package fr.jhelp.compose.constraint.extensions

import androidx.constraintlayout.compose.ConstraintLayoutBaseScope
import androidx.constraintlayout.compose.VerticalAnchorable
import fr.jhelp.compose.constraint.theme.FAR
import fr.jhelp.compose.constraint.theme.NEAR
import fr.jhelp.compose.constraint.theme.NEXT

infix fun VerticalAnchorable.next(VerticalAnchor: ConstraintLayoutBaseScope.VerticalAnchor) =
    this.linkTo(anchor = VerticalAnchor, margin = NEXT)

infix fun VerticalAnchorable.near(VerticalAnchor: ConstraintLayoutBaseScope.VerticalAnchor) =
    this.linkTo(anchor = VerticalAnchor, margin = NEAR)

infix fun VerticalAnchorable.far(VerticalAnchor: ConstraintLayoutBaseScope.VerticalAnchor) =
    this.linkTo(anchor = VerticalAnchor, margin = FAR)