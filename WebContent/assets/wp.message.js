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
        let oMessageUl = document.getElementById("received");
        let emptyLiEle = document.createElement("li");
        let myReceivedMessageLiEle = document.createElement("li");
        myReceivedMessageLiEle.innerHTML = message;
        myReceivedMessageLiEle.className = "others";
        oMessageUl.appendChild(myReceivedMessageLiEle);
        oMessageUl.appendChild(emptyLiEle);
    }
};