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

    //authorization
    if (App.Variables.loggedInUser.dataSet.roles.length > 0) {
        if (App.Variables.loggedInUser.dataSet.roles.indexOf('PRS-007') !== -1) {

        } else {
            App.Actions.goToPage_Main.invoke()
        }
    }
    Page.Variables.varDateDueDate.dataSet.dataValue = null
};

Page.subjet = function(prId) {
    var subject = "5000-" + prId
    if (prId < 10) {
        subject = "5000-0" + prId
    }
    return subject
}

Page.dueDate = function(date) {
    return new Date(new Date(date).valueOf() + (3 * 24 * 60 * 60 * 1000))
}

Page.adjustDate = function(date, adjustment) {
    console.log(new Date(new Date(date).valueOf() + adjustment))
    return new Date(new Date(date).valueOf() + adjustment)
}

Page.select9Change = function($event, widget, newVal, oldVal) {
    Page.Variables.dbTblTPr.maxResults = newVal
    Page.Variables.dbTblTPr.update()
};

// sort function
Page.toggleTableSort = function(field) {
    if (Page.Variables.dbTblTPr.orderBy == field + " ASC") {
        Page.Variables.dbTblTPr.orderBy = field + " DESC"
    } else if (Page.Variables.dbTblTPr.orderBy == field + " DESC") {
        Page.Variables.dbTblTPr.orderBy = ""
    } else {
        Page.Variables.dbTblTPr.orderBy = field + " ASC"
    }
    Page.Variables.dbTblTPr.invoke()
}
Page.container41Click = function($event, widget) {
    Page.toggleTableSort('prModifiedAt')
};
Page.container43Click = function($event, widget) {
    Page.toggleTableSort('prId')
};
Page.container44Click = function($event, widget) {
    Page.toggleTableSort('prModifiedAt')
};
Page.container45Click = function($event, widget) {
    Page.toggleTableSort('prCreatedName')
};

Page.picture10_1Click = function($event, widget, item, currentItemWidgets) {
    if (Page.Widgets.list3.selecteditem.taskType == 'PR') {
        App.Actions.goToPage_PurchaseRequestFormCheckerPage.invoke({
            data: {
                'prId': Page.Widgets.list3.selecteditem.prId
            }
        });
    } else if (Page.Widgets.list3.selecteditem.taskType == 'Reclass') {
        App.Actions.goToPage_FormReclassChecker.invoke({
            data: {
                'bdRcId': Page.Widgets.list3.selecteditem.prId
            }
        });
    } else if (Page.Widgets.list3.selecteditem.taskType == 'Additional') {
        App.Actions.goToPage_FormAdditionalChecker.invoke({
            data: {
                'baId': Page.Widgets.list3.selecteditem.prId
            }
        });
    }

};