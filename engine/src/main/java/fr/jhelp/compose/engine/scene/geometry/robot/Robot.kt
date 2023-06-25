/*
 *  <h1>License :</h1> <br/>
 * The following code is deliver as is. <br/>
 *  You can use, modify, the code as your need for any usage.<br/>
 *  But you can't do any action that avoid me or other person use, modify this code.<br/>
 *  The code is free for usage and modification, you can't change that fact.
 */

package fr.jhelp.compose.engine.scene.geometry.robot

import androidx.annotation.DrawableRes
import fr.jhelp.compose.engine.R
import fr.jhelp.compose.engine.resources.ResourcesAccess
import fr.jhelp.compose.engine.scene.Clone3D
import fr.jhelp.compose.engine.scene.Color3D
import fr.jhelp.compose.engine.scene.Material
import fr.jhelp.compose.engine.scene.Node3D
import fr.jhelp.compose.engine.scene.Texture
import fr.jhelp.compose.engine.scene.geometry.Box
import fr.jhelp.compose.engine.scene.geometry.CrossUV
import fr.jhelp.compose.engine.scene.geometry.Revolution
import fr.jhelp.compose.engine.scene.geometry.Sphere
import fr.jhelp.compose.images.path.Path
import fr.jhelp.android.library.math.extensions.bounds
import fr.jhelp.android.library.math.moduloInterval

private fun createCylinder(): Revolution
{
    val path = Path()
    path.moveTo(0.3f, 2f)
    path.lineTo(0.3f, 0f)
    return Revolution(path)
}

/**
 * A robot
 * @property headTexture Robot head description
 */
class Robot(val headTexture: Head = Head())
{
    /**Node that contains the whole robot. Use it to place robot in scene*/
    val mainNode: Node3D = Node3D()

    /**Head object*/
    private val head = Sphere()

    /**Neck node. It it b the joint for move the head*/
    private val neck = Node3D()

    /**Body object*/
    private val body = Box(CrossUV(5f / 22f, 17f / 22f, 0.1f, 0.5f, 0.6f))

    /**Node for attach something in the back*/
    private val backAttach = Node3D()

    /**Right shoulder joint*/
    private val rightShoulder = Node3D()

    /**Left shoulder joint*/
    private val leftShoulder = Node3D()

    /**Right ass joint*/
    private val rightAss = Node3D()

    /**Left ass joint*/
    private val leftAss = Node3D()

    /**Arms/legs part base*/
    private val cylinder = createCylinder()

    /**Right before arm object*/
    private val rightBeforeArm = Clone3D(this.cylinder)

    /**Right after arm object*/
    private val rightAfterArm = Clone3D(this.cylinder)

    /**Right hand where can be attach something*/
    private val rightHand = Node3D()

    /**Left before arm object*/
    private val leftBeforeArm = Clone3D(this.cylinder)

    /**Left after arm object*/
    private val leftAfterArm = Clone3D(this.cylinder)

    /**Left hand where can be attach something*/
    private val leftHand = Node3D()

    /**Right before leg object*/
    private val rightBeforeLeg = Clone3D(this.cylinder)

    /**Right after leg object*/
    private val rightAfterLeg = Clone3D(this.cylinder)

    /**Left before leg object*/
    private val leftBeforeLeg = Clone3D(this.cylinder)

    /**Left after leg object*/
    private val leftAfterLeg = Clone3D(this.cylinder)

    /**Body material*/
    private val materialBody = Material()

    /**Complete right arm material*/
    private val materialRightArm = Material()

    /**Complete left arm material*/
    private val materialLeftArm = Material()

    /**Complete right leg material*/
    private val materialRightLeg = Material()

    /**Complete left leg material*/
    private val materialLeftLeg = Material()

