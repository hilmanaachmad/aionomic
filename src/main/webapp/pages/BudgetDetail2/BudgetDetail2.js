/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */


var start_date
var end_date
var start_date_tab2
var end_date_tab2
var tab_active = 'tab_1'
var noTotal = 1
var noUsage = 1
var excelData = [];

var totalBudgetAfterAdjustment = 0

Page.formatDate = function(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2)
        month = '0' + month;
    if (day.length < 2)
        day = '0' + day;

    return [day, month, year].join('/');
}

async function update_dataTotalAdjustment() {
    await Page.Variables.sp_BudgetDetails.invoke({
        inputFields: {
            bh_id: Page.pageParams.id,
            type_data: 'NULL',
            value_beginning: 'NULL',
            value_adjustment: 'NULL',
            value_reclass: 'NULL',
            value_additional: 'NULL',
            created_at_start: 'NULL',
            created_at_end: 'NULL',
            created_by: 'NULL',
            coa: 'NULL',
            remarks: 'NULL',
            docNumber: 'NULL'
        }
    }, function(data) {
        if (data.content.length > 0) {
            var array = data.content

            let groupCoa = array.reduce((r, a) => {
                r[a.coa] = [...r[a.coa] || [], a];
                return r;
            }, {});

            for (var key in groupCoa) {
                if (key === 'null' || key === null) {
                    Page.Variables.data_coa.dataSet.push({
                        name: '',
                        dataValue: 'string_kosong'
                    })
                } else {
                    if (groupCoa.hasOwnProperty(key)) {
                        Page.Variables.data_coa.dataSet.push({
                            name: key,
                            dataValue: key
                        })
                    }
                }
            }

            let groupUser = array.reduce((r, a) => {
                r[a.createdBy] = [...r[a.createdBy] || [], a];
                return r;
            }, {});
            for (var key in groupUser) {
                if (groupUser.hasOwnProperty(key)) {
                    Page.Variables.data_createdBy.dataSet.push({
                        name: key,
                        dataValue: key
                    })
                }
            }

            let groupDocNo = array.reduce((r, a) => {
                r[a.docNumber] = [...r[a.docNumber] || [], a];
                return r;
            }, {});

            for (var key in groupDocNo) {
                if (groupDocNo.hasOwnProperty(key)) {
                    Page.Variables.data_docno.dataSet.push({
                        name: key,
                        dataValue: key
                    })
                }
            }
        }
    })




    //Usage
    await Page.Variables.sp_UsageBudgetDetails.invoke({
        inputFields: {
            bh_id: Page.pageParams.id,
            type_data: 'NULL',
            value_beginning: 'NULL',
            value_adjustment: 'NULL',
            value_reserve: 'NULL',
            value_commitment: 'NULL',
            value_actual: 'NULL',
            created_at_start: 'NULL',
            created_at_end: 'NULL',
            created_by: 'NULL',
            coa: 'NULL',
            remarks: 'NULL',
            docNumber: 'NULL'
        }
    }, function(data) {
        // console.log('hehe', data)
        if (data.content.length > 0) {
            var array = data.content
            let groupCoa = array.reduce((r, a) => {
                r[a.coa] = [...r[a.coa] || [], a];
                return r;
            }, {});

            for (var key in groupCoa) {
                if (key === 'null' || key === null) {
                    Page.Variables.data_coa_tab2.dataSet.push({
                        name: '',
                        dataValue: 'string_kosong'
                    })
                } else {
                    if (groupCoa.hasOwnProperty(key)) {
                        Page.Variables.data_coa_tab2.dataSet.push({
                            name: key,
                            dataValue: key
                        })
                    }
                }
            }


            let groupUser = array.reduce((r, a) => {
                r[a.createdBy] = [...r[a.createdBy] || [], a];
                return r;
            }, {});

            for (var key in groupUser) {
                if (groupUser.hasOwnProperty(key)) {
                    Page.Variables.data_createdBy_tab2.dataSet.push({
                        name: key,
                        dataValue: key
                    })
                }
            }

            //docNumber
            let groupDocNo = array.reduce((r, a) => {
                r[a.docNumber] = [...r[a.docNumber] || [], a];
                return r;
            }, {});

            for (var key in groupDocNo) {
                if (groupDocNo.hasOwnProperty(key)) {
                    Page.Variables.data_docno_tab2.dataSet.push({
                        name: key,
                        dataValue: key
                    })
                }
            }
            console.log("tabel", data);

        }
    })
}



var data_total = []
var data_usage = []
/* perform any action on widgets/variables within this block */
Page.onReady = function() {
    data_total = []
    data_usage = []

    window.localStorage.setItem("budget-session", 'true');

    Page.Variables.data_exportBudgetDetail2.invoke({
        inputFields: {
            bh_id: Page.pageParams.id
        }
    })

    // Main Page
    update_dataTotalAdjustment()




    $('.flatpickr').daterangepicker({
        opens: 'left'
    }, function(start, end, label) {
        start_date = start.format('YYYY-MM-DD')
        end_date = end.format('YYYY-MM-DD')
    });



    $('.flatpickr_tab2').daterangepicker({
        opens: 'left'
    }, function(start, end, label) {
        start_date_tab2 = start.format('YYYY-MM-DD')
        end_date_tab2 = end.format('YYYY-MM-DD')
    });

    $('.flatpickr').on('cancel.daterangepicker', function(ev, picker) {
        $(this).val('');
    });
    $('.flatpickr_tab2').on('cancel.daterangepicker', function(ev, picker) {
        $(this).val('');
    });

    $('.flatpickr').val('');
    $('.flatpickr_tab2').val('');



};


