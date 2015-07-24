package operationaldashboard

class ODRequestType {
    String name
    boolean focusFlag = false
    static constraints = {
        name blank: false, unique:true

    }

}
