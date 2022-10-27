package fr.jhelp.compose.engine.view

import fr.jhelp.compose.math.Point2D
import fr.jhelp.compose.math.Point3D

data class ViewBounds(val topLeftNear: Point3D = Point3D(), val bottomRightFar: Point3D = Point3D(),
                      val topLet: Point2D = Point2D(), val bottomRight: Point2D = Point2D())