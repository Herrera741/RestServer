package csuf.cpsc411.Dao.claim

import csuf.cpsc411.Dao.Dao
import csuf.cpsc411.Dao.Database

// class ClaimDao is subclass of Dao
// shares behaviors from parent class Dao
// Dao class defined as "open" so child class "ClaimDao" can inherit
class ClaimDao : Dao() {

    // insert claim into database
    fun addClaim(claimObj : Claim) {

        // 1. get database connection
        // getInstance()? optional type because returns optional Database obj in method declaration
        // optional chaining when have to keep using '?' down the line
        val conn = Database.getInstance()?.getDbConnection()

        // 2. prepare sequel statement
        sqlStmt = "insert into claims (id, title, date, isSolved) values ('${claimObj.id}', '${claimObj.title}', '${claimObj.date}', '${claimObj.isSolved}')"

        // 3. submit sequel statement
        // conn? optional type because conn variable uses optional Database obj
        conn?.exec(sqlStmt)
    }

    fun getAll() : List<Claim> {
        // 1. get database connection
        val conn = Database.getInstance()?.getDbConnection()

        // 2. prepare sequel statement
        // need to initialize sequel statement as query
        sqlStmt = "select id, title, date, isSolved from claim"

        // 3. submit sequel statement
        // using this to go through each record and convert back to kotlin Claim obj
        var claimList : MutableList<Claim> = mutableListOf()
        val st = conn?.prepare(sqlStmt)

        // 4. convert each record into Claim obj and add to list of claims to return
        // st? because optional chaining
        // step() to traverse mutable list
        // added non-null asserted call '!!' to make sure list has elements before iterating
        while (st?.step()!!) {
            // for each record need to get value for each column
            val id = st.columnString(0)
            val title = st.columnString(1)
            val date = st.columnString(2)
            val isSolved = st.columnString(3)

            // convert isSolved to kotlin boolean type
            claimList.add(Claim(id, title, date, isSolved.toBoolean()))
        }

        return claimList // return claims list back to client
    }
}