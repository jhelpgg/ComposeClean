package fr.jhelp.compose.engine.annotations

/**
 * Indicates that a function is called in OpenGL thread
 *
 * Never tries to call directly those methods, unexpected resultor crash  may happen
 */
@Target(AnnotationTarget.FUNCTION)
annotation class OpenGLThread
