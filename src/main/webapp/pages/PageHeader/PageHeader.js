/*
 * Use App.getDependency for Dependency Injection
 * eg: var DialogService = App.getDependency('DialogService');
 */

/* perform any action on widgets/variables within this block */
Partial.initial = ""
Partial.onReady = function() {
    console.log('AWWWWWWWWWWWWWWWWWWWWWW');
    window.onscroll = function() {
        myFunction()
    };

    var header = document.getElementsByClassName("myHeader");
    console.log(header);
    var sticky = header.offsetTop;

    function myFunction() {
        console.log('as');
        if (window.pageYOffset > sticky) {
            header.classList.add("sticky");
        } else {
            header.classList.remove("sticky");
        }
    }
    /*
     * variables can be accessed through 'Partial.Variables' property here
     * e.g. to get dataSet in a staticVariable named 'loggedInUser' use following script
     * Partial.Variables.loggedInUser.getData()
     *
     * widgets can be accessed through 'Partial.Widgets' property here
     * e.g. to get value of text widget named 'username' use following script
     * 'Partial.Widgets.username.datavalue'
     */

    Partial.initial = Partial.App.Variables.loggedInUserData.dataSet.user_full_name.split(" ").filter(function(el) {
        return el.length
    }).map(function(el) {
        return el[0].toUpperCase()
    }).filter(function(e, i, arr) {
        return i == 0 || i == arr.length - 1
    }).join("")

    var locale = Partial.App.Variables.supportedLocale.getDefaultLocale().toUpperCase()
    Partial.Widgets.label16_1.caption = locale == "JA-JP" ? "JP" : locale == "ID-ID" ? "ID" : locale
    Partial.App.Variables.vdbqUnseenInboxCount_new.invoke()
    Partial.App.Variables.vdbqUnseenNotification.invoke({
        page: 1
    })
};

Partial.container2Click = function($event, widget) {
    // if (!$(".aio-p2p-header-container.opened").length) {
    //     Partial.App.Variables.vdbqUnseenNotification.invoke({
    //         page: 1
    //     }).then(function() {
    //         console.log(Partial.App.Variables.vdbqUnseenNotification)
    //     })
    // }
    // if (!$(".aio-p2p-header-container.opened").length) {
    //     Partial.App.Variables.vdbqUnseenNotification.dataSet = []
    //     Partial.App.Variables.vdbqUnseenNotification.invoke({
    //         page: 1
    //     })
    // }
    $(widget.nativeElement).parents(".aio-p2p-header-container").addClass("opened")
    $(widget.nativeElement).parents(".aio-p2p-header-container").addClass("clicked")
    console.log("open")
};

// Partial.container2Click = function($event, widget) {
//     $(widget.nativeElement).parents(".aio-p2p-popup-container").toggleClass("opened")
// };

Partial.container19Click = function($event, widget) {
    // $(widget.nativeElement).parents(".aio-p2p-header-component-mail").toggleClass("opened")
    App.Actions.goToPage_Inbox.invoke()
};
Partial.container20_1Click = function($event, widget) {
    Partial.Widgets.containerPopupLang.show = !Partial.Widgets.containerPopupLang.show
};
Partial.label20Click = function($event, widget) {
    Partial.Widgets.containerPopupLang.show = !Partial.Widgets.containerPopupLang.show
    Partial.Variables.varLang.dataSet[1].status = false
    Partial.Variables.varLang.dataSet[0].status = true
    Partial.Variables.varLang.dataSet[2].status = false
    Partial.App.changeLocale(Partial.Variables.varLang.dataSet[0].id)
    Partial.App.reload()
};
Partial.label21Click = function($event, widget) {
    Partial.Widgets.containerPopupLang.show = !Partial.Widgets.containerPopupLang.show
    Partial.Variables.varLang.dataSet[1].status = true
    Partial.Variables.varLang.dataSet[0].status = false
    Partial.Variables.varLang.dataSet[2].status = false
    Partial.App.changeLocale(Partial.Variables.varLang.dataSet[1].id)
    Partial.App.reload()
};
Partial.label20_1Click = function($event, widget) {
    Partial.Widgets.containerPopupLang.show = !Partial.Widgets.containerPopupLang.show
    Partial.Variables.varLang.dataSet[1].status = false
    Partial.Variables.varLang.dataSet[0].status = false
    Partial.Variables.varLang.dataSet[2].status = true
    Partial.App.changeLocale(Partial.Variables.varLang.dataSet[2].id)
    Partial.App.reload()
};
// Partial.container8Click = function($event, widget) {
//     $event.stopPropagation()
// };