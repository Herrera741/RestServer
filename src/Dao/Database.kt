package csuf.cpsc411.Dao

import com.almworks.sqlite4java.SQLiteConnection
import java.io.File

// purpose of Database class to create a table and keep track of claims (data)
class Database constructor(var dbName: String = "") {

    init {
        // create database, create tables, keep database connection
        dbName = "D:\\CPSC-411\\Lecture-Vids\\2_Async_HTTP\\Claims\\claimsDB.db" // can reassign dbName because var
        val dbConn = SQLiteConnection(File(dbName)) // create connection with file object
        dbConn.open() // if database file exists will open, otherwise will create and open

        // sequel statement will only run if table doesnt already exist, if does then do nothing
        // statement contains id, title, date, isSolved
        // isSolved data type "int" because bool values stored as 0(false) or 1(true) in sqlite
        val sqlStmt = "create table if not exists claims (id text, title text, date text, isSolved int)"
        dbConn.exec(sqlStmt) // execute sequel statement on database connection
    }

    fun getDbConnection() : SQLiteConnection {
        val dbConn = SQLiteConnection(File(dbName)) // create connection with file object
        dbConn.open() // if database file exists will open, otherwise will create and open
        return dbConn
    }

    // singleton obj pattern (single obj)
    // to implement singleton use companion obj (like static method)
    companion object {
        // dont need to give value when instantiate class obj
        private var dbObj : Database? = null // will update value later on

        // getInstance() will return Database obj
        fun getInstance() : Database? {
            if (dbObj == null) {
                dbObj = Database() // if database is null then create empty database
            }
            return dbObj
        }
    }


}