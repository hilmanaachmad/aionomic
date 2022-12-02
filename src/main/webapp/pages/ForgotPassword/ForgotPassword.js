/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */

/* perform any action on widgets/variables within this block */
Page.loginBg = ((new Date()).getDate() % 2) + 1
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
};

Page.generatePassword = function() {
    var length = 2,
        charset1 = "abcdefghijklmnopqrstuvwxyz",
        charset2 = "!@#$%&*",
        charset3 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ",
        charset4 = "0123456789",
        retVal = "";

    for (var i = 0, n = charset1.length; i < 2; ++i) {
        retVal += charset1.charAt(Math.floor(Math.random() * n));
    }

    for (var i = 0, n = charset2.length; i < 1; ++i) {
        retVal += charset2.charAt(Math.floor(Math.random() * n));
    }

    for (var i = 0, n = charset3.length; i < 1; ++i) {
        retVal += charset3.charAt(Math.floor(Math.random() * n));
    }

    for (var i = 0, n = charset4.length; i < 2; ++i) {
        retVal += charset4.charAt(Math.floor(Math.random() * n));
    }
    return retVal;
}

Page.validation = function(text, type) {
    var message = ""
    if (text === undefined) {
        message = type + " Cannot be empty"
    } else {
        if (type === "Email") {
            message = text.indexOf('@') !== -1 ? text.indexOf('.') !== -1 ? "" : "Email not valid" : "Email not valid"
        } else {
            message = ""
        }
    }
    return message
}
Page.button1Click = function($event, widget) {
    Page.Variables.vmErrorMessages.setValue("username", Page.validation(Page.Widgets.textUsername.datavalue, "Username"))
    var object = Page.Variables.vmErrorMessages.setValue('email', Page.validation(Page.Widgets.textEmail.datavalue, "Email"))

    if (object.username.length === 0 && object.email.length === 0) {
        Page.checkUser()
    }
};

Page.checkUser = function() {
    var userEmpl2 = null

    Promise.resolve().then(() => {
        return Page.Variables.qCheckUserEmailNik.invoke({
            "inputFields": {
                "email": Page.Widgets.textEmail.datavalue.split(" ").join("_"),
                "nik": Page.Widgets.textUsername.datavalue.split(" ").join("_")
            }
        })
    }).then((userEmpl) => {
        userEmpl2 = JSON.parse(userEmpl.body).content
        userEmpl = JSON.parse(userEmpl.body).content
        if (userEmpl.length < 1) {
            return Page.Variables.qCheckUserEmailVen.invoke({
                "inputFields": {
                    "username": Page.Widgets.textUsername.datavalue.split(" ").join("_"),
                    "email": Page.Widgets.textEmail.datavalue.split(" ").join("_")
                }
            })
        } else {
            return undefined
        }
    }).then((userVend) => {
        if (userVend === undefined) {
            // ada employe
            Page.sendEmailPass("employe", userEmpl2[0].lgName)
            return 'employee'
        } else {
            userVend = JSON.parse(userVend.body).content
            if (userVend.length < 1) {
                return undefined
            } else {
                // ada vendor
                Page.sendEmailPass("vendor", userVend[0].abUme)
                return 'vendor'
            }
        }
    }).then((data) => {
        if (data === undefined) {
            Page.Actions.loginError.setMessage("Username or Email doesn't match")
            Page.Actions.loginError.setToasterClass("error")
            Page.Actions.loginError.setAlertType("error")
            Page.Actions.loginError.invoke()
        }
    }).catch((err) => {
        console.log("err ", err)
    })
}

Page.sendEmailPass = function(userType, name) {
    var pass = Page.generatePassword()
    if (userType.indexOf('employe') !== -1) {
        return Page.Variables.qForgotPassEmp.invoke({
            "inputFields": {
                "password": CryptoJS.MD5(pass).toString(),
                "username": Page.Widgets.textUsername.datavalue,
                "email": Page.Widgets.textEmail.datavalue
            }
        }).then(() => {
            // send email
            var to = Page.Widgets.textEmail.datavalue
            var cc = ""
            var text = ""
            var subject = "Forgot Password"
            var body = Page.App.bodyForgetEmail(pass, name)

            return App.sendEmail(to, cc, subject, body, text)
        }).then(() => {
            Page.Widgets.textUsername.datavalue = ""
            Page.Widgets.textEmail.datavalue = ""
            Page.Actions.loginError.setMessage("new password will be sent to your email")
            Page.Actions.loginError.setToasterClass("success")
            Page.Actions.loginError.setAlertType("success")
            Page.Actions.loginError.invoke()
            // App.Actions.goToPage_LoginAIO.invoke()
        }).catch((err) => {
            console.log('err ', err)
        })
    } else {
        return Page.Variables.qForgotPassVen.invoke({
            "inputFields": {
                "password": CryptoJS.MD5(pass).toString(),
                "username": Page.Widgets.textUsername.datavalue,
                "email": Page.Widgets.textEmail.datavalue
            }
        }).then(() => {
            // send email
            var to = Page.Widgets.textEmail.datavalue
            var cc = ""
            var text = ""
            var subject = "Forgot Password"
            var body = Page.App.bodyForgetEmail(pass, name)

            return App.sendEmail(to, cc, subject, body, text)
        }).then(() => {
            Page.Widgets.textUsername.datavalue = ""
            Page.Widgets.textEmail.datavalue = ""
            Page.Actions.loginError.setMessage("new password will be sent to your email")
            Page.Actions.loginError.setToasterClass("success")
            Page.Actions.loginError.setAlertType("success")
            Page.Actions.loginError.invoke()
            // App.Actions.goToPage_LoginAIO.invoke()
        }).catch((err) => {
            console.log('err ', err)
        })
    }
    return userType
}
