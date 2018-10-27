wp.message = {
    init: function () {

    },
    appendMyMessage: function (message) {
        let oMessageUl = document.getElementById("received");
        let emptyLiEle = document.createElement("li");
        let mySendMessageLiEle = document.createElement("li");
        mySendMessageLiEle.innerHTML = "我:" + message;
        mySendMessageLiEle.className = "me";
        oMessageUl.appendChild(mySendMessageLiEle);
        oMessageUl.appendChild(emptyLiEle);
    },

    appendReceivedMessage: function (message) {
        let messageObject = JSON.parse(message);
        if (messageObject["type"]) {
            this.appendAppriseMessage(message, messageObject);
        } else {
            let oMessageUl = document.getElementById("received");
            let emptyLiEle = document.createElement("li");
            let myReceivedMessageLiEle = document.createElement("li");
            myReceivedMessageLiEle.innerHTML = message;
            myReceivedMessageLiEle.className = "others";
            oMessageUl.appendChild(myReceivedMessageLiEle);
            oMessageUl.appendChild(emptyLiEle);
        }
    },

    appendAppriseMessage: function (message, messageObject) {
        var typeCH = {
            "online": "上线了",
            "offline": "下线了"
        };
        let oMessageUl = document.getElementById("received");
        let emptyLiEle = document.createElement("li");
        let myReceivedMessageLiEle = document.createElement("li");
        myReceivedMessageLiEle.innerHTML = messageObject.username + typeCH[messageObject.type];
        myReceivedMessageLiEle.className = "apprise";
        oMessageUl.appendChild(myReceivedMessageLiEle);
        oMessageUl.appendChild(emptyLiEle);
    }
};