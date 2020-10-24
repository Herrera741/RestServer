package csuf.cpsc411.Dao.claim

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

// for Kotlin, can define data class for purpose of representing data
// methods not required in data class
// when instantiate Claim obj of class will use primary constructor to initialize properties
data class Claim (var id : String?, var title : String?, var date : String?, var isSolved : Boolean = false)

fun main() {
    // JSON serialization
    // create Claim obj with input values
    val cObj = Claim("testID", "testTitle", "testDate")
    val jsonStr = Gson().toJson(cObj) // using Gson() parses class Claim obj into JSON string
    println("The converted JSON string: ${jsonStr}")

    // serialization of List<Claim>
    var cList : MutableList<Claim> = mutableListOf() // create empty mutable list for Claim objects
    cList.add(cObj) // add cObj to list of claims
    // create Claim obj and add to list automatically
    cList.add(Claim("testID2", "testTitle2", "testDate2"))
    val listJsonString = Gson().toJson(cList) // convert obj from Gson to JSON array of Claim objects
    println("${listJsonString}") // print JSON array obj

    // List<Claim> type obj deserialization
    // defining singleton object using kotlin keyword "object" with its type
    // TypeToken is type in Gson library that is defining list Claim type obj
    val claimListType : Type = object : TypeToken<Claim>(){}.type

    // JSON deserialization
    var cObj1 : Claim // Claim obj to store deserialized obj
    // fromJson() contains two parameters
    // first parameter is input JSON string
    // second parameter is class what youre converting string into
    cObj1 = Gson().fromJson(jsonStr, Claim :: class.java)

    // print (id, title, date, isSolved) as text, text, text, int
    println("${cObj1.toString()}")
}