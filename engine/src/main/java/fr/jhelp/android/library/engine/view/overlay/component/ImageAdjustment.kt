package fr.jhelp.android.library.engine.view.overlay.component

/**
 * Adjust image in [OverlayImage]
 */
enum class ImageAdjustment
{
    /**
     * Cut the image if need.
     *
     * If the image is to big, a part is cut from visibility
     */
    CUT,

    /**
     * Fit the image to the component
     */
    FIT,

    /**
     * Fit on respects image proportions
     */
    FIT_PROPORTION
}