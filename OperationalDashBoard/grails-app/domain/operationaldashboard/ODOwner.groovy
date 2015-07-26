package operationaldashboard

class ODOwner {
    String eID
    String name
    static constraints = {
        name nullable: true, blank: true
    }
}
