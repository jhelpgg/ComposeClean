package fr.jhelp.compose.constraint.extensions

import androidx.constraintlayout.compose.ConstrainScope
import fr.jhelp.compose.constraint.theme.START_END
import fr.jhelp.compose.constraint.theme.TOP_BOTTOM

val ConstrainScope.topParent get() = this.top.linkTo(this.parent.top, margin = TOP_BOTTOM)
val ConstrainScope.startParent get() = this.start.linkTo(this.parent.start, margin = START_END)
val ConstrainScope.bottomParent get() = this.bottom.linkTo(this.parent.bottom, margin = TOP_BOTTOM)
val ConstrainScope.endParent get() = this.end.linkTo(this.parent.end, margin = START_END)
