package operationaldashboard

class ODOwnerController {

    def index() {}
    def setOwnerName() {
        ODOwner owner = ODOwner.findById(params.name)
        owner.setName(params.value)
        owner.save()
        render(status: 200, text: 'HTTP status 200 OK')
    }

}
