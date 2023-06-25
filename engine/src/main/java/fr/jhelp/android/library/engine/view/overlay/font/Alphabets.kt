package fr.jhelp.android.library.engine.view.overlay.font

import fr.jhelp.android.library.engine.R

/**
 * Default alphabet
 */
val DEFAULT_ALPHABET: Alphabet =
    Alphabet(16, 16,
             R.drawable.alphabet_green,
             R.drawable.alphabet_red,
             charArrayOf(
                 // First line
                 ' ', '!', COMBINING_DIAERESIS, '#', '$', '%', '&', COMBINING_ACUTE_ACCENT, '(',
                 ')', '*', '+', ',', '-', '.', '/',
                 // Second line
                 '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';', '<', '=', '>', '?',
                 // Third line
                 ' ', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
                 // Fourth line
                 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[', '\\', ']',
                 COMBINING_CIRCUMFLEX_ACCENT, '_',
                 // Fifth line
                 COMBINING_GRAVE_ACCENT, 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
                 'l', 'm', 'n', 'o',
                 // Sixth line
                 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '{', '|', '}',
                 COMBINING_TILDE, '\n',
                 // Seventh line
                 '@'
                        ),
             16, -16)