Page.generateDocNumPR = function(current_item, urutanDocPR) {
    return new Promise((resolve, reject) => {
        var check = data_usage.filter(item =>
            item.io === current_item.io && item.year === current_item.year && item.docNumber === current_item.docNumber
        )

        if (check.length > 0) {
            urutanDocPR = check.length + 1
        } else {
            urutanDocPR = 1;
            data_usage = [];
        }

        data_usage.push({
            bdAdditionalDocId: current_item.bdAdditionalDocId,
            urutanDocPR: urutanDocPR,
            bdId: current_item.bdId,
            io: current_item.io,
            year: current_item.year,
            docNumber: current_item.docNumber
        })



        resolve(urutanDocPR)


    })
}

Page.TblBudgetBeforedatarender = async function(widget, $data) {
    Page.Variables.totalBudgetOriginal.dataValue = 0
    Page.Variables.totalBudgetAdjustment.dataSet.dataValue = 0
    Page.Variables.totalBudgetReclass.dataSet.dataValue = 0
    Page.Variables.totalBudgetAdditional.dataSet.dataValue = 0
    Page.Variables.totalBudgetAfterAdjustment.dataSet.dataValue = 0
    Page.Variables.var_originalData.dataSet = []

    var urutanDocPR = 0

    $data = $data.filter(item => !item.type.includes('PR') && !item.type.includes('PO') && !item.type.includes('GR'))
    for (var i = 0; i < $data.length; i++) {
        $data[i].num = noTotal++
            // if (widget.currentPage == undefined) {
            //     $data[i].num = i + 1
            // } else {
            //     $data[i].num = (widget.currentPage * widget.pagesize) + (i + 1);
            // }



            // if ($data[i].type.includes('PR')) {
            //     var numberDocPR = await Page.generateDocNumPR(data_total, $data[i], urutanDocPR)
            //     urutanDocPR = numberDocPR

            //     var docPR = $data[i].docNumber
            //     $data[i].docNumberDatabase = $data[i].docNumber
            //     $data[i].docNumber = docPR + ' - ' + Page.formatString(numberDocPR, 3)
            // }



            if ($data[i].type !== "BEGINNING") {
                $data[i].budgetAfterAdjustment = Math.abs($data[i - 1].budgetAfterAdjustment - ($data[i].reclass * -1) - ($data[i].additional * -1) - ($data[i].adjustment * -1))
            }


        //Total
        Page.Variables.totalBudgetAdjustment.dataSet.dataValue = Page.Variables.totalBudgetAdjustment.dataSet.dataValue + Number($data[i].adjustment)
        Page.Variables.totalBudgetReclass.dataSet.dataValue = Page.Variables.totalBudgetReclass.dataSet.dataValue + Number($data[i].reclass)
        Page.Variables.totalBudgetAdditional.dataSet.dataValue = Page.Variables.totalBudgetAdditional.dataSet.dataValue + Number($data[i].additional)
        Page.Variables.totalBudgetAfterAdjustment.dataSet.dataValue = Number($data[i].budgetAfterAdjustment)

        if ($data[i].type.includes('RECLASS') || $data[i].type.includes('ADDITIONAL')) {
            $data[i].coa = '-'
        }


        if ((i + 1) == $data.length) {

            //Budget Original
            var bdOriginal = $data.filter(item =>
                item.type == 'BEGINNING'
            )



            if (bdOriginal.length > 0) {
                Page.Variables.totalBudgetOriginal.dataSet.dataValue = bdOriginal[0].budgetOriginal
            }

        }
    }
};


Page.btn_filterClick = function($event, widget) {
    Page.Variables.totalBudgetOriginal.dataValue = 0
    Page.Variables.totalBudgetAdjustment.dataSet.dataValue = 0
    Page.Variables.totalBudgetReclass.dataSet.dataValue = 0
    Page.Variables.totalBudgetAdditional.dataSet.dataValue = 0
    Page.Variables.totalBudgetAfterAdjustment.dataSet.dataValue = 0


    var type_data = Page.Widgets.tab1_type.datavalue
    var value_beginning = Page.Widgets.tab1_budgetOriginal.datavalue
    var value_reclass = Page.Widgets.tab1_reclass.datavalue
    var value_additional = Page.Widgets.tab1_additional.datavalue
    var value_adjustment = Page.Widgets.tab1_adjustment.datavalue
    var value_after_adjustment = Page.Widgets.tab1_afterAdjustment.datavalue
    var coa = Page.Widgets.tab1_coa.datavalue
    var remarks = Page.Widgets.tab1_remarks.datavalue
    var created_by = Page.Widgets.tab1_createdBy.datavalue
    var docNumber = Page.Widgets.tab1_docno.datavalue



    if (type_data == 'ALL' || type_data == undefined || type_data == '') type_data = 'NULL';
    if (value_beginning == 'all' || value_beginning == undefined || value_beginning == '') value_beginning = 'NULL'
    if (value_reclass == 'all' || value_reclass == undefined || value_reclass == '') value_reclass = 'NULL'
    if (value_additional == 'all' || value_additional == undefined || value_additional == '') value_additional = 'NULL'
    if (value_adjustment == 'all' || value_adjustment == undefined || value_adjustment == '') value_adjustment = 'NULL'
    if (coa == 'all' || coa == undefined || coa == '') coa = 'NULL'
    if (created_by == 'all' || created_by == undefined || created_by == '') created_by = 'NULL'
    if (start_date == undefined) start_date = 'NULL'
    if (end_date == undefined) end_date = 'NULL'
    if (remarks == undefined || remarks === '') remarks = 'NULL'
    if (docNumber === 'all' || docNumber == undefined || docNumber === '') docNumber = 'NULL'

    if (value_after_adjustment == 'all' || value_after_adjustment == undefined) {
        Page.Variables.sp_BudgetDetails.invoke({
            inputFields: {
                bh_id: Page.pageParams.id,
                type_data: type_data,
                value_beginning: value_beginning,
                value_adjustment: value_adjustment,
                value_reclass: value_reclass,
                value_additional: value_additional,
                created_at_start: start_date,
                created_at_end: end_date,
                created_by: created_by,
                coa: coa,
                remarks: remarks,
                docNumber: docNumber
            }
        })
    } else {
        Page.Variables.sp_BudgetDetails.dataSet.content = []
    }


};
Page.btn_clearFilterClick = function($event, widget) {
    Page.Widgets.tab1_type.datavalue = undefined
    Page.Widgets.tab1_budgetOriginal.datavalue = undefined
    Page.Widgets.tab1_reclass.datavalue = undefined
    Page.Widgets.tab1_additional.datavalue = undefined
    Page.Widgets.tab1_adjustment.datavalue = undefined
    Page.Widgets.tab1_afterAdjustment.datavalue = undefined
    Page.Widgets.tab1_createdAt.datavalue = undefined
    Page.Widgets.tab1_createdBy.datavalue = undefined
    Page.Widgets.tab1_coa.datavalue = undefined
    Page.Widgets.tab1_remarks.datavalue = ''
    Page.Widgets.tab1_docno.datavalue = ''
    start_date = undefined
    end_date = undefined
    // $(".flatpickr").flatpickr().clear();
    // $('.flatpickr').flatpickr({
    //     mode: 'range',
    //     dateFormat: 'd.m.Y',
    //     ariaDateFormat: 'Y-m-d',
    //     onChange: function(selectedDates) {
    //         var _this = this;
    //         var dateArr = selectedDates.map(function(date) {
    //             return _this.formatDate(date, 'Y-m-d');
    //         });
    //         start_date = dateArr[0];
    //         end_date = dateArr[1];

    //         return dateArr[0] + ' to ' + dateArr[1]
    //     },
    // })


    $('.flatpickr').daterangepicker({
        opens: 'left'
    }, function(start, end, label) {
        start_date = start.format('YYYY-MM-DD')
        end_date = end.format('YYYY-MM-DD')
    });
    // $('.flatpickr .cancelBtn').click();
    $('.flatpickr').val('');

    Page.btn_filterClick()
};

