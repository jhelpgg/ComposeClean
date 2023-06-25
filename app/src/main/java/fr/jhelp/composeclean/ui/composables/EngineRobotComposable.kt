package fr.jhelp.composeclean.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import fr.jhelp.android.library.constraint.extensions.bottomParent
import fr.jhelp.android.library.constraint.extensions.endParent
import fr.jhelp.android.library.constraint.extensions.near
import fr.jhelp.android.library.constraint.extensions.startParent
import fr.jhelp.android.library.constraint.extensions.topParent
import fr.jhelp.android.library.engine.scene.Scene3D
import fr.jhelp.android.library.engine.scene.geometry.robot.Robot
import fr.jhelp.android.library.engine.scene.geometry.robot.animation.headNoAnimation
import fr.jhelp.android.library.engine.scene.geometry.robot.animation.headYesAnimation
import fr.jhelp.android.library.engine.scene.geometry.robot.animation.run
import fr.jhelp.android.library.engine.scene.geometry.robot.animation.walk
import fr.jhelp.compose.ui.engine.View3DComposable
import fr.jhelp.composeclean.R
import fr.jhelp.composeclean.ui.composables.colorChooser.ColorChooserButton
import fr.jhelp.composeclean.ui.composables.eyeChooser.EyeChooserButton
import fr.jhelp.composeclean.ui.composables.mouthChooser.MouthChooserButton

/**
 * Show the robot sample
 */
class EngineRobotComposable
{
    private val leftEyeChooser = EyeChooserButton()
    private val rightEyeChooser = EyeChooserButton()
    private val mouthChooser = MouthChooserButton()
    private val hairColorChooser = ColorChooserButton()
    private lateinit var scene: Scene3D

    /**
     * Show the robot sample
     */
    @Composable
    fun Show()
    {
        val self = this
        val view3DComposable = View3DComposable()
        val robot = Robot()
        val head = robot.headTexture
        head.hair = this.hairColorChooser.currentColor.value
        head.leftEye = this.leftEyeChooser.eye.value
        head.rightEye = this.rightEyeChooser.eye.value
        head.mouth = this.mouthChooser.mouth.value
        head.refresh()

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val firstSeparator = this.createGuidelineFromStart(0.25f)
            val secondSeparator = this.createGuidelineFromStart(0.5f)
            val thirdSeparator = this.createGuidelineFromStart(0.75f)
            val (view3D, leftEye, rightEye, mouth, hairColor,
                yesAnimation, noAnimation, walkAnimation, runAnimation) = this.createRefs()

            view3DComposable.Draw(modifier = Modifier.constrainAs(view3D) {
                this.width = Dimension.fillToConstraints
                this.height = Dimension.fillToConstraints
                this.topParent
                this.bottom near leftEye.top
                this.startParent
                this.endParent
            }) {
                self.scene = this.scene3D
                this.scenePosition { this.z = -5.5f }
                this.root {
                    this.node { this.add(robot.mainNode) }
                }
            }

            self.leftEyeChooser.Show(modifier = Modifier.constrainAs(leftEye) {
                this.width = Dimension.wrapContent
                this.height = Dimension.wrapContent
                this.bottom near yesAnimation.top
                this.startParent
                this.end near firstSeparator
            })

            self.rightEyeChooser.Show(modifier = Modifier.constrainAs(rightEye) {
                this.width = Dimension.wrapContent
                this.height = Dimension.wrapContent
                this.bottom near yesAnimation.top
                this.start near firstSeparator
                this.end near secondSeparator
            })

            self.mouthChooser.Show(modifier = Modifier.constrainAs(mouth) {
                this.width = Dimension.wrapContent
                this.height = Dimension.wrapContent
                this.bottom near yesAnimation.top
                this.start near secondSeparator
                this.end near thirdSeparator
            })

            self.hairColorChooser.Show(modifier = Modifier.constrainAs(hairColor) {
                this.width = Dimension.wrapContent
                this.height = Dimension.wrapContent
                this.bottom near yesAnimation.top
                this.start near thirdSeparator
                this.endParent
            })

            Button(onClick = { self.scene.play(robot.headYesAnimation) },
                   modifier = Modifier.constrainAs(yesAnimation) {
                       this.width = Dimension.wrapContent
                       this.height = Dimension.wrapContent
                       this.bottom near walkAnimation.top
                       this.startParent
                       this.end near secondSeparator
                   }) { Text(text = stringResource(id = R.string.robotSayYes)) }

            Button(onClick = { self.scene.play(robot.headNoAnimation) },
                   modifier = Modifier.constrainAs(noAnimation) {
                       this.width = Dimension.wrapContent
                       this.height = Dimension.wrapContent
                       this.bottom near walkAnimation.top
                       this.start near secondSeparator
                       this.endParent
                   }) { Text(text = stringResource(id = R.string.robotSayNo)) }

            Button(onClick = { self.scene.play(robot.walk(25, 32)) },
                   modifier = Modifier.constrainAs(walkAnimation) {
                       this.width = Dimension.wrapContent
                       this.height = Dimension.wrapContent
                       this.bottomParent
                       this.startParent
                       this.end near secondSeparator
                   }) { Text(text = stringResource(id = R.string.robotWalk)) }

            Button(onClick = { self.scene.play(robot.run(12, 32)) },
                   modifier = Modifier.constrainAs(runAnimation) {
                       this.width = Dimension.wrapContent
                       this.height = Dimension.wrapContent
                       this.bottomParent
                       this.start near secondSeparator
                       this.endParent
                   }) { Text(text = stringResource(id = R.string.robotRun)) }
        }
    }
}
