package ru.puppeteers.socialnetwork.service

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import java.util.function.Function
import javax.crypto.SecretKey

@Service
class JwtService {

    val secretKey: String = "8d8d6ac160ef830dac3fa7538e71a4e2784516c0afa82901fa022f1ea3f6f5ae"


    fun extractUsername(token: String): String {
        return extractClaim(token, Claims::getSubject)
    }

    fun <T> extractClaim(token: String, claimsResolver: Function<Claims, T>): T {
        val claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    fun generateToken(userDetails: UserDetails): String {
        return generateToken(mapOf(), userDetails)
    }

    fun generateToken(extraClaims: Map<String, Any>, userDetails: UserDetails): String {
        val today = LocalDate.now().atStartOfDay(ZoneId.systemDefault())
        return Jwts
            .builder()
            .claims(extraClaims)
            .subject(userDetails.username)
            .issuedAt(Date.from(today.toInstant()))
            .expiration(Date.from(today.plusDays(1).toInstant()))
            .signWith(getSecretKey())
            .compact()
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractTokenExpiration(token).before(Date())
    }

    private fun extractTokenExpiration(token: String): Date {
        return extractClaim(token, Claims::getExpiration)
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts
            .parser()
            .verifyWith(getSecretKey())
            .build()
            .parseSignedClaims(token)
            .payload
    }

    private fun getSecretKey(): SecretKey {
        val bytes = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(bytes)
    }
}