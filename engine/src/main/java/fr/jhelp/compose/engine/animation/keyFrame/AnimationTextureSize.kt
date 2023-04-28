package fr.jhelp.compose.engine.animation.keyFrame

/**
 * Animation texture size
 * @property size Size in pixels
 */
enum class AnimationTextureSize(val size: Int)
{
    /** Large/big texture */
    LARGE(512),

    /** Medium/normal texture */
    MEDIUM(256),

    /** Small texture */
    SMALL(128)
}