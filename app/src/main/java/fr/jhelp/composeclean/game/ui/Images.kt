package fr.jhelp.composeclean.game.ui

import fr.jhelp.android.library.engine.resources.image.ImagePosition
import fr.jhelp.android.library.engine.resources.image.ImageSource
import fr.jhelp.android.library.engine.resources.image.ImageSourceAsset
import fr.jhelp.android.library.engine.resources.image.ImageSourceStack

// *******************
// *** Base images ***
// *******************

/** Background down image */
val IMAGE_BACKGROUND_DOWN: ImageSource<*> = ImageSourceAsset("game/images/ui/background_down.png")

/** Background grey image */
val IMAGE_BACKGROUND_GREY: ImageSource<*> = ImageSourceAsset("game/images/ui/background_grey.png")

/** Background up image */
val IMAGE_BACKGROUND_UP: ImageSource<*> = ImageSourceAsset("game/images/ui/background_up.png")

/** Fire down image */
val IMAGE_FIRE_DOWN: ImageSource<*> = ImageSourceAsset("game/images/ui/fire_down.png")

/** Fire grey image */
val IMAGE_FIRE_GREY: ImageSource<*> = ImageSourceAsset("game/images/ui/fire_grey.png")

/** Fire up image */
val IMAGE_FIRE_UP: ImageSource<*> = ImageSourceAsset("game/images/ui/fire_up.png")

/** Magic effect image */
val IMAGE_MAGICS: ImageSource<*> = ImageSourceAsset("game/images/ui/magics.png")

/** Rock down image */
val IMAGE_ROCK_DOWN: ImageSource<*> = ImageSourceAsset("game/images/ui/rock_down.png")

/** Rock grey image */
val IMAGE_ROCK_GREY: ImageSource<*> = ImageSourceAsset("game/images/ui/rock_grey.png")

/** Rock up image */
val IMAGE_ROCK_UP: ImageSource<*> = ImageSourceAsset("game/images/ui/rock_up.png")

/** Sword2 down image */
val IMAGE_SWORD2_DOWN: ImageSource<*> = ImageSourceAsset("game/images/ui/sword2_down.png")

/** Sword2 grey image */
val IMAGE_SWORD2_GREY: ImageSource<*> = ImageSourceAsset("game/images/ui/sword2_grey.png")

/** Sword2 up image */
val IMAGE_SWORD2_UP: ImageSource<*> = ImageSourceAsset("game/images/ui/sword2_up.png")

/** Sword down image */
val IMAGE_SWORD_DOWN: ImageSource<*> = ImageSourceAsset("game/images/ui/sword_down.png")

/** Sword grey image */
val IMAGE_SWORD_GREY: ImageSource<*> = ImageSourceAsset("game/images/ui/sword_grey.png")

/** Sword up image */
val IMAGE_SWORD_UP: ImageSource<*> = ImageSourceAsset("game/images/ui/sword_up.png")

/** Wand down image */
val IMAGE_WAND_DOWN: ImageSource<*> = ImageSourceAsset("game/images/ui/wand_down.png")

/** Wand grey image */
val IMAGE_WAND_GREY: ImageSource<*> = ImageSourceAsset("game/images/ui/wand_grey.png")

/** Wand up image */
val IMAGE_WAND_UP: ImageSource<*> = ImageSourceAsset("game/images/ui/wand_up.png")

/** Water down image */
val IMAGE_WATER_DOWN: ImageSource<*> = ImageSourceAsset("game/images/ui/water_down.png")

/** Water grey image */
val IMAGE_WATER_GREY: ImageSource<*> = ImageSourceAsset("game/images/ui/water_grey.png")

/** Water up image */
val IMAGE_WATER_UP: ImageSource<*> = ImageSourceAsset("game/images/ui/water_up.png")

/** Wind down image */
val IMAGE_WIND_DOWN: ImageSource<*> = ImageSourceAsset("game/images/ui/wind_down.png")

/** Wind grey image */
val IMAGE_WIND_GREY: ImageSource<*> = ImageSourceAsset("game/images/ui/wind_grey.png")

/** Wind up image */
val IMAGE_WIND_UP: ImageSource<*> = ImageSourceAsset("game/images/ui/wind_up.png")

// **********************
// *** Buttons images ***
// **********************

/** Button fire down */
val IMAGE_BUTTON_FIRE_DOWN: ImageSource<*> = ImageSourceStack(IMAGE_BACKGROUND_DOWN,
                                                              ImagePosition(IMAGE_FIRE_DOWN))

/** Button fire grey */
val IMAGE_BUTTON_FIRE_GREY: ImageSource<*> = ImageSourceStack(IMAGE_BACKGROUND_GREY,
                                                              ImagePosition(IMAGE_FIRE_GREY))

