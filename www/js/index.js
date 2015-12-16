function customAlert(theMessage){
   //swal({ title: "Alert",   text: theMessage,   timer: 2000,   showConfirmButton: false });
  //alert(theMessage);
  $(".status").html(theMessage);
}

function disableAllButtons(){
  $(".btn").prop('disabled', true);
}

function enableAllButtons(){
  $(".btn").prop('disabled', false);
}

function addButtonMethod(buttonClass, startMessage, callback) {
  document.querySelector(buttonClass).addEventListener('click', function() {
    customAlert(startMessage);
    disableAllButtons();
    callback();
  });
}

var DEVELOPER_ID = "YOUR_DEVELOPER_ID_HERE";

var app = {
  // Application Constructor
  initialize: function() {
    this.bindEvents();
  },
  // Bind Event Listeners
  //
  // Bind any events that are required on startup. Common events are:
  // 'load', 'deviceready', 'offline', and 'online'.
  bindEvents: function() {
    document.addEventListener('deviceready', this.onDeviceReady, false);

    //USER API CALLS
    addButtonMethod(".createUser", "Creating User", function() {
      VoiceIt.createUser({
        developerID: DEVELOPER_ID,
        email: "cordova@voiceit-tech.com",
        password: "password",
        firstName: "John",
        lastName: "Doe"
      }, function(response) {
        customAlert("User Created");
        customAlert('Result: ' + response);
        enableAllButtons();
      }, function(error) {
        customAlert('Error: ' + error);
        enableAllButtons();
      });
    });

    addButtonMethod(".setUser", "Updating User", function() {
      VoiceIt.setUser({
        developerID: DEVELOPER_ID,
        email: "cordova@voiceit-tech.com",
        password: "password",
        firstName: "John",
        lastName: "Adams"
      }, function(response) {
        customAlert("User Updated");
        customAlert('Result: ' + response);
        enableAllButtons();
      }, function(error) {
        customAlert('Error: ' + error);
        enableAllButtons();
      });
    });


    addButtonMethod(".getUser", "Getting User", function() {
      VoiceIt.getUser({
        developerID: DEVELOPER_ID,
        email: "cordova@voiceit-tech.com",
        password: "password"
      }, function(response) {
        customAlert("Got User");
        customAlert('Result: ' + response);
        enableAllButtons();
      }, function(error) {
        // failed
        customAlert('Result: ' + error);
        enableAllButtons();
      });
    });

    addButtonMethod(".deleteUser", "Deleting User", function() {
      VoiceIt.deleteUser({
        developerID: DEVELOPER_ID,
        email: "cordova@voiceit-tech.com",
        password: "password"
      }, function(response) {
        customAlert('Result: ' + response);
        enableAllButtons();
      }, function(error) {
        customAlert('Error: ' + error);
        enableAllButtons();
      })
    });

    //ENROLLMENT API CALLS
    addButtonMethod(".createEnrollment", "Started Recording Enrollment", function() {
      VoiceIt.createEnrollment({
        developerID: DEVELOPER_ID,
        email: "cordova@voiceit-tech.com",
        password: "password"
      }, function(response) {
        customAlert("Done Recording");
        VoiceIt.playback(function(result) {
          //customAlert('Result: ' + result);
          enableAllButtons();
        }, function(error) {
          customAlert('Result: ' + error);
          enableAllButtons();
        });
        customAlert('Result: ' + response);
      }, function(error) {
        enableAllButtons();
        customAlert('Result: ' + error);
      });
    });

    addButtonMethod(".getEnrollments", "Getting Enrollments", function() {
      VoiceIt.getEnrollments({
        developerID: DEVELOPER_ID,
        email: "cordova@voiceit-tech.com",
        password: "password"
      }, function(response) {
        enableAllButtons();
        customAlert("Got Enrollments");
        customAlert('Result: ' + response);
      }, function(error) {
        enableAllButtons();
        customAlert('Result: ' + error);
      });
    });

    addButtonMethod(".deleteEnrollment", "Deleting Enrollment", function() {
      VoiceIt.deleteEnrollment({
        developerID: DEVELOPER_ID,
        email: "cordova@voiceit-tech.com",
        password: "password",
        enrollmentId: $("#enrollmentID").val()
      }, function(response) {
        enableAllButtons();
        customAlert("Delete Enrollment");
        customAlert('Result: ' + response);
      }, function(error) {
        enableAllButtons();
        customAlert('Error: ' + error);
      });
    });

    //AUTHENTICATION API CALLS

    addButtonMethod(".authentication", "Started Recording Authentication", function() {
      VoiceIt.authentication({
        developerID: DEVELOPER_ID,
        email: "cordova@voiceit-tech.com",
        password: "password",
        accuracy: "0",
        accuracyPasses: "5",
        accuracyPassIncrement: "2",
        confidence: "85"
      }, function(response) {
        VoiceIt.playback(function(result) {
          enableAllButtons();
          //customAlert('Result: ' + result);
        }, function(error) {
          enableAllButtons();
          customAlert('Result: ' + error);
        });
        customAlert("Done Recording");
        customAlert('Result: ' + response);
      }, function(error) {
        enableAllButtons();
        customAlert('Result: ' + error);
      });
    });

    //PLAYBACK METHOD
    addButtonMethod(".playLast", "Started Playback", function() {
      VoiceIt.playback(function(result) {
        enableAllButtons();
        customAlert("Finished Playback");
        customAlert('Result: ' + result);
      }, function(error) {
        enableAllButtons();
        customAlert('Result: ' + error);
      });
    });

  },
  // deviceready Event Handler
  //
  // The scope of 'this' is the event. In order to call the 'receivedEvent'
  // function, we must explicitly call 'app.receivedEvent(...);'
  onDeviceReady: function() {
    app.receivedEvent('deviceready');
  },
  // Update DOM on a Received Event
  receivedEvent: function(id) {
    var parentElement = document.getElementById(id);
    var listeningElement = parentElement.querySelector('.listening');
    var receivedElement = parentElement.querySelector('.received');

    listeningElement.setAttribute('style', 'display:none;');
    receivedElement.setAttribute('style', 'display:block;');
    console.log('Received Event: ' + id);
  }
};

app.initialize();
