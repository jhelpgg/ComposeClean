package fr.jhelp.composeclean.models.shared

import fr.jhelp.compose.ui.recycler.RecyclerModel
import fr.jhelp.composeclean.models.presentation.Presentation
import fr.jhelp.composeclean.models.presentation.PresentationElement
import fr.jhelp.composeclean.models.presentation.PresentationType

/**
 * Main activity model
 */
interface MainPresentationModel
{
    /**
     * Initialize the model
     *
     * @param  recyclerModel Model of presentation possibility list
     */
    fun initialize(recyclerModel: RecyclerModel<Presentation>)

    /** Show the complete list */
    fun showAll()

    /** Show only the given type */
    fun showType(presentationType: PresentationType)

    /** Select one element */
    fun select(presentationElement: PresentationElement)
}