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
    console.log(App.Variables.loggedInUserData.dataSet);
};


Page.select9Change = function($event, widget, newVal, oldVal) {
    Page.Variables.dbTblTTasklist.maxResults = newVal
    Page.Variables.dbTblTTasklist.update()
};

Page.selectSearchByChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.searchValue.datavalue = ""
    Page.Widgets.date2.datavalue = ""
    Page.Widgets.date3.datavalue = ""
    Page.Variables.dbTblTTasklist.update()
};

// sort function
Page.toggleTableSort = function(field) {
    if (Page.Variables.dbTblTTasklist.orderBy == field + " ASC") {
        Page.Variables.dbTblTTasklist.orderBy = field + " DESC"
    } else if (Page.Variables.dbTblTTasklist.orderBy == field + " DESC") {
        Page.Variables.dbTblTTasklist.orderBy = ""
    } else {
        Page.Variables.dbTblTTasklist.orderBy = field + " ASC"
    }
    Page.Variables.dbTblTTasklist.invoke()
}
Page.container41Click = function($event, widget) {
    Page.toggleTableSort('tlTimestamp')
};
Page.container42Click = function($event, widget) {
    Page.toggleTableSort('tlType')
};
Page.container43Click = function($event, widget) {
    Page.toggleTableSort('tlSubject')
};
Page.container44Click = function($event, widget) {
    Page.toggleTableSort('tlDueDate')
};
Page.container45Click = function($event, widget) {
    Page.toggleTableSort('tlSubmitterName');
};

Page.picture10_1Click = function($event, widget, item, currentItemWidgets) {
    if (Page.Widgets.list3.selecteditem.tlType.indexOf("Reclass") !== -1) {
        App.Actions.goToPage_FormReclassApproval.invoke({
            data: {
                'bdRcId': Page.Widgets.list3.selecteditem.tlParamId,
                "tlId": Page.Widgets.list3.selecteditem.tlId
            }
        });
    } else if (Page.Widgets.list3.selecteditem.tlType.indexOf("Additional") !== -1) {
        App.Actions.goToPage_FormAdditionalApprove.invoke({
            data: {
                'baId': Page.Widgets.list3.selecteditem.tlParamId
            }
        });
    } else if (Page.Widgets.list3.selecteditem.tlType.indexOf("PR") !== -1) {
        App.Actions.goToPage_PurchaseRequestFormApproval.invoke({
            data: {
                'prId': Page.Widgets.list3.selecteditem.tlParamId
            }
        });
    } else if (Page.Widgets.list3.selecteditem.tlType.indexOf("PO") !== -1) {
        App.Actions.goToPage_POFormView.invoke({
            data: {
                'id': Page.Widgets.list3.selecteditem.tlParamId,
                'action': Page.Widgets.list3.selecteditem.tlStatus,
                "tasklist": Page.Widgets.list3.selecteditem.tlId,
            }
        });
    }

};
Page.searchValueKeyup = function($event, widget) {
    if ($event.key === "Enter") {
        Page.Variables.dbTblTTasklist.update();
    }
};