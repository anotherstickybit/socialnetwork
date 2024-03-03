package ru.puppeteers.socialnetwork.consumer

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service
import ru.puppeteers.socialnetwork.dao.FriendDao
import ru.puppeteers.socialnetwork.dao.UserDao
import ru.puppeteers.socialnetwork.entity.PostEntity
import ru.puppeteers.socialnetwork.service.cacheable.FeedCacheableProvider

@Service
class PostsConsumer(
    val userDao: UserDao,
    val friendDao: FriendDao,
    val feedCacheableProvider: FeedCacheableProvider
) {

    @RabbitListener(queues = [ "\${rabbit.queue.name}" ])
    fun consume(postEntity: PostEntity) {
        val user = userDao.getUserById(postEntity.userId)
        if (user != null && user.isCelebrity) {
            friendDao.getUserFriends(user.id).forEach {
                feedCacheableProvider.updateCache(it.id)
            }
        }
    }
}