;wp.message = {
    init: function () {

    },

    appendMyMessage: function (message) {
        let oMessageUl = document.getElementById("received");
        let emptyLiEle = document.createElement("li");
        let mySendMessageLiEle = document.createElement("li");
        mySendMessageLiEle.appendChild(this.generatePreElement(message));
        mySendMessageLiEle.className = "me";
        oMessageUl.appendChild(mySendMessageLiEle);
        oMessageUl.appendChild(emptyLiEle);
    },

    appendReceivedMessage: function (message) {
        let messageObject = JSON.parse(message);
        let oMessageUl = document.getElementById("received");
        let emptyLiEle = document.createElement("li");
        let myReceivedMessageLiEle = document.createElement("li");
        switch (messageObject.type) {
            case "event":
                let typeCH = {
                    "online": "上线了",
                    "offline": "下线了"
                };
                myReceivedMessageLiEle.innerHTML = messageObject.username + typeCH[messageObject.event];
                myReceivedMessageLiEle.className = "apprise";
                oMessageUl.appendChild(myReceivedMessageLiEle);
                oMessageUl.appendChild(emptyLiEle);
                break;

            case "message":
                myReceivedMessageLiEle.appendChild(this.generatePreElement(messageObject.message));
                myReceivedMessageLiEle.className = "others";
                oMessageUl.appendChild(myReceivedMessageLiEle);
                oMessageUl.appendChild(emptyLiEle);
                break;


            case "reference":
                wp.ajax.get(wp.main.constants.longtextUrl, {uuid: messageObject.uuid}, function (response) {
                    myReceivedMessageLiEle.appendChild(wp.message.generatePreElement(response));
                    myReceivedMessageLiEle.className = "others";
                    oMessageUl.appendChild(myReceivedMessageLiEle);
                    oMessageUl.appendChild(emptyLiEle);
                });
                break;

            default:
                alert("出BUG了");
                break;
        }
    },

    generatePreElement: function (naturalMessage) {
        let preEle = document.createElement("pre");
        preEle.innerText = naturalMessage;
        return preEle;
    },
};