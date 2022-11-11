package nl.donyell.data

import java.io.File
import java.io.InputStream

interface FileManager {
    fun save(byteStream: InputStream, fileName: String): Boolean
    fun get(fileName: String): File
}