package wikisearch.utilities

import wikisearch.document.Document
import java.io.*
import java.util.zip.GZIPInputStream

class Loader {
    companion object {
        private fun unzip(source: String, target: String): String {
            val gis = GZIPInputStream(FileInputStream(source))
            val fos = FileOutputStream(target)
            val buffer = ByteArray(1024)
            var len: Int
            while (gis.read(buffer).also { len = it } > 0) {
                fos.write(buffer, 0, len)
            }
            fos.close()
            gis.close()
            return target
        }

        fun loadDocuments(source: String, target: String): List<Document> {
            val documents : MutableList<Document> = mutableListOf()
            val file = File(source)
            val parser = XmlParser()
            if (file.isDirectory) {
                for (child : File in file.listFiles()!!) {
                    if (child.name.endsWith(".gz")) {
                        val target = unzip(child.absolutePath, target)
                        documents.addAll(parser.parse(File(target).inputStream()))
                    }
                }
            } else {
                val target = unzip(source, target)
                documents.addAll(parser.parse(File(target).inputStream()))
            }

            return documents
        }
    }
}