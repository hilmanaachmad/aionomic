/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */

/* perform any action on widgets/variables within this block */
Page.timeout_id = 0
Page.onReady = function() {
    /*
     * variables can be accessed through 'Page.Variables' property here
     * e.g. to get dataSet in a staticVariable named 'loggedInUser' use following script
     * Page.Variables.loggedInUser.getData()
     *
     * widgets can be accessed through 'Page.Widgets' property here
     * e.g. to get value of text widget named 'username' use following script
     * 'Page.Widgets.username.datavalue'
     */

    try {
        var username = Page.App.Variables.loggedInUserData.getValue("username").split("::")[1]
        var password = atob(window.localStorage.getItem("aio_p2p_cup"))

        var path = 'https://ivendor.aio.co.id:11000/login/processLogin'
        var method = 'post'
        var form = document.createElement("form");
        form.setAttribute("method", method);
        form.setAttribute("action", path);
        // form.setAttribute("target", "_blank");
        var hiddenField = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", 'username');
        hiddenField.setAttribute("id", 'username');
        hiddenField.setAttribute("value", username);
        var hiddenField2 = document.createElement("input");
        hiddenField2.setAttribute("type", "hidden");
        hiddenField2.setAttribute("name", 'password');
        hiddenField2.setAttribute("id", 'password');
        hiddenField2.setAttribute("value", password);
        var hiddenField3 = document.createElement("input");
        hiddenField3.setAttribute("type", "hidden");
        hiddenField3.setAttribute("name", 'loginas');
        hiddenField3.setAttribute("id", 'loginas');
        hiddenField3.setAttribute("value", 'Vendor');
        form.appendChild(hiddenField);
        form.appendChild(hiddenField2);
        form.appendChild(hiddenField3);
        document.body.appendChild(form);
        form.submit();
    } catch (e) {
        Page.timeout_id = setTimeout(function() {
            window.location.hash = "#/Main"
        }, 3000)
    }
};

Page.onBeforePageLeave = function() {
    window.clearTimeout(Page.timeout_id)
}