package com.example.usersservice.factory.integration

import com.example.usersservice.user.UserRepository
import factory.UserFactory
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ContextConfiguration

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserFactoryTest() {

    @Autowired
    lateinit var userRepository: UserRepository

    private lateinit var userFactory: UserFactory

    @BeforeEach
    fun setUp() {
        userFactory = UserFactory(userRepository)
    }

    @Test
    fun `WHEN createOne is called THEN a user is returned`() {
        runBlocking {
            // When
            val userDto = userFactory.createOne()

            // Then
            assertNotNull(userDto.id)
            assertNotNull(userDto.username)
            assertNotNull(userDto.email)
            // password is @JsonIgnore() and therefore should not be returned
            assertNull(userDto.password)
        }
    }

    @Test
    fun `GIVEN a quantity WHEN createMany is called THEN a list of users is returned`() {
        runBlocking {
            // Given
            val quantity = 3

            // When
            val users = userFactory.createMany(quantity)

            // Then
            assertEquals(quantity, users.size)

            users.forEachIndexed { index, userDto ->
                assertNotNull(userDto.id)
                assertNotNull(userDto.username)
                assertNotNull(userDto.email)
                assertNull(userDto.password)
            }
        }
    }
}