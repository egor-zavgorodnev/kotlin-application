package com.epam.crud.endpoints

import com.epam.crud.dto.AuthorDto
import com.epam.crud.exceptions.AuthorOperationException
import com.epam.crud.services.AuthorService
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.authorRout(authorService: AuthorService) {

    route("/author") {
        post {
            try {
                val dto = call.receive<AuthorDto>()
                authorService.addAuthor(dto)
                call.respond(ResponseInfo(HttpStatusCode.OK, "Success!"))
            } catch (ex: UnsupportedMediaTypeException) {
                call.respond(ResponseInfo(HttpStatusCode.UnsupportedMediaType, "Incorrect request media type"))
            } catch (ex: AuthorOperationException) {
                call.respond(ResponseInfo(HttpStatusCode.InternalServerError, ex.message.toString()))
            }

        }
        get("/getAll") {
            try {
                call.respond(authorService.getAllAuthors())
            } catch (ex: AuthorOperationException) {
                call.respond(ResponseInfo(HttpStatusCode.InternalServerError, ex.message.toString()))
            }

        }
        get("/{id}") {
            try {
                call.respond(authorService.getById(call.parameters["id"]!!.toLong()))
            } catch (ex: AuthorOperationException) {
                call.respond(ResponseInfo(HttpStatusCode.InternalServerError, ex.message.toString()))
            }

        }
        delete("/{id}") {
            try {
                authorService.deleteById(call.parameters["id"]!!.toLong())
                call.respond(ResponseInfo(HttpStatusCode.OK, "Success!"))
            } catch (ex: AuthorOperationException) {
                call.respond(ResponseInfo(HttpStatusCode.InternalServerError, ex.message.toString()))
            }


        }
    }

}

