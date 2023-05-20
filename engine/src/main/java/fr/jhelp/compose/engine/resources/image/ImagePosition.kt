package fr.jhelp.compose.engine.resources.image

/**
 *  Image with a draw position
 *  @property image Image to draw
 *  @property x Position abscissa
 *  @property y Position ordinate
 */
data class ImagePosition(val image: ImageSource<*>, val x: Float = 0f, val y: Float = 0f)
