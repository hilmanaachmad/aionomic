/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */

/* perform any action on the variables within this block(on-page-load) */


// beginning of APP Configuration
// App.domain = "http://ptamerta.cloud.wavemakeronline.com/Procure_to_Pay/"
// App.domain = "https://hotestsvr.aio.co.id:8443/Procure_to_Pay/"
App.domain = "https://aionomic.aio.co.id:8080/"
App.isDevelopment = false
App.developmentEmailReceiver = "ekusumaningtyas@aio.co.id, ibeawiharta@aio.co.id, wverawaty@aio.co.id, ryusteja@aio.co.id, nlarasati@aio.co.id, hachmad@aio.co.id, rbkurniawan@aio.co.id, wlistiana@aio.co.id"
// end of APP Configuration

window.addEventListener("click", function() {
    var isOpenedBefore = $(".aio-p2p-header-container.opened").length > 0
    $(".aio-p2p-header-container:not(.clicked)").removeClass("opened")
    $(".aio-p2p-header-container").removeClass("clicked")
    if (!$(".aio-p2p-header-container.opened").length && App.Variables.vdbqUnseenNotification.lastRecord.inbTimestamp && isOpenedBefore) {
        App.Variables.vdbqTickNotif.invoke({
            inputFields: {
                date: new Date(App.Variables.vdbqUnseenNotification.lastRecord.inbTimestamp)
            }
        }).then(function() {
            return App.Variables.vdbqUnseenNotification.invoke({
                page: 1
            })
        })
    }
    // console.log("tryclose", $(".aio-p2p-header-container.opened"))
})
// end of config


App.JasperLink = function() {
    return "https://myapps.aio.co.id/jasperserver/rest_v2/reports/"
}
App.HariIni = function() {
    var d = new Date
    var month = d.getMonth() + 1
    var date = d.getDate()
    var year = d.getFullYear()
    var hours = d.getHours()
    var minutes = d.getMinutes()
    var seconds = d.getSeconds()

    return [year, month, date].join('-') + ' ' + [hours, minutes, seconds].join(':')
}


App.addDays = function(date, days) {
    var result = new Date(date);
    result.setDate(result.getDate() + days);
    return result;
}

//Format date
App.formatDateAndTime = function(date) {
    if (date != '' && date != null && date != undefined) {
        var d = new Date(date)
        var month = d.getMonth() + 1
        var date = d.getDate()
        var year = d.getFullYear()
        var hours = d.getHours()
        var minutes = d.getMinutes()
        var seconds = d.getSeconds()

        return [year, month, date].join('-') + ' ' + [hours, minutes, seconds].join(':')
    } else {
        return '-';
    }
}

App.formatDate = function(date) {
    if (date != '' && date != null && date != undefined) {
        var d = new Date(date)
        var month = d.getMonth() + 1
        var date = d.getDate()
        var year = d.getFullYear()

        return [year, month, date].join('-')
    } else {
        return '-';
    }
}


App.onAppVariablesReady = function() {
    /*
     * variables can be accessed through 'App.Variables' property here
     * e.g. App.Variables.staticVariable1.getData()
     */


    //  hardcode loggedin user
    // App.Variables.loggedInUser.dataSet.name = "emp::0390"

    // loading scren
    if (App.Variables.loggedInUser.getValue("isAuthenticated")) {
        // console.log(App.Variables.loggedInUser)
        App.originalHash = window.location.hash
        window.location.hash = "#/AppLoadingScreen"
    }
};

App.generateUUID = function() {
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
        var r = Math.random() * 16 | 0,
            v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
    });
}


App.formatCurrency = function(val) {
    try {
        // let newVal = val.toString().replace(".", ",")
        // return newVal.replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.')
        return val.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.')
    } catch (err) {
        return val
    }

}




