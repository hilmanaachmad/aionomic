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
    Page.Variables.sp_UsageBudgetDetails.invoke({
        inputFields: {
            bh_id: 835,
            type_data: 'NULL',
            value_beginning: 'NULL',
            value_adjustment: 'NULL',
            value_reserve: 'NULL',
            value_commitment: 'NULL',
            value_actual: 'NULL',
            created_at_start: 'NULL',
            created_at_end: 'NULL',
            created_by: 'NULL',
            coa: 'NULL',
            remarks: 'NULL',
            docNumber: 'NULL'
        }
    });

};


Page.sp_UsageBudgetDetailsonBeforeDatasetReady = function(variable, data) {
    let budgetAfterAdjustment;
    let totalBudgetAfterAdjustment;
    for (var i = 0; i < data.length; i++) {
        data[i].num = noUsage++

            //Edit 14 Desember
            if (data[i].type.includes('BEGINNING')) {
                budgetAfterAdjustment = data[i].budgetAfterAdjustment
                data[i].available = data[i].budgetAfterAdjustment
                totalBudgetAfterAdjustment = budgetAfterAdjustment - (data[i].reserved + data[i].commitment + data[i].actual)

            } else {
                totalBudgetAfterAdjustment = totalBudgetAfterAdjustment - (data[i].reserved + data[i].commitment + data[i].actual)
                data[i].available = totalBudgetAfterAdjustment
                console.log($data[i].available)
            }

        if ($data[i].type.includes('PR') || data[i].type.includes('PO') || data[i].type.includes('GR')) {
            budgetAfterAdjustment = '-'
        }
        data[i].budgetAfterAdjustment = budgetAfterAdjustment
        //Edit 14 Desember

        // if ($data[i].type.includes('RECLASS') || $data[i].type.includes('ADDITIONAL')) {
        //     $data[i].coa = '-'
        // }




        if (data.length == (i + 1)) {
            //Budget Original
            var bdOriginal = data.filter(item =>
                item.type == 'BEGINNING'
            )

            if (bdOriginal.length > 0) {
                Page.Variables.totalUsageBudgetOriginal.dataSet.dataValue = bdOriginal[0].budgetOriginal
            }

        }
    }
    console.log("data", data);
    return data;
};

Page.sp_UsageBudgetDetailsonSuccess = function(variable, data) {
    console.log("var", Page.Variables.sp_UsageBudgetDetails.dataSet);
    let budgetAfterAdjustment;
    let totalBudgetAfterAdjustment;
    for (var i = 0; i < data.length; i++) {
        data[i].num = noUsage++

            //Edit 14 Desember
            if (data[i].type.includes('BEGINNING')) {
                budgetAfterAdjustment = data[i].budgetAfterAdjustment
                data[i].available = data[i].budgetAfterAdjustment
                totalBudgetAfterAdjustment = budgetAfterAdjustment - (data[i].reserved + data[i].commitment + data[i].actual)

            } else {
                totalBudgetAfterAdjustment = totalBudgetAfterAdjustment - (data[i].reserved + data[i].commitment + data[i].actual)
                data[i].available = totalBudgetAfterAdjustment
                console.log($data[i].available)
            }

        if ($data[i].type.includes('PR') || data[i].type.includes('PO') || data[i].type.includes('GR')) {
            budgetAfterAdjustment = '-'
        }
        data[i].budgetAfterAdjustment = budgetAfterAdjustment
        //Edit 14 Desember

        // if ($data[i].type.includes('RECLASS') || $data[i].type.includes('ADDITIONAL')) {
        //     $data[i].coa = '-'
        // }




        if (data.length == (i + 1)) {
            //Budget Original
            var bdOriginal = data.filter(item =>
                item.type == 'BEGINNING'
            )

            if (bdOriginal.length > 0) {
                Page.Variables.totalUsageBudgetOriginal.dataSet.dataValue = bdOriginal[0].budgetOriginal
            }

        }
    }
    Page.Variables.sp_UsageBudgetDetails.dataSet = data;
    console.log("dataafter", Page.Variables.sp_UsageBudgetDetails.dataSet);
};