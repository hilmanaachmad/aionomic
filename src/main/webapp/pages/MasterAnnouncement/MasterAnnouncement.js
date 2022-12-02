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

    if (App.Variables.loggedInUser.dataSet.roles.length > 0) {
        if (App.Variables.loggedInUser.dataSet.roles.indexOf('MST-014') !== -1) {

        } else {
            App.Actions.goToPage_Main.invoke();
        }
    }
};


var fileUpload = null

Page.tableBeforedatarender = function(widget, $data) {
    for (var i = 0; i < $data.length; i++) {
        $data[i].num = (Page.Variables.DBListData.pagination.number * Page.Variables.DBListData.pagination.size) + (i + 1);
    }
};

//Tambah Data
Page.btn_saveClick = function($event, widget) {
    //Variable Name
    var formValid = true
    var d = new Date,
        dformat = [d.getFullYear(),
            d.getMonth() + 1,
            d.getDate()
        ].join('/') + ' ' + [d.getHours(),
            d.getMinutes(),
            d.getSeconds()
        ].join(':');


    var name = Page.Widgets.form_name.datavalue
    var type = Page.Widgets.form_type.datavalue
    var startDate = Page.Widgets.form_start_date.datavalue
    var endDate = Page.Widgets.form_end_date.datavalue
    var page = Page.Widgets.form_page.datavalue
    var desc = Page.Widgets.form_desc.datavalue



    //Validation
    Page.Widgets.add_spinner.show = true

    if (name == undefined || name == '' || name == null) {
        formValid = false
        Page.Widgets.add_label_announcement_name.show = true
        Page.Widgets.add_spinner.show = false
    }

    if (type == undefined || type == '' || type == null) {
        formValid = false
        Page.Widgets.add_label_announcement_type.show = true
        Page.Widgets.add_spinner.show = false
    }


    if (fileUpload == undefined || fileUpload == '' || fileUpload == null) {
        formValid = false
        Page.Widgets.add_label_announcement_file.show = true
        Page.Widgets.add_spinner.show = false
    }

    if (type === 'single_date' || type === 'monthly' || type === 'yearly') {
        if (startDate == undefined || startDate == '' || startDate == null) {
            formValid = false
            Page.Widgets.add_label_announcement_start_date.show = true
            Page.Widgets.add_spinner.show = false
        }
        Page.Widgets.form_end_date.datavalue = null
    } else if (type === 'date_range') {
        if (startDate == undefined || startDate == '' || startDate == null) {
            formValid = false
            Page.Widgets.add_label_announcement_start_date.show = true
            Page.Widgets.add_spinner.show = false
        }
        if (endDate == undefined || endDate == '' || endDate == null) {
            formValid = false
            Page.Widgets.add_label_announcement_end_date.show = true
            Page.Widgets.add_spinner.show = false
        }
    }
    //Validation
    if (formValid == true) {
        Page.Variables.DBListData.createRecord({
            row: {
                name: name,
                startDate: startDate,
                endDate: endDate,
                isValid: 1,
                page: page,
                content: fileUpload,
                type: type,
                _desc: desc
            }
        }, (result) => {
            Page.Widgets.add_spinner.show = false
            Page.Widgets.dialogAdd.close()
            Page.Variables.DBListData.invoke()
            App.Actions.appNotification.setMessage("Data Insert Successfully")
            App.Actions.appNotification.setToasterClass("success")
            App.Actions.appNotification.getToasterDuration(5000)
            App.Actions.appNotification.invoke()
        })
    }
};





//Form Trigger
Page.form_nameKeypress = function($event, widget) {
    Page.Widgets.add_label_announcement_start_date.show = false
};
Page.form_typeChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.add_label_announcement_type.show = false
    Page.Widgets.add_label_announcement_start_date.show = false
    Page.Widgets.add_label_announcement_end_date.show = false
};

Page.fileSelect = function($event, widget, selectedFiles) {
    Page.Widgets.add_spinner.show = true
    fileUpload = null
};

Page.FileServiceUploadFileonSuccess = function(variable, data) {
    Page.Widgets.add_spinner.show = false
    fileUpload = data[0].path
};

