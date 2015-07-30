package operationaldashboard

class ODRequestType {
    String name
    boolean focusFlag = false
    ODThreshold threshold
    int thresholdValue = 0
    static constraints = {
        name blank: false, unique:true

    }

}
