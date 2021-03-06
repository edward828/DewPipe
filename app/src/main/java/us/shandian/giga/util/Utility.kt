package us.shandian.giga.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.widget.Toast

import org.schabi.newpipe.R

import java.io.BufferedOutputStream
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.experimental.and

object Utility {

    enum class FileType {
        VIDEO,
        MUSIC,
        UNKNOWN
    }

    fun formatBytes(bytes: Long): String {
        return when {
            bytes < 1024 -> String.format("%d B", bytes)
            bytes < 1024 * 1024 -> String.format("%.2f kB", bytes.toFloat() / 1024)
            bytes < 1024 * 1024 * 1024 -> String.format("%.2f MB", bytes.toFloat() / 1024f / 1024f)
            else -> String.format("%.2f GB", bytes.toFloat() / 1024f / 1024f / 1024f)
        }
    }

    fun formatSpeed(speed: Float): String {
        return when {
            speed < 1024 -> String.format("%.2f B/s", speed)
            speed < 1024 * 1024 -> String.format("%.2f kB/s", speed / 1024)
            speed < 1024 * 1024 * 1024 -> String.format("%.2f MB/s", speed / 1024f / 1024f)
            else -> String.format("%.2f GB/s", speed / 1024f / 1024f / 1024f)
        }
    }

    fun writeToFile(fileName: String, serializable: Serializable) {
        var objectOutputStream: ObjectOutputStream? = null

        try {
            objectOutputStream = ObjectOutputStream(BufferedOutputStream(FileOutputStream(fileName)))
            objectOutputStream.writeObject(serializable)
        } catch (e: Exception) {
            //nothing to do
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close()
                } catch (e: Exception) {
                    //nothing to do
                }

            }
        }
    }

    fun <T> readFromFile(file: String): T? {
        var `object`: T? = null
        var objectInputStream: ObjectInputStream? = null

        try {
            objectInputStream = ObjectInputStream(FileInputStream(file))
            `object` = objectInputStream.readObject() as T
        } catch (e: Exception) {
            //nothing to do
        }

        if (objectInputStream != null) {
            try {
                objectInputStream.close()
            } catch (e: Exception) {
                //nothing to do
            }

        }

        return `object`
    }

    fun getFileExt(url: String): String? {
        var url = url
        var index: Int = url.indexOf("?")
        if (index > -1) {
            url = url.substring(0, index)
        }

        index = url.lastIndexOf(".")
        return if (index == -1) {
            null
        } else {
            var ext = url.substring(index)
            index = ext.indexOf("%")
            if ( index > -1) {
                ext = ext.substring(0, index)
            }
            index = ext.indexOf("/")
            if (index > -1) {
                ext = ext.substring(0, index)
            }
            ext.toLowerCase()
        }
    }

    fun getFileType(file: String): FileType {
        return if (file.endsWith(".mp3") || file.endsWith(".wav") || file.endsWith(".flac") || file.endsWith(".m4a")) {
            FileType.MUSIC
        } else if (file.endsWith(".mp4") || file.endsWith(".mpeg") || file.endsWith(".rm") || file.endsWith(".rmvb")
                || file.endsWith(".flv") || file.endsWith(".webp") || file.endsWith(".webm")) {
            FileType.VIDEO
        } else {
            FileType.UNKNOWN
        }
    }

    @ColorRes
    fun getBackgroundForFileType(type: FileType): Int {
        return when (type) {
            Utility.FileType.MUSIC -> R.color.audio_left_to_load_color
            Utility.FileType.VIDEO -> R.color.video_left_to_load_color
            else -> R.color.gray
        }
    }

    @ColorRes
    fun getForegroundForFileType(type: FileType): Int {
        return when (type) {
            Utility.FileType.MUSIC -> R.color.audio_already_load_color
            Utility.FileType.VIDEO -> R.color.video_already_load_color
            else -> R.color.gray
        }
    }

    @DrawableRes
    fun getIconForFileType(type: FileType): Int {
        return when (type) {
            Utility.FileType.MUSIC -> R.drawable.music
            Utility.FileType.VIDEO -> R.drawable.video
            else -> R.drawable.video
        }
    }

    fun copyToClipboard(context: Context, str: String) {
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cm.primaryClip = ClipData.newPlainText("text", str)
        Toast.makeText(context, R.string.msg_copied, Toast.LENGTH_SHORT).show()
    }

    fun checksum(path: String, algorithm: String): String {
        var md: MessageDigest? = null

        try {
            md = MessageDigest.getInstance(algorithm)
        } catch (e: NoSuchAlgorithmException) {
            throw RuntimeException(e)
        }

        var i: FileInputStream? = null

        try {
            i = FileInputStream(path)
        } catch (e: FileNotFoundException) {
            throw RuntimeException(e)
        }

        val buf = ByteArray(1024)
        var len = 0

        try {
            len = i.read(buf)
            while (len != -1) {
                md!!.update(buf, 0, len)
            }
        } catch (ignored: IOException) {

        }

        val digest = md!!.digest()

        // HEX
        val sb = StringBuilder()
        for (b in digest) {
            sb.append(Integer.toString((b and 0xff.toByte()) + 0x100, 16).substring(1))
        }

        return sb.toString()

    }
}
