package factory

import com.example.usersservice.user.UserRepository
import com.example.usersservice.user.dto.UserDto
import io.bloco.faker.Faker

class UserFactory(val userRepository: UserRepository) {

    val faker = Faker(locale = "en-CA")

    fun makeOne(userId: Long? = null): UserDto {
        val name = faker.name.firstName().lowercase()+"."+faker.name.lastName().lowercase()
        return UserDto(userId ?: 1, name, "$name@test.com", "secret")
    }

    fun makeMany(quantities: Int): List<UserDto> {
        return (0 until quantities).map { makeOne(it + 1L ) }
    }

    suspend fun createOne(): UserDto {
        return userRepository.register(makeOne())
    }

    suspend fun createMany(quantities: Int): List<UserDto> {
        return (0 until quantities).map { userRepository.register(makeOne()) }
    }
}