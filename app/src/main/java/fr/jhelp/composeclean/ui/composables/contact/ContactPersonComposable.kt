package fr.jhelp.composeclean.ui.composables.contact

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import fr.jhelp.compose.constraint.extensions.far
import fr.jhelp.compose.constraint.extensions.startParent
import fr.jhelp.composeclean.models.contact.ContactPerson
import fr.jhelp.composeclean.ui.theme.ComposeCleanTheme

/**
 * Draw a contact
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

/**
 * Composable preview of [DrawContactPerson]
 */
@Preview(showBackground = true)
@Composable
fun DrawContactPersonPreview()
{
    ComposeCleanTheme {
        DrawContactPerson(ContactPerson("John", "Doe"))
    }
}
