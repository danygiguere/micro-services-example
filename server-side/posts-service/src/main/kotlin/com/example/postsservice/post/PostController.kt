package com.example.postsservice.post

import com.example.postsservice.post.dto.PostDto
import com.example.postsservice.post.dto.PostWithImagesDto
import jakarta.validation.Valid
import kotlinx.coroutines.flow.Flow
import org.springframework.core.env.Environment
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
class PostController(private val postService: PostService,
                     private val env: Environment? = null) {

    @GetMapping("/status/check")
    fun statusCheck(): String {
        return "Posts api is working on port " + env?.getProperty("local.server.port")
    }

    @GetMapping("/posts")
    suspend fun getAll(): ResponseEntity<Flow<PostDto>?> {
        val response = postService.findAll()
        return if (response != null) ResponseEntity.ok(response)
        else ResponseEntity.notFound().build()
    }

    @GetMapping("/posts/{id}")
    suspend fun getById(@PathVariable id: Long): ResponseEntity<PostDto> {
        val response = postService.findById(id)
        return if (response != null) ResponseEntity.ok(response)
        else ResponseEntity.notFound().build()
    }

    @GetMapping("/posts/{id}/with-images")
    suspend fun getByIdWithImages(@PathVariable id: Long): ResponseEntity<PostWithImagesDto> {
        val response = postService.findByIdWithImages(id)
        return if (response != null) ResponseEntity.ok(response)
        else ResponseEntity.notFound().build()
    }

    @PostMapping("/posts")
    suspend fun create(@Valid @RequestBody postDto: PostDto): ResponseEntity<PostDto> {
        val userId: Long = 1; // for demo only. The userId needs to be taken from the auth user
        val response = postService.create(userId, postDto)
        return if (response != null) ResponseEntity.ok(response)
        else ResponseEntity.notFound().build()
    }

    @PutMapping("/posts/{id}")
    suspend fun update(@Valid @PathVariable id: Long, @RequestBody postDto: PostDto): ResponseEntity<Long> {
        val response = postService.update(id, postDto)
        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/posts/{id}")
    suspend fun delete(@PathVariable id: Long): ResponseEntity<Long> {
        val response = postService.delete(id)
        return ResponseEntity.ok(response)
    }
}