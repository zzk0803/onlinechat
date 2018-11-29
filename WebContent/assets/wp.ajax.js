;wp.ajax = {
    xhr: new XMLHttpRequest(),
};

wp.ajax.init = function () {

};

wp.ajax.get = function (url, parameter, callbackFunction) {
    let theXhr = this.xhr;
    theXhr.onreadystatechange = function () {
        try {
            if (theXhr.readyState === 4) {
                if (theXhr.status >= 200 && theXhr.status < 300 || theXhr.status === 304) {
                    callbackFunction(theXhr.responseText);
                }
            }
        } catch (exception) {
            console.log(exception)
        }
    };

    theXhr.open("get", url + "?" + wp.ajax.encoderFormData(parameter), true);
    theXhr.send(null)
};

wp.ajax.post = function (url, parameter, callbackFunction) {
    let theXhr = this.xhr;
    theXhr.onreadystatechange = function () {
        try {
            if (theXhr.readyState === 4) {
                if (theXhr.status >= 200 && theXhr.status < 300 || theXhr.status === 304) {
                    callbackFunction(theXhr.responseText);
                }
            }
        } catch (exception) {
            console.log(exception)
        }
    };

    theXhr.open("post", url, true);
    theXhr.send(parameter)
};

wp.ajax.encoderFormData = function (data) {
    if (!data) {
        return "";
    }
    var pairs = [];
    for (let key in data) {
        if (!data.hasOwnProperty(key)) {
            continue;
        }
        if (typeof data[key] === "function") {
            continue;
        }
        var value = data[key].toString();
        key = encodeURIComponent(key.replace("%20", "+"));
        value = encodeURIComponent(value.replace("%20", "+"));
        pairs.push(key + "=" + value);
    }
    return pairs.join("&");
};