Page.tab1_arrowtopClick = function($event, widget) {
    Page.Variables.boolean_showTab1.dataSet.dataValue = false
};

Page.tab1_arrowbottomClick = function($event, widget) {
    Page.Variables.boolean_showTab1.dataSet.dataValue = true
};

Page.btn_addAdjustmentClick = function($event, widget) {
    Page.Widgets.modal_addAdjustment.open()

    setTimeout(function() {
        $('.flatpickr-single').flatpickr({
            enableTime: true,
            dateFormat: 'd/m/Y H:i',
            ariaDateFormat: 'Y-m-d H:i',
            onChange: function(selectedDates, dateStr, instance) {
                Page.Widgets.label_add_date.show = false
            },
        })


    }, 1000);

};
Page.btn_addClick = function($event, widget) {
    var formValid = true
    // var date = Page.Widgets.add_date.datavalue

    var month = Page.Widgets.add_month.datavalue
    var remarks = Page.Widgets.add_remarks.datavalue
    var amount = Page.Variables.model_add_amount.dataSet.dataValue
    var coa = Page.Widgets.add_coa.datavalue

    if (amount.includes('-')) {
        amount = -Number(amount.replace(/\D/g, ''))
    } else {
        amount = Number(amount.replace(/\D/g, ''))
    }


    if (month == undefined || month == '' || month == null) {
        formValid = false
        Page.Widgets.label_add_month.show = true
    }
    if (amount == undefined || amount == '' || amount == null) {
        formValid = false
        Page.Widgets.label_add_amount.caption = 'This Field is required'
        Page.Widgets.label_add_amount.show = true
    } else {
        if (amount < 0) {
            if ((Page.Variables.totalBudgetAfterAdjustment.dataSet.dataValue - Math.abs(amount)) < 0) {
                formValid = false
                Page.Widgets.label_add_amount.caption = 'Amount not correct, amount of budget after adjustment : ' + Page.App.formatCurrency(Page.Variables.totalBudgetAfterAdjustment.dataSet.dataValue)
                Page.Widgets.label_add_amount.show = true
            }
        }
    }
    if (coa == undefined || coa == '' || coa == null) {
        formValid = false
        Page.Widgets.label_add_coa.show = true
    }
    if (remarks == undefined || remarks == '' || remarks == null) {
        formValid = false
        Page.Widgets.label_add_remarks.show = true
    }

    if (formValid == true) {
        Page.Widgets.spinner_modal.show = true
        Page.Variables.forCheckData.listRecords({
            filterFields: {
                "bhId": {
                    "value": Page.pageParams.id
                },
                "bdAdjustmentType": {
                    "value": 'ADJUSTMENT'
                }
            }
        }, function(data) {
            var no_doc = data.length + 1
            // var last_afterAdjustment = Page.Variables.sp_BudgetDetail2.dataSet.content[Page.Variables.sp_BudgetDetail2.dataSet.content.length - 1].bdAfterAdjustment
            // if (last_afterAdjustment == null || last_afterAdjustment == '') {
            //     last_afterAdjustment = 0
            // }

            let today = new Date();
            let date = today.getFullYear() + '-' + (today.getMonth() + 1) + '-' + today.getDate();
            let time = today.getHours() + ':' + today.getMinutes() + ':' + today.getSeconds();




            Page.Variables.q_insertBudgetDetail.invoke({
                "inputFields": {
                    "bhId": Page.pageParams.id,
                    "bdAdjustment": amount,
                    "bdAdjustmentType": "ADJUSTMENT",
                    "bdDocumentId": '4000-' + Page.formatString(no_doc, 3),
                    "bdRemarks": remarks,
                    "createdBy": Page.App.Variables.loggedInUser.dataSet.name,
                    "createdAt": date + ' ' + time,
                    "rCatId": null,
                    "ubCatId": null,
                    "coaId": coa
                }
            }).
            then(function() {
                Page.Widgets.spinner_modal.show = false;
                Page.Widgets.modal_addAdjustment.close()
                update_dataTotalAdjustment();
                window.localStorage.removeItem("budget-session");
                App.Actions.appNotification.setMessage("Data Insert Successfully")
                App.Actions.appNotification.setToasterClass("success")
                App.Actions.appNotification.getToasterDuration(5000)
                App.Actions.appNotification.invoke()
                Page.Variables.model_add_amount.dataSet.dataValue = ''
            }).catch(function(error) {
                Page.Widgets.spinner_modal.show = false;
            })
        })
    }


};
Page.add_monthChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.label_add_month.show = false
};











