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
        longtextUrl: "http://" + window.location.host + window.wp_context_path + "/longtext",
        iconUrl: "http://" + window.location.host + window.wp_context_path + "/usericon"
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
            } else {
                byteCount += 2;
            }
            charCount++;
        }
        return {"byteCount": byteCount, "charCount": charCount};
    },
    bind_sendBtn: function () {
        //绑定发送按钮事件
        let oSendBtn = document.getElementById("send");
        oSendBtn.onclick = function (event) {
            let oTextarea = document.getElementById("msg");
            let message = oTextarea.value;
            // 判断是否是短文本，是的话直接使用websocket推送，否则通过servlet提交
            if (wp.main.isShortText(message)) {
                wp.air.send(
                    JSON.stringify(
                        wp.message.shortMessage(message)
                    )
                );
            } else {
                let formData = new FormData();
                formData.append("text", message);
                wp.ajax.post(
                    wp.main.constants.longtextUrl,
                    formData,
                    function (xml) {
                        let response = JSON.parse(xml);
                        let message = wp.message.referenceMessage(response.content);
                        wp.air.send(
                            JSON.stringify(
                                message
                            )
                        )
                    }
                );
            }
            wp.message.appendMyMessage(message);
            oTextarea.value = "";
            event.stopPropagation();
        }
    },
    init: function () {
        this.bind_sendBtn();
    }
};