package wikisearch.document

import wikisearch.utilities.Analyzer.Companion.analyze
import kotlin.math.log10

class Index {
    private var index = mutableMapOf<String, MutableSet<Int>>()
    private var documents = mutableMapOf<Int, Document>()

    fun addDocument(document: Document) {

        if (!documents.containsKey(document.id)) {
            documents[document.id] = document
            val freqMap = termFrequency(listOfNotNull(document.title, document.brief))
            document.frequencyMap = freqMap
        }
        val tokens = listOfNotNull(document.title, document.brief).flatMap { analyze(it) }
        for (token in tokens) {
            if (!index.containsKey(token)) {
                index[token] = mutableSetOf()
            }
            index[token]?.add(document.id)
        }
    }


    fun search(query: String, searchType: String = "AND", rank: Boolean = true): List<Document> {
        val results = search(query, searchType)
        return if (rank) {
            rank(analyze(query), results).map { it.first }
        } else {
            results
        }
    }
    private fun search(query: String, searchType: String = "AND"): List<Document> {
        if (searchType !in listOf("AND", "OR")) {
            return listOf()
        }
        val results = search(query)
        return if (searchType == "AND") {
            results.reduce { acc, set -> acc.intersect(set).toMutableSet() }.map { documents[it]!! }
        } else {
            results.reduce { acc, set -> acc.union(set).toMutableSet() }.map { documents[it]!! }
        }
    }

    private fun search(query: String): List<Set<Int>> {
        val tokens = analyze(query)
        val results = mutableListOf<Set<Int>>()
        for (token in tokens) {
            if (index.containsKey(token)) {
                results.add(index[token]!!)
            }
        }
        return results
    }

    private fun rank(analyzedQuery: List<String>, documents: List<Document>) : List<Pair<Document, Int>> {
        val rankedDocuments = mutableListOf<Pair<Document, Int>>()
        for (document in documents) {
            var score = 0
            for (token in analyzedQuery) {
                val tf = document.termFrequency(token)
                val idf = inverseDocumentFrequency(token)
                score += (tf * idf).toInt()
            }
            rankedDocuments.add(Pair(document, score))
        }
        return rankedDocuments.sortedByDescending { it.second }
    }

    private fun documentFrequency(term: String) : Int {
        return index[term]?.size ?: 0
    }

    private fun inverseDocumentFrequency(term: String) : Double {
        val n = documents.size
        val df = documentFrequency(term)
        return log10(n.toDouble() / df)
    }

    private fun termFrequency(fulltext : List<String>) : Map<String, Int>{
        return fulltext.flatMap { analyze(it) }.groupBy { it }.mapValues { it.value.size }
    }
}