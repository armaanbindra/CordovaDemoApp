# Cordova VoiceIt Plugin

An Apache Cordova plugin that lets you easily integrate VoiceIt's Voice Authentication API into your Cordova Based iOS and Android apps.

For more information on VoiceIt and its features, see [the website](http://voiceit.tech) and [getting started docs](https://siv.voiceprintportal.com/getstarted.jsp)

* [Supported Platforms](#supported-platforms)
* [Getting Started](#getting-started)
* [Installation](#installation)
* [API Calls](#api-calls)
  * [Create User](#create-user)
  * [Set User](#set-user)
  * [Get User](#get-user)
  * [Delete User](#delete-user)
  * [Create Enrollment](#create-enrollment)
  * [Get Enrollments](#get-enrollments)
  * [Delete Enrollment](#delete-enrollment)
  * [Authentication](#authentication)
  * [Playback](#playback)

## Supported Platforms

* Android
* iOS

## Getting Started

To use the VoiceIt Cordova Plugin in your Cordova Project, if you haven't already, please Sign Up for a free **Developer Id** at [http://voiceit.tech](https://siv.voiceprintportal.com/getDeveloperID.jsp). Then follow the installation instructions below.

## Installation

First please cd into your Cordova App's root directory via the terminal/command line and then run the following command:

```bash
$ cordova plugin add cordova-plugin-voiceit
```

## API Calls

Here are code snippets that show you how you can call the Various VoiceIt API Calls inside of your Cordova Project JavaScript Files.

### Create User

To create a new user call the createUser function like this with the following parameters: developerID, email, password, first name, last name

```javascript
VoiceIt.createUser({
  developerID: "DEVELOPER_ID_HERE",
  email: "cordova@voiceit-tech.com",
  password: "password",
  firstName: "John",
  lastName: "Doe"
}, function(response) {
  alert('Result: ' + response);
}, function(error) {
  alert('Error: ' + error);
});
```
### Set User

To update an existing user call the setUser function like this with the following parameters: developerID, email, password, first name, last name

```javascript
VoiceIt.setUser({
  developerID: "DEVELOPER_ID_HERE",
  email: "cordova@voiceit-tech.com",
  password: "password",
  firstName: "John",
  lastName: "Doe"
}, function(response) {
  alert('Result: ' + response);
}, function(error) {
  alert('Error: ' + error);
});
```
### Get User

To retrieve an existing user call the getUser function like this with the following parameters: developerID, email, password

```javascript
VoiceIt.getUser({
  developerID: "DEVELOPER_ID_HERE",
  email: "cordova@voiceit-tech.com",
  password: "password"
}, function(response) {
  alert('Result: ' + response);
}, function(error) {
  alert('Error: ' + error);
});
```
### Delete User

To delete an existing user call the deleteUser function like this with the following parameters: developerID, email, password

```javascript
VoiceIt.deleteUser({
  developerID: "DEVELOPER_ID_HERE",
  email: "cordova@voiceit-tech.com",
  password: "password"
}, function(response) {
  alert('Result: ' + response);
}, function(error) {
  alert('Error: ' + error);
});
```
### Create Enrollment

To create a new enrollment template for the specified user profile use the createEnrollment function like this with the following parameters: developerID, email, password.

Please Note: Unlike other wrappers, this createEnrollment function actually has recording inbuilt(supporting both Android and iOS platforms), it records the user saying their VoicePrint phrase for 5 seconds and then makes the Create Enrollment API call to send that audio file as an enrollment.

The recorder starts recording as soon as the createEnrollment function is called, it is also recommended that you either provide a visual or auditory indication to the user before the recording is about to start for example "playing a beep".

```javascript
VoiceIt.createEnrollment({
  developerID: "DEVELOPER_ID_HERE",
  email: "cordova@voiceit-tech.com",
  password: "password"
}, function(response) {
  alert('Result: ' + response);
}, function(error) {
  alert('Error: ' + error);
});
```

### Get Enrollments

To get a list of the existing enrollments simply call the getEnrollments method for the specific user like this with the following parameters: developerID, email, password

```javascript
VoiceIt.getEnrollments({
  developerID: "DEVELOPER_ID_HERE",
  email: "cordova@voiceit-tech.com",
  password: "password"
}, function(response) {
  alert('Result: ' + response);
}, function(error) {
  alert('Error: ' + error);
});
```
### Delete Enrollment

To delete an enrollment simply call the deleteEnrollment method for the specific user like this with the following parameters: developerID, email, password, enrollmentId

```javascript
VoiceIt.deleteEnrollment({
  developerID: "DEVELOPER_ID_HERE",
  email: "cordova@voiceit-tech.com",
  password: "password",
  enrollmentId:"2461"
}, function(response) {
  alert('Result: ' + response);
}, function(error) {
  alert('Error: ' + error);
});
```

### Authentication

To authenticate the user profile use the authentication method like this with the following parameters: email, password, accuracy of authentication (between 0-5, 0 being the most strict and 5 the most lax), number of accuracy passes (between 1-10), accuracy pass increment (between 1-5) and confidence level (between 85-100).

Please Note: Unlike other wrappers, this authentication function actually has recording inbuilt(supporting both Android and iOS platforms), it records the user saying their VoicePrint phrase for 5 seconds and then makes the Authentication API call to send that audio file in for authentication.

The recorder starts recording as soon as the authentication function is called, it is also recommended that you either provide a visual or auditory indication to the user before the recording is about to start for example "playing a beep".

```javascript
VoiceIt.authentication({
  developerID: "DEVELOPER_ID_HERE",
  email: "cordova@voiceit-tech.com",
  password: "password",
  accuracy: "0",
  accuracyPasses: "5",
  accuracyPassIncrement: "2",
  confidence: "85"
}, function(response) {
  alert('Result: ' + response);
}, function(error) {
  alert('Error: ' + error);
});
```
### Playback

This is a special method for this plugin that simply plays back the most recent recording if called right after either the [createEnrollment](#create-enrollment) or [authentication](#authentication) method.

```javascript
VoiceIt.playback( function(response) {
  alert('Result: ' + response);
}, function(error) {
  alert('Error: ' + error);
});
```