App.formatCurrencyPTP = function(data) {
    // console.log(data)
    if (data == null) {
        return '0';
    }
    data = data.toString();
    if (data.includes('.')) {
        data = Number(data).toFixed(2)
        data = App.replaceAll(data, '.', ',');
        var splitString = data.toString().split(",");
        var beforeComma = splitString[0]
        var afterComma = splitString[1]
        if (afterComma != undefined) {
            return (beforeComma || "").toString().replace(/\D/g, '').split(".").join("").replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.') + ',' + afterComma
        } else {
            return (beforeComma || "").toString().replace(/\D/g, '').split(".").join("").replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.')
        }
    } else {
        try {
            return data.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.')
        } catch (err) {
            return "0"
        }
    }
}



//ubah koma ke titik
App.formatDecimalComma = function(val) {
    if (val == null) {
        return
    }
    let value = val.toString();
    if (value.includes(".")) {
        value = App.replaceAll(value, '.', '');
        value = value.replace(',', '.');
    }
    return Number(value)
}

App.formatDecimalComma = function(val) {
    if (val == null) {
        return
    }
    let value = val.toString();
    if (value.includes(".")) {
        value = App.replaceAll(value, '.', ',');
        return App.parseDataFloatByComma(value)
    } else {
        return App.formatCurrency(value)
    }

}

App.parseDataFloatByComma = function(data) {
    var splitString = data.split(",");
    var beforeComma = splitString[0]
    var afterComma = splitString[1]
    return (beforeComma || "").toString().replace(/\D/g, '').split(".").join("").replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.') + ',' + afterComma
}

App.replaceAll = function(string, search, replace) {
    return string.split(search).join(replace);
}



// ubah koma ke titik
App.formatDecimalStr = function(val) {
    if (val == null) {
        return
    }
    let dataDec = val.toString();
    if (dataDec.includes(",")) {
        dataDec = App.replaceAll(dataDec, '.', '');
        dataDec = dataDec.replace(",", ".");
        return Number(dataDec);
    } else {
        dataDec = App.replaceAll(dataDec, '.', '');
        return Number(dataDec)
    }
}


App.parseDataFloatByComma = function(data) {
    var splitString = data.split(",");
    var beforeComma = splitString[0]
    var afterComma = splitString[1]
    return (beforeComma || "").toString().replace(/\D/g, '').split(".").join("").replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1.') + ',' + afterComma
}


App.getVDBAllRecords = function(variable, records, notFirst, fails) {
    if (!fails) {
        fails = 0
    }
    if (!records) {
        records = []
    }
    if (variable.pagination && variable.pagination.last && notFirst) {
        return Promise.resolve(records)
    }
    if (fails > 20) {
        return Promise.resolve(records)
    }
    return Promise.resolve().then(function() {
        if (!notFirst) {
            return variable.invoke({
                page: 1
            })
        }
        return variable.invoke({
            page: variable.pagination.number + 2
        })
    }).then(function(res) {
        records = records.concat(JSON.parse(JSON.stringify(res.data)))
        return App.getVDBAllRecords(variable, records, true, 0)
    }).catch(function(err) {
        console.log(err)
        return App.getVDBAllRecords(variable, records, notFirst, fails + 1)
    })
}

App.getVDBAllRecordsHilman = async function(variable, records, notFirst, fails, page) {
    if (!fails) {
        fails = 0
    }
    if (!records) {
        records = []
    }
    if (variable.pagination && variable.pagination.last && notFirst) {
        return Promise.resolve(records)
    }
    if (fails > 20) {
        return Promise.resolve(records)
    }

    console.log(notFirst, fails, variable, page)

    return await Promise.resolve().then(async function() {
        if (variable.pagination.first) {
            page = 1
        } else {
            page = page + 1
        }

        return await variable.invoke({
            page: page
        })
    }).then(async function(res) {
        records = records.concat(JSON.parse(JSON.stringify(res.data)))
        return await App.getVDBAllRecordsHilman(variable, records, true, 0, page)
    }).catch(function(err) {
        console.log(err)
        // return App.getVDBAllRecordsHilman(variable, records, notFirst, fails + 1)
        return Promise.resolve(records)
    })
}

