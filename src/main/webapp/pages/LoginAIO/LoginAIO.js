/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */

/* perform any action on widgets/variables within this block */
Page.loginBg = ((new Date()).getDate() % 2) + 1
Page.onReady = async function() {
    /*
     * variables can be accessed through 'Page.Variables' property here
     * e.g. to get dataSet in a staticVariable named 'loggedInUser' use following script
     * Page.Variables.loggedInUser.getData()
     *
     * widgets can be accessed through 'Page.Widgets' property here
     * e.g. to get value of text widget named 'username' use following script
     * 'Page.Widgets.username.datavalue'
     */
    var d = new Date,
        today = [d.getFullYear(),
            d.getMonth() + 1,
            d.getDate()
        ].join('-')


    if (Page.pageParams.status == "success") {
        Page.Widgets.dialogSuccess.open();
    }

    // debugger;
    let isParams = (Page.pageParams.username && Page.pageParams.password) ? true : false;
    let isLoggedIn = App.Variables.loggedInUser.dataSet.name ? true : false;
    if (isParams) {
        if (isLoggedIn) {
            // await App.Actions.logoutAction.invoke()
            // Page.doLogin(Page.pageParams.username, Page.pageParams.password);
            Page.Actions.goToLoadingScreen.invoke();
        } else {
            Page.doLogin(Page.pageParams.username, Page.pageParams.password);
        }
    } else {
        App.originalHash = "#/" + (Page.pageParams.redirectTo || "Main")
        var params = []
        for (var attr in Page.pageParams) {
            if (attr == "redirectTo") {
                continue
            }
            params.push(attr + "=" + Page.pageParams[attr])
        }
        App.originalHash += "?" + params.join("&")
    }




    var singleDate = await Page.Variables.DBAnnouncement.listRecords({
        filterFields: {
            "isValid": {
                "value": 1,
                "matchMode": "EQUALS"
            },
            "page": {
                "value": 'login',
                "matchMode": "EQUALS"
            },
            "startDate": {
                "value": today,
                "matchMode": "EQUALS"
            }
        },
    })


    var rangeDate = await Page.Variables.DBAnnouncement.listRecords({
        filterFields: {
            "isValid": {
                "value": 1,
                "matchMode": "EQUALS"
            },
            "page": {
                "value": 'login',
                "matchMode": "EQUALS"
            },
            "startDate": {
                "value": today,
                "matchMode": "LESS_THAN_OR_EQUALS"
            },
            "endDate": {
                "value": today,
                "matchMode": "GREATER_THAN_OR_EQUALS"
            }
        },
    })


    var announcement = [].concat(singleDate.data, rangeDate.data)
    const uniqueId = new Set();
    Page.Variables.ModelAnnouncement.dataSet = announcement.filter(item => {
        const isDuplicate = uniqueId.has(item.id);
        uniqueId.add(item.id);
        if (!isDuplicate) {
            return true;
        }
        return false;
    });


    if (Page.Variables.ModelAnnouncement.dataSet.length > 0) {
        Page.Widgets.dialogAnnouncement.open()
    }

};

Page.doLogin = function(username, password) {
    if (username.length > 4) {
        let firstNik = username.substring(0, 1);
        if (firstNik == "0") {
            username = username.substring(1, username.length);
        }
    }
    Page.Actions.loginAction.invoke({
        input: {
            j_username: username,
            j_password: password,
            j_rememberme: false
        }
    }, function(data) {
        console.log(data);
    }, function(error) {
        console.log(error);
        App.Actions.appNotification.invoke({
            "class": "error",
            "message": error,
            "position": "bottom right"
        });
    });
}



