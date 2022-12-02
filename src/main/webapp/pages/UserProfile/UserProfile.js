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
    // console.log(App.Variables.loggedInUserData.dataSet)
    // console.log(App.Variables.loggedInUser.dataSet)
};

Page.getDepartmentList = function(data) {
    console.log(data)
    return data.map(function(item) {
        return item.department
    }).filter(function(e, i, arr) {
        return arr.indexOf(e) == i
    }).join(", ")
}

Page.passwordValidation = function(password) {
    var message = ""
    var pattern = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{4,})");

    if (password.length < 4) {
        message = "minimal panjang karater 4 digit"
    } else {
        if (pattern.test(password) === false) {
            message = "wajib menggunakan kombinasi huruf besar, kecil, dan angka"
        } else {
            message = "success"
        }
    }
    return message
    // return 'success'
}

Page.button3Click = function($event, widget) {
    var dataPassword = Page.Variables.vmPassword.dataSet
    Promise.resolve().then(() => {
        var tmp = []
        for (var item in dataPassword) {
            var errMsg = Page.passwordValidation(dataPassword[item])
            if (item === "userPassword") {
                // untuk mematikan validasi Current Password
            } else {
                if (errMsg != "success") {
                    tmp.push("success")
                    Page.Variables.vmErrorMessages.setValue(item, errMsg)
                }
            }
        }
        return tmp
    }).then((valid) => {
        if (dataPassword.newPassword != dataPassword.confirmPassword) {
            Page.Variables.vmErrorMessages.setValue("confirmPassword", "New Password & Re-Type New Password tidak cocok")
            Page.Variables.vmErrorMessages.setValue("newPassword", "New Password & Re-Type New Password tidak cocok")
            return Promise.reject()
        } else {
            if (valid.length === 0) {
                if (App.Variables.loggedInUserData.dataSet.user_type.indexOf("Employee") !== -1) {
                    return Page.changePassword()
                } else {
                    return Page.changePasswordVendor()
                }
            } else {
                return Promise.reject()
            }
        }

    }).catch(function(err) {
        console.log(err)
    })
};

Page.changePassword = function() {
    // Employee
    return Promise.resolve().then(() => {
        return Page.Variables.qCheckUserNikPass.invoke({
            "inputFields": {
                "nik": App.Variables.loggedInUserData.dataSet.username.split('::')[1],
                "password": CryptoJS.MD5(Page.Variables.vmPassword.dataSet.userPassword).toString()
            }
        })
    }).then((user) => {
        user = JSON.parse(user.body).content
        if (user.length < 1) {
            Page.Actions.loginError.setMessage("Current Password is False")
            return false
        } else if (Page.Variables.vmPassword.dataSet.userPassword.indexOf(Page.Variables.vmPassword.dataSet.newPassword) !== -1) {
            Page.Actions.loginError.setMessage("tidak dapat menggunakan Password akun yang lama")
            return false
        } else {
            return true
        }
    }).then((valid) => {
        if (valid === true) {
            return Page.Variables.qChangePassEmpl.invoke({
                "inputFields": {
                    "password": CryptoJS.MD5(Page.Variables.vmPassword.dataSet.newPassword).toString(),
                    "nik": App.Variables.loggedInUserData.dataSet.username.split('::')[1]
                }
            }).then(() => {
                Page.Actions.loginError.setMessage("Password berhasil dirubah")
                return 'success'
            }).catch((err) => {
                Page.Actions.loginError.setMessage("Password gagal dirubah")
                return 'error'
            })
        } else {
            Promise.resolve()
            return 'error'
        }
    }).then((msgs) => {
        if (msgs === 'success') {
            Page.setLastChangePassword()
            return 'success'
        } else {
            Promise.resolve()
            return 'error'
        }
    }).then((msg) => {
        if (msg === 'success') {
            Page.Widgets.currentPassword.datavalue = ""
            Page.Widgets.newPassword.datavalue = ""
            Page.Widgets.confirmPassword.datavalue = ""
            Page.Actions.loginError.setToasterClass("success")
            Page.Actions.loginError.setAlertType("success")
            Page.Actions.loginError.invoke()
            return true
        } else {
            Page.Actions.loginError.setToasterClass("error")
            Page.Actions.loginError.setAlertType("error")
            Page.Actions.loginError.invoke()
            return false
        }
    }).catch((err) => {
        console.log('err ', err)
        return false
    })
}

