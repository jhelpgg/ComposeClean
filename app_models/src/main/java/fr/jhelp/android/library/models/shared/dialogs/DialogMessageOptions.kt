package fr.jhelp.android.library.models.shared.dialogs

/**
 * Possible combination of buttons for message dialog option
 * @property options List of button for the combination
 */
enum class DialogMessageOptions(val options: List<DialogMessageOptionButton>)
{
    /** Ok only */
    OK(listOf(DialogMessageOptionButton.OK)),

    /** Ok or cancel */
    OK_CANCEL(listOf(DialogMessageOptionButton.OK, DialogMessageOptionButton.CANCEL)),

    /** Yes or no */
    YES_NO(listOf(DialogMessageOptionButton.YES, DialogMessageOptionButton.NO)),

    /** Tex, no or cancel */
    YES_NO_CANCEL(listOf(DialogMessageOptionButton.YES,
                         DialogMessageOptionButton.NO,
                         DialogMessageOptionButton.CANCEL))
}