    init
    {
        val joint = Sphere(slice = 7, slack = 7)
        this.head.material.texture = this.headTexture.texture

        //Head
        this.head.position.position(0f, 0.75f, 0f)
        this.neck.add(this.head)
        this.neck.position.position(0f, 2.25f, 0f)
        this.mainNode.add(this.neck)

        //Body
        this.body.position.scale(2.4f, 4f, 1f)
        this.body.material = this.materialBody
        this.mainNode.add(this.body)
        this.bodyTexture(R.drawable.body_costume)

        this.backAttach.position.position(0f, 2f, -0.6f)
        this.mainNode.add(this.backAttach)

        //Right arm
        val joint1 = Clone3D(joint)
        joint1.position.scale(0.3f)
        this.rightShoulder.add(joint1)
        this.rightShoulder.add(this.rightBeforeArm)
        this.rightShoulder.position.position(-1.5f, 1.7f, 0f)
        this.rightShoulder.position.angleX = 180f
        this.mainNode.add(this.rightShoulder)

        val joint2 = Clone3D(joint)
        joint2.position.scale(0.3f)
        joint2.position.position(0f, 2f, 0f)
        this.rightBeforeArm.add(joint2)
        this.rightAfterArm.position.position(0f, 2f, 0f)
        this.rightBeforeArm.add(this.rightAfterArm)

        val joint3 = Clone3D(joint)
        joint3.position.scale(0.3f)
        joint3.position.position(0f, 2f, 0f)
        this.rightAfterArm.add(joint3)

        this.rightHand.position.position(0f, 2.1f, 0f)
        this.rightAfterArm.add(this.rightHand)

        this.rightShoulder.applyMaterialHierarchically(this.materialRightArm)
        this.rightArmColor()

        //Left arm
        val joint4 = Clone3D(joint)
        joint4.position.scale(0.3f)
        this.leftShoulder.add(joint4)
        this.leftShoulder.add(this.leftBeforeArm)
        this.leftShoulder.position.position(1.5f, 1.7f, 0f)
        this.leftShoulder.position.angleX = 180f
        this.mainNode.add(this.leftShoulder)

        val joint5 = Clone3D(joint)
        joint5.position.scale(0.3f)
        joint5.position.position(0f, 2f, 0f)
        this.leftBeforeArm.add(joint5)
        this.leftAfterArm.position.position(0f, 2f, 0f)
        this.leftBeforeArm.add(this.leftAfterArm)

        val joint6 = Clone3D(joint)
        joint6.position.scale(0.3f)
        joint6.position.position(0f, 2f, 0f)
        this.leftAfterArm.add(joint6)

        this.leftHand.position.position(0f, 2.1f, 0f)
        this.leftAfterArm.add(this.leftHand)

        this.leftShoulder.applyMaterialHierarchically(this.materialLeftArm)
        this.leftArmColor()
        // Right leg
        val joint7 = Clone3D(joint)
        joint7.position.scale(0.3f)
        this.rightAss.add(joint7)
        this.rightAss.add(this.rightBeforeLeg)
        this.rightAss.position.position(-0.6f, -2.2f, 0f)
        this.rightAss.position.angleX = 180f
        this.mainNode.add(this.rightAss)

        val joint8 = Clone3D(joint)
        joint8.position.scale(0.3f)
        joint8.position.position(0f, 2f, 0f)
        this.rightBeforeLeg.add(joint8)
        this.rightAfterLeg.position.position(0f, 2f, 0f)
        this.rightBeforeLeg.add(this.rightAfterLeg)

        val joint9 = Clone3D(joint)
        joint9.position.scale(0.3f)
        joint9.position.position(0f, 2f, 0f)
        this.rightAfterLeg.add(joint9)

        this.rightAss.applyMaterialHierarchically(this.materialRightLeg)
        this.rightLegColor()

        // Left leg
        val joint10 = Clone3D(joint)
        joint10.position.scale(0.3f)
        this.leftAss.add(joint10)
        this.leftAss.add(this.leftBeforeLeg)
        this.leftAss.position.position(0.6f, -2.2f, 0f)
        this.leftAss.position.angleX = 180f
        this.mainNode.add(this.leftAss)

        val joint11 = Clone3D(joint)
        joint11.position.scale(0.3f)
        joint11.position.position(0f, 2f, 0f)
        this.leftBeforeLeg.add(joint11)
        this.leftAfterLeg.position.position(0f, 2f, 0f)
        this.leftBeforeLeg.add(this.leftAfterLeg)

        val joint12 = Clone3D(joint)
        joint12.position.scale(0.3f)
        joint12.position.position(0f, 2f, 0f)
        this.leftAfterLeg.add(joint12)

        this.leftAss.applyMaterialHierarchically(this.materialLeftLeg)
        this.leftLegColor()
    }

    /**
     * Change body texture
     */
    fun bodyTexture(@DrawableRes resourceId: Int)
    {
        this.materialBody.texture = ResourcesAccess.obtainTexture(resourceId)
    }

    /**
     * Change body texture
     */
    fun bodyTexture(texture: Texture)
    {
        this.materialBody.texture = texture
    }

    /**
     * Change right arm color
     */
    fun rightArmColor(color: Color3D = Color3D(0xFF828105.toInt()))
    {
        this.materialRightArm.diffuse = color
    }

    /**
     * Change left arm color
     */
    fun leftArmColor(color: Color3D = Color3D(0xFF828105.toInt()))
    {
        this.materialLeftArm.diffuse = color
    }

    /**
     * Change right leg color
     */
    fun rightLegColor(color: Color3D = Color3D(0xFF020260.toInt()))
    {
        this.materialRightLeg.diffuse = color
    }

