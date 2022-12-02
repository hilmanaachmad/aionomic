/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */

/* perform any action on widgets/variables within this block */
Partial.onReady = function() {
    /*
     * variables can be accessed through 'Partial.Variables' property here
     * e.g. to get dataSet in a staticVariable named 'loggedInUser' use following script
     * Partial.Variables.loggedInUser.getData()
     *
     * widgets can be accessed through 'Partial.Widgets' property here
     * e.g. to get value of text widget named 'username' use following script
     * 'Partial.Widgets.username.datavalue'
     */
};

Partial.JSON = JSON

Partial.container2Click = function($event, widget) {
    if (Partial.pageParams.disabled) {
        return
    }
    $(Partial.Widgets.fileupload1.nativeElement).find("input").click()
};

Partial.FileServiceUploadFileonResult = function(variable, data) {
    data = data[0]
    Partial.Variables.vmUploadData.setData({
        "fileName": data.fileName,
        "fileUrl": data.path,
        "fileSize": data.length
    })
};

Partial.FileServiceUploadFileonProgress = function(variable, data, event) {
    Partial.Variables.vmUploadProgress.setValue("dataValue", data)
    if (data == 100) {
        setTimeout(function() {
            Partial.Variables.vmUploadProgress.setValue("dataValue", 0)
        }, 500);
    }
};
Partial.fileupload1Beforeselect = function($event, widget, files) {
    Partial.Variables.vmUploadProgress.setValue("dataValue", 1)
};
Partial.label1Click = function($event, widget) {
    var url = Partial.Variables.vmUploadData.getValue("fileUrl")
    if (!url || !url.length) {
        return
    }
    window.open(url, "__blank")
};
