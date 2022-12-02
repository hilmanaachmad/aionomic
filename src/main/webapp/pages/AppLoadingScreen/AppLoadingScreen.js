/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */

/* perform any action on widgets/variables within this block */
Page.onReady = function() {
    /*
     * variables can be accessed through 'Page.Variables' property here
     * e.g. to get dataSet in a staticVariable named 'loggedInUser' use following script
     * Page.Variables.loggedInUser.getValue()
     *
     * widgets can be accessed through 'Page.Widgets' property here
     * e.g. to get value of text widget named 'username' use following script
     * 'Page.Widgets.username.datavalue'
     */
    console.log('prepare')
    Page.Variables.loadingMessage.setValue("dataValue", "Loading user data ...")
    // Page.App.Variables.loggedInUser.setValue("id", Page.App.Variables.loggedInUser.getValue("name"))
    Page.App.Variables.loggedInUser.setValue("id", Page.App.Variables.loggedInUser.getValue("name").replace("emp::", ""))
    // console.log(Page.App.Variables.loggedInUser)
    console.log('prepare')
    Page.App.fetchUserData().then(function() {

        console.log('prepare success')
        console.log('app loading after fetching', Page.App.Variables.loggedInUser)
        if (!Page.App.originalHash || Page.App.originalHash == window.location.hash || Page.App.originalHash == "#/LoginAIO") {
            window.location.hash = "#/Main"
        } else {
            window.location.hash = Page.App.originalHash
        }
        delete Page.App.originalHash
    })
};