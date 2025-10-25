# LockScreen by naiveHA
Uncomplicated simple: it just does what it says it does. It locks the screen with a touch of a button. Just one touch!
Philosophy: You have the world at your finger tips, but you need to hold the phone with two hands to turn off the screen... "LockScreen by NaiveHA" gives back the power to your thumb. For years Apple refused to make big screen phone because Steve Jobs believed the phone should be browsed with one hand only. So that you can hold the coffee with the other. "LockScreen by naiveHA" puts Steve Jobs dream in your hand. From now on, you can hold on to your coffee with one hand and browse the Internet with the other. And when you are done, you do not need to put the coffee down to turn off the screen: just tap "LockScreen by naiveHA" and get on with your day.

# How does it work?
"LockScreen by naiveHA" does one thing and one thing only: the app locks the screen when you, the user, so desire by tapping the app's icon.
Watch this video for a demonstration:

[![demonstration of concept](https://img.youtube.com/vi/IMocmDyAMuE/0.jpg)](https://www.youtube.com/watch?v=IMocmDyAMuE)

To perform its only function, the app makes use of the AccessibilityService API. The use of AccessibilityService API is restricted to the app only: when you, the user launch the app the window change is noticed by the AccessibilityService which then proceeds to lock the screen.
For the app to use the AccessibilityService API you the user need to explicitly grant permission by pressing the below purple button. Without this permission, the app does not perform any action. Watch this intro video demonstrating just that:
[![demonstration of concept](https://img.youtube.com/vi/Pm_u0AZ_oa8/0.jpg)](https://www.youtube.com/watch?v=Pm_u0AZ_oa8)

In Android 13, you will need to take an extra step: enable Accessiblity API by navigating to:
```
Settings / Apps / LockScreen by naiveHA
```
and taping:
```
Allow restricted setting
```
![Android 13](https://raw.githubusercontent.com/naive-HA/LockScreen/master/IMG_20230416_105446.jpg)

Once granted, the permission can be revoked at any time by navigating to:
```
Settings / Accessibility / Downloaded apps
```
At any time the app does NOT collect any data. The app does NOT access phone contacts. The app does NOT access the location. The app does NOT display any ads. "LockScreen by naiveHA" does one thing and one thing only: locks the screen when you tap its icon.

If you like it, remember to donate some satoshi/BTC to:

1HwgShr1TniuBxNQwy2xAhpQaNuZhtw6sh

