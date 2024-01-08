package ru.puppeteers.socialnetwork.service

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.puppeteers.socialnetwork.api.dto.*
import ru.puppeteers.socialnetwork.dao.UserDao
import ru.puppeteers.socialnetwork.exception.UserDoesNotExistsException

@Service
class UserService(
    val userDao: UserDao,
    val jwtService: JwtService,
    val authenticationManager: AuthenticationManager,
    val passwordEncoder: PasswordEncoder
) {

    fun register(request: RegisterRequest): RegisterResponse {
        return userDao.register(request.copy(password = passwordEncoder.encode(request.password)))
    }

    fun login(request: LoginRequest): LoginResponse {
        authenticationManager.authenticate(UsernamePasswordAuthenticationToken(
            request.email,
            request.password
        ))

        val user = userDao.getUserByEmail(request.email)
        val token = jwtService.generateToken(user!!)

        return LoginResponse(token)
    }

    fun getUserInfo(request: UserInfoRequest): UserInfoResponse {
        val user = userDao.getUserById(request.id)
            ?: throw UserDoesNotExistsException("User with id ${request.id} does not exist.")

        return UserInfoResponse(
            user.username,
            user.firstName,
            user.secondName,
            user.birthDate,
            user.city,
            user.interests
        )
    }
}