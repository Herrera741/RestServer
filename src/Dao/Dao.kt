package csuf.cpsc411.Dao

// defined as open so child classes can inherit
open class Dao {

    // defining in parent class Dao so subclasses can reuse
    lateinit var sqlStmt : String
}