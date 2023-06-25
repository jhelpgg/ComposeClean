package fr.jhelp.composeclean.models.shared.dialogs

import fr.jhelp.composeclean.models.source.text.TextSource
import fr.jhelp.android.library.tasks.TaskType

/**
 * Message dialog
 *
 * @property title Message title
 * @property message Message text
 * @property messageButtons Message buttons text
 * @property horizontalButtons Indicates if align buttons horizontally (`true`) or vertically (`false`)
 * @property taskType Task type to use for call back the button click lister
 * @property buttonClicked Callback called when a button is clicked. The parameter is the button index in the given array buttons in `messageButtons`
 */
class DialogMessage(val title: TextSource,
                    val message: TextSource,
                    val messageButtons: Array<TextSource>,
                    val horizontalButtons: Boolean,
                    val taskType: TaskType = TaskType.SHORT_TASK,
                    val buttonClicked: (buttonIndex: Int) -> Unit) : DialogModel()
{
    init
    {
        if (this.messageButtons.isEmpty())
        {
            throw IllegalArgumentException("Message buttons list must not be empty")
        }
    }
}
