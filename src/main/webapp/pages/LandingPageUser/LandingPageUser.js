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

    // Page.Variables.svGetForgotUser.invoke().then(function() {
    //     if (Page.Variables.svGetForgotUser.dataSet.abStatus == 'New') {
    //         Page.Widgets.picture1.show = false
    //     }
    // })
};

Page.btnApproveClick = function($event, widget) {
    Page.Variables.svUpdateStatus.invoke({
        inputFields: {
            "id": Page.Widgets.list1.selecteditem.abId,
            "status": "Approved"
        }
    });
    Page.sendEmail()
};

Page.btnRejectClick = function($event, widget) {
    Page.Variables.svUpdateStatus.invoke({
        inputFields: {
            "id": Page.Widgets.list1.selecteditem.abId,
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
                "to": Page.Widgets.list1.selecteditem.abCompanyEmail,
                "cc": "",
                "subject": `Forgot User Vendor`,
                "body": `<p> Dear Valued Vendor, </p>
                            <p> Your vendor has been validation.
                            Please open this <a href="https://aionomic.aio.co.id:8080/#/FormNewPassword?vendorCode=${Page.pageParams.vendorCode}" target="_blank">link </a> and insert your company email and new password.
                            <p> If you have other queries, please ask our assistance or email to ekusumaningtyas@aio.co.id
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
                "to": Page.Widgets.list1.selecteditem.abCompanyEmail,
                "cc": "",
                "subject": `Forgot User Vendor`,
                "body": `<p> Dear Valued Vendor, </p>
                            <p> Thank you for your information. 
                                Unfortunately, your data was not found. 
                                To continue, please register first with this <a href="https://aionomic.aio.co.id:8080/#/CompanyDomicilie" target="_blank">link </a>
                            <p> If you have other queries, please ask our assistance or email to ekusumaningtyas@aio.co.id
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
};

Page.svUpdateStatusonSuccess = function(variable, data) {
    Page.Widgets.picture1.show = false
};

Page.picture1Click = function($event, widget, item, currentItemWidgets) {
    Page.Variables.svGetDataVendor.invoke({
        inputFields: {
            "npwpNumber": (Page.Widgets.list1.selecteditem.abCompanyNpwp.length == 15 ? Page.Widgets.list1.selecteditem.abCompanyNpwp : null),
            "idDirector": (Page.Widgets.list1.selecteditem.abCompanyNpwp.length == 15 ? null : Page.Widgets.list1.selecteditem.abKtp)
        }
    })
};

Page.select1Change = function($event, widget, newVal, oldVal) {
    Page.Variables.dvGetForgotUser.maxResults = newVal
    Page.Variables.dvGetForgotUser.update()
};

Page.select3Change = function($event, widget, newVal, oldVal) {
    Page.Variables.dvGetForgotUser.update()
};
Page.searchCompanyKeydown = function($event, widget) {
    Page.Variables.dvGetForgotUser.update()
};
Page.searchCompanyKeyup = function($event, widget) {
    Page.Variables.dvGetForgotUser.update()
};
Page.container30Click = function($event, widget) {
    window.open("https://aionomic.aio.co.id:8080/#/LoginAIO");
};
Page.container31Click = function($event, widget) {
    window.open("http://ivendor.aio.co.id ");
};