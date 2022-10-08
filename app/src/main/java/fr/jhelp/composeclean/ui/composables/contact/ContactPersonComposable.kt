package fr.jhelp.composeclean.ui.composables.contact

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import fr.jhelp.compose.constraint.extensions.bottomParent
import fr.jhelp.compose.constraint.extensions.far
import fr.jhelp.compose.constraint.extensions.startParent
import fr.jhelp.compose.constraint.extensions.topParent
import fr.jhelp.composeclean.models.contact.ContactPerson
import fr.jhelp.composeclean.ui.theme.ComposeCleanTheme

@Composable
fun DrawContactPerson(contactPerson: ContactPerson)
{
    ConstraintLayout(Modifier
                         .fillMaxWidth()
                         .wrapContentHeight()) {
        val (firstName, lastName) = createRefs()

        Text(text = contactPerson.firstName,
             Modifier.constrainAs(firstName) {
                 width = Dimension.wrapContent
                 height = Dimension.wrapContent
                 topParent
                 bottomParent
                 startParent
             })

        Text(text = contactPerson.lastName,
             Modifier.constrainAs(lastName) {
                 width = Dimension.wrapContent
                 height = Dimension.wrapContent
                 topParent
                 bottomParent
                 start far firstName.end
             })
    }
}

@Preview(showBackground = true)
@Composable
fun DrawContactPersonPreview()
{
    ComposeCleanTheme {
        DrawContactPerson(ContactPerson("Jhon", "Doe"))
    }
}
