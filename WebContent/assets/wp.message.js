;wp.message = {
    messageType: {
        short_message: "SHORT_MESSAGE",
        reference_message: "REFERENCE",
        system_message: "SYSTEM_MESSAGE"
    },

    airmessage: {
        type: "SHORT_MESSAGE",
        content: "",
        broadcastMessage: "true",
        fromAccount: "",
        toAccount: ""
    },

    init: function () {
    },

    shortMessage(content) {
        let message = {};
        message.type = this.airmessage.type;
        message.content = content;
        message.broadcastMessage = this.airmessage.broadcastMessage;
        message.fromAccount = this.airmessage.fromAccount;
        message.toAccount = this.airmessage.toAccount;
        return message;
    },

    referenceMessage(uuid) {
        let message = {};
        message.type = this.messageType.reference_message;
        message.content = uuid;
        message.broadcastMessage = this.airmessage.broadcastMessage;
        message.fromAccount = this.airmessage.fromAccount;
        message.toAccount = this.airmessage.toAccount;
        return message;
    },

    appendMyMessage: function (message) {
        let oMessageUl = document.getElementById("received");
        let emptyLiEle = document.createElement("li");
        let mySendMessageLiEle = document.createElement("li");
        mySendMessageLiEle.appendChild(this._wrapWithPreElement(message));
        mySendMessageLiEle.className = "me";
        oMessageUl.appendChild(mySendMessageLiEle);
        oMessageUl.appendChild(emptyLiEle);
    },

    appendAccountEventMessage(account_event, target_account) {
        let oMessageUl = document.getElementById("received");
        let emptyLiEle = document.createElement("li");
        let myReceivedMessageLiEle = document.createElement("li");
        myReceivedMessageLiEle.innerHTML = target_account + "<<<>>>" + account_event;
        myReceivedMessageLiEle.className = "apprise";
        oMessageUl.appendChild(myReceivedMessageLiEle);
        oMessageUl.appendChild(emptyLiEle);
        if (account_event === "online") {
            this._appendOnlineFriend(target_account);
        } else if (account_event === "offline") {
            this._removeOfflineFriend(target_account);
        }
    },

    appendReferenceMessage(uuid, fromAccount) {
        let oMessageUl = document.getElementById("received");
        let emptyLiEle = document.createElement("li");
        let myReceivedMessageLiEle = document.createElement("li");
        wp.ajax.get(wp.main.constants.longtextUrl, {uuid: uuid}, function (response) {
            myReceivedMessageLiEle.appendChild(wp.message._wrapWithPreElement(response));
            myReceivedMessageLiEle.className = "others";
            oMessageUl.appendChild(myReceivedMessageLiEle);
            oMessageUl.appendChild(emptyLiEle);
        });
    },

    appendReceivedMessage: function (content, fromAccount) {
        let oMessageUl = document.getElementById("received");
        let emptyLiEle = document.createElement("li");
        let myReceivedMessageLiEle = document.createElement("li");
        myReceivedMessageLiEle.appendChild(this._wrapWithPreElement(content));
        myReceivedMessageLiEle.className = "others";
        oMessageUl.appendChild(myReceivedMessageLiEle);
        oMessageUl.appendChild(emptyLiEle);
    },

    _appendOnlineFriend: function (username) {
        let rosterEle = document.getElementById("left-items");
        let aFriend = document.createElement("li");
        let friendIcon = document.createElement("img");
        friendIcon.setAttribute("src", wp.main.constants.iconUrl + "?username=" + username);
        friendIcon.setAttribute("alt", "usericon");
        aFriend.appendChild(friendIcon);
        let friendName = document.createElement("span");
        friendName.innerHTML = username;
        aFriend.appendChild(friendName);
        rosterEle.appendChild(aFriend);
    },

    _removeOfflineFriend: function (username) {
        let liEleList = document.querySelector("#left-items").children;
        for (let index = 0; index < liEleList.length; index++) {
            let theFriendEle = liEleList[index];
            let spanEle = theFriendEle.getElementsByTagName("span");
            if (spanEle[0].innerHTML === username) {
                theFriendEle.parentNode.removeChild(theFriendEle);
                break;
            }
        }
    },

    _wrapWithPreElement: function (content) {
        let preEle = document.createElement("pre");
        preEle.innerText = content;
        return preEle;
    },
};