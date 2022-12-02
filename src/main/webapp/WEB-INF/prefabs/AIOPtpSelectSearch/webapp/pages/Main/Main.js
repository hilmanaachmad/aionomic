/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */

/*
 * This function will be invoked when any of this prefab's property is changed
 * @key: property name
 * @newVal: new value of the property
 * @oldVal: old value of the property
 */
Prefab.onPropertyChange = function(key, newVal, oldVal) {};

Prefab.onReady = function() {
    // this method will be triggered post initialization of the prefab.
    Prefab.Variables.listDataset.clearData()
    Prefab.datavariable.invoke().then(function() {
        Prefab.Variables.listDataset.setData(Prefab.datavariable.dataSet.map(function($) {
            let display = null
            let value = null
            try {
                if (Prefab.listdisplaykey == "ALL") {
                    display = $
                } else {
                    display = eval(Prefab.listdisplaykey || "")
                }
            } catch (err) {
                display = $[Prefab.listdisplaykey || ""]
            }
            try {
                if (Prefab.listdatakey == "ALL") {
                    value = $
                } else {
                    value = eval(Prefab.listdatakey || "")
                }
            } catch (err) {
                value = $[Prefab.listdatakey || ""]
            }
            return {
                display: display,
                value: value
            }
        }))
    })
};

Prefab.text1Change = function($event, widget, newVal, oldVal) {
    console.log(Prefab, newVal)
    Prefab.datavariable.setInput(Prefab.searchkey, newVal)
    Prefab.datavariable.setFilter(Prefab.searchkey, newVal)

    Prefab.datavariable.invoke().then(function() {
        Prefab.Variables.listDataset.setData(Prefab.datavariable.dataSet.map(function($) {
            let display = null
            let value = null
            try {
                if (Prefab.listdisplaykey == "ALL") {
                    display = $
                } else {
                    display = eval(Prefab.listdisplaykey || "")
                }
            } catch (err) {
                display = $[Prefab.listdisplaykey || ""]
            }
            try {
                if (Prefab.listdatakey == "ALL") {
                    value = $
                } else {
                    value = eval(Prefab.listdatakey || "")
                }
            } catch (err) {
                value = $[Prefab.listdatakey || ""]
            }
            return {
                display: display,
                value: value
            }
        }))
    })
};

Prefab.popuplistSelect = function(widget, $data) {
    Prefab.Variables.selectedData.setValue("display", $data.display)
    Prefab.Variables.selectedData.setValue("value", $data.value)
};
Prefab.popuplistSetrecord = function($event, widget, $index, $data) {
    if (($index + 1) * 20 <= Prefab.Variables.listDataset.dataSet.length || Prefab.datavariable.pagination.last) {
        return
    }
    Prefab.datavariable.invoke({
        page: Prefab.datavariable.pagination.number + 2
    }).then(function() {
        Prefab.Variables.listDataset.setData(Prefab.Variables.listDataset.getData().concat(Prefab.datavariable.dataSet.map(function($) {
            let display = null
            let value = null
            try {
                if (Prefab.listdisplaykey == "ALL") {
                    display = $
                } else {
                    display = eval(Prefab.listdisplaykey || "")
                }
            } catch (err) {
                display = $[Prefab.listdisplaykey || ""]
            }
            try {
                if (Prefab.listdatakey == "ALL") {
                    value = $
                } else {
                    value = eval(Prefab.listdatakey || "")
                }
            } catch (err) {
                value = $[Prefab.listdatakey || ""]
            }
            return {
                display: display,
                value: value
            }
        })))
    })
};
