/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */

/* perform any action on widgets/variables within this block */
Page.onReady = function() {
    Page.Variables.modelNull.setValue = null;
};

Page.btnApproveClick = function($event, widget) {
    // Page.pageParams.statusNew = "Approved"
    // Page.Widgets.listForgotUser.selecteditem.abStatus = "Approved"

    // let index = Page.Variables.dvGetForgotUser.dataSet.findIndex(function(e) {
    //     return e.abId == Page.Widgets.listForgotUser.selecteditem.abId;
    // });
    // let parameter = {
    //     abApprovalDate: Page.Widgets.listForgotUser.selecteditem.abApprovalDate,
    //     abCompanyName: Page.Widgets.listForgotUser.selecteditem.abCompanyName,
    //     abCompanyNpwp: Page.Widgets.listForgotUser.selecteditem.abCompanyNpwp,
    //     abCreatedDate: Page.Widgets.listForgotUser.selecteditem.abCreatedDate,
    //     abId: Page.Widgets.listForgotUser.selecteditem.abId,
    //     abKtp: Page.Widgets.listForgotUser.selecteditem.abKtp,
    //     abPuon: Page.Widgets.listForgotUser.selecteditem.abPuon,
    //     abStatus: "Approved",
    //     abUsername: Page.Widgets.listForgotUser.selecteditem.abUsername
    // };
    // Page.Variables.dvGetForgotUser.setItem(index, parameter);


    Page.Variables.svUpdateStatus.invoke({
        inputFields: {
            "id": Page.Widgets.listForgotUser.selecteditem.abId,
            "status": "Approved"
        }
    });
    Page.sendEmail()
};

Page.btnRejectClick = function($event, widget) {
    Page.Variables.svUpdateStatus.invoke({
        inputFields: {
            "id": Page.Widgets.listForgotUser.selecteditem.abId,
            "status": "Rejected"
        }
    });
    Page.sendEmailReject()
};

//send email
Page.sendEmail = function($event, widget) {
    Page.Variables.svNotifyVendorForgotUser.invoke({
        inputFields: {
            RequestBody: {
                "to": Page.Widgets.listForgotUser.selecteditem.abCompanyEmail,
                "cc": "",
                "subject": `Forgot User Vendor`,
                "body": `<p> Dear Valued Vendor, </p>
                            <p> Your registration data has been update.
                            Please open this <a href="https://aionomic.aio.co.id:8080/#/FormNewPassword?vendorCode=${Page.pageParams.vendorCode}" target="_blank">link </a> and insert your company email and new password.
                            <p> If you have other queries, please ask our assistance or email to gsaputra@aio.co.id
                            <br><br><br>
                            <p>Thanks
                            <br>
                            Regards, 
                            E-Vendor AIO
                            <br><br><br>
                            Please do not reply this email`
            }
        }
    }, function(data) {
        console.log(data);
    }, function(error) {});
}

Page.sendEmailReject = function($event, widget) {
    Page.Variables.svNotifyVendorForgotUser.invoke({
        inputFields: {
            RequestBody: {
                "to": Page.Widgets.listForgotUser.selecteditem.abCompanyEmail,
                "cc": "",
                "subject": `Forgot User Vendor`,
                "body": `<p> Dear Valued Vendor, </p>
                            <p> Thank you for your information. 
                                Unfortunately, your data was not found. 
                                To continue, please register first with this <a href="https://aionomic.aio.co.id:8080/#/CompanyDomicilie" target="_blank">link </a>
                            <p> If you have other queries, please ask our assistance or email to gsaputra@aio.co.id
                            <br><br><br>
                            <p>Thanks
                            <br>
                            Regards, 
                            E-Vendor AIO
                            <br><br><br>
                            Please do not reply this email`
            }
        }
    }, function(data) {
        console.log(data);
    }, function(error) {});
}

Page.svGetDataVendoronSuccess = function(variable, data) {
    Page.pageParams.vendorCode = btoa(Page.Variables.svGetDataVendor.dataSet[0].vendorCode)
    if (Page.Variables.svGetDataVendor.dataSet[0].vendorCode == '0         ') {
        Page.Widgets.btnApprove.show = false;
        App.Actions.appNotification.invoke({
            'message': 'Vendor ini belum terdaftar',
            'position': 'top right',
            'class': 'warning'
        })
    }
};

Page.svUpdateStatusonSuccess = function(variable, data) {
    Page.Widgets.picture1.show = false
    Page.Variables.dvGetForgotUser.listRecords({
        "page": Page.Variables.modelPageList.dataSet
    })

};

Page.picture1Click = function($event, widget, item, currentItemWidgets) {
    Page.Variables.svGetDataVendor.invoke({
        inputFields: {
            "npwpNumber": (Page.Widgets.listForgotUser.selecteditem.abCompanyNpwp.length == 15 ? Page.Widgets.listForgotUser.selecteditem.abCompanyNpwp : null),
            "idDirector": (Page.Widgets.listForgotUser.selecteditem.abCompanyNpwp.length == 15 ? null : Page.Widgets.listForgotUser.selecteditem.abKtp)
        }
    }, function(success) {
        Page.Widgets.btnApprove.disabled = false;
    })
};

Page.select1Change = function($event, widget, newVal, oldVal) {
    Page.Variables.dvGetForgotUser.maxResults = newVal
    Page.Variables.dvGetForgotUser.update()
};

Page.select3Change = function($event, widget, newVal, oldVal) {
    // Page.Variables.dvGetForgotUser.update()

    if (Page.Widgets.select3_1.datavalue == 'PHOF') {
        Page.Variables.dvGetForgotUser.listRecords({
            filterFields: {
                abPuon: {
                    "value": "PHOF, PSHO, 0",
                    "matchMode": "IN"
                },
                abStatus: {
                    "value": Page.Widgets.select3.datavalue,
                    "matchMode": "IS_EQUAL_TO"
                },
                abCompanyNpwp: {
                    "value": Page.Widgets.searchCompany.datavalue,
                    "matchMode": "CONTAINS_IGNORE_CASE"
                }
            }
        })
    } else {
        Page.Variables.dvGetForgotUser.listRecords({
            filterFields: {
                abPuon: {
                    "value": Page.Widgets.select3_1.datavalue,
                    "matchMode": "IN"
                },
                abStatus: {
                    "value": Page.Widgets.select3.datavalue,
                    "matchMode": "IS_EQUAL_TO"
                },
                abCompanyNpwp: {
                    "value": Page.Widgets.searchCompany.datavalue,
                    "matchMode": "CONTAINS_IGNORE_CASE"
                }
            }
        })
    }
};
Page.searchCompanyKeydown = function($event, widget) {
    Page.Variables.dvGetForgotUser.update()
};
Page.searchCompanyKeyup = function($event, widget) {
    Page.Variables.dvGetForgotUser.update()
};
Page.listForgotUserSelect = function(widget, $data) {
    console.log('heyyy', Page.Widgets.listForgotUser.selecteditem)
};

Page.dvGetForgotUseronSuccess = function(variable, data) {
    Page.Variables.modelPageList.dataSet = data.pagination.number + 1
}