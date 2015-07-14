package operationaldashboard

class ODRequestType {
    String name
    static constraints = {
        name blank: false, unique:true

    }

}
