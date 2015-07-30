package operationaldashboard

class SettingsController {

    def index() {
        [customers: ODCustomer.list(), requestTypes: ODRequestType.list(), owners: ODOwner.list()]
    }
}