App.getVDBQAllRecords = function(variable, results, notFirst, fails) {
    if (!results) {
        results = []
    }
    if (!fails) {
        fails = 0
    }
    if (!variable) {
        return Promise.resolve(results)
    }

    if (variable.pagination && variable.pagination.last && notFirst) {
        return Promise.resolve(results)
    }

    if (fails > 20) {
        return Promise.resolve(results)
    }

    return Promise.resolve().then(function() {
        if (!notFirst) {
            return variable.invoke({
                page: 1
            })
        }
        return variable.invoke({
            page: variable.pagination.number + 2
        })
    }).then(function({
        body
    }) {
        return App.getVDBQAllRecords(variable, results.concat(JSON.parse(body).content), true, 0)
    }).catch(function(err) {
        console.log(err)
        return App.getVDBQAllRecords(variable, results, true, fails + 1)
    })
}

App.fetchUserData = function() {

    App.Variables.loggedInUserData.setValue("username", App.Variables.loggedInUser.getValue("name"))
    App.Variables.loggedInUserData.setValue("user_type", App.Variables.loggedInUser.getValue("name").startsWith("emp::") ? "Employee" : "Vendor")
    App.Variables.loggedInUserData.setValue("user_last_change_pass", new Date(0))
    App.Variables.loggedInUserData.setValue("user_last_login", new Date())

    return App.Variables.vdbUserData.listRecords().catch(function(err) {
        console.log(err)
        return {
            data: []
        }
    }).then(function({
        data
    }) {
        if (!data.length) {
            return App.Variables.vdbUserData.createRecord({
                row: {
                    "udatUsername": App.Variables.loggedInUser.getValue("name"),
                    "udatProfilePic": null,
                    "udatLastLogin": new Date(),
                    "udatLastChangePass": new Date(),
                    "udatLastAccess": new Date(),
                    "udatInboxLastSeen": new Date(10000),
                    "udatNotificationLastSeen": new Date(10000)
                }
            }).catch(function(err) {
                console.log(err)
                return Promise.resolve()
            })
        }

        data = data[0]
        App.Variables.loggedInUserData.setValue("user_last_change_pass", data.udatLastChangePass || new Date(0))
        App.Variables.loggedInUserData.setValue("user_last_login", data.udatLastLogin || new Date())
        App.Variables.loggedInUserData.setValue("user_profile_pic", data.udatProfilePic)
        return Promise.resolve()
    }).then(function() {
        if (App.Variables.loggedInUser.getValue("name").startsWith("emp::")) {
            return Promise.all([
                App.Variables.vdbEmpData.invoke(),
                App.Variables.vdbEmpDataNew.listRecords(),
                App.getVDBAllRecords(App.Variables.vdbEmpDepartment)
            ]).then(function(vals) {
                // console.log(vals)
                vals[0] = {
                    data: JSON.parse(vals[0].body).content
                }
                return Promise.resolve(vals)
            }).then(function([{
                data
            }, dataRamco, dataDepartment]) {
                dataRamco = dataRamco.data


                if (data.length) {
                    data = data[0]
                    App.Variables.loggedInUserData.setValue("user_email", data.lgEmailAio)
                }

                if (dataRamco.length) {
                    dataRamco = dataRamco[0]
                    App.Variables.loggedInUserData.setValue("user_full_name", dataRamco.employeeName)
                    App.Variables.loggedInUserData.setValue("user_position_code", dataRamco.positionCode)
                    App.Variables.loggedInUserData.setValue("user_position", dataRamco.positionDesc)
                    App.Variables.loggedInUserData.setValue("user_level_code", dataRamco.jobGradeCode)
                    App.Variables.loggedInUserData.setValue("user_level", dataRamco.jobGradeDesc)
                    App.Variables.loggedInUserData.setValue("user_supervisor_name", dataRamco.supervisorName)
                    App.Variables.loggedInUserData.setValue("user_supervisor_nik", dataRamco.supervisorNik)
                }

                App.Variables.loggedInUserData.setValue("user_department", dataDepartment.map(function(item) {
                    return {
                        company: item.company,
                        companyId: item.companyId,
                        departmentId: item.departmentId,
                        department: item.departrment
                    }
                }))

                return Promise.resolve()
            })
        } else {
            //get data vendor ke email terus ke vendor code
            return App.Variables.vdbVendorData.invoke().then(function(vals) {
                return Promise.resolve({
                    data: JSON.parse(vals.body).content
                })
            }).then(function(data) {

                if (data.data.length == 0) {
                    //ambil data disini aja.
                    return Promise.resolve()
                }
                data = data.data[0]

                console.log(data)
                App.Variables.loggedInUserData.setValue("user_full_name", data.title + " " + data.vendorName)
                App.Variables.loggedInUserData.setValue("user_email", data.email)
                App.Variables.loggedInUserData.setValue("user_sapcode", data.vendorCode)
                return Promise.resolve()
            })
        }
    }).then(function() {
        return App.Variables.vdbqUserRole.invoke().catch(function(err) {
            console.log(err)
            return {
                body: "{\"auth\": \"\"}"
            }
        })
        // console.log(App.Variables.vdbqUserRole)
    }).then(function({
        body
    }) {
        var data = JSON.parse(body)
        // console.log(data)
        App.Variables.loggedInUserData.setValue("user_roles", (data.auth || "").split(","))
        return Promise.resolve()
    }).then(function() {
        return App.getVDBQAllRecords(App.Variables.vdbqUserDelegatedRoles).then(function(roles) {
            App.Variables.loggedInUserData.setValue("user_delegated_roles", roles.map(function(item) {
                return item.authCode
            }))
            App.Variables.loggedInUserData.setValue("user_delegated_data", roles.reduce(function(accu, item) {
                if (!accu[item.authModule]) {
                    accu[item.authModule] = []
                }
                if (accu[item.authModule].indexOf(item.delFromUserId) < 0) {
                    accu[item.authModule].push(item.delFromUserId)
                }
                return accu
            }, {}))
            return Promise.resolve()
        })
    }).then(function() {
        App.Variables.loggedInUser.setValue("roles", App.Variables.loggedInUserData.getValue("user_roles").concat(App.Variables.loggedInUserData.getValue("user_delegated_roles")))
        // console.log(App.Variables.loggedInUser, App.Variables.loggedInUserData)
        return Promise.resolve()
    })
}

