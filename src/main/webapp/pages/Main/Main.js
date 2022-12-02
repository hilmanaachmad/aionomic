/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */

/* perform any action on widgets/variables within this block */
Page.onReady = async function() {

    var d = new Date,
        today = [d.getFullYear(),
            d.getMonth() + 1,
            d.getDate()
        ].join('-')
    /*
     * variables can be accessed through 'Page.Variables' property here
     * e.g. to get dataSet in a staticVariable named 'loggedInUser' use following script
     * Page.Variables.loggedInUser.getData()
     *
     * widgets can be accessed through 'Page.Widgets' property here
     * e.g. to get value of text widget named 'username' use following script
     * 'Page.Widgets.username.datavalue'
     */
    // console.log(Page)

    if (Page.Variables.loggedInUserData.dataSet.username.includes('ven:')) {


        var singleDate = await Page.Variables.DBAnnouncement.listRecords({
            filterFields: {
                "isValid": {
                    "value": 1,
                    "matchMode": "EQUALS"
                },
                "page": {
                    "value": 'dashboard',
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
                    "value": 'dashboard',
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
            var file_object = fetch(Page.Variables.ModelAnnouncement.dataSet[0].content, {
                    responseType: 'arraybuffer'
                })
                .then(r => r.blob())
                .then(blob => {
                    var objectURL = URL.createObjectURL(blob);
                    PDFObject.embed(objectURL, "#pdf-canvas-container");
                });
        }
    }

};