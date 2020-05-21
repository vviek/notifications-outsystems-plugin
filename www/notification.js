
  function Notification() {
  }
  
  Notification.prototype.register = function (successCallback, errorCallback) {
  
   cordova.exec(successCallback, errorCallback, "NotificationPlugin", "register", []);
  };
  
  Notification.install = function () {
    if (!window.plugins) {
      window.plugins = {};
    }
  
    window.plugins.notification = new Notification();
    return window.plugins.notification;
  };
  
  cordova.addConstructor(Notification.install);

  