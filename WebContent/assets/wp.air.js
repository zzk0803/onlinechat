;wp.air = {
    init_flag: false,
    ws_url: "ws://" + window.location.host + window.wp_context_path + "/ws/chat",
    websocket: null,
};

wp.air.init = function () {
    this.websocket = new WebSocket(this.ws_url);
    this.websocket.onopen = function () {
        console.log("websocket connect success!!")
    };

    this.websocket.onmessage = function (event) {
        let received = event.data;
        console.log(received);
        let messageObject = JSON.parse(received);
        let fromAccount = messageObject.fromAccount;
        let isBroadcast = messageObject.broadcastMessage;
        switch (messageObject.type) {
            case wp.message.messageType.system_message:
                let accountEventType = messageObject.content;
                wp.message.dealSystemMessage(accountEventType, fromAccount);
                break;

            case wp.message.messageType.short_message:
                let content = messageObject.content;
                content = unescape(content);
                wp.message.appendReceivedMessage(content, fromAccount, isBroadcast);
                break;

            case wp.message.messageType.reference_message:
                let uuid = messageObject.content;
                wp.message.appendReferenceMessage(uuid, fromAccount, isBroadcast);
                break;

            case wp.message.messageType.account_administer:
                // todo:接收到账户管控信息，禁用或启用禁言
                break;

            case wp.message.messageType.heart_beat:
                wp.air.send(wp.message.heartBeatMessage());
                break;

            default:
                break;
        }
    };

    window.onbeforeunload = function () {
        wp.air.websocket.close();
    };

    this.init_flag = true;

};

wp.air.send = function (message) {
    wp.air.websocket.send(message);
};