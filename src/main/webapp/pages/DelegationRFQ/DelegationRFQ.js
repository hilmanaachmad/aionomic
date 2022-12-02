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
        if (App.Variables.loggedInUser.dataSet.roles.indexOf('RFQ-001') !== -1) {

        } else {
            App.Actions.goToPage_Main.invoke()
        }
    }
};

// submit form
Page.button1Click = function($event, widget) {
    Page.Variables.vmErrorMessage.clearData()

    var isValid = true
    var auth = Page.Variables.vdbAuthorization.dataSet

    var data = Page.Variables.vmDelegationModel.dataSet
    data.delToUserId = "emp::" + data.delToUserId
    data.delCreatedAt = new Date().toISOString()

    if (!data.delToUserName) {
        Page.Variables.vmErrorMessage.setValue("employeeName", "this field is required")
        isValid = false
    }
    if (data.delReason == "") {
        Page.Variables.vmErrorMessage.setValue("reason", "this field is required")
        isValid = false
    }
    if (data.delAvailDatetime == "" || data.delExpDatetime == "") {
        Page.Variables.vmErrorMessage.setValue("date", "this field is required")
        isValid = false
    }
    if (!isValid) {
        return
    }

    Page.Widgets.spinner1.show = true
    // record user map history
    Page.Variables.vdbDelegation.createRecord({
        row: data
    }).catch(function(err) {
        console.log(err)
        return Promise.reject({
            message: "Failed set delegation",
            type: "danger"
        })
    }).then(res => {
        return Page.insertRelDelAuth(auth.reverse(), res.delId)
    }).then(function() {
        // get supervisor email
        if (!Page.Variables.vdbEmployeeSupervisor.dataSet.length || !Page.Variables.vdbEmployeeSupervisor.dataSet[0].lgEmailAio || !Page.Variables.vdbEmployeeSupervisor.dataSet[0].lgEmailAio.length) {
            return Promise.reject({
                message: "Failed send email to supervisor: email not found",
                type: "warning"
            })
        } else {
            // send email to supervisor
            return Page.sendEmailSupervisor(Page.Variables.vdbEmployeeSupervisor.dataSet[0], data.delFromUserName, Page.changeFormatDate(data.delAvailDatetime), Page.changeFormatDate(data.delExpDatetime))
        }
    }).then(res => {
        // message
        App.Actions.appNotification.setMessage("Data Saved")
        // type: success, info, warning, danger
        App.Actions.appNotification.setToasterClass("success")
        // delayed
        App.Actions.appNotification.getToasterDuration(5000)
        // invoke
        App.Actions.appNotification.invoke()

        Page.Variables.vmDelegationModel.clearData()
        Page.Widgets.spinner1.show = false
    }).catch(function(err) {
        console.log(err)
        App.Actions.appNotification.setMessage(err.message)
        App.Actions.appNotification.setToasterClass(err.type)
        App.Actions.appNotification.getToasterDuration(5000)
        App.Actions.appNotification.invoke()
        Page.Widgets.spinner1.show = false
    })
};

Page.insertRelDelAuth = function(data, delId) {
    if (!data || !data.length) {
        return Promise.resolve()
    }

    // loop till data empty
    var dt = data.pop()
    return Page.Variables.vdbDelAuth.createRecord({
        row: {
            "delId": delId,
            "authId": dt.authId
        }
    }).then(function(res) {
        return Page.insertRelDelAuth(data, delId)
    }).catch(function(err) {
        console.log(err)
        return Page.insertRelDelAuth(data, delId)
    })
}

// select employee
Page.searchEmployeeSelect = function($event, widget, selectedValue) {
    // Page.Widgets.label7.show = false;
    Page.Variables.vmErrorMessage.setValue("employeeName", "")
    Page.Variables.vmDelegationModel.setValue('delToUserId', selectedValue.nik)
    Page.Variables.vmDelegationModel.setValue('delToUserName', selectedValue.employeeName)
};
Page.dateFromChange = function($event, widget, newVal, oldVal) {
    Page.Variables.vmErrorMessage.setValue("date", "")
};
Page.dateToChange = function($event, widget, newVal, oldVal) {
    // Page.Widgets.label8.show = false;
    Page.Variables.vmErrorMessage.setValue("date", "")
    Page.App.formatDateAndTime(Page.Variables.vmDelegationModel.dataSet.delExpDatetime)
};
Page.textarea2Keyup = function($event, widget) {
    // Page.Widgets.label10.show = false;
    Page.Variables.vmErrorMessage.setValue("reason", "")
};

Page.changeFormatDate = function(data) {
    const d = new Date(data);
    const ye = new Intl.DateTimeFormat('en', {
        year: 'numeric'
    }).format(d);
    const mo = new Intl.DateTimeFormat('en', {
        month: 'long'
    }).format(d);
    const da = new Intl.DateTimeFormat('en', {
        day: '2-digit'
    }).format(d);

    return (`${da} ${mo} ${ye}`);
}

Page.sendEmailSupervisor = function(supervisorData, name, dateFrom, dateTo) {
    return Promise.resolve().then(function() {
        // render email template
        var emailBody = `<html>
            <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800;900&amp;display=swap" rel="stylesheet" />
            <body>
            <table style=" width: 560px;
            		height: 631px;
            		background: white;
            		border-radius: 20px;
            		font-family: Inter;
            		text-align: center; 
            		vertical-align: middle;">
            	<tr>
            		<th>
            			<img width="206" height="103" src="${Page.App.domain}resources/images/logos/login-otsuka.png" />
            		</th>
            	</tr>
            
              <tr style="font-weight: normal;
            		font-size: 16px;
            		line-height: 24px;
            		text-align: center;
            		color: #000000;">
              <td style = "padding-left: 50px; padding-right: 50px;">
               <a>Dear Mr / Mrs,</a><br>
               <a>You have a delegation of Role “Sales” from <a style="font-weight: bold; color: #262626;">${name}</a> from <a style="font-weight: bold; color: #262626;">${dateFrom}</a> to <a style="font-weight: bold; color: #262626;">${dateTo}</a> on the ground of leave. </a> <br>
                <br><br><a>Please click button below to go to the application</a><br><br><br>
            	<a href="${Page.App.domain}#/LoginAIO" target="_blank"
            	style="align-items: center;
            		padding: 12px 20px;
            		width: 122px;
            		height: 41px;
            		color: #fff;
            		background: #1861B7;
            		border-radius: 4px;
            		text-decoration: none;">Login</a>
            	</td>
              </tr>
              
                <tr>
               
              </tr>
              
              <tr>
                <td style="align-items: center;
            		padding: 12px 20px;
            		width: 100%;
            		height: 41px;
            		color: #fff;
            		background: #1861B7;
            		border-radius: 0px 0px 20px 20px;
            		text-decoration: none;">
            		<img width="20" height="15" src="${Page.App.domain}resources/images/logos/icon-footer-mail.png" />
            		
            	<a style="align-items: center;
            		color: #fff;
            		background: #1861B7;
            		text-decoration: none;">Team Procure to Pay</a>
            	</td>
              </tr>
              
            </table>
            
            </body>
            </html>`
        return Page.App.sendEmail(supervisorData.lgEmailAio, "", "Delegation Notification", emailBody, "").catch(function(err) {
            console.log(err)
            return Promise.reject({
                message: "Failed send email to supervisor",
                type: "warning"
            })
        })
    })
}