// table numbering
App.getTableNumbering = function(variable, data, idField) {
    try {
        return (variable.pagination.size *
                variable.pagination.number) +
            variable.dataSet.map(function(item) {
                return item[idField]
            }).indexOf(data[idField]) + 1
    } catch (err) {
        return variable.dataSet.map(function(item) {
            return item[idField]
        }).indexOf(data[idField]) + 1
    }
}


/* perform any action on session timeout here, e.g clearing some data, etc */
App.onSessionTimeout = function() {
    /*
     * NOTE:
     * On re-login after session timeout:
     * if the same user logs in(through login dialog), app will retain its state
     * if a different user logs in, app will be reloaded and user is redirected to respective landing page configured in Security.
     */
};

/*
 * This application level callback function will be invoked after the invocation of PAGE level onPageReady function.
 * Use this function to write common logic across the pages in the application.
 * activePageName : name of the page
 * activePageScope: scope of the page
 * $activePageEl  : page jQuery element
 */
App.onPageReady = async function(activePageName, activePageScope, $activePageEl) {
    try {
        console.log('loggedInUser', App.Variables.loggedInUser.dataSet);
        //get identity
        if (Object.keys(App.Variables.mDeviceIdentity.dataSet).length == 0) {
            await App.Variables.getIdentity.invoke();
        }

        //check chatbot access
        if (activePageName !== 'LoginAIO' &&
            App.Variables.loggedInUser.dataSet.name.substring(0, 5) == 'emp::' &&
            App.Variables.loggedInUser.dataSet.roles.length > 0) {
            var nik = String(App.Variables.loggedInUser.dataSet.id);
            var role = App.Variables.loggedInUser.dataSet.roles.filter(x => {
                return x.substring(0, 3) == 'BOT';
            }).join(',');

            console.log({
                nik,
                role
            })

            if (role.length > 0) {
                if (!App.Variables.loggedInUser.dataSet.chatbot) {
                    window.addEventListener("message", function(event) {
                        if (event.data.name === "webchatReady") {
                            window.botpressWebChat.sendEvent({
                                type: "proactive-trigger",
                                channel: "web",
                                payload: {
                                    nik: nik,
                                    role: role
                                },
                            })
                        }
                    })




                    window.botpressWebChat.init({
                        host: "https://ihelp.aio.co.id/bot-api",
                        botId: "bot-helpdesk",
                        hideWidget: true,
                    })

                    let btn = document.createElement("img");
                    btn.src = "resources/images/imagelists/sisuka.png"
                    btn.classList.add("bottom-right");
                    btn.id = "show-bp";
                    btn.addEventListener('click', function() {
                        window.botpressWebChat.sendEvent({
                            type: 'show'
                        })
                    })

                    document.body.appendChild(btn);
                    App.Variables.loggedInUser.dataSet.chatbot = true
                }

            }


        }
    } catch (e) {
        console.log(e);
        App.Actions.appNotification.invoke({
            "class": "error",
            "message": "An error has been occured, " + e.message,
            "position": "bottom right"
        });
    }
};

