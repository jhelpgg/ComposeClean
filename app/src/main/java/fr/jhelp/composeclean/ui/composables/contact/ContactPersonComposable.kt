package fr.jhelp.composeclean.ui.composables.contact

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import fr.jhelp.android.library.constraint.extensions.far
import fr.jhelp.android.library.constraint.extensions.startParent
import fr.jhelp.composeclean.models.contact.ContactPerson

/**
 * Draw a contact that represents a person
 */
@Composable
fun DrawContactPerson(contactPerson: ContactPerson)
{
    ConstraintLayout(Modifier
                         .fillMaxWidth()
                         .wrapContentHeight()) {
        val (firstName, lastName) = this.createRefs()

        Text(text = contactPerson.firstName,
             Modifier.constrainAs(firstName) {
                 this.width = Dimension.wrapContent
                 this.height = Dimension.wrapContent
                 this.top.linkTo(this.parent.top)
                 this.bottom.linkTo(this.parent.bottom)
                 this.startParent
             })

        Text(text = contactPerson.lastName,
             Modifier.constrainAs(lastName) {
                 this.width = Dimension.wrapContent
                 this.height = Dimension.wrapContent
                 this.top.linkTo(this.parent.top)
                 this.bottom.linkTo(this.parent.bottom)
                 this.start far firstName.end
             })
    }
}