//Usage
Page.table_spendUsageBeforedatarender = async function(widget, $data) {
    Page.Variables.totalUsageBudgetOriginal.dataSet.datavalue = 0
    Page.Variables.totalUsageBudgetAfterAdjustment.dataSet.datavalue = 0
    Page.Variables.totalUsageReserved.dataSet.datavalue = 0
    Page.Variables.totalUsageCommitment.dataSet.datavalue = 0
    Page.Variables.totalUsageActual.dataSet.datavalue = 0
    Page.Variables.totalUsageAvailable.dataSet.datavalue = 0;

    var urutanDocPR = 0

    var budgetAfterAdjustment = 0
    for (var i = 0; i < $data.length; i++) {
        $data[i].num = noUsage++
            if ($data[i].type.includes('PR')) {
                var numberDocPR = await Page.generateDocNumPR($data[i], urutanDocPR)
                urutanDocPR = numberDocPR

                var docPR = $data[i].docNumber
                $data[i].docNumberDatabase = docPR
                $data[i].docNumber = docPR + ' - ' + Page.formatString(urutanDocPR, 3)
            }

        //Edit 14 Desember
        if ($data[i].type.includes('BEGINNING')) {
            budgetAfterAdjustment = $data[i].budgetAfterAdjustment
            $data[i].available = $data[i].budgetAfterAdjustment
            totalBudgetAfterAdjustment = budgetAfterAdjustment - ($data[i].reserved + $data[i].commitment + $data[i].actual)

            //Total
            Page.Variables.totalUsageBudgetAfterAdjustment.dataSet.dataValue = budgetAfterAdjustment
        } else {
            totalBudgetAfterAdjustment = totalBudgetAfterAdjustment - ($data[i].reserved + $data[i].commitment + $data[i].actual)
            $data[i].available = totalBudgetAfterAdjustment
            // console.log($data[i].available)
        }

        if ($data[i].type.includes('PR') || $data[i].type.includes('PO') || $data[i].type.includes('GR')) {
            budgetAfterAdjustment = '-'
        }
        $data[i].budgetAfterAdjustment = budgetAfterAdjustment
        //Edit 14 Desember



        //Total
        Page.Variables.totalUsageReserved.dataSet.dataValue = Page.Variables.totalUsageReserved.dataSet.dataValue + $data[i].reserved
        Page.Variables.totalUsageCommitment.dataSet.dataValue = Page.Variables.totalUsageCommitment.dataSet.dataValue + $data[i].commitment
        Page.Variables.totalUsageActual.dataSet.dataValue = Page.Variables.totalUsageActual.dataSet.dataValue + $data[i].actual

        if ($data[i].type.includes('RECLASS') || $data[i].type.includes('ADDITIONAL')) {
            $data[i].coa = '-'
        }




        if ($data.length == (i + 1)) {
            //Budget Original
            var bdOriginal = $data.filter(item =>
                item.type == 'BEGINNING'
            )

            if (bdOriginal.length > 0) {
                Page.Variables.totalUsageBudgetOriginal.dataSet.dataValue = bdOriginal[0].budgetOriginal
            }



            // Page.Variables.totalUsageAvailable.dataSet.dataValue = $data[i].available
            Page.Variables.totalUsageAvailable.dataSet.dataValue = Math.abs(Page.Variables.totalUsageBudgetAfterAdjustment.dataSet.dataValue - Page.Variables.totalUsageReserved.dataSet.dataValue - Page.Variables.totalUsageCommitment.dataSet.dataValue - Page.Variables.totalUsageActual.dataSet.dataValue)
        }
    }
    excelData = $data;
};
Page.tab2_arrowbottomClick = function($event, widget) {
    Page.Variables.boolean_showTab2.dataSet.dataValue = true
};
Page.tab2_arrowtopClick = function($event, widget) {
    Page.Variables.boolean_showTab2.dataSet.dataValue = false
};

