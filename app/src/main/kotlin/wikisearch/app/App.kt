/*
 * This Kotlin source file was generated by the Gradle 'init' task.
 */
package wikisearch.app

import wikisearch.document.Document
import wikisearch.utilities.Index
import wikisearch.utilities.Loader.Companion.loadDocuments
import kotlin.system.measureTimeMillis

object App {
    fun getDocs(): List<Document> {
        val source = "C:\\data\\enwiki-latest-abstract1.xml.gz"
        val target = "C:\\data\\enwiki-latest-abstract1.xml"
        // Unzip the Wikipedia dump from source and store xml file at target
        val documents = loadDocuments(source, target)
        println("Loaded ${documents.size} documents from $source")
        return documents
    }
}

fun main() {
    // Load documents from Wikipedia dump
    val documents = App.getDocs()

    // Instantiate Index data structure and add sections of documents
    val index = Index()
    val indexingTime = measureTimeMillis {
        for (document in documents) {
            index.addDocument(document)
        }
    }
    println("Indexing took $indexingTime ms")

    // Instantiate sample query term and query on index
    val query = "machine learning"
    val searchTime = measureTimeMillis {
        val results = index.search(query)
        println("Found ${results.size} results for query: $query")
        results.forEach { println(it) }
    }
    println("Search took $searchTime ms")
}