Page.form_edit_typeChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.edit_label_announcement_type.show = false
    Page.Widgets.edit_label_announcement_start_date.show = false
    Page.Widgets.edit_label_announcement_end_date.show = false
};
Page.form_edit_nameKeypress = function($event, widget) {
    Page.Widgets.edit_label_announcement_name.show = false
};

Page.form_edit_pageChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.edit_label_announcement_page.show = false
};

Page.file_editSelect = function($event, widget, selectedFiles) {
    Page.Widgets.edit_spinner.show = true
    fileUpload = null
};

Page.FileServiceUploadFileEditonSuccess = function(variable, data) {
    Page.Widgets.edit_spinner.show = false
    fileUpload = data[0].path
};
//Form Trigger


//Delete Data
Page.btn_trigger_deleteClick = function($event, widget, item, currentItemWidgets) {
    Page.Variables.selectedItem.dataSet = item;
    Page.Widgets.deleteConfirm.open();
};

Page.deleteConfirmOk = function($event, widget) {
    widget.close();
    Page.Variables.DBListData.deleteRecord({
        row: {
            id: Page.Variables.selectedItem.dataSet.id
        }
    }, (res) => {
        Page.Variables.DBListData.invoke();
        App.Actions.appNotification.setMessage("Data Deleted Successfully")
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()
    });
};




//Edit data
Page.btn_trigger_editClick = function($event, widget, item, currentItemWidgets) {
    Page.Variables.selectedItem.dataSet = item;
    fileUpload = null
    Page.Widgets.EditDialog.open();
};


Page.btn_updateClick = async function($event, widget) {
    //Variable Name
    var formValid = true
    var d = new Date,
        dformat = [d.getFullYear(),
            d.getMonth() + 1,
            d.getDate()
        ].join('/') + ' ' + [d.getHours(),
            d.getMinutes(),
            d.getSeconds()
        ].join(':');


    var name = Page.Widgets.form_edit_name.datavalue
    var type = Page.Widgets.form_edit_type.datavalue
    var startDate = Page.Widgets.form_edit_start_date.datavalue
    var endDate = Page.Widgets.form_edit_end_date.datavalue
    var page = Page.Widgets.form_edit_page.datavalue
    var status = Page.Widgets.form_edit_status.datavalue
    var desc = Page.Widgets.form_edit_desc.datavalue


    //Validation
    Page.Widgets.edit_spinner.show = true

    if (name == undefined || name == '' || name == null) {
        formValid = false
        Page.Widgets.edit_label_announcement_name.show = true
        Page.Widgets.edit_spinner.show = false
    }

    if (type == undefined || type == '' || type == null) {
        formValid = false
        Page.Widgets.edit_label_announcement_type.show = true
        Page.Widgets.edit_spinner.show = false
    }

    if (page == undefined || page == '' || page == null) {
        formValid = false
        Page.Widgets.edit_label_announcement_page.show = true
        Page.Widgets.edit_spinner.show = false
    }

    if (type === 'single_date' || type === 'monthly' || type === 'yearly') {
        if (startDate == undefined || startDate == '' || startDate == null) {
            formValid = false
            Page.Widgets.edit_label_announcement_start_date.show = true
            Page.Widgets.edit_spinner.show = false
        }
        Page.Widgets.form_edit_end_date.datavalue = null
    } else if (type === 'date_range') {
        if (startDate == undefined || startDate == '' || startDate == null) {
            formValid = false
            Page.Widgets.edit_label_announcement_start_date.show = true
            Page.Widgets.edit_spinner.show = false
        }
        if (endDate == undefined || endDate == '' || endDate == null) {
            formValid = false
            Page.Widgets.edit_label_announcement_start_date.show = true
            Page.Widgets.edit_spinner.show = false
        }
    }

    //Validation
    if (formValid == true) {
        Page.Variables.DBListData.updateRecord({
            row: {
                name: name,
                startDate: startDate,
                endDate: endDate,
                isValid: status == "Active" ? 1 : 0,
                page: page,
                type: type,
                content: fileUpload ? fileUpload : Page.Variables.selectedItem.dataSet.content,
                id: Page.Variables.selectedItem.dataSet.id,
                _desc: desc
            }
        }, function(res) {
            Page.Variables.DBListData.invoke();
            Page.Widgets.edit_spinner.show = false;
            Page.Widgets.EditDialog.close();
            App.Actions.appNotification.setMessage("Data Updated Successfully")
            App.Actions.appNotification.setToasterClass("success")
            App.Actions.appNotification.setToasterDuration(5000)
            App.Actions.appNotification.invoke()
        });
    }
};




