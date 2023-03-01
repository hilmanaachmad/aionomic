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

    // Page.$page[0].addEventListener("click", function() {
    //     $('.aio-ptp-dropdown:not(.clicked)').removeClass("active")
    //     $('.aio-ptp-dropdown').removeClass("clicked")
    // })

    setTimeout(() => {
        $('.yearpicker').yearpicker()
        $('.yearpicker').change(function() {
            year_value = $(this).val()
            Page.setYear(year_value)
        })
        $('.yearpicker').val(new Date().getFullYear())
        Page.Widgets.year.datavalue = new Date().getFullYear()



        $('.flatpickr_create_date').daterangepicker({
            opens: 'left'
        }, function(start, end, label) {
            Page.Variables.MODELVariable.setValue('create_date_start', start.format('YYYY-MM-DD'))
            Page.Variables.MODELVariable.setValue('create_date_end', end.format('YYYY-MM-DD'))
            Page.Widgets.create_date.datavalue = start.format('YYYY-MM-DD') + ' - ' + end.format('YYYY-MM-DD')
        });

        $('.flatpickr_delivery_date').daterangepicker({
            opens: 'left'
        }, function(start, end, label) {
            Page.Variables.MODELVariable.setValue('delivery_date_start', start.format('YYYY-MM-DD'))
            Page.Variables.MODELVariable.setValue('delivery_date_end', end.format('YYYY-MM-DD'))
            Page.Widgets.delivery_date.datavalue = start.format('YYYY-MM-DD') + ' - ' + end.format('YYYY-MM-DD')
        });

        $('.flatpickr_create_date').on('cancel.daterangepicker', function(ev, picker) {
            $(this).val('');
            Page.Widgets.create_date.datavalue = ''
            Page.Variables.MODELVariable.setValue('create_date_start', null)
            Page.Variables.MODELVariable.setValue('create_date_end', null)
        });
        $('.flatpickr_delivery_date').on('cancel.daterangepicker', function(ev, picker) {
            $(this).val('');
            Page.Widgets.delivery_date.datavalue = ''
            Page.Variables.MODELVariable.setValue('delivery_date_start', null)
            Page.Variables.MODELVariable.setValue('delivery_date_end', null)
        });

        $('.flatpickr_create_date').val('');
        $('.flatpickr_delivery_date').val('');

    }, 1000);




};


// sort function
Page.toggleTableSort = function(field) {
    if (Page.Variables.DBPOReadyToGRUser.orderBy == field + " ASC") {
        Page.Variables.DBPOReadyToGRUser.orderBy = field + " DESC"
    } else if (Page.Variables.DBPOReadyToGRUser.orderBy == field + " DESC") {
        Page.Variables.DBPOReadyToGRUser.orderBy = ""
    } else {
        Page.Variables.DBPOReadyToGRUser.orderBy = field + " ASC"
    }
    Page.Variables.DBPOReadyToGRUser.invoke()
}
Page.container41Click = function($event, widget) {
    Page.toggleTableSort('poNumber')
};
Page.container44Click = function($event, widget) {
    Page.toggleTableSort('releaseDate')
};
Page.container111Click = function($event, widget) {
    Page.toggleTableSort('currency')
};
Page.container41_1Click = function($event, widget) {
    Page.toggleTableSort('deliveryDate')
};
Page.container45Click = function($event, widget) {
    Page.toggleTableSort('status')
};
Page.container46Click = function($event, widget) {
    Page.toggleTableSort('shipping')
};
Page.container47Click = function($event, widget) {
    Page.toggleTableSort('poItem')
};
Page.container48Click = function($event, widget) {
    Page.toggleTableSort('deliveryItem')
};
Page.container51Click = function($event, widget) {
    Page.toggleTableSort('confirmItem')
};
Page.container44_3Click = function($event, widget) {
    Page.toggleTableSort('poItem')
};
// sort function

//Show row table 
Page.select9Change = function($event, widget, newVal, oldVal) {
    Page.Variables.DBPOReadyToGRUser.maxResults = newVal
    Page.Variables.DBPOReadyToGRUser.update()
};



//Filter
Page.buttonApplyClick = function($event, widget) {
    Page.Variables.DBPOReadyToGRUser.invoke()
};

Page.buttonClearClick = function($event, widget) {
    Page.Widgets.create_date.datavalue = null
    Page.Widgets.delivery_date.datavalue = null
    Page.Variables.MODELVariable.setValue('create_date_start', null)
    Page.Variables.MODELVariable.setValue('create_date_end', null)
    Page.Variables.MODELVariable.setValue('delivery_date_start', null)
    Page.Variables.MODELVariable.setValue('delivery_date_end', null)
    Page.Widgets.company.datavalue = null
    Page.Widgets.department.datavalue = null
    Page.Widgets.po_status.datavalue = null
    Page.Widgets.item.datavalue = null
    Page.Variables.DBPOReadyToGRUser.invoke()
};



Page.buttonGrClick = function($event, widget, item, currentItemWidgets) {
    App.Actions.goToPage_GRForm.invoke({
        data: {
            'id': Page.Widgets.list2.selecteditem.id
        }
    })
};

Page.buttonAssignClick = function($event, widget, item, currentItemWidgets) {
    App.Actions.goToPage_GRFormAssign.invoke({
        data: {
            'id': Page.Widgets.list2.selecteditem.id
        }
    })
};
Page.button6Click = function($event, widget, item, currentItemWidgets) {
    App.Actions.goToPage_GRForm.invoke({
        data: {
            'id': Page.Widgets.list2.selecteditem.id
        }
    })
};
Page.buttonConfirmClick = function($event, widget, item, currentItemWidgets) {
    App.Actions.goToPage_GRFormConfirm.invoke({
        data: {
            'id': Page.Widgets.list2.selecteditem.id
        }
    })
};

Page.setYear = function(year) {
    Page.Variables.mYear.dataSet = year
}
Page.yearChange = function($event, widget, newVal, oldVal) {
    Page.Variables.mYear.dataSet = newVal;
};