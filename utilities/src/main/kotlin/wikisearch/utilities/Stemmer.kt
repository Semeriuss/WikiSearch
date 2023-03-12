package wikisearch.utilities

import com.londogard.smile.SmileSingleton
import java.util.*

class Stemmer {
    companion object {
        val stopWords = setOf(
            "the", "be", "to", "and",
            "a", "in", "that", "have",
            "i", "it", "for", "not",
            "on", "with", "he", "as",
            "you", "do", "at", "this",
            "but", "his", "by", "from",
            "they", "we", "say", "her",
            "she", "or", "an", "will",
            "my", "one", "all", "would",
            "there", "their", "what", "so",
            "up", "out", "if", "about",
            "who", "get", "which", "go",
            "me", "when", "make", "can",
            "wikipedia"
        )

        fun tokenize(text: String): List<String> {
            val tokens = mutableListOf<String>()
            var token = ""
            text.forEach { char: Char ->
                if (char.isLetterOrDigit()) {
                    token += char
                } else {
                    if (token.isNotEmpty()) {
                        tokens.add(token)
                        token = ""
                    }
                }
            }
            if (token.isNotEmpty()) {
                tokens.add(token)
            }
            return tokens
        }

        fun lowercaseFilter(tokens: List<String>): List<String> {
            return tokens.map { it.lowercase(Locale.getDefault()) }
        }

        fun stemFilter(tokens: List<String>): List<String> {
            return tokens.map { SmileSingleton.porter(it) }
        }

        fun punctuationFilter(tokens: List<String>): List<String> {
            return tokens.filter { it -> it.all { it.isLetterOrDigit() } }
        }

        fun stopWordFilter(tokens: List<String>): List<String> {
            return tokens.filter { it !in stopWords }
        }
    }
}