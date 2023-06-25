package fr.jhelp.android.library.tasks.future

/**
 * Future result possible status
 */
enum class FutureResultStatus
{
    /** Indicates that future still computing the result */
    COMPUTING,

    /** Indicates that future completed on success */
    SUCCEED,

    /** Indicates that future completed on failure */
    FAILED,

    /** Indicates that future completed by cancellation */
    CANCELED
}