/** Button fire up */
val IMAGE_BUTTON_FIRE_UP: ImageSource<*> = ImageSourceStack(IMAGE_BACKGROUND_UP,
                                                            ImagePosition(IMAGE_FIRE_UP))

/** Button rock down */
val IMAGE_BUTTON_ROCK_DOWN: ImageSource<*> = ImageSourceStack(IMAGE_BACKGROUND_DOWN,
                                                              ImagePosition(IMAGE_ROCK_DOWN))

/** Button rock grey */
val IMAGE_BUTTON_ROCK_GREY: ImageSource<*> = ImageSourceStack(IMAGE_BACKGROUND_GREY,
                                                              ImagePosition(IMAGE_ROCK_GREY))

/** Button rock up */
val IMAGE_BUTTON_ROCK_UP: ImageSource<*> = ImageSourceStack(IMAGE_BACKGROUND_UP,
                                                            ImagePosition(IMAGE_ROCK_UP))

/** Button with 2 swords down */
val IMAGE_BUTTON_TWO_SWORDS_DOWN: ImageSource<*> = ImageSourceStack(IMAGE_BACKGROUND_DOWN,
                                                                    ImagePosition(IMAGE_SWORD_DOWN),
                                                                    ImagePosition(IMAGE_SWORD2_DOWN))

/** Button with 2 swords grey */
val IMAGE_BUTTON_TWO_SWORDS_GREY: ImageSource<*> = ImageSourceStack(IMAGE_BACKGROUND_GREY,
                                                                    ImagePosition(IMAGE_SWORD_GREY),
                                                                    ImagePosition(IMAGE_SWORD2_GREY))

/** Button with 2 swords up */
val IMAGE_BUTTON_TWO_SWORDS_UP: ImageSource<*> = ImageSourceStack(IMAGE_BACKGROUND_UP,
                                                                  ImagePosition(IMAGE_SWORD_UP),
                                                                  ImagePosition(IMAGE_SWORD2_UP))

/** Button sword down */
val IMAGE_BUTTON_SWORD_DOWN: ImageSource<*> = ImageSourceStack(IMAGE_BACKGROUND_DOWN,
                                                               ImagePosition(IMAGE_SWORD_DOWN))

/** Button sword grey */
val IMAGE_BUTTON_SWORD_GREY: ImageSource<*> = ImageSourceStack(IMAGE_BACKGROUND_GREY,
                                                               ImagePosition(IMAGE_SWORD_GREY))

/** Button sword up */
val IMAGE_BUTTON_SWORD_UP: ImageSource<*> = ImageSourceStack(IMAGE_BACKGROUND_UP,
                                                             ImagePosition(IMAGE_SWORD_UP))

/** Button wand down */
val IMAGE_BUTTON_WAND_DOWN: ImageSource<*> = ImageSourceStack(IMAGE_BACKGROUND_DOWN,
                                                              ImagePosition(IMAGE_WAND_DOWN),
                                                              ImagePosition(IMAGE_MAGICS))

/** Button wand grey */
val IMAGE_BUTTON_WAND_GREY: ImageSource<*> = ImageSourceStack(IMAGE_BACKGROUND_GREY,
                                                              ImagePosition(IMAGE_WAND_GREY))

/** Button wand up */
val IMAGE_BUTTON_WAND_UP: ImageSource<*> = ImageSourceStack(IMAGE_BACKGROUND_UP,
                                                            ImagePosition(IMAGE_WAND_UP),
                                                            ImagePosition(IMAGE_MAGICS))

/** Button water down */
val IMAGE_BUTTON_WATER_DOWN: ImageSource<*> = ImageSourceStack(IMAGE_BACKGROUND_DOWN,
                                                               ImagePosition(IMAGE_WATER_DOWN))

/** Button water grey */
val IMAGE_BUTTON_WATER_GREY: ImageSource<*> = ImageSourceStack(IMAGE_BACKGROUND_GREY,
                                                               ImagePosition(IMAGE_WATER_GREY))

/** Button water up */
val IMAGE_BUTTON_WATER_UP: ImageSource<*> = ImageSourceStack(IMAGE_BACKGROUND_UP,
                                                             ImagePosition(IMAGE_WATER_UP))

/** Button wind down */
val IMAGE_BUTTON_WIND_DOWN: ImageSource<*> = ImageSourceStack(IMAGE_BACKGROUND_DOWN,
                                                              ImagePosition(IMAGE_WIND_DOWN))

/** Button wind grey */
val IMAGE_BUTTON_WIND_GREY: ImageSource<*> = ImageSourceStack(IMAGE_BACKGROUND_GREY,
                                                              ImagePosition(IMAGE_WIND_GREY))

/** Button wind up */
val IMAGE_BUTTON_WIND_UP: ImageSource<*> = ImageSourceStack(IMAGE_BACKGROUND_UP,
                                                            ImagePosition(IMAGE_WIND_UP))


