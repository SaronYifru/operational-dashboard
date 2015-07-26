package operationaldashboard

class SettingsController {

    def index() {
        [customers: ODCustomer.list(), thresholds: ODThreshold.list(), owners: ODOwner.list()]
    }
}