Page.loginActiononError = async function(variable, data) {
    if (!Page.Widgets.UsernameInp.datavalue || !Page.Widgets.UsernameInp.datavalue.length) {
        Page.Actions.loginError.setMessage("Username Or Password Incorrect")
    } else if (!Page.Widgets.passwordInp.datavalue || !Page.Widgets.passwordInp.datavalue.length) {
        Page.Actions.loginError.setMessage("Username Or Password Incorrect")
    } else {
        Page.Actions.loginError.setMessage("Username Or Password Incorrect")
    }
    Page.Actions.loginError.setToasterClass("error")
    Page.Actions.loginError.setAlertType("error")
    Page.Actions.loginError.setToasterDuration(7000)
    Page.Actions.loginError.invoke()
    await App.Variables.logAudit.invoke({
        inputFields: {
            RequestBody: {
                "ip_address": App.Variables.mDeviceIdentity.dataSet.ip,
                "root_url": window.location.host,
                "full_url": window.location.href,
                "action": "LOGIN",
                "message": "login failed",
                "device": App.Variables.mDeviceIdentity.dataSet.uag,
                "param": "",
                "created_by": Page.Widgets.UsernameInp.datavalue
            }
        }
    })
};
Page.loginActiononSuccess = async function(variable, data) {
    console.log('data baru', App.Variables.loggedInUser.dataSet);
    try {
        let vdbUserData = await Page.App.Variables.vdbUserData.invoke({
            filterFields: {
                udatUsername: {
                    value: App.Variables.loggedInUser.dataSet.name
                }
            }
        });

        if (vdbUserData.data.length > 0) {
            data = vdbUserData.data[0]
            data.udatLastLogin = new Date();

            await Page.App.Variables.vdbUserData.updateRecord({
                row: data
            });
        }

        await App.Variables.logAudit.invoke({
            inputFields: {
                RequestBody: {
                    "ip_address": App.Variables.mDeviceIdentity.dataSet.ip,
                    "root_url": window.location.host,
                    "full_url": window.location.href,
                    "action": "LOGIN",
                    "message": "login successfull",
                    "device": App.Variables.mDeviceIdentity.dataSet.uag,
                    "param": "",
                    "created_by": App.Variables.loggedInUser.dataSet.name
                }
            }
        })

        window.localStorage.setItem("aio_p2p_cup", btoa(Page.Widgets.passwordInp.datavalue))
        Page.Actions.goToLoadingScreen.invoke();
    } catch (e) {
        console.log(e);
        App.Actions.appNotification.invoke({
            "class": "error",
            "message": "An error has been occured, " + e.message,
            "position": "bottom right"
        });
    }




    // // Page.Widgets.spinner1.show = true
    // return Page.App.Variables.vdbUserData.invoke({
    //     filterFields: {
    //         udatUsername: {
    //             value: App.Variables.loggedInUser.dataSet.name
    //         }
    //     }
    // }).then(function(data) {
    //     console.log('oioi', data);
    //     if (!data.data.length) {
    //         return Promise.resolve()
    //     }
    //     data = data.data[0]
    //     data.udatLastLogin = new Date()
    //     Page.App.Variables.vdbUserData.updateRecord({
    //         row: data
    //     }).catch(function(err) {
    //         // console.log(err)
    //         return Promise.resolve()
    //     })


    // }).then(async function() {
    //     await App.Variables.logAudit.invoke({
    //         inputFields: {
    //             RequestBody: {
    //                 "ip_address": App.Variables.mDeviceIdentity.dataSet.ip,
    //                 "root_url": window.location.host,
    //                 "full_url": window.location.href,
    //                 "action": "LOGIN",
    //                 "message": "login successfull",
    //                 "device": App.Variables.mDeviceIdentity.dataSet.uag,
    //                 "param": "",
    //                 "created_by": App.Variables.loggedInUser.dataSet.name
    //             }
    //         }
    //     })

    //     window.localStorage.setItem("aio_p2p_cup", btoa(Page.Widgets.passwordInp.datavalue))
    //     Page.Actions.goToLoadingScreen.invoke()
    // })
};

// login action invoke on enter
Page.UsernameInpKeyup = function($event, widget) {
    window.localStorage.setItem('unique-id', btoa(Page.Widgets.UsernameInp.datavalue));
    window.localStorage.setItem('unique-token', btoa(Page.Widgets.passwordInp.datavalue));
    if ($event.key === "Enter") {
        // Page.Actions.loginAction.invoke()
        Page.doLogin(Page.Widgets.UsernameInp.datavalue, CryptoJS.MD5(Page.Widgets.passwordInp.datavalue).toString());
    }
};
Page.passwordInpKeyup = function($event, widget) {
    window.localStorage.setItem('unique-id', btoa(Page.Widgets.UsernameInp.datavalue));
    window.localStorage.setItem('unique-token', btoa(Page.Widgets.passwordInp.datavalue));
    if ($event.key === "Enter") {
        // Page.Actions.loginAction.invoke()
        Page.doLogin(Page.Widgets.UsernameInp.datavalue, CryptoJS.MD5(Page.Widgets.passwordInp.datavalue).toString());
    }
};