/*
 * This application level callback function will be invoked after a Variable receives an error from the target service.
 * Use this function to write common error handling logic across the application.
 * errorMsg:    The error message returned by the target service. This message will be displayed through appNotification variable
 *              You can change this though App.Variables.appNotification.setMessage(YOUR_CUSTOM_MESSAGE)
 * xhrObj:      The xhrObject used to make the service call
 *              This object contains useful information like statusCode, url, request/response body.
 */
App.onServiceError = function(errorMsg, xhrObj) {

};

App.bodyForgetEmail = function(password, name) {
    return `<html>
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
			<img width="206" height="103" src="${App.domain}resources/images/logos/login-otsuka.png" />
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
		color: #262626;">Hi ${name},</a><br><br>
   <a style="color: #262626;">You are recently requested to reset your password for your Procure to Pay account here is your new password<br><br>
    your password:</a> <br>
    <a style="font-weight: bold; color: #262626;">${password}</a><br><br><br>
	<a href="${App.domain}#/LoginAIO" target="_blank"
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
		<img width="20" height="15" src="${App.domain}resources/images/logos/icon-footer-mail.png" />
		
	<a style="align-items: center;
		color: #fff;
		background: #1861B7;
		text-decoration: none;">Team Procure to Pay</a>
	</td>
  </tr>
  
</table>

</body>
</html>`
}

/*
    ===Notification===
    idUserReceiver(string) => username e.g: emp::643
    taskType(string) => Change Password, Additional, Reclass, PR, PO, GR
    else => not match
*/
App.sendNotification = function(taskType, idUserReceiver, messages = "") {
    // check not null
    if (!taskType || !idUserReceiver) {
        return;
    }

    if (!messages) {
        switch (taskType) {
            case "Change Password":
                messages = "<b>Konfirmasi Password</b> - Segera ubah password untuk meningkatkan keamanan akun anda pada menu Profile";
                break;
            default:
                messages = "<b>" + taskType + "</b> - Segera melakukan approval pada halaman task list";
                break;
        }
    }

    var dataNotif = {
        "inbTaskType": taskType,
        "inbTimestamp": new Date(),
        "userId": idUserReceiver,
        "inbSubject": messages,
        "inbCreatedBy": App.Variables.loggedInUserData.getValue('user_full_name')
    }

    return App.Variables.vdbInboxNotif.createRecord({
        row: dataNotif
    }).catch(err => {
        console.log(err)
        return Promise.resolve()
    })
}

