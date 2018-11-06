;var wp = {
    main: {},
    air: {},
    message: {},
    ajax: {}
};

window.onload = function () {
    wp.main.init();
    wp.air.init();
    wp.message.init();
    wp.ajax.init();
};

wp.main = {
    constants: {
        longtextLength: 200,
        longtextUrl: "http://" + window.location.host + "/longtext",
        iconUrl:"http://" + window.location.host + "/usericon"
    },
    isShortText: function (message) {
        let stringLength = wp.main.checkStringLength(message);
        return stringLength.charCount < wp.main.constants.longtextLength;
    },
    checkStringLength: function (aString) {
        let byteCount = 0;
        let charCount = 0;
        for (let i = 0; i < aString.length; i++) {
            let c = aString.charCodeAt(i);
            if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
                byteCount++;
            }
            else {
                byteCount += 2;
            }
            charCount++;
        }
        return {"byteCount": byteCount, "charCount": charCount};
    },
    init: function () {
        //绑定发送按钮事件
        let oSendBtn = document.getElementById("send");
        oSendBtn.onclick = function (event) {
            let oTextarea = document.getElementById("msg");
            let message = oTextarea.value;
            // 判断是否是短文本，是的话直接使用websocket推送，否则通过servlet提交
            if (wp.main.isShortText(message)) {
                wp.air.send(message);
            } else {
                let formData = new FormData();
                formData.append("text", message);
                wp.ajax.post(
                    wp.main.constants.longtextUrl,
                    formData,
                    function (xml) {
                    }
                );
            }
            wp.message.appendMyMessage(message);
            oTextarea.value = "";
            event.stopPropagation();
        }
    }
};