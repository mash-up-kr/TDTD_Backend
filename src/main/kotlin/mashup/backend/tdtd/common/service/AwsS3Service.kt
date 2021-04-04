package mashup.backend.tdtd.common.service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.CannedAccessControlList
import com.amazonaws.services.s3.model.PutObjectRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.sql.Timestamp
import java.time.LocalDateTime

@Service
class AwsS3Service(
    private val awsS3Client: AmazonS3,

    @Value("\${cloud.aws.s3.bucket}")
    private val bucket: String,

    @Value("\${cloud.aws.s3.voice-file-directory}")
    private val voiceFileDirectory: String,
) {
    fun uploadVoiceFile(voiceFile: MultipartFile): String {
        val fileName: String = getVoiceFileNameWithTimestamp(voiceFile.originalFilename!!)
        val putObjectRequest: PutObjectRequest = PutObjectRequest(
            bucket,
            fileName,
            voiceFile.inputStream,
            null
        ).withCannedAcl(CannedAccessControlList.PublicRead)

        awsS3Client.putObject(putObjectRequest)

        return awsS3Client.getUrl(bucket, fileName).toString()
    }

    private fun getVoiceFileNameWithTimestamp(originalFileName: String): String {
        val stringBuffer = StringBuffer()
        val timeStamp: Timestamp = Timestamp.valueOf(LocalDateTime.now())
        stringBuffer.append(voiceFileDirectory)
            .append("/")
            .append(timeStamp)
            .append("-")
            .append(originalFileName)
        return stringBuffer.toString()
    }
}