    /**
     * Change left leg color
     */
    fun leftLegColor(color: Color3D = Color3D(0xFF020260.toInt()))
    {
        this.materialLeftLeg.diffuse = color
    }

    /**
     * Change left knee position
     * @param angleX Angle around X axis
     */
    fun leftKnee(angleX: Float = 0f)
    {
        this.leftAfterLeg.position.angleX = angleX.bounds(0f, 150f)
    }

    /**
     * Rotate left knee
     * @param rotateX Angle around X axis to add
     */
    fun rotateLeftKnee(rotateX: Float = 0f)
    {
        this.leftKnee(this.leftAfterLeg.position.angleX + rotateX)
    }

    /**
     * Change left ass position
     * @param angleX Angle around X axis
     * @param angleZ Angle around Z axis
     */
    fun leftAss(angleX: Float = 180f, angleZ: Float = 0f)
    {
        this.leftAss.position.angleX = angleX.bounds(90f, 270f)
        this.leftAss.position.angleZ = angleZ.bounds(-90f, 30f)
    }

    /**
     * Rotate left ass position
     * @param rotateX Angle around X axis to add
     * @param rotateZ Angle around Z axis to add
     */
    fun rotateLeftAss(rotateX: Float = 0f, rotateZ: Float = 0f)
    {
        this.leftAss(this.leftAss.position.angleX + rotateX,
                     this.leftAss.position.angleZ + rotateZ)
    }

    /**
     * Change right knee position
     * @param angleX Angle around X axis
     */
    fun rightKnee(angleX: Float = 0f)
    {
        this.rightAfterLeg.position.angleX = angleX.bounds(0f, 150f)
    }

    /**
     * Rotate right knee
     * @param rotateX Angle around X axis to add
     */
    fun rotateRightKnee(rotateX: Float = 0f)
    {
        this.rightKnee(this.rightAfterLeg.position.angleX + rotateX)
    }

    /**
     * Change right ass position
     * @param angleX Angle around X axis
     * @param angleZ Angle around Z axis
     */
    fun rightAss(angleX: Float = 180f, angleZ: Float = 0f)
    {
        this.rightAss.position.angleX = angleX.bounds(90f, 270f)
        this.rightAss.position.angleZ = angleZ.bounds(-30f, 90f)
    }

    /**
     * Rotate right ass position
     * @param rotateX Angle around X axis to add
     * @param rotateZ Angle around Z axis to add
     */
    fun rotateRightAss(rotateX: Float = 0f, rotateZ: Float = 0f)
    {
        this.rightAss(this.rightAss.position.angleX + rotateX,
                      this.rightAss.position.angleZ + rotateZ)
    }

    /**
     * Change left elbow position
     * @param angleX Angle around X axis
     */
    fun leftElbow(angleX: Float = 0f)
    {
        this.leftAfterArm.position.angleX = angleX.bounds(-150f, 0f)
    }

    /**
     * Rotate left elbow
     * @param rotateX Angle around X axis to add
     */
    fun rotateLeftElbow(rotateX: Float = 0f)
    {
        this.leftElbow(this.leftAfterArm.position.angleX + rotateX)
    }

    /**
     * Change left shoulder position
     * @param angleX Angle around X axis
     * @param angleZ Angle around Z axis
     */
    fun leftShoulder(angleX: Float = 180f, angleZ: Float = 0f)
    {
        this.leftShoulder.position.angleX = moduloInterval(angleX, 0f, 360f)
        this.leftShoulder.position.angleZ = angleZ.bounds(-180f, 0f)
    }

    /**
     * Rotate left shoulder position
     * @param rotateX Angle around X axis to add
     * @param rotateZ Angle around Z axis to add
     */
    fun rotateLeftShoulder(rotateX: Float = 0f, rotateZ: Float = 0f)
    {
        this.leftShoulder(this.leftShoulder.position.angleX + rotateX,
                          this.leftShoulder.position.angleZ + rotateZ)
    }

    /**
     * Change right elbow position
     * @param angleX Angle around X axis
     */
    fun rightElbow(angleX: Float = 0f)
    {
        this.rightAfterArm.position.angleX = angleX.bounds(-150f, 0f)
    }

    /**
     * Rotate right elbow
     * @param rotateX Angle around X axis to add
     */
    fun rotateRightElbow(rotateX: Float = 0f)
    {
        this.rightElbow(this.rightAfterArm.position.angleX + rotateX)
    }

