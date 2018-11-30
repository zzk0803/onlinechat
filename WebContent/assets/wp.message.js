;wp.message = {
    currentMessageBox: undefined,

    defaultMessageBox: undefined,

    messageBoxMap: {},

    messageBoxPrototype: {
        name: "",
        messageLiElements: [],
        unreadCount: 0,
        clearReceiveChildren: function () {
            document.getElementById("received").innerHTML = "";
        },
        appendMessageLi: function (messageLiElement) {
            this.messageLiElements.push(messageLiElement);
        },
        applyMessageLi: function (messageLiElement) {
            document.getElementById("received").appendChild(messageLiElement);
        },

        updateAndShowUnreadBadge: function (username) {
            let accountLiElements = document.querySelector("#online-accounts").children;
            for (let index = 0; index < accountLiElements.length; index++) {
                let current = accountLiElements[index];
                let spanEle = current.getElementsByTagName("span")[0];
                if (spanEle.innerHTML === username) {
                    let unreadBadge = current.getElementsByTagName("p")[0];
                    if (unreadBadge) {
                        unreadBadge.innerHTML = this.unreadCount;
                        unreadBadge.className = "";
                        unreadBadge.classList.add("unread");
                    } else {
                        let unreadBadge = document.createElement("p");
                        unreadBadge.innerHTML = this.unreadCount;
                        unreadBadge.classList.add("unread");
                        current.append(unreadBadge);
                    }
                }
            }
        },

        hideUnreadBadge: function () {
            let accountLiElements = document.querySelector("#online-accounts").children;
            for (let index = 0; index < accountLiElements.length; index++) {
                let current = accountLiElements[index];
                let spanEle = current.getElementsByTagName("span")[0];
                if (spanEle.innerHTML === this.name) {
                    let unreadBadge = current.getElementsByTagName("p")[0];
                    if (unreadBadge) {
                        unreadBadge.className = "";
                        unreadBadge.classList.add("hide");
                        unreadBadge.classList.add("unread");
                    }
                }
            }
        },

        receiveMessageLi: function (messageLiElement) {
            this.appendMessageLi(messageLiElement);
            let sourceAccount = messageLiElement.getAttribute("source");

            let currentIsDefault = wp.message.getCurrentMessageBox().name === wp.message.getDefaultMessageBox().name;
            let itIsBroadcastMessage = window.bool_dict[messageLiElement.getAttribute("broadcast")];
            let itIsMyMessage = !messageLiElement.hasAttribute("source");
            let messageAuthorIsCurrentName = sourceAccount === wp.message.getCurrentMessageBox().name;
            if (currentIsDefault && itIsMyMessage) {
                this.applyMessageLi(messageLiElement);
            } else if (currentIsDefault && itIsBroadcastMessage) {
                this.applyMessageLi(messageLiElement);
            } else if (messageAuthorIsCurrentName || itIsMyMessage) {
                this.applyMessageLi(messageLiElement);
            } else {
                this.unreadCount += 1;
                this.updateAndShowUnreadBadge(sourceAccount);
            }
        },
        applyMessageBox: function () {
            console.log(this.name + "-message-box-apply");
            this.unreadCount = 0;
            this.hideUnreadBadge();
            this.clearReceiveChildren();
            this.messageLiElements.forEach(function (li) {
                document.getElementById("received").appendChild(li);
            });
        }
    },

    messageType: {
        system_message: "SYSTEM_MESSAGE",
        short_message: "SHORT_MESSAGE",
        reference_message: "REFERENCE",
        account_administer: "ACCOUNT_ADMINISTER"
    },

    airMessagePrototype: {
        type: "SHORT_MESSAGE",
        content: "",
        broadcastMessage: "true",
        fromAccount: "",
        toAccount: ""
    },
};

/**
 * 信息盒子
 * @param boxName
 * @returns {wp.message.messageBoxPrototype}
 */

