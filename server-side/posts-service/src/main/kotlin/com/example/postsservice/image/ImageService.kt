package com.example.postsservice.image

import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service

@Service
class ImageService(val repository: ImageRepository) {

    suspend fun findAll(): Flow<ImageDto>? =
            repository.findAll()

    suspend fun findById(id: Long): ImageDto? =
            repository.findById(id)

    suspend fun create(imageDto: ImageDto): ImageDto? =
            repository.create(imageDto)

    suspend fun update(id: Long, imageDto: ImageDto): Long =
            repository.update(id, imageDto)

    suspend fun delete(id: Long): Long =
            repository.delete(id)
}