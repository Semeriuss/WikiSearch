package wikisearch.utilities

import java.io.IOException

import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import wikisearch.document.Document
import java.io.InputStream

class XmlParser {
    private val ns: String? = null
    private var id = 1

    @Throws(XmlPullParserException::class, IOException::class)
    fun parse(inputStream: InputStream): List<Document> {
        return inputStream.use { input ->
            val parser: XmlPullParser = XmlPullParserFactory.newInstance().newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(input, null)
            parser.nextTag()
            return@use readFeed(parser)
        }
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readFeed(parser: XmlPullParser): List<Document> {
        val documents = mutableListOf<Document>()
        parser.require(XmlPullParser.START_TAG, ns, "feed")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "doc" -> documents.add(readDoc(parser))
                else -> skip(parser)
            }
        }
        return documents
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readDoc(parser: XmlPullParser): Document {

        parser.require(XmlPullParser.START_TAG, ns, "doc")
        var title: String? = null
        var brief: String? = null
        var url: String? = null
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "title" -> title = readTitle(parser)
                "abstract" -> brief = readBrief(parser)
                "url" -> url = readUrl(parser)
                else -> skip(parser)
            }
        }
        return Document(id++, title, brief, url)
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readTitle(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "title")
        val title = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "title")
        return title
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readBrief(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "abstract")
        val brief = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "abstract")
        return brief
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readUrl(parser: XmlPullParser): String {
        parser.require(XmlPullParser.START_TAG, ns, "url")
        val url = readText(parser)
        parser.require(XmlPullParser.END_TAG, ns, "url")
        return url
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun readText(parser: XmlPullParser): String {
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        return result
    }

    @Throws(XmlPullParserException::class, IOException::class)
    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }
}