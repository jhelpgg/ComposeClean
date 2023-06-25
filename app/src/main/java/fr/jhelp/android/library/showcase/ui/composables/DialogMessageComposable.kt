package fr.jhelp.android.library.showcase.ui.composables

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import fr.jhelp.android.library.constraint.extensions.bottomParent
import fr.jhelp.android.library.constraint.extensions.endParent
import fr.jhelp.android.library.constraint.extensions.near
import fr.jhelp.android.library.constraint.extensions.next
import fr.jhelp.android.library.constraint.extensions.startParent
import fr.jhelp.android.library.constraint.extensions.topParent
import fr.jhelp.android.library.models.shared.dialogs.DialogMessage
import fr.jhelp.android.library.showcase.ui.theme.DIALOG_TEXT_COLOR
import fr.jhelp.android.library.showcase.ui.theme.MESSAGE_SIZE
import fr.jhelp.android.library.showcase.ui.theme.TITLE_SIZE

/**
 * Dialog message UI
 */
class DialogMessageComposable
{
    /**
     * Show the dialog message
     */
    @Composable
    fun Show(dialogMessage: DialogMessage, modifier: Modifier)
    {
        ConstraintLayout(modifier = modifier) {
            val (title, message) = this.createRefs()
            val buttons =
                Array<ConstrainedLayoutReference>(dialogMessage.messageButtons.size) { this.createRef() }

            Text(text = dialogMessage.title.text,
                 color = DIALOG_TEXT_COLOR,
                 fontSize = TITLE_SIZE,
                 textAlign = TextAlign.Center,
                 modifier = Modifier.constrainAs(title) {
                     this.width = Dimension.wrapContent
                     this.height = Dimension.wrapContent

                     this.topParent
                     this.startParent
                     this.endParent
                 })

            Text(text = dialogMessage.message.text,
                 color = DIALOG_TEXT_COLOR,
                 fontSize = MESSAGE_SIZE,
                 textAlign = TextAlign.Start,
                 modifier = Modifier.constrainAs(message) {
                     this.width = Dimension.fillToConstraints
                     this.height = Dimension.wrapContent

                     this.top near title.bottom
                     this.bottom next buttons[0].top
                     this.startParent
                     this.endParent
                 })


            if (dialogMessage.horizontalButtons)
            {
                for ((index, button) in dialogMessage.messageButtons.withIndex())
                {
                    Button(onClick = {
                        dialogMessage.taskType.launch(index,
                                                      dialogMessage.buttonClicked)
                    },
                           modifier = Modifier.constrainAs(buttons[index]) {
                               this.width = Dimension.wrapContent
                               this.height = Dimension.wrapContent

                               if (index == 0)
                               {
                                   this.startParent
                               }
                               else
                               {
                                   this.start near buttons[index - 1].end
                               }

                               if (index == dialogMessage.messageButtons.size - 1)
                               {
                                   this.endParent
                               }

                               this.bottomParent
                           }) { Text(text = button.text) }

                }
            }
            else
            {
                for ((index, button) in dialogMessage.messageButtons.withIndex())
                {
                    Button(onClick = {
                        dialogMessage.taskType.launch(index,
                                                      dialogMessage.buttonClicked)
                    },
                           modifier = Modifier.constrainAs(buttons[index]) {
                               this.width = Dimension.fillToConstraints
                               this.height = Dimension.wrapContent

                               this.startParent
                               this.endParent

                               if (index < buttons.size - 1)
                               {
                                   this.bottom near buttons[index + 1].top
                               }
                               else
                               {
                                   this.bottomParent
                               }
                           }) { Text(text = button.text) }

                }
            }
        }
    }
}
