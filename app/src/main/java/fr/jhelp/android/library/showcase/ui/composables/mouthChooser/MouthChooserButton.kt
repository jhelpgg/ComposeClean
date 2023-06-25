package fr.jhelp.android.library.showcase.ui.composables.mouthChooser

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import fr.jhelp.android.library.engine.resources.Mouths
import fr.jhelp.android.library.provider.provided
import fr.jhelp.android.library.models.shared.MouthChooserModel
import fr.jhelp.android.library.models.shared.NavigationModel

/**
 * Button for choose an mouth
 */
class MouthChooserButton
{
    private val navigationModel: NavigationModel by provided<NavigationModel>()
    private val mouthChooserModel: MouthChooserModel by provided<MouthChooserModel>()
    private val mouthMutable = mutableStateOf(Mouths.SMILE_BIG_2)

    /**
     * Current selected mouth
     */
    val mouth: State<Mouths> = this.mouthMutable

    /**
     * Show the mouth chooser button
     */
    @Composable
    fun Show(modifier: Modifier = Modifier)
    {
        Button(onClick = {
            val previousListener = this.mouthChooserModel.selectMouthListener
            this.mouthChooserModel.selectMouthListener = { mouth ->
                this.mouthMutable.value = mouth
                this.mouthChooserModel.selectMouthListener = previousListener
            }
            this.navigationModel.chooseMouth()
        },
               modifier = modifier
                   .wrapContentSize()) {
            Image(bitmap = this@MouthChooserButton.mouthMutable.value.bitmap().asImageBitmap(),
                  contentDescription = "Mouth",
                  modifier = Modifier
                      .size(64.dp)
                      .background(Color.LightGray))
        }
    }
}