package fr.jhelp.android.library.models

import fr.jhelp.android.library.provider.provideSingle
import fr.jhelp.android.library.models.implementation.ColorChooserModelImplementation
import fr.jhelp.android.library.models.implementation.ContactListModelImplementation
import fr.jhelp.android.library.models.implementation.EngineInterpolationModelImplementation
import fr.jhelp.android.library.models.implementation.EyeChooserModelImplementation
import fr.jhelp.android.library.models.implementation.ImageAddModelImplementation
import fr.jhelp.android.library.models.implementation.ImageBumpMapModelImplementation
import fr.jhelp.android.library.models.implementation.ImageChooserModelImplementation
import fr.jhelp.android.library.models.implementation.ImageContrastModelImplementation
import fr.jhelp.android.library.models.implementation.ImageDarkerModelImplementation
import fr.jhelp.android.library.models.implementation.ImageGreyModelImplementation
import fr.jhelp.android.library.models.implementation.ImageInvertColorsModelImplementation
import fr.jhelp.android.library.models.implementation.ImageMaskModelImplementation
import fr.jhelp.android.library.models.implementation.ImageMultiplyModelImplementation
import fr.jhelp.android.library.models.implementation.ImageNeonLinesModelImplementation
import fr.jhelp.android.library.models.implementation.ImageRepeatOnLineModelImplementation
import fr.jhelp.android.library.models.implementation.ImageShiftModelImplementation
import fr.jhelp.android.library.models.implementation.ImageTintModelImplementation
import fr.jhelp.android.library.models.implementation.MainPresentationModelImplementation
import fr.jhelp.android.library.models.implementation.MaskChooserModelImplementation
import fr.jhelp.android.library.models.implementation.MouthChooserModelImplementation
import fr.jhelp.android.library.models.implementation.NavigationModelImplementation
import fr.jhelp.android.library.models.implementation.SoundManagerExampleModelImplementation
import fr.jhelp.android.library.models.shared.ColorChooserModel
import fr.jhelp.android.library.models.shared.ContactListModel
import fr.jhelp.android.library.models.shared.EngineInterpolationModel
import fr.jhelp.android.library.models.shared.EyeChooserModel
import fr.jhelp.android.library.models.shared.ImageAddModel
import fr.jhelp.android.library.models.shared.ImageBumpMapModel
import fr.jhelp.android.library.models.shared.ImageChooserModel
import fr.jhelp.android.library.models.shared.ImageContrastModel
import fr.jhelp.android.library.models.shared.ImageDarkerModel
import fr.jhelp.android.library.models.shared.ImageGreyModel
import fr.jhelp.android.library.models.shared.ImageInvertColorsModel
import fr.jhelp.android.library.models.shared.ImageMaskModel
import fr.jhelp.android.library.models.shared.ImageMultiplyModel
import fr.jhelp.android.library.models.shared.ImageNeonLinesModel
import fr.jhelp.android.library.models.shared.ImageRepeatOnLineModel
import fr.jhelp.android.library.models.shared.ImageShiftModel
import fr.jhelp.android.library.models.shared.ImageTintModel
import fr.jhelp.android.library.models.shared.MainPresentationModel
import fr.jhelp.android.library.models.shared.MaskChooserModel
import fr.jhelp.android.library.models.shared.MouthChooserModel
import fr.jhelp.android.library.models.shared.NavigationModel
import fr.jhelp.android.library.models.shared.SoundManagerExampleModel

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
    provideSingle<EyeChooserModel> { EyeChooserModelImplementation() }
    provideSingle<MouthChooserModel> { MouthChooserModelImplementation() }

    provideSingle<ContactListModel> { ContactListModelImplementation() }
    provideSingle<SoundManagerExampleModel> { SoundManagerExampleModelImplementation() }
    provideSingle<ImageGreyModel> { ImageGreyModelImplementation() }
    provideSingle<ImageTintModel> { ImageTintModelImplementation() }
    provideSingle<ImageMaskModel> { ImageMaskModelImplementation() }
    provideSingle<ImageShiftModel> { ImageShiftModelImplementation() }
    provideSingle<ImageContrastModel> { ImageContrastModelImplementation() }
    provideSingle<ImageMultiplyModel> { ImageMultiplyModelImplementation() }
    provideSingle<ImageAddModel> { ImageAddModelImplementation() }
    provideSingle<ImageDarkerModel> { ImageDarkerModelImplementation() }
    provideSingle<ImageInvertColorsModel> { ImageInvertColorsModelImplementation() }
    provideSingle<ImageBumpMapModel> { ImageBumpMapModelImplementation() }
    provideSingle<ImageNeonLinesModel> { ImageNeonLinesModelImplementation() }
    provideSingle<ImageRepeatOnLineModel> { ImageRepeatOnLineModelImplementation() }

    provideSingle<EngineInterpolationModel> { EngineInterpolationModelImplementation() }
}