Page.btn_clearFilter_tab2Click = function($event, widget) {
    Page.Widgets.tab2_type.datavalue = undefined
    Page.Widgets.tab2_budgetOriginal.datavalue = undefined
    Page.Widgets.tab2_budgetAdjustment.datavalue = undefined
    Page.Widgets.tab2_reserve.datavalue = undefined
    Page.Widgets.tab2_commitment.datavalue = undefined
    Page.Widgets.tab2_actual.datavalue = undefined
    Page.Widgets.tab2_available.datavalue = undefined
    Page.Widgets.tab2_date.datavalue = undefined
    Page.Widgets.tab2_user.datavalue = undefined
    Page.Widgets.tab2_coa.datavalue = undefined
    Page.Widgets.tab2_remarks.datavalue = undefined
    Page.Widgets.tab2_docno.datavalue = undefined
    start_date_tab2 = undefined
    end_date_tab2 = undefined
    // $(".flatpickr_tab2").flatpickr().clear();
    // $('.flatpickr_tab2').flatpickr({
    //     mode: 'range',
    //     dateFormat: 'd.m.Y',
    //     ariaDateFormat: 'Y-m-d',
    //     onChange: function(selectedDates) {
    //         var _this = this;
    //         var dateArr = selectedDates.map(function(date) {
    //             return _this.formatDate(date, 'Y-m-d');
    //         });
    //         start_date_tab2 = dateArr[0];
    //         end_date_tab2 = dateArr[1];

    //         return dateArr[0] + ' to ' + dateArr[1]
    //     },
    // })


    $('.flatpickr_tab2').daterangepicker({
        opens: 'left'
    }, function(start, end, label) {
        start_date_tab2 = start.format('YYYY-MM-DD')
        end_date_tab2 = end.format('YYYY-MM-DD')
    });
    $('.flatpickr_tab2 .cancelBtn').click();

    Page.btn_filter_tab2Click()

};
Page.btn_filter_tab2Click = function($event, widget) {
    Page.Variables.totalUsageBudgetOriginal.dataSet.datavalue = 0
    Page.Variables.totalUsageBudgetAfterAdjustment.dataSet.datavalue = 0
    Page.Variables.totalUsageReserved.dataSet.datavalue = 0
    Page.Variables.totalUsageCommitment.dataSet.datavalue = 0
    Page.Variables.totalUsageActual.dataSet.datavalue = 0
    Page.Variables.totalUsageAvailable.dataSet.datavalue = 0

    var type = Page.Widgets.tab2_type.datavalue
    var value_original = Page.Widgets.tab2_budgetOriginal.datavalue
    var value_adjustment = Page.Widgets.tab2_budgetAdjustment.datavalue
    var value_reserve = Page.Widgets.tab2_reserve.datavalue
    var value_commitment = Page.Widgets.tab2_commitment.datavalue
    var value_actual = Page.Widgets.tab2_actual.datavalue
    var value_available = Page.Widgets.tab2_available.datavalue
    var user = Page.Widgets.tab2_user.datavalue
    var coa = Page.Widgets.tab2_coa.datavalue
    var remarks = Page.Widgets.tab2_remarks.datavalue
    var docNumber = Page.Widgets.tab2_docno.datavalue


    if (type == 'all' || type == undefined || type == '') type = 'NULL';
    if (value_original == 'all' || value_original == undefined || value_original == '') value_original = 'NULL'
    if (value_adjustment == 'all' || value_adjustment == undefined || value_adjustment == '') value_adjustment = 'NULL'
    if (value_reserve == 'all' || value_reserve == undefined || value_reserve == '') value_reserve = 'NULL'
    if (value_commitment == 'all' || value_commitment == undefined || value_commitment == '') value_commitment = 'NULL'
    if (value_actual == 'all' || value_actual == undefined || value_actual == '') value_actual = 'NULL'
    if (value_available == 'all' || value_available == undefined || value_available == '') value_available = 'NULL'
    if (coa == 'all' || coa == undefined || coa == '') coa = 'NULL'
    if (user == 'all' || user == undefined || user == '') user = 'NULL'
    if (start_date_tab2 == undefined) start_date_tab2 = 'NULL'
    if (end_date_tab2 == undefined) end_date_tab2 = 'NULL'
    if (remarks == '' || remarks == undefined) remarks = 'NULL'
    if (docNumber == 'all' || docNumber == '' || docNumber == undefined) docNumber = 'NULL'

    if (value_available == 'NULL') {
        Page.Variables.sp_UsageBudgetDetails.invoke({
            inputFields: {
                bh_id: Page.pageParams.id,
                type_data: type,
                value_beginning: value_original,
                value_adjustment: value_adjustment,
                value_reserve: value_reserve,
                value_commitment: value_commitment,
                value_actual: value_actual,
                created_at_start: start_date_tab2,
                created_at_end: end_date_tab2,
                created_by: user,
                coa: coa,
                remarks: remarks,
                docNumber: docNumber
            }
        })
    } else {
        Page.Variables.sp_UsageBudgetDetails.dataSet.content = []
    }
};

Page.container114Click = function($event, widget, item, currentItemWidgets) {
    //CLICK goto Detail Data
    if (item.docNumber != null) {
        if (item.type == 'ADDITIONAL') {
            var id = item.docNumber.substring(5);
            Page.Actions.goto_additional.invoke({
                data: {
                    baId: id
                }
            })
        } else if (item.type == 'RECLASS') {
            var id = item.docNumber.substring(5);
            Page.Actions.goto_reclass.invoke({
                data: {
                    bdRcId: id
                }
            })
        } else if (item.type == 'PR') {
            Page.Widgets.spinner_table_adjustment.show = true
            Page.Variables.get_pr.listRecords({
                filterFields: {
                    "prRef": {
                        "value": item.docNumberDatabase
                    }
                }
            }, function(data_pr) {
                Page.Widgets.spinner_table_adjustment.show = false
                if (data_pr.length > 0) {
                    Page.Actions.goto_pr.invoke({
                        data: {
                            prId: data_pr[0].prId
                        }
                    })
                } else {
                    App.Actions.appNotification.setMessage("Document Number is null")
                    App.Actions.appNotification.setToasterClass("success")
                    App.Actions.appNotification.getToasterDuration(5000)
                    App.Actions.appNotification.invoke()
                }

            })
        }
    } else {
        App.Actions.appNotification.setMessage("Document Number is null")
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()
    }
};

Page.container77Click = function($event, widget, item, currentItemWidgets) {
    //CLICK goto Detail Data
    if (item.docNumber != null) {
        if (item.type == 'ADDITIONAL') {
            var id = item.docNumber.substring(5);
            Page.Actions.goto_additional.invoke({
                data: {
                    baId: id
                }
            })
        } else if (item.type == 'RECLASS') {
            var id = item.docNumber.substring(5);
            Page.Actions.goto_reclass.invoke({
                data: {
                    bdRcId: id
                }
            })
        } else if (item.type == 'PR') {
            Page.Widgets.spinner_table_usage.show = true
            Page.Variables.get_pr.listRecords({
                filterFields: {
                    "prRef": {
                        "value": item.docNumberDatabase
                    }
                }
            }, function(data_pr) {
                Page.Widgets.spinner_table_usage.show = false
                if (data_pr.length > 0) {
                    Page.Actions.goto_pr.invoke({
                        data: {
                            prId: data_pr[0].prId
                        }
                    })
                } else {
                    App.Actions.appNotification.setMessage("Document Number is null")
                    App.Actions.appNotification.setToasterClass("success")
                    App.Actions.appNotification.getToasterDuration(5000)
                    App.Actions.appNotification.invoke()
                }

            })
        }
    } else {
        App.Actions.appNotification.setMessage("Document Number is null")
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()
    }
};


