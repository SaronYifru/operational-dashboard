package jprogress

/**
 * simple controller to demonstrate the behavior of the progressbar
 */
class JProgressTesterController {

    ProgressService progressService

    def index = { }

    /**
     * the test method
     */
    def test = {

    }

    /**
     * the progress handling
     */
    def showProgress = {

    }

    /**
     * shows a progress dialog and closes it once it's done
     */
    def showProgressDialog = {

    }

    def executeAction = {

        for (int i = 1; i <= 100; i++) {
            progressService.setProgressBarValue("123", i)

            //let's waste some time
            for (int a = 0; a < 10000; a++) {

                for (int b = 0; b < 1000; b++) {

                }
            }
        }

        render "the progress is done"
    }
}
