package fr.jhelp.android.library.constraint.extensions

import androidx.constraintlayout.compose.ConstraintLayoutBaseScope
import androidx.constraintlayout.compose.HorizontalAnchorable
import fr.jhelp.android.library.constraint.theme.FAR
import fr.jhelp.android.library.constraint.theme.NEAR
import fr.jhelp.android.library.constraint.theme.NEXT

/** Place component at default distance to other one in horizontal */
infix fun HorizontalAnchorable.next(horizontalAnchor: ConstraintLayoutBaseScope.HorizontalAnchor): Unit =
    this.linkTo(anchor = horizontalAnchor, margin = NEXT)

/** Place component near to other one in horizontal */
infix fun HorizontalAnchorable.near(horizontalAnchor: ConstraintLayoutBaseScope.HorizontalAnchor): Unit =
    this.linkTo(anchor = horizontalAnchor, margin = NEAR)

/** Place component far from other one in horizontal */
infix fun HorizontalAnchorable.far(horizontalAnchor: ConstraintLayoutBaseScope.HorizontalAnchor): Unit =
    this.linkTo(anchor = horizontalAnchor, margin = FAR)