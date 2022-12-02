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
     * 'Page.Widgets.username.datavalue'lo
     */
    // console.log(parseInt(App.Variables.loggedInUserData.dataSet.user_sapcode))
};

// rows number
Page.select9Change = function($event, widget, newVal, oldVal) {
    Page.Variables.viewVendorQuotation.maxResults = newVal
    Page.Variables.viewVendorQuotation.update()
};

Page.selectSearchByChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.searchValue.datavalue = ""
    Page.Widgets.date3.datavalue = ""
    Page.Variables.viewVendorQuotation.update()
};

// sort function
Page.toggleTableSort = function(field) {
    if (Page.Variables.viewVendorQuotation.orderBy == field + " ASC") {
        Page.Variables.viewVendorQuotation.orderBy = field + " DESC"
    } else if (Page.Variables.viewVendorQuotation.orderBy == field + " DESC") {
        Page.Variables.viewVendorQuotation.orderBy = ""
    } else {
        Page.Variables.viewVendorQuotation.orderBy = field + " ASC"
    }
    Page.Variables.viewVendorQuotation.invoke()
}
Page.container41Click = function($event, widget) {
    Page.toggleTableSort('rfqNumber')
};
Page.container44Click = function($event, widget) {
    Page.toggleTableSort('overTimeLimit')
};
Page.container45Click = function($event, widget) {
    Page.toggleTableSort('status')
};

Page.searchValueKeyup = function($event, widget) {
    if ($event.key === "Enter") {
        Page.Variables.viewVendorQuotation.update()
    }
};

// data manipulation
Page.listDataBeforedatarender = function(widget, $data) {
    for (var i = 0; i < $data.length; i++) {
        $data[i].nomor = (Page.Variables.viewVendorQuotation.pagination.number * Page.Variables.viewVendorQuotation.pagination.size) + (i + 1);
    }
};

// Page.addDays = function(date, days) {
//     var result = new Date(date);
//     result.setDate(result.getDate() + days);
//     return result;
// };