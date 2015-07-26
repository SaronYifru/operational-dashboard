package jprogress

/**
 * the controller who does the acutal progress handling
 */
class JProgressController {

    /**
     * the service
     */
    ProgressService progressService

    /**
     * render the progress of the bar with the given id
     */
    def ajaxRenderProgress = {
        def session = params.id

        //teh current value of the progress
        def progress = progressService.retrieveProgressBarValue(session)

        try {
            render "${progress}"
        }
        catch (Exception e) {
            log.error(e.getMessage(), e)
            render "-1"
        }
    }

}
