package ru.puppeteers.socialnetwork.service.cacheable

import org.apache.commons.collections4.CollectionUtils
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Service
import org.springframework.context.event.EventListener
import ru.puppeteers.socialnetwork.dao.PostDao
import ru.puppeteers.socialnetwork.entity.PostEntity
import ru.puppeteers.socialnetwork.service.PostService
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Service
class FeedCacheableProvider(
    val redisTemplate: RedisTemplate<String, PostEntity>,
    val postDao: PostDao
) {
    companion object {
        const val KEY = "FEED"
    }

    fun updateCache(userId: UUID): List<PostEntity> {
        val feed = postDao.getUserFeedForCache(userId)
        if (CollectionUtils.isNotEmpty(feed)) {
            val compositeKey = createCompositeKey(userId)
            redisTemplate.delete(compositeKey)
            redisTemplate.opsForList().rightPushAll(compositeKey, feed)
        }

        return feed
    }

    fun readFromCache(userId: UUID): List<PostEntity>? {
        val feed = redisTemplate.opsForList().range(createCompositeKey(userId), 0, -1)
        if (CollectionUtils.isEmpty(feed)) {
            return updateCache(userId)
        }

        return feed
    }

    private fun createCompositeKey(userId: UUID): String {
        return KEY + userId
    }
}