//Table Trigger Action
Page.buttonOpenFileClick = function($event, widget, item, currentItemWidgets) {
    window.open(item.content, '_blank');
};
//Table Trigger Action



//Searching
Page.select1Change = function($event, widget, newVal, oldVal) {
    if (newVal == '') {
        Page.Variables.DBListData.filterExpressions.rules[0].value = '';
        Page.Variables.DBListData.filterExpressions.rules[1].value = '';
        Page.Widgets.search_data.datavalue = ''
        Page.Variables.DBListData.invoke()
    }
};

Page.search_dataSubmit = function($event, widget) {
    switch (Page.Widgets.select1.datavalue) {
        case '1':
            Page.Variables.DBListData.filterExpressions.rules[0].value = Page.Widgets.search_data.query;
            Page.Variables.DBListData.filterExpressions.rules[1].value = '';
            break;
        case '2':
            Page.Variables.DBListData.filterExpressions.rules[0].value = '';
            Page.Variables.DBListData.filterExpressions.rules[1].value = Page.Widgets.search_data.query;
            break;
        default:
            Page.Variables.DBListData.filterExpressions.rules[0].value = '';
            Page.Variables.DBListData.filterExpressions.rules[1].value = '';
            break;
    }
    Page.Variables.DBListData.invoke()
};

// Show Row
Page.select_dataChange = function($event, widget, newVal, oldVal) {
    Page.Variables.DBListData.maxResults = Number(Page.Widgets.select_data.datavalue)
    Page.Variables.DBListData.invoke()
};


//Sorting Data
Page.TableSort = function(field) {
    if (Page.Variables.DBListData.orderBy == field + " ASC") {
        Page.Variables.DBListData.orderBy = field + " DESC"
    } else if (Page.Variables.DBListData.orderBy == field + " DESC") {
        Page.Variables.DBListData.orderBy = ""
    } else {
        Page.Variables.DBListData.orderBy = field + " ASC"
    }

    Page.Variables.DBListData.update()
}

Page.kolom_nameClick = function($event, widget) {
    Page.TableSort("name");
    if (Page.Variables.DBListData.orderBy == 'name ASC') {
        Page.Widgets.picture_name.picturesource = 'resources/images/logos/icon-up-down-asc.png'
    } else if (Page.Variables.DBListData.orderBy == 'name DESC') {
        Page.Widgets.picture_name.picturesource = 'resources/images/logos/icon-up-down-desc.png'
    } else {
        Page.Widgets.picture_name.picturesource = 'resources/images/logos/icon-up-down.png'
    }
};

Page.kolom_dateClick = function($event, widget) {
    Page.TableSort("startDate");
    if (Page.Variables.DBListData.orderBy == 'startDate ASC') {
        Page.Widgets.picture_date.picturesource = 'resources/images/logos/icon-up-down-asc.png'
    } else if (Page.Variables.DBListData.orderBy == 'startDate DESC') {
        Page.Widgets.picture_date.picturesource = 'resources/images/logos/icon-up-down-desc.png'
    } else {
        Page.Widgets.picture_date.picturesource = 'resources/images/logos/icon-up-down.png'
    }
};

Page.kolom_statusClick = function($event, widget) {
    Page.TableSort("status");

    if (Page.Variables.DBListData.orderBy == 'status ASC') {
        Page.Widgets.picture_status.picturesource = 'resources/images/logos/icon-up-down-asc.png'
    } else if (Page.Variables.DBListData.orderBy == 'status DESC') {
        Page.Widgets.picture_status.picturesource = 'resources/images/logos/icon-up-down-desc.png'
    } else {
        Page.Widgets.picture_status.picturesource = 'resources/images/logos/icon-up-down.png'
    }
};


//Form Action