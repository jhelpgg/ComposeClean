package fr.jhelp.composeclean.ui.extensions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import fr.jhelp.composeclean.ui.theme.DIALOG_BACKGROUND

fun Modifier.dialog(align: Alignment.Vertical): Modifier =
    this
        .wrapContentHeight(align = align)
        .fillMaxWidth()
        .background(color = DIALOG_BACKGROUND,
                    shape = RoundedCornerShape(
                        16.dp))
        .padding(all = 16.dp)