package operationaldashboard

class ODProblemsController {
    def progressService
    def index() {
        def problems = ODProblems.list()
        def requestType = ODRequestType.list()


        [problems:problems, requestType:requestType, customerName:null]
    }
    def getCustomersProblems() {
        ODCustomer customer = ODCustomer.findById(params.id)
        String env = params.env
        def problems
        if (env.equals("Unknown")) {
            problems= ODProblems.findAllByCustomerAndEnvIsNull(customer)
        }
        else {
            problems = ODProblems.findAllByCustomerAndEnvLike(customer,params.env)
        }
        render(view: "index", model: [problems:problems, customerName:customer.name])
    }
    def getTicket() {
        ODProblems problem = ODProblems.findByTicketID(params.id)
        render(view:"index", model: [problems: problem? [problem]: []])
    }
    def doUpload() {
        def file = params.prbFile
        if (file.empty) {
            flash.message = 'File cannot be empty!'
            render(template: '../settings/iframeStatus')
            return
        }
        progressService.setProgressBarValue("reportUpdateProgress", 33)
        def webrootDir = servletContext.getRealPath("/") //app directory
        File fileDest = new File(webrootDir,"../data/lsps_problems.csv")
        log.info(fileDest)
        file.transferTo(fileDest)
        flash.message = 'File Successfully Uploaded!'
        render(template: "../settings/iframeStatus")



    }

}
