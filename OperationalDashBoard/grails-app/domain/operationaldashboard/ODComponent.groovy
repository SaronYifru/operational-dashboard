package operationaldashboard

class ODComponent {
    String value
    static constraints = {
        value blank: false, unique:true
    }

}
