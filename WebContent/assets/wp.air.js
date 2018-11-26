;wp.air = {
    init_flag: false,
    ws_url: "ws://" + window.location.host + window.zzk_context_path + "/ws/chat",
    websocket: null,
    init: function () {
        this.websocket = new WebSocket(this.ws_url);
        this.websocket.onopen = function () {
            console.log("websocket connect success!!")
        };

        this.websocket.onmessage = function (event) {
            let received = event.data;
            let messageObject = JSON.parse(received);
            let fromAccount = messageObject.fromAccount;
            switch (messageObject.type) {
                case wp.message.messageType.system_message:
                    let account_event = messageObject.content;
                    let target_account = messageObject.fromAccount;
                    wp.message.appendAccountEventMessage(account_event, target_account);
                    break;

                case wp.message.messageType.short_message:
                    let content = messageObject.content;
                    wp.message.appendReceivedMessage(content, fromAccount);
                    break;

                case wp.message.messageType.reference_message:
                    let uuid = messageObject.content;
                    wp.message.appendReferenceMessage(uuid, fromAccount);
            }
        };

        this.init_flag = true;
    },

    send: function (message) {
        this.websocket.send(message);
    },


};