Page.btn_excelClick = function($event, widget) {
    const workbook = new ExcelJS.Workbook();
    let sheet = workbook.addWorksheet('BUDGET REPORT (2) ');;
    let columnWidth = [0, 5, 15, 20, 10, 50, 20, 20, 0, 20, 20, 20, 25, 0, 20, 20, 20, 20, 20]



    let alignCenter = {
        vertical: 'middle',
        'horizontal': 'center'
    };

    let border = {
        top: {
            style: 'thin'
        },
        left: {
            style: 'thin'
        },
        bottom: {
            style: 'thin'
        },
        right: {
            style: 'thin'
        }
    };

    let boldCell = function(cells) {
        cells.forEach(el => {
            sheet.getCell(el).font = {
                bold: true
            }
        });
        return;
    }


    // Style
    sheet.getColumn('D').alignment = alignCenter;
    sheet.getRow(12).alignment = alignCenter;
    sheet.getRow(13).alignment = alignCenter;
    sheet.getColumn('S').alignment = alignCenter;
    boldCell(["B3", "B4", "B5", "B6", "B7", "B8", "B9"]);

    sheet.mergeCells('B12:B13');
    sheet.mergeCells('C12:C13');
    sheet.mergeCells('D12:D13');
    sheet.mergeCells('E12:E13');
    sheet.mergeCells('F12:F13');
    sheet.mergeCells('G12:G13');
    sheet.mergeCells('H12:H13');
    sheet.mergeCells('J12:L12');
    sheet.mergeCells('M12:M13');
    sheet.mergeCells('O12:S12');

    var data_border = ["B12", "C12", "D12", "E12", "F12", "G12", "H12", "J12", "M12", "J13", "K13", "L13", "O12", "O13", "P13", "Q13", "R13", "S13"]
    data_border.map(key => {
        sheet.getCell(key).border = {
            top: {
                style: 'thin'
            },
            left: {
                style: 'thin'
            },
            bottom: {
                style: 'thin'
            },
            right: {
                style: 'thin'
            }
        }
    })




    //Data
    let data = []
    if (tab_active == 'tab_1') {
        data = Page.Variables.sp_BudgetDetails.dataSet.content
    } else {
        data = excelData
        // data = Page.Variables.sp_UsageBudgetDetails.dataSet.content
    }


    // let row = 2;




    // header
    sheet.getCell("D3").value = "Budget Year";
    sheet.getCell("D4").value = "Company";
    sheet.getCell("D5").value = "Department";
    sheet.getCell("D6").value = "Brand";
    sheet.getCell("D7").value = "Account";
    sheet.getCell("D8").value = "IO";
    sheet.getCell("D9").value = "Currency";

    sheet.getCell("E3").value = ":";
    sheet.getCell("E4").value = ":";
    sheet.getCell("E5").value = ":";
    sheet.getCell("E6").value = ":";
    sheet.getCell("E7").value = ":";
    sheet.getCell("E8").value = ":";
    sheet.getCell("E9").value = ":";

    sheet.getCell("F3").value = Page.Variables.q_budgetHeader_Detail2.dataSet[0].bhYear;
    sheet.getCell("F4").value = Page.Variables.q_budgetHeader_Detail2.dataSet[0].ctitle
    sheet.getCell("F5").value = Page.Variables.q_budgetHeader_Detail2.dataSet[0].department;
    sheet.getCell("F6").value = Page.Variables.q_budgetHeader_Detail2.dataSet[0].brTitle
    sheet.getCell("F7").value = Page.Variables.q_budgetHeader_Detail2.dataSet[0].accTitle
    sheet.getCell("F8").value = Page.Variables.q_budgetHeader_Detail2.dataSet[0].ioNumber;
    sheet.getCell("F9").value = Page.Variables.q_budgetHeader_Detail2.dataSet[0].bhCurrency;

    sheet.getCell("M6").value = new Date().getDate().toString().padStart(2, "0") + '/' + (Number(new Date().getMonth()) + 1).toString().padStart(2, "0") + '/' + new Date().getFullYear();
    sheet.getCell("M7").value = "Data autogenerate by system";



    //Activities
    sheet.getCell("B12").value = "No.";
    sheet.getCell("C12").value = "Type";
    sheet.getCell("D12").value = "Date";
    sheet.getCell("E12").value = "No. Case";
    sheet.getCell("F12").value = "Remark/Activities";
    sheet.getCell("G12").value = "User";
    sheet.getCell("H12").value = "Budget Original";

    sheet.getCell("J12").value = "Total Adjustment";
    sheet.getCell("J13").value = "Reclass";
    sheet.getCell("K13").value = "Additional";
    sheet.getCell("L13").value = "Adjustment";

    sheet.getCell("M12").value = "Budget After Adjustment";


    sheet.getCell("O12").value = "Spend / Usage";
    sheet.getCell("O13").value = "Reserved";
    sheet.getCell("P13").value = "commitment";
    sheet.getCell("Q13").value = "Actual";
    sheet.getCell("R13").value = "Available";
    sheet.getCell("S13").value = "COA";





    var row_activity = 14
    var no_activity = 1
    var total_reclass = 0
    var total_additional = 0
    var total_adjustment = 0
    var total_after_adjustment = 0
    var total_original = 0
    var total_reserved = 0
    var total_commitment = 0
    var total_actual = 0
    data.forEach(result => {
        //Activities
        sheet.getCell("B" + row_activity).value = no_activity
        if (result.type != null) {
            sheet.getCell("C" + row_activity).value = result.type.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">")
        }
        sheet.getCell("D" + row_activity).value = Page.formatDate(result.datetime)
        if (result.docNumber != null) {
            sheet.getCell("E" + row_activity).value = result.docNumber.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">")
        }
        if (result.remarks != null) {
            sheet.getCell("F" + row_activity).value = result.remarks.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">")
        }
        if (result.createdBy != null) {
            sheet.getCell("G" + row_activity).value = result.createdBy.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">")
        }

        if (result.type == 'BEGINNING') {
            total_original = result.budgetOriginal
            sheet.getCell("H" + row_activity).value = result.budgetOriginal
            sheet.getCell("H" + row_activity).numFmt = '#,##0.00;[Red]-#,##0.00';
        }



        //ADJUSTMENT
        if (result.type.toLowerCase() == 'adjustment') {
            sheet.getCell("L" + row_activity).value = result.adjustment
            sheet.getCell("L" + row_activity).numFmt = '#,##0.00;[Red]-#,##0.00';

            total_adjustment = total_adjustment + result.adjustment
        }
        //RECLASS
        if (result.type.toLowerCase() == 'reclass') {
            sheet.getCell("J" + row_activity).value = result.reclass
            sheet.getCell("J" + row_activity).numFmt = '#,##0.00;[Red]-#,##0.00';

            total_reclass = total_reclass + result.reclass
        }

        //ADDITIONAL
        if (result.type.toLowerCase() == 'additional') {
            sheet.getCell("K" + row_activity).value = result.additional
            sheet.getCell("K" + row_activity).numFmt = '#,##0.00;[Red]-#,##0.00';

            total_additional = total_additional + result.additional
        }

        sheet.getCell("M" + row_activity).value = result.budgetAfterAdjustment
        sheet.getCell("M" + row_activity).numFmt = '#,##0.00;[Red]-#,##0.00';

        total_after_adjustment = total_after_adjustment + result.budgetAfterAdjustment


        //PR
        if (result.type.toLowerCase() == 'pr') {
            sheet.getCell("O" + row_activity).value = result.reserved
            sheet.getCell("O" + row_activity).numFmt = '#,##0.00;[Red]-#,##0.00';

            total_reserved = total_reserved + result.reserved
        }


        //PO
        if (result.type.toLowerCase() == 'po') {
            sheet.getCell("P" + row_activity).value = result.commitment
            sheet.getCell("P" + row_activity).numFmt = '#,##0.00;[Red]-#,##0.00';
            total_commitment = total_commitment + result.commitment

            if (result.reserved != 0) {
                sheet.getCell("O" + row_activity).value = result.reserved
                sheet.getCell("O" + row_activity).numFmt = '#,##0.00;[Red]-#,##0.00';
                total_reserved = total_reserved + result.reserved
            }

        }

        //GR
        if (result.type.toLowerCase() == 'gr') {
            sheet.getCell("Q" + row_activity).value = result.actual
            sheet.getCell("Q" + row_activity).numFmt = '#,##0.00;[Red]-#,##0.00';

            if (result.commitment != 0) {
                sheet.getCell("P" + row_activity).value = result.commitment
                sheet.getCell("P" + row_activity).numFmt = '#,##0.00;[Red]-#,##0.00';
                total_commitment = total_commitment + result.commitment
            }

            total_actual = total_actual + result.actual
        }

        //available
        sheet.getCell("R" + row_activity).value = result.available
        sheet.getCell("R" + row_activity).numFmt = '#,##0.00;[Red]-#,##0.00';


        if (result.type.toLowerCase() != 'reclass' || result.type.toLowerCase() != 'additional') {
            sheet.getCell("S" + row_activity).value = result.coa
        }


        if (no_activity == data.length) {
            sheet.getCell("H" + (row_activity + 2)).value = total_original
            sheet.getCell("J" + (row_activity + 2)).value = total_reclass
            sheet.getCell("K" + (row_activity + 2)).value = total_additional
            sheet.getCell("L" + (row_activity + 2)).value = total_adjustment
            sheet.getCell("M" + (row_activity + 2)).value = result.budgetAfterAdjustment
            sheet.getCell("O" + (row_activity + 2)).value = total_reserved
            sheet.getCell("P" + (row_activity + 2)).value = total_commitment
            sheet.getCell("Q" + (row_activity + 2)).value = total_actual
            sheet.getCell("R" + (row_activity + 2)).value = result.available

            sheet.getCell("B" + (row_activity + 2)).value = 'TOTAL'
            sheet.mergeCells('B' + (row_activity + 2) + ':G' + (row_activity + 2));


            sheet.getCell("H" + (row_activity + 2)).numFmt = '#,##0.00;[Red]-#,##0.00';
            sheet.getCell("J" + (row_activity + 2)).numFmt = '#,##0.00;[Red]-#,##0.00';
            sheet.getCell("K" + (row_activity + 2)).numFmt = '#,##0.00;[Red]-#,##0.00';
            sheet.getCell("L" + (row_activity + 2)).numFmt = '#,##0.00;[Red]-#,##0.00';
            sheet.getCell("M" + (row_activity + 2)).numFmt = '#,##0.00;[Red]-#,##0.00';
            sheet.getCell("O" + (row_activity + 2)).numFmt = '#,##0.00;[Red]-#,##0.00';
            sheet.getCell("P" + (row_activity + 2)).numFmt = '#,##0.00;[Red]-#,##0.00';
            sheet.getCell("Q" + (row_activity + 2)).numFmt = '#,##0.00;[Red]-#,##0.00';
            sheet.getCell("R" + (row_activity + 2)).numFmt = '#,##0.00;[Red]-#,##0.00';


            var data_border_isi = ["B", "H", "J", "K", "L", "M", "O", "P", "Q", "R", "S"]
            data_border_isi.map(key => {
                sheet.getCell(key + (row_activity + 2)).border = {
                    top: {
                        style: 'thin'
                    },
                    left: {
                        style: 'thin'
                    },
                    bottom: {
                        style: 'thin'
                    },
                    right: {
                        style: 'thin'
                    }
                }
            })
        }


        //border
        var data_border_isi = ["B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "O", "P", "Q", "R", "S"]
        data_border_isi.map(key => {
            sheet.getCell(key + row_activity).border = {
                top: {
                    style: 'thin'
                },
                left: {
                    style: 'thin'
                },
                bottom: {
                    style: 'thin'
                },
                right: {
                    style: 'thin'
                }
            }
        })


        row_activity++;
        no_activity++;
    });







    // data.forEach(el => {
    //     sheet.getCell("A" + row).value = el.eplEmployeeLevel.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">")
    //     sheet.getCell("B" + row).value = el.eplAction.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">")
    //     sheet.getCell("C" + row).value = el.eplApproxMin
    //     sheet.getCell("D" + row).value = el.eplApproxMax
    //     sheet.getCell("E" + row).value = el.eplStatus.replace(/&amp;/g, "&").replace(/&lt;/g, "<").replace(/&gt;/g, ">")
    //     row++;
    // });


    // Adjust column width
    sheet.columns.forEach((col, index) => {
        if (columnWidth[index]) {
            col.width = columnWidth[index];
        }
    });


    // exporting
    workbook.xlsx.writeBuffer().then(function(datas) {
        var blob = new Blob([datas], {
            type: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        });

        let currentTime = new Date().getDate().toString().padStart(2, "0") + (Number(new Date().getMonth()) + 1).toString().padStart(2, "0") + new Date().getFullYear();
        saveAs(blob, "Budget Detail 2" + currentTime + ".xlsx");
    })
};



