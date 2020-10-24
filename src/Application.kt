package csuf.cpsc411

import com.google.gson.Gson
import csuf.cpsc411.Dao.Database
import csuf.cpsc411.Dao.claim.Claim
import csuf.cpsc411.Dao.claim.ClaimDao
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.utils.io.*
import java.util.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads

// extension - another way to extend class behavior
// module() will be main method to handle all HTTP messages sent to server
fun Application.module(testing: Boolean = false) {
    
    routing {
//        // HTTP method get() to request data from specified resource
//        get("/get") {
//            println("HTTP is using GET method with /get")
//
//            // call obj, HTTP request, queryParameters dictionary
//            // queryParameters is mapped key,value
//            val fn = call.request.queryParameters["FirstName"]
//            val ln = call.request.queryParameters["LastName"]
//            val sn = call.request.queryParameters["SSN"]
//
//            // create database obj using getInstance() from Database class
//            val dbObj = Database.getInstance()
//
//            // response to HTTP request in string format
//            val response = String.format("First Name %s and Last Name %s", fn, ln)
//
//            val cObj = Person(fn, ln, sn)
//
//            // response HTTP message
//            // status is OK response to /GET
//            // contentType is only plain text
//            call.respondText(response, status= HttpStatusCode.OK, contentType = ContentType.Text.Plain)
//        }

//        // HTTP method post() to send data to server to create/update a resource
//        post("/post") {
//            val contType = call.request.contentType() // how to get message body data
//            val data  = call.request.receiveChannel() // standard pattern to receive data
//            val dataLength = data.availableForRead // get length of data
//            var output = ByteArray(dataLength) // create space for data
//            data.readAvailable(output) // read data from message body and put in output
//            val str = String(output) // for further processing
//
//            // print content type and data in message body
//            println("HTTP is using POST method with /post ${contType} ${str}")
//            call.respondText("The POST request was successfully processed.", status= HttpStatusCode.OK, contentType = ContentType.Text.Plain)
//        }

        // "/ClaimService/getAll" makes web service more meaningful
        get("/ClaimService/getAll") {

            val cList = ClaimDao().getAll()
            println("Number of claims: ${cList.size}") // print size of claims list

            // need way to responsd back after converting data from different format to kotlin
            // JSON serialization/deserialization
            // GSON (Google library)
            val responseJson = Gson().toJson(cList) // create JSON array

            // return json response string with application obj of content type json
            call.respondText(responseJson, status= HttpStatusCode.OK, contentType = ContentType.Application.Json)
        }

        // HTTP method post() to send data to server to create/update a resource
        post("/ClaimService/add") {
            val contType = call.request.contentType() // how to get message body data
            val data  = call.request.receiveChannel() // standard pattern to receive data
            val dataLength = data.availableForRead // get length of data
            var output = ByteArray(dataLength) // create space for data
            data.readAvailable(output) // read data from message body and put in output
            val str = String(output) // for further processing

            // JSON deserialization
            val gsonString = Gson().fromJson(str, Claim :: class.java)
            println(gsonString)
            val claimObj = Claim(UUID.randomUUID().toString(), gsonString.title, gsonString.date, false)
            val dao = ClaimDao().addClaim(claimObj)

            // print content type and data in message body
            println("HTTP is using POST method with /post ${contType}")
            call.respondText("The POST request was successfully processed.", status= HttpStatusCode.OK, contentType = ContentType.Text.Plain)
        }

    }
}

