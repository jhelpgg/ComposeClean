package fr.jhelp.compose.constraint.extensions

import androidx.constraintlayout.compose.ConstrainScope
import fr.jhelp.compose.constraint.theme.START_END
import fr.jhelp.compose.constraint.theme.TOP_BOTTOM

/** Attach component top to it's parent top with standard distance */
val ConstrainScope.topParent get() = this.top.linkTo(this.parent.top, margin = TOP_BOTTOM)

/** Attach component start to it's parent start with standard distance */
val ConstrainScope.startParent get() = this.start.linkTo(this.parent.start, margin = START_END)

/** Attach component bottom to it's parent bottom with standard distance */
val ConstrainScope.bottomParent get() = this.bottom.linkTo(this.parent.bottom, margin = TOP_BOTTOM)

/** Attach component end to it's parent end with standard distance */
val ConstrainScope.endParent get() = this.end.linkTo(this.parent.end, margin = START_END)
