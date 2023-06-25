package fr.jhelp.android.library.engine.dsl

/**
 * Create several animation node references base on nodes references given on creation
 */
class AnimationNodeReferencesCreator(private vararg var nodeReferences: NodeReference)
{
    /** Animation on node reference for first node reference in parameter  */
    operator fun component1(): AnimationNodeReference =
        AnimationNodeReference(this.nodeReferences[0])

    /** Animation on node reference for second node reference in parameter  */
    operator fun component2(): AnimationNodeReference =
        AnimationNodeReference(this.nodeReferences[1])

    /** Animation on node reference for third node reference in parameter  */
    operator fun component3(): AnimationNodeReference =
        AnimationNodeReference(this.nodeReferences[2])

    /** Animation on node reference for fourth node reference in parameter  */
    operator fun component4(): AnimationNodeReference =
        AnimationNodeReference(this.nodeReferences[3])

    /** Animation on node reference for 5th node reference in parameter  */
    operator fun component5(): AnimationNodeReference =
        AnimationNodeReference(this.nodeReferences[4])

    /** Animation on node reference for 6th node reference in parameter  */
    operator fun component6(): AnimationNodeReference =
        AnimationNodeReference(this.nodeReferences[5])

    /** Animation on node reference for 67h node reference in parameter  */
    operator fun component7(): AnimationNodeReference =
        AnimationNodeReference(this.nodeReferences[6])

    /** Animation on node reference for 8th node reference in parameter  */
    operator fun component8(): AnimationNodeReference =
        AnimationNodeReference(this.nodeReferences[7])

    /** Animation on node reference for 9th node reference in parameter  */
    operator fun component9(): AnimationNodeReference =
        AnimationNodeReference(this.nodeReferences[8])

    /** Animation on node reference for 10th node reference in parameter  */
    operator fun component10(): AnimationNodeReference =
        AnimationNodeReference(this.nodeReferences[9])

    /** Animation on node reference for 11th node reference in parameter  */
    operator fun component11(): AnimationNodeReference =
        AnimationNodeReference(this.nodeReferences[10])

    /** Animation on node reference for 12th node reference in parameter  */
    operator fun component12(): AnimationNodeReference =
        AnimationNodeReference(this.nodeReferences[11])

    /** Animation on node reference for 13th node reference in parameter  */
    operator fun component13(): AnimationNodeReference =
        AnimationNodeReference(this.nodeReferences[12])

    /** Animation on node reference for 14th node reference in parameter  */
    operator fun component14(): AnimationNodeReference =
        AnimationNodeReference(this.nodeReferences[13])

    /** Animation on node reference for 15th node reference in parameter  */
    operator fun component15(): AnimationNodeReference =
        AnimationNodeReference(this.nodeReferences[14])

    /** Animation on node reference for 16th node reference in parameter  */
    operator fun component16(): AnimationNodeReference =
        AnimationNodeReference(this.nodeReferences[15])
}