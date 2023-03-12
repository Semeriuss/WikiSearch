package wikisearch.utilities

class Analyzer {
    companion object {
        fun analyze(text: String): List<String> {
            val tokens = Stemmer.tokenize(text)
            val lowercase = Stemmer.lowercaseFilter(tokens)
            val punctuationFiltered = Stemmer.punctuationFilter(lowercase)
            val stopWordFiltered = Stemmer.stopWordFilter(punctuationFiltered)
            return Stemmer.stemFilter(stopWordFiltered)
        }
    }
}