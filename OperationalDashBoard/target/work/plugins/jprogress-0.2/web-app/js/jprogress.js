/**
 * creates and run the progress bar
 * @param progressId the id of the progressBar
 * @param infoId the id of the infoElement
 * @param infoMessage teh infoMessage like a please wait while we do this for you
 * @param updateUrl the url which will be used to update the progressbar
 */
function createJProgressBar(progressId, infoId, infoMessage, updateUrl, updateInterval, progressTargetSelector, trigger) {
    jQuery(document).ready(function() {

        var select = trigger;

        //build the selector on the fly
        if (select != '*') {
            select = '[name="' + select + '"],' + '#' + select;
        }

        //our actual trigger, so we only fire events when we need to
        jQuery(select).click(function() {
            showProgressBar(progressId, infoId, infoMessage, updateUrl, updateInterval, progressTargetSelector);
        });

    });
}

/**
 * displays a jprogress dialog
 * @param progressId
 * @param infoId
 * @param infoMessage
 * @param updateUrl
 * @param updateInterval
 * @param progressTargetSelector
 * @param trigger
 */
function createJProgressBarDialog(progressId, infoId, infoMessage, updateUrl, updateInterval, progressTargetSelector, trigger) {
    jQuery(document).ready(function() {

        var select = trigger;

        var selectMonitor = "#monitor_"+progressId;


        //build the selector on the fly
        if (select != '*') {
            select = '[name="' + select + '"],' + '#' + select;
        }

        //our actual trigger, so we only fire events when we need to
        jQuery(select).click(function() {
            $(selectMonitor).dialog({height:240,width:320,minWidth:320,minHeight:240,resizable:false,modal:true});

            showProgressBar(progressId, infoId, infoMessage, updateUrl, updateInterval, progressTargetSelector,function(){ $(selectMonitor).dialog("destroy");});
        });

    });
}

/**
 * shows the actual progress bar
 * @param progressId
 * @param infoId
 * @param infoMessage
 * @param updateUrl
 * @param updateInterval
 * @param progressTargetSelector
 */
function showProgressBar(progressId, infoId, infoMessage, updateUrl, updateInterval, progressTargetSelector,closureComplete) {

    //generate the selectors
    var progressTarget = "#" + progressTargetSelector;
    var infoTarget = "#" + infoId;
    //set the bar to zero
    jQuery(progressTarget).progressbar({value:0});


    var info = "<div class='ui-state-highlight ui-corner-all'>"+infoMessage+"</div>";

    var errorMessage = "<div class='ui-state-highlight ui-corner-all'>there was some error</div>";

    var complete = "<div class='ui-state-highlight ui-corner-all'>process was complte</div>";


    jQuery(infoTarget).html(info);

    //generate the timer
    var progressTimer = setInterval((function() {
        return function() {
            jQuery.ajax({
                method: 'get',
                url : updateUrl,
                dataType : 'text',
                success: function (text) {
                    var value = parseFloat(text);

                    //set the info message
                    //we are done
                     if (value >= 100) {
                        jQuery(progressTarget).progressbar("value", 100);
                        clearInterval(progressTimer);

                        //remove the progress target
                        jQuery(progressTarget).progressbar("destroy");
                        //remove the info target
                        jQuery(infoTarget).html(complete);

                        if(closureComplete){
                            closureComplete();
                        }
                    }

                    //an un exspected error happened
                    else if (value < 0) {
                        clearInterval(progressTimer);
                        jQuery(progressTarget).progressbar("destroy");
                        jQuery(infoTarget).html(errorMessage);

                        if(closureComplete){
                            closureComplete();
                        }
                    }
                    //update the progress bar
                    else {
                         jQuery(infoTarget).html(info);

                        jQuery(progressTarget).progressbar("value", value);
                    }
                }
            });
        };
    })(progressId), updateInterval);

}