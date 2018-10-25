;var wp = {
    main: {},
    air: {},
    message: {}
};

window.onload = function () {
    wp.main.init();
    wp.air.init();
    wp.message.init();
};

wp.main = {
    init: function () {
        let oSendBtn = document.getElementById("send");
        oSendBtn.onclick = function (event) {
            let oTextarea = document.getElementById("msg");
            let message = oTextarea.value;
            wp.air.send(message);
            wp.message.appendMyMessage(message);
            oTextarea.value = "";
            event.stopPropagation();
        }
    },
};