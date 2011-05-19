# GameMenu
This is an Android application aimed at beautifying the experience of an american bar customer.

##The project
This software is developed as an UNIPD project for the Embedded Systems course. Main page of the course is at http://es1011.wikispaces.com/

##Usage
This repository has been uploaded using Eclipse with the Egit plugin.
To use it, first set up a development environment as per the official Android documentation (e.g. install Eclipse, install and set up the Android SDK, install Egit).
Then get a copy of this repository. If Eclipse says the project properties are not correct, right click on the project, select "Android tools" from the menu, then select "Fix project properties".
Since the Android SDK auto-generates a resources file (R.java), but this is NOT tracked with git, you should force its creation. The simplest way is to edit an XML from /res/layout (e.g. add and delete a space) and save it. Eclipse will update (and create) R.java.
To use the app you should have a real device, since the game uses the accelerometer to move the pad.

##The application
The app is organized as three tabs; the first one is relative to the drink list, drink details and current order. The second tab enables the user to take a photo of its table number and send it to the waiter, to call him. The third tab contains a little game, to entertain the user while he waits for his drinks.

###The drink list tab

###The waiter call tab

###The game entertainer tab