// send email using aio web service
App.sendEmail = function(to, cc, subject, body, text) {
    if (App.isDevelopment) {
        body += "<br><br><p><b>Note: THIS IS DEVELOPMENT EMAIL, EMAIL SHOULD BE SENDED TO " + to + "</b></p>"
        to = App.developmentEmailReceiver
    }
    return App.Variables.varSendEmail.invoke({
        inputFields: {
            to: to,
            cc: cc,
            subject: subject,
            String: body,
            text: text
        }
    })
}



App.sendEmailRFQ = function(sendTo, name, url, createdName, companyName) {
    //return Promise.resolve();
    Promise.resolve().then(function() {
        var urlRedirect = App.domain + '#/' + url

        // render email template
        var emailBody = `<html>
            <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700;800;900&amp;display=swap" rel="stylesheet" />
            <body>
            <table style=" width: 560px;
            		height: 780px;
            		background: white;
            		border-radius: 20px;
            		font-family: Inter;
            		text-align: center; 
            		vertical-align: middle;">
            	<tr>
            		<th>
            			<img width="206" height="103" src="${App.domain}resources/images/logos/login-otsuka.png" />
            		</th>
            	</tr>
                <tr style="font-weight: normal;
                	font-size: 13px;
                	line-height: 24px;
                	text-align: center;
                	color: #000000;">
                    <td style = "padding-left: 32px; padding-right: 32px;">
                        <p>
                            Dear ${name},Good day to you!<br>
                            Below Is a link for request for Quotation (RFQ) from our e Procurement system.
                        </p>
                        <p style="color: #666666">
                            Dear ${name},<br>
                            Dibawah adalah link dari permintaan penawaran (RFQ) dari system e Procurement kami.
                        </p>
                        <p>
                            Please click the button and review the RFQ online where you may enter your response directly through the Application.
                        </p>
                        <p style="color: #666666">
                            Silahkan klik tombol berikut dan silahkan melakukan pengecekan terhadap RFQ online dimana anda dapat langsung melakukan respon secara langsung didalam applikasi.
                        </p>
                        <br><br>
                        <a href=${urlRedirect} target="_blank"
                        	style="align-items: center;
                        		padding: 12px 20px;
                        		width: 122px;
                        		height: 41px;
                        		color: #fff;
                        		background: #1861B7;
                        		border-radius: 4px;
                        		text-decoration: none;
                                font-size: 12px;">Go to Request For Quotation Form</a>
                        <br><br><br>
                        <p>
                            Thank You<br>
                            <span style="color: #666666">Terima Kasih</span>
                        </p>
                        <p>
                            ${createdName}<br>
                            ${companyName || 'PT. Amerta Indah Otsuka'}
                        </p>
                    </td>
                </tr>
                <tr></tr>
                <tr>
                    <td style="align-items: center;
                		padding: 12px 20px;
                		width: 100%;
                		height: 41px;
                		color: #fff;
                		background: #1861B7;
                		border-radius: 0px 0px 20px 20px;
                		text-decoration: none;
                        font-size: 14px;">
                		<img width="20" height="15" src="${App.domain}resources/images/logos/icon-footer-mail.png" />
                		
                    	<a style="align-items: center;
                    		color: #fff;
                    		background: #1861B7;
                    		text-decoration: none;">Team Procure to Pay</a>
                	</td>
                </tr>
            </table>
            
            </body>
            </html>`
        return App.sendEmail(sendTo, "", "Request for Quotation", emailBody, "").catch(function(err) {
            console.log(err)
            return Promise.reject({
                message: "Failed send email to vendor",
                type: "warning"
            })
        })
    })
}

App.getIdentityonSuccess = function(variable, data) {
    App.Variables.mDeviceIdentity.dataSet = {};
    let dataList = data.value.split('\n');
    dataList.forEach(x => {
        let b = x.split('=');
        App.Variables.mDeviceIdentity.dataSet[b[0]] = b[1]
    })
    console.log('id', App.Variables.mDeviceIdentity.dataSet);
};