    /**
     * Change right shoulder position
     * @param angleX Angle around X axis
     * @param angleZ Angle around Z axis
     */
    fun rightShoulder(angleX: Float = 180f, angleZ: Float = 0f)
    {
        this.rightShoulder.position.angleX = moduloInterval(angleX, 0f, 360f)
        this.rightShoulder.position.angleZ = angleZ.bounds(0f, 180f)
    }

    /**
     * Rotate right shoulder position
     * @param rotateX Angle around X axis to add
     * @param rotateZ Angle around Z axis to add
     */
    fun rotateRightShoulder(rotateX: Float = 0f, rotateZ: Float = 0f)
    {
        this.rightShoulder(this.rightShoulder.position.angleX + rotateX,
                           this.rightShoulder.position.angleZ + rotateZ)
    }

    /**
     * Change neck position
     * @param angleX Angle around X axis
     * @param angleY Angle around Y axis
     * @param angleZ Angle around Z axis
     */
    fun neck(angleX: Float = 0f, angleY: Float = 0f, angleZ: Float = 0f)
    {
        this.neck.position.angleX = angleX.bounds(-45f, 45f)
        this.neck.position.angleY = angleY.bounds(-90f, 90f)
        this.neck.position.angleZ = angleZ.bounds(-22f, 22f)
    }

    /**
     * Rotate neck position
     * @param rotateX Angle around X axis to add
     * @param rotateY Angle around Y axis to add
     * @param rotateZ Angle around Z axis to add
     */
    fun rotateNeck(rotateX: Float = 0f, rotateY: Float = 0f, rotateZ: Float = 0f)
    {
        this.neck(this.neck.position.angleX + rotateX,
                  this.neck.position.angleY + rotateY,
                  this.neck.position.angleZ + rotateZ)
    }

    /**
     * Current robot position
     * @return Current robot position
     */
    fun robotPosition(): RobotPosition =
        RobotPosition(this.neck.position.angleX, this.neck.position.angleY,
                      this.neck.position.angleZ,
                      this.rightShoulder.position.angleX, this.rightShoulder.position.angleZ,
                      this.rightAfterArm.position.angleX,
                      this.leftShoulder.position.angleX, this.leftShoulder.position.angleZ,
                      this.leftAfterArm.position.angleX,
                      this.rightAss.position.angleX, this.rightAss.position.angleZ,
                      this.rightAfterLeg.position.angleX,
                      this.leftAss.position.angleX, this.leftAss.position.angleZ,
                      this.leftAfterLeg.position.angleX)

    /**
     * Change robot position
     * @param robotPosition New robot position
     */
    fun robotPosition(robotPosition: RobotPosition)
    {
        this.neck.position.angleX = robotPosition.neckAngleX
        this.neck.position.angleY = robotPosition.neckAngleY
        this.neck.position.angleZ = robotPosition.neckAngleZ

        this.rightShoulder.position.angleX = robotPosition.rightShoulderAngleX
        this.rightShoulder.position.angleZ = robotPosition.rightShoulderAngleZ

        this.rightAfterArm.position.angleX = robotPosition.rightElbowAngleX

        this.leftShoulder.position.angleX = robotPosition.leftShoulderAngleX
        this.leftShoulder.position.angleZ = robotPosition.leftShoulderAngleZ

        this.leftAfterArm.position.angleX = robotPosition.leftElbowAngleX

        this.rightAss.position.angleX = robotPosition.rightAssAngleX
        this.rightAss.position.angleZ = robotPosition.rightAssAngleZ

        this.rightAfterLeg.position.angleX = robotPosition.rightKneeAngleX

        this.leftAss.position.angleX = robotPosition.leftAssAngleX
        this.leftAss.position.angleZ = robotPosition.leftAssAngleZ

        this.leftAfterLeg.position.angleX = robotPosition.leftKneeAngleX
    }

    /**
     * Remove any object in the right hand
     */
    fun freeRightHand(): Unit = this.rightHand.removeAllChildren()

    /**
     * Put something in the right hand
     * @param node Main node of the thing to carry
     */
    fun putOnRightHand(node: Node3D)
    {
        this.freeRightHand()
        this.rightHand.add(node)
    }

    /**
     * Remove any object in the left hand
     */
    fun freeLeftHand(): Unit = this.leftHand.removeAllChildren()

    /**
     * Put something in the left hand
     * @param node Main node of the thing to carry
     */
    fun putOnLeftHand(node: Node3D)
    {
        this.freeLeftHand()
        this.leftHand.add(node)
    }

    /**
     * Remove any object attach on the back
     */
    fun freeBack(): Unit = this.backAttach.removeAllChildren()

    /**
     * Attach something in the back
     * @param node Main node of the thing to attach
     */
    fun putOnBack(node: Node3D)
    {
        this.freeBack()
        this.backAttach.add(node)
    }
}