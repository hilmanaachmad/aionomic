/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */

/* perform any action on widgets/variables within this block */
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
    Promise.all([
        Page.Variables.vdbqTickInbox.invoke({
            inputFields: {
                date: new Date()
            }
        }),
        Page.Variables.vdbqTickNotif.invoke({
            inputFields: {
                date: new Date()
            }
        })
    ]).then(function() {
        Page.App.Variables.vdbqUnseenInboxCount.invoke()
        Page.App.Variables.vdbqUnseenNotification.invoke({
            page: 1
        })
    })
};

// sort functions
Page.toggleTableSort = function(field) {
    if (Page.Variables.vdbInbox.orderBy == field + " ASC") {
        Page.Variables.vdbInbox.orderBy = field + " DESC"
    } else if (Page.Variables.vdbInbox.orderBy == field + " DESC") {
        Page.Variables.vdbInbox.orderBy = ""
    } else {
        Page.Variables.vdbInbox.orderBy = field + " ASC"
    }
    Page.Variables.vdbInbox.update()
}
Page.container30Click = function($event, widget) {
    Page.toggleTableSort('inbTimestamp')
};
Page.container31Click = function($event, widget) {
    Page.toggleTableSort('inbTaskType')

};
Page.container32Click = function($event, widget) {
    Page.toggleTableSort('inbSubject')
};
Page.container34Click = function($event, widget) {
    Page.toggleTableSort('inbCreatedBy')
};

Page.selectPerPageChange = function($event, widget, newVal, oldVal) {
    Page.Variables.vdbInbox.maxResults = newVal
    Page.Variables.vdbInbox.update()
};

Page.searchValueKeyup = function($event, widget) {
    if ($event.key === "Enter") {
        Page.Variables.vdbInbox.update()
    }
};
Page.searchByChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.searchValue.datavalue = ""
    Page.Widgets.date1.datavalue = ""
    Page.Variables.vdbInbox.update()
};
