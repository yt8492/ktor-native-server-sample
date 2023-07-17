package com.yt8492.ktornative.repository

import com.yt8492.ktornative.TodoQueries
import com.yt8492.ktornative.Todos
import com.yt8492.ktornative.model.Todo
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.uuid.UUID
import kotlinx.uuid.generateUUID

class TodoRepository(
    private val todoQueries: TodoQueries,
) {
    fun findAll(): List<Todo> {
        return todoQueries.selectAll()
            .executeAsList()
            .map {
                toModel(it)
            }
    }

    fun find(id: String): Todo? {
        return todoQueries.select(id)
            .executeAsOneOrNull()
            ?.let {
                toModel(it)
            }
    }

    fun create(title: String, content: String): Todo {
        val now = Clock.System.now()
        val id = UUID.generateUUID()
        todoQueries.insert(
            id = id.toString(false),
            title = title,
            content = content,
            create_at = now.toString(),
            update_at = now.toString(),
        )
        return Todo(
            id = id.toString(false),
            title = title,
            content = content,
            createAt = now,
            updateAt = now,
        )
    }

    fun update(todo: Todo): Todo {
        val now = Clock.System.now()
        todoQueries.update(
            id = todo.id,
            title = todo.title,
            content = todo.content,
            update_at = now.toString(),
        )
        return todo.copy(
            updateAt = now,
        )
    }

    fun delete(id: String) {
        todoQueries.delete(id)
    }

    private fun toModel(todos: Todos): Todo {
        return Todo(
            id = todos.id,
            title = todos.title,
            content = todos.content,
            createAt = Instant.parse(todos.create_at),
            updateAt = Instant.parse(todos.update_at),
        )
    }
}