//tambahan Darisinse
Page.forgot_user_formOpened = function($event, widget) {
    var taxMask = IMask(
        document.getElementById('inputTax'), {
            mask: '00.000.000.0-000.000'
        });

    $("#inputTax").change(function() {
        if ($('#inputTax').val().replace(/\D+/g, '') === '000000000000000') {
            Page.Widgets.btnSubmit.disabled = false
        } else if ($('#inputTax').val().replace(/\D+/g, '') !== Page.Variables.svGetDataVendorByName.dataSet[0].npwpNumber) {
            Page.Actions.naNpwpMismatch.invoke()
            Page.Widgets.btnSubmit.disabled = true
        } else {
            Page.Widgets.btnSubmit.disabled = false
        }
    });

};

Page.companyEmailClick = function($event, widget) {
    Page.Variables.modelGeneral.setValue("npwpNumber", $("#inputTax").val().replace(/\D+/g, ''));
};

//send email
Page.sendEmail = function($event, widget) {
    var email = []
    if (Page.Variables.svGetDataVendorByName.dataSet[0].puon == 'PSKB') {
        email = "gsaputra@aio.co.id"
    } else if (Page.Variables.svGetDataVendorByName.dataSet[0].puon == 'PKJY') {
        email = "aaswianto@aio.co.id"
    } else {
        email = "gsaputra@aio.co.id"
    }

    Page.Variables.svNotifyAdminForgotUser.invoke({
        inputFields: {
            RequestBody: {
                "to": email,
                "cc": "",
                "subject": `Forgot User Vendor`,
                "body": `<p> Ada permintaan baru forgot user vendor dari ${Page.Widgets.sCompanyName.datavalue} </p>
                            <p>Silahkan cek list permintaan pada link berikut:
                            <a href="https://aionomic.aio.co.id:8080/#/ForgotUserList" target="_blank">Buka Link</a>
                            <p>`
            }
        }
    }, function(data) {
        console.log(data);
    }, function(error) {});
}

Page.sendEmailVendor = function($event, widget) {
    Page.Variables.svNotifyAdminForgotUser.invoke({
        inputFields: {
            RequestBody: {
                "to": Page.Widgets.companyEmail.datavalue,
                "cc": "",
                "subject": `Forgot User Vendor`,
                "body": `<p> Dear Valued Vendor, </p>
                            <p> Your registration data on Fogot User has been submitted.
                            Please wait until next information.
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

Page.svInsertForgotUseronSuccess = function(variable, data) {
    Page.sendEmail();
    Page.sendEmailVendor();
};

Page.citizenIdChange = function($event, widget, newVal, oldVal) {
    if (Page.Widgets.citizenId.datavalue === '0000000000000000') {
        Page.Widgets.btnSubmit.disabled = false
    } else if (Page.Widgets.citizenId.datavalue !== Page.Variables.svGetDataVendorByName.dataSet[0].idDirector) {
        Page.Actions.naKtpMismatch.invoke()
        Page.Widgets.btnSubmit.disabled = true
    } else {
        Page.Widgets.btnSubmit.disabled = false
    }
};
Page.picture2Click = function($event, widget) {
    window.open("https://cloudbc.aio.co.id:5001/fsdownload/tSS0KhjPd/Video%20to%20External");
};
Page.button1Click = function($event, widget) {


    window.localStorage.setItem('unique-id', btoa(Page.Widgets.UsernameInp.datavalue));
    window.localStorage.setItem('unique-token', btoa(Page.Widgets.passwordInp.datavalue));

    Page.doLogin(Page.Widgets.UsernameInp.datavalue, CryptoJS.MD5(Page.Widgets.passwordInp.datavalue).toString());
};
Page.anchorLaporAIOClick = function($event, widget) {
    $('#laporaio-form').submit();
};
Page.containerEyeClick = function($event, widget) {
    if (Page.Widgets.passwordInp.type == "password") {
        Page.Widgets.passwordInp.type = "text";
        Page.Widgets.icon1.iconclass = "far fa-eye";
    } else {
        Page.Widgets.passwordInp.type = "password";
        Page.Widgets.icon1.iconclass = "far fa-eye-slash";
    }
};

Page.svGetDataVendorByNameonSuccess = function(variable, data) {
    if (data[0].vendorCode === '0') {
        Page.Widgets.btnSubmit.disabled = true;
    }
    console.log('ini data', data)
};