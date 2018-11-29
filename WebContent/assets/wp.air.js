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
                let account_event = messageObject.content;
                wp.message.appendSystemMessage(account_event, fromAccount);
                break;

            case wp.message.messageType.short_message:
                let content = messageObject.content;
                wp.message.appendReceivedMessage(content, fromAccount, isBroadcast);
                break;

            case wp.message.messageType.reference_message:
                let uuid = messageObject.content;
                wp.message.appendReferenceMessage(uuid, fromAccount, isBroadcast);
        }
    };

    this.init_flag = true;

};

wp.air.send = function (message) {
    wp.air.websocket.send(message);
};