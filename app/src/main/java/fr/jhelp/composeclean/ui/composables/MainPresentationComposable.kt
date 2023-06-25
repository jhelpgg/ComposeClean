package fr.jhelp.composeclean.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import fr.jhelp.android.library.constraint.extensions.bottomParent
import fr.jhelp.android.library.constraint.extensions.endParent
import fr.jhelp.android.library.constraint.extensions.near
import fr.jhelp.android.library.constraint.extensions.startParent
import fr.jhelp.android.library.provider.provided
import fr.jhelp.compose.ui.recycler.RecyclerComposable
import fr.jhelp.composeclean.R
import fr.jhelp.composeclean.models.presentation.Presentation
import fr.jhelp.composeclean.models.presentation.PresentationType
import fr.jhelp.composeclean.models.shared.MainPresentationModel
import fr.jhelp.composeclean.ui.composables.presentation.DrawPresentation

/**
 * Main activity composable
 */
class MainPresentationComposable
{
    companion object
    {
        private const val INDEX_ALL = 0
        private const val INDEX_UI = 1
        private const val INDEX_2D = 2
        private const val INDEX_3D = 3
    }

    private val mainPresentationModel: MainPresentationModel by provided<MainPresentationModel>()

    /**
     * Show the main activity
     */
    @Composable
    fun Show()
    {
        var selectedFilterIndex: Int by remember { mutableStateOf(MainPresentationComposable.INDEX_ALL) }
        val select = this.mainPresentationModel::select
        val recyclerView =
            RecyclerComposable<Presentation> { presentation ->
                DrawPresentation(presentation, select)
            }
        this.mainPresentationModel.initialize(recyclerView.recyclerModel)

        ConstraintLayout(Modifier.fillMaxSize()) {
            val (filters, list) = this.createRefs()

            recyclerView.Draw(Modifier.constrainAs(list) {
                this.width = Dimension.fillToConstraints
                this.height = Dimension.fillToConstraints
                this.top near filters.bottom
                this.bottomParent
                this.startParent
                this.endParent
            })

            TabRow(selectedTabIndex = selectedFilterIndex,
                   modifier = Modifier.constrainAs(filters) {
                       this.width = Dimension.fillToConstraints
                       this.height = Dimension.wrapContent
                       this.top.linkTo(this.parent.top)
                       this.startParent
                       this.endParent
                   }) {
                Tab(selected = selectedFilterIndex == MainPresentationComposable.INDEX_ALL,
                    text = { Text(text = stringResource(id = R.string.presentationAll)) },
                    onClick = {
                        selectedFilterIndex = MainPresentationComposable.INDEX_ALL
                        this@MainPresentationComposable.mainPresentationModel.showAll()
                    })
                Tab(selected = selectedFilterIndex == MainPresentationComposable.INDEX_UI,
                    text = { Text(text = stringResource(id = R.string.presentationUI)) },
                    onClick = {
                        selectedFilterIndex = MainPresentationComposable.INDEX_UI
                        this@MainPresentationComposable.mainPresentationModel.showType(
                            PresentationType.UI)
                    })
                Tab(selected = selectedFilterIndex == MainPresentationComposable.INDEX_2D,
                    text = { Text(text = stringResource(id = R.string.presentation2D)) },
                    onClick = {
                        selectedFilterIndex = MainPresentationComposable.INDEX_2D
                        this@MainPresentationComposable.mainPresentationModel.showType(
                            PresentationType.IMAGE_MANIPULATION)
                    })
                Tab(selected = selectedFilterIndex == MainPresentationComposable.INDEX_3D,
                    text = { Text(text = stringResource(id = R.string.presentation3D)) },
                    onClick = {
                        selectedFilterIndex = MainPresentationComposable.INDEX_3D
                        this@MainPresentationComposable.mainPresentationModel.showType(
                            PresentationType.ENGINE_3D)
                    })
            }
        }
    }
}