Page.changePasswordVendor = function() {
    // Vendor
    return Promise.resolve().then(() => {
        return Page.Variables.qCheckUserVendor.invoke({
            "inputFields": {
                "username": App.Variables.loggedInUserData.dataSet.username.split('::')[1],
                "password": CryptoJS.MD5(Page.Variables.vmPassword.dataSet.userPassword).toString()
            }
        })
    }).then((user) => {
        user = JSON.parse(user.body).content
        if (user.length < 1) {
            Page.Actions.loginError.setMessage("Current Password is False")
            return false
        } else if (Page.Variables.vmPassword.dataSet.userPassword.indexOf(Page.Variables.vmPassword.dataSet.newPassword) !== -1) {
            Page.Actions.loginError.setMessage("tidak dapat menggunakan Password akun yang lama")
            return false
        } else {
            return true
        }
    }).then((valid) => {
        if (valid === true) {
            return Page.Variables.qChangePasswordVen.invoke({
                "inputFields": {
                    "password": CryptoJS.MD5(Page.Variables.vmPassword.dataSet.newPassword).toString(),
                    "username": App.Variables.loggedInUserData.dataSet.username.split('::')[1]
                }
            }).then(() => {
                Page.Actions.loginError.setMessage("Password berhasil dirubah")
                return 'success'
            }).catch((err) => {
                Page.Actions.loginError.setMessage("Password gagal dirubah")
                return 'error'
            })
        } else {
            Promise.resolve()
            return 'error'
        }
    }).then((msgs) => {
        if (msgs === 'success') {
            Page.setLastChangePassword()
            return 'success'
        } else {
            Promise.resolve()
            return 'error'
        }
    }).then((msg) => {
        if (msg === 'success') {
            Page.Widgets.currentPassword.datavalue = ""
            Page.Widgets.newPassword.datavalue = ""
            Page.Widgets.confirmPassword.datavalue = ""
            Page.Actions.loginError.setToasterClass("success")
            Page.Actions.loginError.setAlertType("success")
            Page.Actions.loginError.invoke()
            return true
        } else {
            Page.Actions.loginError.setToasterClass("error")
            Page.Actions.loginError.setAlertType("error")
            Page.Actions.loginError.invoke()
            return false
        }

    }).catch((err) => {
        console.log('err ', err)
        return false
    })
}

Page.setLastChangePassword = function() {
    var status = ""
    return Promise.resolve().then(() => {
        return Page.Variables.dbTblMUserData.invoke()
    }).then((check) => {
        check = check.data
        if (check.length < 1) {
            // insert
            return Page.Variables.dbTblMUserData.createRecord({
                row: {
                    "udatUsername": App.Variables.loggedInUserData.dataSet.username,
                    "udatProfilePic": App.Variables.loggedInUserData.dataSet.user_profile_pic,
                    "udatLastLogin": App.Variables.loggedInUserData.dataSet.user_last_login,
                    "udatLastChangePass": new Date().toISOString()
                }
            }).then(() => {
                status = "success"
                return "success"
            }).catch((err) => {
                status = "error"
                return "error"
            })
        } else {
            // update
            check[0].udatLastChangePass = new Date()
            return Page.Variables.dbTblMUserData.updateRecord({
                row: check[0]
            }).then(() => {
                status = "success"
                return "success"
            }).catch((err) => {
                status = "error"
                return "error"
            })
        }
    })
    return status
}

// reset error message
Page.currentPasswordKeyup = function($event, widget) {
    Page.Variables.vmErrorMessages.setValue("userPassword", "")
};
Page.newPasswordKeyup = function($event, widget) {
    Page.Variables.vmErrorMessages.setValue("newPassword", "")
};
Page.confirmPasswordKeyup = function($event, widget) {
    Page.Variables.vmErrorMessages.setValue("confirmPassword", "")
};
Page.button1Click = function($event, widget) {
    window.open("#/LoginIVendor", "__blank")
};
Page.picture2_1Click = function($event, widget) {
    Page.Widgets.fileupload1.$element.find("input").click()
};

Page.vdbqSetProfilePiconResult = function(variable, data) {
    Page.App.fetchUserData().then(function() {
        Page.Actions.notificationAction1.invoke()
    })
};

Page.FileServiceUploadFileonSuccess = function(variable, data) {
    Page.Variables.vdbqSetProfilePic.setInput("imageUrl", data[0].path)
    Page.Variables.vdbqSetProfilePic.invoke()
};

Page.button3_1Click = function($event, widget) {
    if (window.confirm("Are you sure you want to delete the photo?")) {
        if (Page.Variables.loggedInUserData.dataSet.user_profile_pic !== null) {
            var img = Page.Variables.loggedInUserData.dataSet.user_profile_pic.split('returnName=')[1]
            return Page.Variables.FileServiceDeleteFile.invoke({
                "inputFields": {
                    "file": img
                }
            }).then(function() {
                return Page.Variables.vdbqSetProfilePic.invoke({
                    "inputFields": {
                        "username": Page.Variables.loggedInUserData.dataSet.username,
                        "imageUrl": null
                    }
                })
            })
        }
    }
};

Page.pictureViewCurrentClick = function($event, widget) {
    if (Page.Widgets.currentPassword.type === "text") {
        Page.Widgets.currentPassword.type = "Password"
    } else {
        Page.Widgets.currentPassword.type = "text"
    }
};
Page.pictureNewPassClick = function($event, widget) {
    if (Page.Widgets.newPassword.type === "text") {
        Page.Widgets.newPassword.type = "Password"
    } else {
        Page.Widgets.newPassword.type = "text"
    }
};
Page.pictureConfirmPassClick = function($event, widget) {
    if (Page.Widgets.confirmPassword.type === "text") {
        Page.Widgets.confirmPassword.type = "Password"
    } else {
        Page.Widgets.confirmPassword.type = "text"
    }
};