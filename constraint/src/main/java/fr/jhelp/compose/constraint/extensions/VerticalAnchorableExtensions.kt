package fr.jhelp.compose.constraint.extensions

import androidx.constraintlayout.compose.ConstraintLayoutBaseScope
import androidx.constraintlayout.compose.VerticalAnchorable
import fr.jhelp.compose.constraint.theme.FAR
import fr.jhelp.compose.constraint.theme.NEAR
import fr.jhelp.compose.constraint.theme.NEXT

/** Place component at default distance to other one in vertical */
infix fun VerticalAnchorable.next(VerticalAnchor: ConstraintLayoutBaseScope.VerticalAnchor) =
    this.linkTo(anchor = VerticalAnchor, margin = NEXT)

/** Place component near to other one in vertical */
infix fun VerticalAnchorable.near(VerticalAnchor: ConstraintLayoutBaseScope.VerticalAnchor) =
    this.linkTo(anchor = VerticalAnchor, margin = NEAR)

/** Place component far from other one in vertical */
infix fun VerticalAnchorable.far(VerticalAnchor: ConstraintLayoutBaseScope.VerticalAnchor) =
    this.linkTo(anchor = VerticalAnchor, margin = FAR)