wp.message.messageBox = function (boxName) {
    let messageDom = Object.create(wp.message.messageBoxPrototype);
    messageDom.name = boxName;
    messageDom.messageLiElements = new Array();
    console.log(boxName + "<->" + messageDom);
    return messageDom;
};

wp.message.putMessageBox = function (boxName, messageBox) {
    wp.message.messageBoxMap[boxName] = messageBox;
};

wp.message.removeMessageBox = function (boxName) {
    delete wp.message.messageBoxMap[boxName];
};

wp.message.getMessageBoxByName = function (boxName) {
    return wp.message.messageBoxMap[boxName];
};

wp.message.getDefaultMessageBox = function () {
    return wp.message.defaultMessageBox;
};

wp.message.setDefaultMessageBox = function (messageBox) {
    wp.message.defaultMessageBox = messageBox;
};

wp.message.getCurrentMessageBox = function () {
    return wp.message.currentMessageBox;
};

wp.message.setCurrentMessageBox = function (messageBox) {
    wp.message.currentMessageBox = messageBox;
    wp.message.currentMessageBox.applyMessageBox();
};

wp.message.prepareMessageBox = function (boxName) {
    let messageBox = wp.message.messageBox(boxName);
    wp.message.putMessageBox(boxName, messageBox);
};

wp.message.removeMessageBox = function (boxName) {
    if (this.getCurrentMessageBox().name === boxName) {
        this.setCurrentMessageBox(this.getDefaultMessageBox());
        this.putInSpecificMessageBox("public", true, wp.message.produceAppriseMessageLiElement("Target user was offline,public chat has been apply"));
    }
    wp.message.removeMessageBox(boxName);
};

wp.message.putInSpecificMessageBox = function (boxName, useDefaultBox, messageLiElement) {
    if (window.bool_dict[useDefaultBox]) {
        let messageBox = wp.message.getDefaultMessageBox();
        messageBox.receiveMessageLi(messageLiElement);
        messageBox.receiveMessageLi(this.produceEmptyLi());
    } else {
        let messageBox = wp.message.getMessageBoxByName(boxName);
        if (messageBox) {
            messageBox.receiveMessageLi(messageLiElement);
            messageBox.receiveMessageLi(this.produceEmptyLi());
        }
    }
};

/**
 * 构造方法
 * @param content
 * @returns {wp.message.airMessagePrototype}
 */

wp.message.shortMessage = function (content) {
    let message = Object.create(wp.message.airMessagePrototype);
    message.content = content;
    message.broadcastMessage = wp.message.getDefaultMessageBox().name === wp.message.getCurrentMessageBox().name;
    message.fromAccount = window.my_username;
    message.toAccount = wp.message.getCurrentMessageBox().name !== wp.message.getDefaultMessageBox().name
        ? wp.message.getCurrentMessageBox().name
        : "";
    return message;
};

wp.message.referenceMessage = function (uuid) {
    let message = Object.create(wp.message.airMessagePrototype);
    message.type = wp.message.messageType.reference_message;
    message.content = uuid;
    message.broadcastMessage = wp.message.getDefaultMessageBox().name === wp.message.getCurrentMessageBox().name;
    message.fromAccount = window.my_username;
    message.toAccount = wp.message.getCurrentMessageBox().name !== wp.message.getDefaultMessageBox().name
        ? wp.message.getCurrentMessageBox().name
        : "";
    return message;
};

/**
 * 处理收获信息
 * @param message
 */

wp.message.appendMyMessage = function (message) {
    let mySendMessageLiEle = document.createElement("li");
    mySendMessageLiEle.appendChild(wp.message.wrapPre(message));
    mySendMessageLiEle.className = "me";
    wp.message.getCurrentMessageBox().receiveMessageLi(mySendMessageLiEle);
    wp.message.getCurrentMessageBox().receiveMessageLi(this.produceEmptyLi());
};

