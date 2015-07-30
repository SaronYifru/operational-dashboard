package operationaldashboard

class ODThreshold {
//    ODRequestType type
//    int thresholdValue
//    String attribute
//    static constraints = {
//        type nullable: true
//        thresholdValue min:0, blank:true
//        attribute blank:true, nullable: true
//
//    }
    String attribute
    static constraints = {
        attribute unique: true
    }
}
