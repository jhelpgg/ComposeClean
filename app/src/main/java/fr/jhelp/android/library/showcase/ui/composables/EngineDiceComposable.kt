package fr.jhelp.android.library.showcase.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import fr.jhelp.android.library.constraint.extensions.bottomParent
import fr.jhelp.android.library.constraint.extensions.endParent
import fr.jhelp.android.library.constraint.extensions.near
import fr.jhelp.android.library.constraint.extensions.startParent
import fr.jhelp.android.library.constraint.extensions.topParent
import fr.jhelp.android.library.engine.dsl.DiceReference
import fr.jhelp.android.library.engine.dsl.roll
import fr.jhelp.android.library.engine.extensions.position
import fr.jhelp.android.library.engine.scene.LIGHT_GREEN
import fr.jhelp.android.library.engine.scene.LIGHT_RED
import fr.jhelp.android.library.engine.view.touch.View3DTouchNothing
import fr.jhelp.android.library.math.COLOR_GREEN_0300
import fr.jhelp.android.library.math.COLOR_RED_0300
import fr.jhelp.android.library.showcase.ui.engine.View3DComposable
import fr.jhelp.android.library.showcase.R
import fr.jhelp.android.library.tasks.extensions.observedBy

/**
 * Show dice animation
 */
class EngineDiceComposable
{
    private lateinit var diceRed: DiceReference
    private lateinit var diceGreen: DiceReference

    /**
     * Show dice animation
     */
    @Composable
    fun Show()
    {
        val self = this
        val view3DComposable = View3DComposable()
        var redValue: Int by remember { mutableStateOf(3) }
        var greenValue: Int by remember { mutableStateOf(3) }

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (view3D, rollButton, redValueText, greenValueText) = this.createRefs()

            view3DComposable.view3DTouchAction = View3DTouchNothing

            view3DComposable.Draw(modifier = Modifier.constrainAs(view3D) {
                this.width = Dimension.fillToConstraints
                this.height = Dimension.fillToConstraints
                this.topParent
                this.bottom near rollButton.top
                this.startParent
                this.endParent
            }) {
                self.diceRed = this.dice()
                self.diceGreen = this.dice()

                this.scenePosition { this.z = -4f }

                this.root {
                    this.dice(self.diceRed) {
                        redValue = this.value
                        this.position { this.y = 1f }
                        this.color(LIGHT_RED)
                        this.diceInfoState.observedBy { value -> redValue = value.diceValue }
                    }

                    this.dice(self.diceGreen) {
                        greenValue = this.value
                        this.position { this.y = -1f }
                        this.color(LIGHT_GREEN)
                        this.diceInfoState.observedBy { value -> greenValue = value.diceValue }
                    }
                }
            }

            Button(onClick = {
                roll(self.diceRed)
                roll(self.diceGreen)
            },
                   modifier = Modifier.constrainAs(rollButton) {
                       this.width = Dimension.wrapContent
                       this.height = Dimension.wrapContent
                       this.bottom near redValueText.top
                       this.startParent
                       this.endParent
                   }) {
                Text(text = stringResource(id = R.string.rollDice))
            }

            val textSeparator = this.createGuidelineFromStart(0.5f)

            Text(text = redValue.toString(),
                 textAlign = TextAlign.Center,
                 color = Color.Black,
                 modifier = Modifier
                     .constrainAs(redValueText) {
                         this.width = Dimension.fillToConstraints
                         this.height = Dimension.wrapContent
                         this.bottomParent
                         this.startParent
                         this.end near textSeparator
                     }
                     .background(Color(COLOR_RED_0300)))

            Text(text = greenValue.toString(),
                 textAlign = TextAlign.Center,
                 color = Color.Black,
                 modifier = Modifier
                     .constrainAs(greenValueText) {
                         this.width = Dimension.fillToConstraints
                         this.height = Dimension.wrapContent
                         this.bottomParent
                         this.start near textSeparator
                         this.endParent
                     }
                     .background(Color(COLOR_GREEN_0300)))
        }
    }
}