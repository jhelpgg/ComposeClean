package fr.jhelp.android.library.engine.annotations;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Texture possible width or height
 */
@IntDef({1, 2, 4, 8, 16, 32, 64, 128, 256, 512})
@Retention(RetentionPolicy.SOURCE)
public @interface TextureSize {
}