Page.formatString = function(num, size) {
    var string = String(num);
    while (string.length < (size || 2)) {
        string = "0" + string;
    }
    return string;
}
Page.add_remarksKeypress = function($event, widget, newVal, oldVal) {
    Page.Widgets.label_add_remarks.show = false
};
Page.add_coaChange = function($event, widget, newVal, oldVal) {
    Page.Widgets.label_add_coa.show = false
};
Page.add_amountKeypress = function($event, widget) {
    Page.Widgets.label_add_amount.show = false
};
Page.add_amountKeyup = function($event, widget) {
    Page.Widgets.label_add_amount.show = false
};

Page.add_amountChange = function($event, widget, newVal, oldVal) {
    if (Page.Widgets.add_amount.datavalue.includes('-')) {
        Page.Variables.model_add_amount.dataSet.dataValue = '-' + Page.App.formatCurrency(Page.Widgets.add_amount.datavalue.replace(/\D/g, ''))
    } else {
        Page.Variables.model_add_amount.dataSet.dataValue = Page.App.formatCurrency(Page.Widgets.add_amount.datavalue.replace(/\D/g, ''))
    }

};
Page.modal_addAdjustmentOpened = function($event, widget) {
    $(document).ready(function() {
        $(".only_number").keypress(function(value) {
            var charCode = value.charCode
            if (charCode === 45) {
                return true
            } else {
                if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                    return false;
                }
            }

            return true;
        });
    });
};

