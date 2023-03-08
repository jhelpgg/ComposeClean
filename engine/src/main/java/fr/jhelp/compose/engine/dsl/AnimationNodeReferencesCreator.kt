package fr.jhelp.compose.engine.dsl

class AnimationNodeReferencesCreator(private vararg var nodeReferences: NodeReference)
{
    operator fun component1() = AnimationNodeReference(this.nodeReferences[0])
    operator fun component2() = AnimationNodeReference(this.nodeReferences[1])
    operator fun component3() = AnimationNodeReference(this.nodeReferences[2])
    operator fun component4() = AnimationNodeReference(this.nodeReferences[3])
    operator fun component5() = AnimationNodeReference(this.nodeReferences[4])
    operator fun component6() = AnimationNodeReference(this.nodeReferences[5])
    operator fun component7() = AnimationNodeReference(this.nodeReferences[6])
    operator fun component8() = AnimationNodeReference(this.nodeReferences[7])
    operator fun component9() = AnimationNodeReference(this.nodeReferences[8])
    operator fun component10() = AnimationNodeReference(this.nodeReferences[9])
    operator fun component11() = AnimationNodeReference(this.nodeReferences[10])
    operator fun component12() = AnimationNodeReference(this.nodeReferences[11])
    operator fun component13() = AnimationNodeReference(this.nodeReferences[12])
    operator fun component14() = AnimationNodeReference(this.nodeReferences[13])
    operator fun component15() = AnimationNodeReference(this.nodeReferences[14])
    operator fun component16() = AnimationNodeReference(this.nodeReferences[15])
}