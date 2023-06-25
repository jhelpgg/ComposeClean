package fr.jhelp.android.library.engine.annotations

/**
 * Indicates that a function is called in OpenGL thread
 *
 * Never tries to call directly those methods, unexpected result or crash  may happen
 */
@Target(AnnotationTarget.FUNCTION)
annotation class OpenGLThread
