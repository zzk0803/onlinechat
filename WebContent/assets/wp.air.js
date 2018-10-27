wp.air = {
    init_flag: false,
    ws_url: "ws://" + window.location.host + "/ws/chat",
    websocket: null,
    init: function () {
        this.websocket = new WebSocket(this.ws_url);
        this.websocket.onopen = function () {
            console.log("websocket connect success!!")
        };

        this.websocket.onmessage = function (event) {
            console.log(event);
            let received = event.data;
            wp.message.appendReceivedMessage(received);
        };

        this.init_flag = true;
    },

    send: function (message) {
        this.websocket.send(message);
    },


};