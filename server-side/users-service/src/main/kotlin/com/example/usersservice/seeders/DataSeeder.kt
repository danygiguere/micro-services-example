package com.example.usersservice.seeders

import com.example.usersservice.configuration.FlywayConfiguration
import com.example.usersservice.user.UserRepository
import factory.UserFactory
import kotlinx.coroutines.runBlocking
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class DataSeeder(val flywayConfiguration: FlywayConfiguration,
                 val userRepository: UserRepository
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
        UserFactory(userRepository).createMany(9)
    }

}