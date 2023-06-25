package fr.jhelp.android.library.models.implementation

import fr.jhelp.android.library.provider.provided
import fr.jhelp.android.library.common.ui.recycler.RecyclerModel
import fr.jhelp.android.library.models.presentation.Presentation
import fr.jhelp.android.library.models.presentation.PresentationElement
import fr.jhelp.android.library.models.presentation.PresentationSeparator
import fr.jhelp.android.library.models.presentation.PresentationType
import fr.jhelp.android.library.models.presentation.type
import fr.jhelp.android.library.models.shared.MainPresentationModel
import fr.jhelp.android.library.models.shared.NavigationModel

/**
 * Main activity model implementation
 */
internal class MainPresentationModelImplementation : MainPresentationModel
{
    private val navigationModel: NavigationModel by provided<NavigationModel>()
    private lateinit var recyclerModel: RecyclerModel<Presentation>

    override fun initialize(recyclerModel: RecyclerModel<Presentation>)
    {
        this.recyclerModel = recyclerModel
        recyclerModel.clear()

        for (type in PresentationType.values())
        {
            recyclerModel += PresentationSeparator(type)

            for (element in PresentationElement.values())
            {
                if (element.type == type)
                {
                    recyclerModel += element
                }
            }
        }
    }

    override fun showAll()
    {
        this.recyclerModel.removeFilter()
    }

    override fun showType(presentationType: PresentationType)
    {
        this.recyclerModel.filter { presentation -> presentation.type == presentationType }
    }

    override fun select(presentationElement: PresentationElement)
    {
        (this.navigationModel as NavigationModelImplementation).chooseScreen(presentationElement.screen)
    }
}