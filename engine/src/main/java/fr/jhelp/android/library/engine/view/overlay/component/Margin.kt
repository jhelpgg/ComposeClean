package fr.jhelp.android.library.engine.view.overlay.component

/**
 * Margin to apply on components
 *
 * @property left Number pixel in left margin
 * @property top Number pixel in top margin
 * @property right Number pixel in right margin
 * @property bottom Number pixel in bottom margin
 */
data class Margin(val left: Int = 0, val top: Int = 0, val right: Int = 0, val bottom: Int = 0) {
    /** Total pixels take in width */
    val width = this.left + this.right
    /** Total pixels take in height */
    val height = this.top + this.bottom
}
