package ru.puppeteers.socialnetwork.service

import org.springframework.stereotype.Service
import ru.puppeteers.socialnetwork.api.dto.*
import ru.puppeteers.socialnetwork.dao.FriendDao
import ru.puppeteers.socialnetwork.dao.PostDao
import ru.puppeteers.socialnetwork.entity.PostEntity
import ru.puppeteers.socialnetwork.publisher.PostsProducer
import ru.puppeteers.socialnetwork.service.cacheable.FeedCacheableProvider
import java.util.*

@Service
class PostService(
    val postDao: PostDao,
    val postsProducer: PostsProducer,
    val feedCacheableProvider: FeedCacheableProvider
) {

    fun addPost(postAddRequest: PostAddRequest): BaseResponse {
        val post = postDao.addPost(postAddRequest)
        postsProducer.sendPostCreated(post)
        return BaseResponse(true, null)
    }

    fun updatePost(postUpdateRequest: PostUpdateRequest): BaseResponse {
        postDao.updatePost(postUpdateRequest)
        return BaseResponse(true, null)
    }

    fun removePost(postRemoveRequest: PostRemoveRequest): BaseResponse {
        postDao.removePost(postRemoveRequest)
        return BaseResponse(true, null)
    }

    fun getFeed(id: UUID, getFeedRequest: GetFeedRequest): List<PostEntity> {
        if (getFeedRequest.forceUpdate) {
            return feedCacheableProvider.updateCache(id)
        }

        return feedCacheableProvider.readFromCache(id) ?: emptyList()
    }
}