package com.yt8492.ktornative

import com.yt8492.ktornative.model.CreateTodoRequest
import com.yt8492.ktornative.model.UpdateTodoRequest
import com.yt8492.ktornative.repository.TodoRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.module(
    todoRepository: TodoRepository,
) {
    routing {
        post("/todos") {
            val request = call.receive<CreateTodoRequest>()
            val todo = todoRepository.create(
                title = request.title,
                content = request.content,
            )
            call.respond(HttpStatusCode.Created, todo)
        }
        get("/todos") {
            val todos = todoRepository.findAll()
            call.respond(HttpStatusCode.OK, todos)
        }
        get("/todos/{id}") {
            val id = call.parameters["id"]
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@get
            }
            val todo = todoRepository.find(id)
            if (todo == null) {
                call.respond(HttpStatusCode.NotFound)
                return@get
            }
            call.respond(HttpStatusCode.OK, todo)
        }
        patch("/todos/{id}") {
            val id = call.parameters["id"]
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@patch
            }
            val request = call.receive<UpdateTodoRequest>()
            val todo = todoRepository.find(id)
            if (todo == null) {
                call.respond(HttpStatusCode.NotFound)
                return@patch
            }
            val updated = todoRepository.update(
                todo.copy(
                    title = request.title ?: todo.title,
                    content = request.content ?: todo.content,
                )
            )
            call.respond(HttpStatusCode.OK, updated)
        }
        delete("/todos/{id}") {
            val id = call.parameters["id"]
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }
            val todo = todoRepository.find(id)
            if (todo == null) {
                call.respond(HttpStatusCode.NotFound)
                return@delete
            }
            todoRepository.delete(id)
            call.respond(HttpStatusCode.NoContent)
        }
    }
}
