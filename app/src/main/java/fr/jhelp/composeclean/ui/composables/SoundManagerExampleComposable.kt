package fr.jhelp.composeclean.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import fr.jhelp.android.library.constraint.theme.FAR
import fr.jhelp.android.library.constraint.theme.NEAR
import fr.jhelp.android.library.constraint.theme.NEXT
import fr.jhelp.android.library.constraint.theme.START_END
import fr.jhelp.android.library.constraint.theme.TOP_BOTTOM
import fr.jhelp.android.library.provider.provided
import fr.jhelp.composeclean.R
import fr.jhelp.composeclean.models.shared.SoundManagerExampleModel
import fr.jhelp.composeclean.ui.resources.Sounds
import fr.jhelp.composeclean.ui.theme.TITLE_SIZE

/**
 * Sound manage example interface
 */
class SoundManagerExampleComposable
{
    private val soundManagerExampleModel: SoundManagerExampleModel by provided<SoundManagerExampleModel>()

    /**
     * Show the sound manager example interface
     */
    @Composable
    fun Show()
    {
        val self = this
        LazyColumn(content = {
            this.item {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()) {

                    // Background

                    Text(text = stringResource(id = R.string.soundBackgroundTitle),
                         modifier = Modifier
                             .padding(top = TOP_BOTTOM,
                                      bottom = 0.dp,
                                      start = START_END,
                                      end = START_END)
                             .fillMaxWidth()
                             .wrapContentHeight(),
                         fontSize = TITLE_SIZE,
                         textAlign = TextAlign.Center)

                    Button(onClick = { self.soundManagerExampleModel.backgroundSound(Sounds.BACKGROUND_1.soundResource) },
                           modifier = Modifier
                               .padding(top = NEXT, bottom = 0.dp, start = FAR, end = START_END)
                               .fillMaxWidth()
                               .wrapContentHeight()) {
                        Text(text = stringResource(id = Sounds.BACKGROUND_1.textResource))
                    }


                    Button(onClick = { self.soundManagerExampleModel.backgroundSound(Sounds.BACKGROUND_2.soundResource) },
                           modifier = Modifier
                               .padding(top = NEXT, bottom = 0.dp, start = FAR, end = START_END)
                               .fillMaxWidth()
                               .wrapContentHeight()) {
                        Text(text = stringResource(id = Sounds.BACKGROUND_2.textResource))
                    }

                    Button(onClick = { self.soundManagerExampleModel.backgroundSound(Sounds.BACKGROUND_3.soundResource) },
                           modifier = Modifier
                               .padding(top = NEXT, bottom = 0.dp, start = FAR, end = START_END)
                               .fillMaxWidth()
                               .wrapContentHeight()) {
                        Text(text = stringResource(id = Sounds.BACKGROUND_3.textResource))
                    }

                    // Effect

                    Text(text = stringResource(id = R.string.soundEffectTitle),
                         modifier = Modifier
                             .padding(top = FAR, bottom = 0.dp, start = START_END, end = START_END)
                             .fillMaxWidth()
                             .wrapContentHeight(),
                         fontSize = TITLE_SIZE,
                         textAlign = TextAlign.Center)

                    Button(onClick = { self.soundManagerExampleModel.effectSound(Sounds.EFFECT_1.soundResource) },
                           modifier = Modifier
                               .padding(top = NEXT, bottom = 0.dp, start = FAR, end = START_END)
                               .fillMaxWidth()
                               .wrapContentHeight()) {
                        Text(text = stringResource(id = Sounds.EFFECT_1.textResource))
                    }

                    Button(onClick = { self.soundManagerExampleModel.effectSound(Sounds.EFFECT_2.soundResource) },
                           modifier = Modifier
                               .padding(top = NEXT, bottom = 0.dp, start = FAR, end = START_END)
                               .fillMaxWidth()
                               .wrapContentHeight()) {
                        Text(text = stringResource(id = Sounds.EFFECT_2.textResource))
                    }

                    Button(onClick = { self.soundManagerExampleModel.effectSound(Sounds.EFFECT_3.soundResource) },
                           modifier = Modifier
                               .padding(top = NEXT, bottom = 0.dp, start = FAR, end = START_END)
                               .fillMaxWidth()
                               .wrapContentHeight()) {
                        Text(text = stringResource(id = Sounds.EFFECT_3.textResource))
                    }

                    // Controlled

                    Text(text = stringResource(id = R.string.soundControlledTitle),
                         modifier = Modifier
                             .padding(top = FAR, bottom = 0.dp, start = START_END, end = START_END)
                             .fillMaxWidth()
                             .wrapContentHeight(),
                         fontSize = TITLE_SIZE,
                         textAlign = TextAlign.Center)

                    Button(onClick = { self.soundManagerExampleModel.controlledSound(Sounds.CONTROLLED_1.soundResource) },
                           modifier = Modifier
                               .padding(top = NEXT, bottom = 0.dp, start = FAR, end = START_END)
                               .fillMaxWidth()
                               .wrapContentHeight()) {
                        Text(text = stringResource(id = Sounds.CONTROLLED_1.textResource))
                    }

                    Button(onClick = { self.soundManagerExampleModel.controlledSound(Sounds.CONTROLLED_2.soundResource) },
                           modifier = Modifier
                               .padding(top = NEXT, bottom = 0.dp, start = FAR, end = START_END)
                               .fillMaxWidth()
                               .wrapContentHeight()) {
                        Text(text = stringResource(id = Sounds.CONTROLLED_2.textResource))
                    }

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()) {
                        Button(onClick = self.soundManagerExampleModel::playControlledSound,
                               modifier = Modifier
                                   .padding(top = NEXT, bottom = 0.dp, start = FAR, end = 0.dp)
                                   .wrapContentSize()
                              ) {
                            Image(painter = painterResource(id = android.R.drawable.ic_media_play),
                                  contentDescription = "Play",
                                  modifier = Modifier.size(32.dp))
                        }

                        Button(onClick = self.soundManagerExampleModel::pauseControlledSound,
                               modifier = Modifier
                                   .padding(top = NEXT, bottom = 0.dp, start = NEAR, end = 0.dp)
                                   .wrapContentSize()
                              ) {
                            Image(painter = painterResource(id = android.R.drawable.ic_media_pause),
                                  contentDescription = "Pause",
                                  modifier = Modifier.size(32.dp))
                        }

                        Button(onClick = self.soundManagerExampleModel::resumeControlledSound,
                               modifier = Modifier
                                   .padding(top = NEXT, bottom = 0.dp, start = NEAR, end = 0.dp)
                                   .wrapContentSize()
                              ) {
                            Image(painter = painterResource(id = android.R.drawable.ic_media_ff),
                                  contentDescription = "Resume",
                                  modifier = Modifier.size(32.dp))
                        }

                        Button(onClick = self.soundManagerExampleModel::stopControlledSound,
                               modifier = Modifier
                                   .padding(top = NEXT, bottom = 0.dp, start = NEAR, end = 0.dp)
                                   .wrapContentSize()
                              ) {
                            Image(painter = painterResource(id = android.R.drawable.ic_delete),
                                  contentDescription = "Resume",
                                  modifier = Modifier.size(32.dp))
                        }
                    }

                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()) {
                        Text(text = stringResource(id = R.string.soundControlledLeft),
                             modifier = Modifier
                                 .padding(top = NEXT, bottom = 0.dp, start = FAR, end = 0.dp)
                                 .wrapContentSize()
                            )

                        Slider(value = self.soundManagerExampleModel.controlledSoundLeftVolume.value,
                               onValueChange = { value ->
                                   self.soundManagerExampleModel.leftControlledSoundVolume(value)
                               },
                               modifier = Modifier
                                   .padding(top = NEXT,
                                            bottom = 0.dp,
                                            start = NEAR,
                                            end = START_END)
                                   .fillMaxWidth()
                                   .wrapContentHeight()
                              )
                    }


                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()) {
                        Text(text = stringResource(id = R.string.soundControlledRight),
                             modifier = Modifier
                                 .padding(top = NEXT, bottom = 0.dp, start = FAR, end = 0.dp)
                                 .wrapContentSize()
                            )

                        Slider(value = self.soundManagerExampleModel.controlledSoundRightVolume.value,
                               onValueChange = { value ->
                                   self.soundManagerExampleModel.rightControlledSoundVolume(value)
                               },
                               modifier = Modifier
                                   .padding(top = NEXT,
                                            bottom = 0.dp,
                                            start = NEAR,
                                            end = START_END)
                                   .fillMaxWidth()
                                   .wrapContentHeight()
                              )
                    }
                }
            }
        })

    }
}