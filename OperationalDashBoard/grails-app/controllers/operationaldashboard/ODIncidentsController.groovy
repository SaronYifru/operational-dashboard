package operationaldashboard

class ODIncidentsController {

    def index() {}
    def doUpload() {
        def file = params.incFile
        if (file.empty) {
            flash.message = 'File cannot be empty!'
            render(template: '../settings/iframeStatus')
            return
        }
        File fileDest = grailsApplication.mainContext.getResource("data/lsps_incidents.csv").file
        file.transferTo(fileDest)
        flash.message = 'File Successfully Uploaded!'
        render(template: "../settings/iframeStatus")



    }

}
