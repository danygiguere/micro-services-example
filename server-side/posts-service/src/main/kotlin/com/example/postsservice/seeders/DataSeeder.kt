package com.example.postsservice.seeders

import com.example.postsservice.configuration.FlywayConfiguration
import com.example.postsservice.image.ImageRepository
import com.example.postsservice.post.PostRepository
import factory.ImageFactory
import factory.PostFactory
import kotlinx.coroutines.runBlocking
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class DataSeeder(val flywayConfiguration: FlywayConfiguration,
    val postRepository: PostRepository,
    val imageRepository: ImageRepository
    ) : ApplicationRunner {

    override fun run(args: ApplicationArguments) {
        runBlocking {
            // On startup, clean the db, migrate it and seed it with data
            recreateAndSeedDb()
        }
    }

    suspend fun recreateAndSeedDb() {
        flywayConfiguration.flyway().clean()
        flywayConfiguration.flyway().migrate()
        seed()
    }

    suspend fun seed() {

        val posts = PostFactory(postRepository).createMany(2, 1)
        for (singlePost in posts) {
            singlePost.id?.let { postId ->
                ImageFactory(imageRepository).createMany(2, postId)
            }
        }

    }

}