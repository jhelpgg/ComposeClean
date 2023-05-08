package fr.jhelp.composeclean.models

import fr.jhelp.compose.provider.provideSingle
import fr.jhelp.composeclean.models.implementation.ColorChooserModelImplementation
import fr.jhelp.composeclean.models.implementation.ContactListModelImplementation
import fr.jhelp.composeclean.models.implementation.ImageChooserModelImplementation
import fr.jhelp.composeclean.models.implementation.ImageContrastModelImplementation
import fr.jhelp.composeclean.models.implementation.ImageGreyModelImplementation
import fr.jhelp.composeclean.models.implementation.ImageMaskModelImplementation
import fr.jhelp.composeclean.models.implementation.ImageMultiplyModelImplementation
import fr.jhelp.composeclean.models.implementation.ImageShiftModelImplementation
import fr.jhelp.composeclean.models.implementation.ImageTintModelImplementation
import fr.jhelp.composeclean.models.implementation.MainPresentationModelImplementation
import fr.jhelp.composeclean.models.implementation.MaskChooserModelImplementation
import fr.jhelp.composeclean.models.implementation.NavigationModelImplementation
import fr.jhelp.composeclean.models.implementation.SoundManagerExampleModelImplementation
import fr.jhelp.composeclean.models.shared.ColorChooserModel
import fr.jhelp.composeclean.models.shared.ContactListModel
import fr.jhelp.composeclean.models.shared.ImageChooserModel
import fr.jhelp.composeclean.models.shared.ImageContrastModel
import fr.jhelp.composeclean.models.shared.ImageGreyModel
import fr.jhelp.composeclean.models.shared.ImageMaskModel
import fr.jhelp.composeclean.models.shared.ImageMultiplyModel
import fr.jhelp.composeclean.models.shared.ImageShiftModel
import fr.jhelp.composeclean.models.shared.ImageTintModel
import fr.jhelp.composeclean.models.shared.MainPresentationModel
import fr.jhelp.composeclean.models.shared.MaskChooserModel
import fr.jhelp.composeclean.models.shared.NavigationModel
import fr.jhelp.composeclean.models.shared.SoundManagerExampleModel

/**
 * Inject models implementations to share them
 */
fun injectModels()
{
    provideSingle<NavigationModel> { NavigationModelImplementation() }
    provideSingle<MainPresentationModel> { MainPresentationModelImplementation() }

    provideSingle<ImageChooserModel> { ImageChooserModelImplementation() }
    provideSingle<ColorChooserModel> { ColorChooserModelImplementation() }
    provideSingle<MaskChooserModel> { MaskChooserModelImplementation() }

    provideSingle<ContactListModel> { ContactListModelImplementation() }
    provideSingle<SoundManagerExampleModel> { SoundManagerExampleModelImplementation() }
    provideSingle<ImageGreyModel> { ImageGreyModelImplementation() }
    provideSingle<ImageTintModel> { ImageTintModelImplementation() }
    provideSingle<ImageMaskModel> { ImageMaskModelImplementation() }
    provideSingle<ImageShiftModel> { ImageShiftModelImplementation() }
    provideSingle<ImageContrastModel> { ImageContrastModelImplementation() }
    provideSingle<ImageMultiplyModel> { ImageMultiplyModelImplementation() }
}