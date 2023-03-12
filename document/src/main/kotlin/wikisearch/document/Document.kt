package wikisearch.document

data class Document(
    val id: Int,
    val title: String?,
    val brief: String?,
    val url: String?,
    var frequencyMap: Map<String, Int> = mapOf()
) {
    fun termFrequency(term: String) : Int {
        return frequencyMap[term] ?: 0
    }
}