wp.message.dealSystemMessage = function (message, fromAccount) {
    let appriseMessage = fromAccount + " is " + message;
    let appriseMessageLiElement = wp.message.produceAppriseMessageLiElement(appriseMessage);
    wp.message.putInSpecificMessageBox("public", true, appriseMessageLiElement);

    //为在线用户新建信息盒子，以备私聊用
    if (message === "online") {
        wp.message.prepareMessageBox(fromAccount);
        wp.message.addOnlineAccount(fromAccount);
    } else if (message === "offline") {
        wp.message.delOfflineAccount(fromAccount);
        wp.message.removeMessageBox(fromAccount);
    }
};

wp.message.appendReferenceMessage = function (uuid, fromAccount, isBroadcast) {
    let receivedReferenceMessageLiElement = document.createElement("li");
    receivedReferenceMessageLiElement.setAttribute("source", fromAccount);
    receivedReferenceMessageLiElement.setAttribute("broadcast", isBroadcast);
    wp.ajax.get(wp.main.constants.longtextUrl, {uuid: uuid}, function (response) {
        receivedReferenceMessageLiElement.appendChild(wp.message.wrapPre(response));
        receivedReferenceMessageLiElement.className = "others";
        wp.message.putInSpecificMessageBox(fromAccount, isBroadcast, receivedReferenceMessageLiElement);
    });
};

wp.message.appendReceivedMessage = function (content, fromAccount, isBroadcast) {
    let receivedShortMessageLiElement = document.createElement("li");
    receivedShortMessageLiElement.setAttribute("source", fromAccount);
    receivedShortMessageLiElement.setAttribute("broadcast", isBroadcast);
    receivedShortMessageLiElement.appendChild(wp.message.wrapPre(content));
    receivedShortMessageLiElement.className = "others";
    wp.message.putInSpecificMessageBox(fromAccount, isBroadcast, receivedShortMessageLiElement);
};

wp.message.addOnlineAccount = function (username) {
    let accountLiParents = document.getElementById("online-accounts");
    let accountLi = document.createElement("li");
    let accountLiIcon = document.createElement("img");
    accountLiIcon.setAttribute("src", wp.main.constants.iconUrl + "?username=" + username);
    accountLiIcon.setAttribute("alt", "usericon");
    accountLi.appendChild(accountLiIcon);
    let accountName = document.createElement("span");
    accountName.innerHTML = username;
    accountLi.appendChild(accountName);

    //绑定单击用户的事件后，单击后进入私聊，再次单击退出到群聊
    accountLi.addEventListener("click", function (event) {
        if (accountLi.className === "select") {
            accountLi.className = "";
            wp.message.setCurrentMessageBox(wp.message.getDefaultMessageBox());
        } else {
            accountLi.className = "select";
            wp.message.setCurrentMessageBox(wp.message.getMessageBoxByName(username));
        }
        event.stopPropagation();
    }, true);
    accountLiParents.appendChild(accountLi);

};

wp.message.delOfflineAccount = function (username) {
    let accountLiElements = document.querySelector("#online-accounts").children;
    for (let index = 0; index < accountLiElements.length; index++) {
        let current = accountLiElements[index];
        let spanEle = current.getElementsByTagName("span");
        if (spanEle[0].innerHTML === username) {
            current.parentNode.removeChild(current);
            break;
        }
    }
};

/**
 * 工具方法
 * @param content
 * @returns {HTMLElement}
 */

wp.message.wrapPre = function (content) {
    let preEle = document.createElement("pre");
    preEle.innerText = content;
    return preEle;
};

wp.message.produceAppriseMessageLiElement = function (message) {
    let systemAppriseLiElement = document.createElement("li");
    systemAppriseLiElement.innerHTML = message;
    systemAppriseLiElement.className = "apprise";
    return systemAppriseLiElement;
};

wp.message.produceEmptyLi = function () {
    return document.createElement("li");
};

/**
 * 对象初始化
 */
wp.message.init = function () {
    wp.message.setDefaultMessageBox(wp.message.messageBox("public"));
    wp.message.putMessageBox(wp.message.getDefaultMessageBox());
    wp.message.setCurrentMessageBox(wp.message.getDefaultMessageBox());
};