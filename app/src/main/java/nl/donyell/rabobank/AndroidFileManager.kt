package nl.donyell.rabobank

import android.content.Context
import nl.donyell.data.FileManager
import java.io.*
import javax.inject.Inject

class AndroidFileManager @Inject constructor(
    private val applicationContext: Context
): FileManager {

    // https://nphau.medium.com/android-downloadfile-from-server-using-retrofit-part-1-817ae8caa169
    override fun save(byteStream: InputStream, fileName: String): Boolean {
        return try {
            val file = File(applicationContext.filesDir, fileName)
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null

            try {
                val fileReader = ByteArray(4096)
                var fileSizeDownloaded: Long = 0
                inputStream = byteStream
                outputStream = FileOutputStream(file)
                while (true) {
                    val read = inputStream.read(fileReader)
                    if (read == -1) {
                        break
                    }

                    outputStream.write(fileReader, 0, read)
                    fileSizeDownloaded += read.toLong()
                }
                outputStream.flush()
                true
            } catch (e: IOException) {
                throw e
            } finally {
                inputStream?.close()
                outputStream?.close()
            }
        } catch (e: IOException) {
            throw e
        }
    }

    override fun get(fileName: String): File {
        return File(applicationContext.filesDir, fileName)
    }
}