Page.tabpane1Select = function($event, widget) {
    tab_active = 'tab_1'
};

Page.tabpane2Select = function($event, widget) {
    tab_active = 'tab_2'
};
Page.label12Click = function($event, widget, item, currentItemWidgets) {
    if (item.docNumber != null) {
        if (item.type == 'ADDITIONAL') {
            var id = item.docNumber.substring(5);
            Page.Actions.goto_additional.invoke({
                data: {
                    baId: id
                }
            })
        } else if (item.type == 'RECLASS') {
            var id = item.docNumber.substring(5);
            Page.Actions.goto_reclass.invoke({
                data: {
                    bdRcId: id
                }
            })
        } else if (item.type == 'PR') {
            Page.Widgets.spinner_table_adjustment.show = true
            Page.Variables.get_pr.listRecords({
                filterFields: {
                    "prRef": {
                        "value": item.docNumberDatabase
                    }
                }
            }, function(data_pr) {
                Page.Widgets.spinner_table_adjustment.show = false
                if (data_pr.length > 0) {
                    Page.Actions.goto_pr.invoke({
                        data: {
                            prId: data_pr[0].prId
                        }
                    })
                } else {
                    App.Actions.appNotification.setMessage("Document Number is null")
                    App.Actions.appNotification.setToasterClass("success")
                    App.Actions.appNotification.getToasterDuration(5000)
                    App.Actions.appNotification.invoke()
                }

            })
        }
    } else {
        App.Actions.appNotification.setMessage("Document Number is null")
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()
    }
};
Page.label139Click = function($event, widget, item, currentItemWidgets) {
    //CLICK goto Detail Data
    if (item.docNumber != null) {
        if (item.type == 'ADDITIONAL') {
            var id = item.docNumber.substring(5);
            Page.Actions.goto_additional.invoke({
                data: {
                    baId: id
                }
            })
        } else if (item.type == 'RECLASS') {
            var id = item.docNumber.substring(5);
            Page.Actions.goto_reclass.invoke({
                data: {
                    bdRcId: id
                }
            })
        } else if (item.type == 'PR') {
            Page.Widgets.spinner_table_usage.show = true
            Page.Variables.get_pr.listRecords({
                filterFields: {
                    "prRef": {
                        "value": item.docNumberDatabase
                    }
                }
            }, function(data_pr) {
                Page.Widgets.spinner_table_usage.show = false
                if (data_pr.length > 0) {
                    Page.Actions.goto_pr.invoke({
                        data: {
                            prId: data_pr[0].prId
                        }
                    })
                } else {
                    App.Actions.appNotification.setMessage("Document Number is null")
                    App.Actions.appNotification.setToasterClass("success")
                    App.Actions.appNotification.getToasterDuration(5000)
                    App.Actions.appNotification.invoke()
                }

            })
        }
    } else {
        App.Actions.appNotification.setMessage("Document Number is null")
        App.Actions.appNotification.setToasterClass("success")
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()
    }
};

Page.sp_UsageBudgetDetailsonBeforeDatasetReady = function(variable, data) {

};