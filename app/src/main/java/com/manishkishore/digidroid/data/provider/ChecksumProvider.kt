package com.manishkishore.digidroid.data.provider

import android.content.Context
import android.net.Uri
import com.manishkishore.digidroid.util.FileSizeFormatter
import java.security.MessageDigest

object ChecksumProvider {
    data class ChecksumResult(
        val fileName: String,
        val fileSize: String,
        val sha256: String
    )

    fun sha256(context: Context, uri: Uri): ChecksumResult {
        val resolver = context.contentResolver
        val digest = MessageDigest.getInstance("SHA-256")
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
        var total = 0L

        resolver.openInputStream(uri)?.use { input ->
            while (true) {
                val read = input.read(buffer)
                if (read <= 0) break
                digest.update(buffer, 0, read)
                total += read
            }
        }

        val hash = digest.digest().joinToString("") { "%02x".format(it) }
        return ChecksumResult(
            fileName = uri.lastPathSegment?.substringAfterLast('/') ?: "Selected file",
            fileSize = FileSizeFormatter.format(total),
            sha256 = hash
        )
    }
}
