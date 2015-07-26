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
        //validate file or do something crazy hehehe

//now transfer file
        def webrootDir = servletContext.getRealPath("/") //app directory
        File fileDest = new File(webrootDir,"../data/lsps_incidents.csv")
        file.transferTo(fileDest)
        flash.message = 'File Successfully Uploaded!'
        render(template: "../settings/iframeStatus")



    }

}
