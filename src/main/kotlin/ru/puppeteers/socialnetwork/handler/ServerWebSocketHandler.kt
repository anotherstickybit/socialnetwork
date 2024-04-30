package ru.puppeteers.socialnetwork.handler

import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import java.util.concurrent.CopyOnWriteArraySet

class ServerWebSocketHandler(
    val sessions: CopyOnWriteArraySet<WebSocketSession> = CopyOnWriteArraySet()
) : TextWebSocketHandler() {

    override fun afterConnectionEstablished(session: WebSocketSession) {
        sessions.add(session)
        val textMessage = TextMessage("Session established")
        session.sendMessage(textMessage)
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        sessions.remove(session)
    }

    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
//            val value: Map = Gson().fromJson(message.payload, Map::class.java)
            session.sendMessage(message)
    }
}