package com.example.postsservice.image

import io.r2dbc.spi.Row
import org.springframework.stereotype.Component
import java.util.function.BiFunction

@Component
class ImageMapper: BiFunction<Row, Any, ImageDto> {
    override fun apply(row: Row, o: Any): ImageDto {
        return ImageEntity(
                row.get("id") as Long,
                row.get("postId") as Long,
                row.get("url") as String
        ).toDto()
    }
}