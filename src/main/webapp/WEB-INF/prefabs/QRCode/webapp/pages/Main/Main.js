'use strict';
var _renderQRCode;

function renderQRCode() {
    var qrEle = Prefab.Widgets.qrcode.$element.get(0);
    var qrErrorCorrectLevel = {
        L: 1,
        M: 0,
        Q: 3,
        H: 2
    };

    qrEle.innerHTML = '';
    var qrCode = new QRCode(qrEle, {
        'width': parseInt(Prefab.width),
        'height': parseInt(Prefab.height),
        'typeNumber': Prefab.type,
        'colorDark': Prefab.darkcolor,
        'colorLight': Prefab.lightcolor,
        'correctLevel': qrErrorCorrectLevel[Prefab.level]
    });

    qrCode.makeCode(Prefab.text);
}
_renderQRCode = _.debounce(renderQRCode, 100);
Prefab.onPropertyChange = function (key, newVal, oldVal) {
    _renderQRCode();
};

Prefab.onReady = _renderQRCode;
