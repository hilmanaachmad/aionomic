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

    console.log(Page.Variables.vdbEmployeeList)
};

// submit form
Page.button1Click = function($event, widget) {
    console.log("data", App.Variables.loggedInUserData.dataSet);
    Page.Variables.vmErrorMessage.clearData()

    var isValid = true
    var auth = JSON.parse(JSON.stringify(Page.Variables.vdbAuthorization.dataSet)).filter(function(item) {
        return item.authModule == 'PRS'
    })

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
        }
        // send email to supervisor
        return Page.sendEmailSupervisor(Page.Variables.vdbEmployeeSupervisor.dataSet[0])
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

    Page.Variables.vmErrorMessage.setValue("employeeName", "")
    Page.Variables.vmDelegationModel.setValue('delToUserId', selectedValue.nik)
    Page.Variables.vmDelegationModel.setValue('delToUserName', selectedValue.employeeName)

    console.log("dataset", Page.Variables.vmDelegationModel.dataSet)
};
Page.dateFromChange = function($event, widget, newVal, oldVal) {
    Page.Variables.vmErrorMessage.setValue("date", "")
};
Page.dateToChange = function($event, widget, newVal, oldVal) {
    Page.Variables.vmErrorMessage.setValue("date", "")
    Page.App.formatDateAndTime(Page.Variables.vmDelegationModel.dataSet.delExpDatetime)
};
Page.textarea2Keyup = function($event, widget) {
    Page.Variables.vmErrorMessage.setValue("reason", "")
};

Page.sendEmailSupervisor = function(supervisorData) {
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
              <td>
               <a style="font-style: normal;
            		font-weight: 500;
            		font-size: 40px;
            		line-height: 55px;
            		color: #262626;">Hi ${supervisorData.lgName},</a><br><br>
               <p style="color: #262626;">Please be informed that ${Page.App.Variables.loggedInUserData.dataSet.user_full_name} has delegated the purchase request module in the aio procure to pay application to ${Page.Variables.vmDelegationModel.dataSet.delToUserName} from ${Page.Variables.vmDelegationModel.dataSet.delAvailDatetime} until ${Page.Variables.vmDelegationModel.dataSet.delExpDatetime} for the following reasons: <br><b>${Page.Variables.vmDelegationModel.dataSet.delReason}</b></p> <br>
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

Page.vdbAuthorizationonSuccess = function(variable, data) {
    console.log("auth", data);
};