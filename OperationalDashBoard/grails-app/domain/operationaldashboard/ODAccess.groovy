package operationaldashboard

class ODAccess {
    String value
    static constraints = {
        value blank: false, unique:true
    }
}
