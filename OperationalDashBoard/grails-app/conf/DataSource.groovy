grails {
    mongo {
        host = "localhost"
        port = 27017
        //username = "dblogin"
        //password = "password"
        createDrop  = "database"
        databaseName = "odb"
        options {
            autoConnectRetry = true
            connectTimeout = 300
        }
    }
}