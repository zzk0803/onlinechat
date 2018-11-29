;var wp = {
    main: {},
    air: {},
    message: {},
    ajax: {}
};

wp.main.constants = {
    longtextLength: 200,
    longtextUrl: "http://" + window.location.host + window.wp_context_path + "/longtext",
    iconUrl: "http://" + window.location.host + window.wp_context_path + "/usericon"
};

wp.main.onlineAccountAndElements = {};

wp.main.isShortText = function (message) {
    let stringLength = wp.main.checkStringLength(message);
    return stringLength.charCount < wp.main.constants.longtextLength;
};

wp.main.checkStringLength = function (aString) {
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
};

wp.main.sendMessage = function () {
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
            function (resJson) {
                let response = JSON.parse(resJson);
                let referenceMessageWithUUID = wp.message.referenceMessage(response.content);
                wp.air.send(
                    JSON.stringify(
                        referenceMessageWithUUID
                    )
                )
            }
        );
    }
    wp.message.appendMyMessage(message);
    oTextarea.value = "";
};

wp.main.bindSendBtn = function () {
    //绑定发送按钮事件
    let oSendBtn = document.getElementById("send");
    oSendBtn.onclick = function (event) {
        wp.main.sendMessage();
        event.stopPropagation();
    }
};

// wp.main.bindKeyShortCut = function () {
//     window.onkeydown = function (event) {
//         if (event.ctrlKey && event.code === 13) {
//             this.sendMessage();
//         }
//         event.stopPropagation();
//     }
// };

wp.main.init = function () {
    this.bindSendBtn();
    // this.bindKeyShortCut();
};