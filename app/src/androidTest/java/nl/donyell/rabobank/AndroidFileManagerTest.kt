package nl.donyell.rabobank

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
class AndroidFileManagerTest {

    private lateinit var applicationContext: Context
    private lateinit var androidFileManager: AndroidFileManager
    private lateinit var saveTestFile: File
    private lateinit var getTestFile: File

    @Before
    fun setup() {
        applicationContext = ApplicationProvider.getApplicationContext()
        androidFileManager = AndroidFileManager(applicationContext)
        saveTestFile = File(applicationContext.filesDir, SAVE_TEST_FILE_NAME)
        getTestFile = File(applicationContext.filesDir, GET_TEST_FILE_NAME)
    }

    @Test
    fun whenAFileIsSaved_itShouldExist() {
        // Given
        val fakeText = "My fake text"
        val inputStream = fakeText.byteInputStream()

        // Then
        assert(!saveTestFile.exists())

        // When
        androidFileManager.save(inputStream, SAVE_TEST_FILE_NAME)

        // Then
        assert(saveTestFile.exists())
    }

    @Test
    fun whenAFileIsRequested_itShouldBeReturned() {
        // Given
        val fakeText = "My fake text"
        val inputStream = fakeText.byteInputStream()

        // When
        androidFileManager.save(inputStream, GET_TEST_FILE_NAME)
        val result = androidFileManager.get(GET_TEST_FILE_NAME)

        // Then
        assert(result.isFile)
    }

    @After
    fun tearDown() {
        deleteFile(saveTestFile)
        deleteFile(getTestFile)
    }

    private fun deleteFile(file: File){
        if (file.exists()) {
            file.delete()
        }
    }

    companion object {
        private const val SAVE_TEST_FILE_NAME = "save_test_file.txt"
        private const val GET_TEST_FILE_NAME = "get_test_file.txt"
    }
}