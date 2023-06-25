package fr.jhelp.android.library.images.path

internal sealed class SegmentPointCloseInfo

internal class SegmentInfo(val segment: Segment) : SegmentPointCloseInfo()

internal class PointInfo(val x:Float,val  y:Float) : SegmentPointCloseInfo()

internal object CloseInfo : SegmentPointCloseInfo()