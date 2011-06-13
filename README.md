# GameMenu
This is an Android application aimed at improving the experience of an american bar customer.

##The project
This software is developed as an UNIPD project for the Embedded Systems course (a.y. 2010/2011). Main page of the course is at http://es1011.wikispaces.com/

##Usage
This repository has been uploaded using Eclipse with the Egit plugin.
To use it, first set up a development environment as per the official Android documentation (e.g. install Eclipse, install and set up the Android SDK, install Egit).
Then get a copy of this repository. If Eclipse says the project properties are not correct, right click on the project, select "Android tools" from the menu, then select "Fix project properties".
Since the Android SDK auto-generates a resources file (R.java), but this is NOT tracked with git, you should force its creation. The simplest way is to edit an XML from /res/layout (e.g. add and delete a space) and save it. Eclipse will update (and create) R.java.
To use the app you should have a real device, since the game uses the accelerometer to move the pad.

##The application
The app is organized in three tabs; the first one is relative to the drink list, drink details and current order. The second tab enables the user to take a photo of its table number and send it to the waiter, to call him. The third tab contains a little game, to entertain the user while he waits for his drinks.

###The main TabActivity
The TabActivity that hosts all other activities is in the GameMenu class. It just instantiates the tabs and attaches to them an intent that starts the related activity.

###The drink list tab
The main purpose of this activity is to show a simple usage of a database to store and retrieve informations about a drink list and a current drink order.
This activity is structured like a Content Management System: there are three XML layouts that are loaded on the fly as pages are swapped.
There is a drink list page, a current order page, and a drink details page.
If the activity has been shown previously, the last page will be shown again; otherwise the drink list will be shown.
This activity permits screen orientation changes, and displays three alternative pages in landscape mode (stored in res/layout-land). This functionality is handled automatically by the Android OS.

####GUI
The user can switch from the drink list to the current order and back again, and when he taps on an item in the drink list or in the current order a "details" page is shown.
From the details page the user can increment or decrement the ordered quantity of that particular drink; if the drink was not in the order it will be inserted.
To go back from the details page a "last page" pointer is kept.

The drink_list layout contains a linear layout; a first child is horizontally split in two, and contains the current order total and a button to switch to the current order page. This upper part is fixed, and does not scroll with the list.
Under the button there is a scrollable linear layout that will contain the full list of drinks available.
In the landscape version the drink list is on the left, and is scrollable, while the order total and the button to change page are on the right, and are fixed.

The drink_order layout is similar to the drink_list one, except that a custom view (LinearListView) is used to avoid automatic scrolling of the order list. Under the ordered drinks there are the order summary and buttons to clear/send the order or go back to the full list.
The portrait layout is fully scrollable, and the user has to reach the end of the list to read the total items and total cost.
In the landscape version only the drink list scrolls, while the order summary and the buttons stay still on the right.

The drink_details layout contains the bigger picture of the drink, its cost, its name, and a TextView with its full description (actually, a lorem ipsum). At the end of the page are the buttons to increment/decrement the ordered quantity, and a "back" button that goes to the last shown page.
The landscape version splits the information on the drink and the buttons. Buttons are placed on the right, while drink information is kept on the left. Just to try a different layout, the full page scrolls.
  
A helper layout also belongs to this section; drink_listitem is a layout used for each item in the drink lists. It contains an ImageView on the far left, a TextView on the center left, and a TextView aligned to the right.

####Objects and database
Apart from the Activity, only a single View has been made to fit the project. LinearListView implements a simple LinearLayout with a simple DataSetObserver and a ParameterizedClickListener. Its sole purpose is to display a clickable list of drinks that does not automatically scroll.
On the onChanged event (referred to the DataSet being observed), the object populates itself with the views from the data adapter.

Drink_ImageListAdapter provides such a data adapter, and inflates a drink_listitem layout for every item in the dataset required. The inflated layout is also populated with data from the item.
Drink images are stored in the database as names, and the Drink_ImageListAdapter loads them from the PNG in the resources.

Two more objects, namely Drink_Order and Drink_OriginalList hide the abstraction of the current order model and the original list model. Such objects access the database in similar ways, instantiating an SQLiteOpenHelper and executing simple queries.
The Drink_OriginalList model handles database creation and population with dummy data, and exposes methods to retrieve the full list of drinks and details about a particular drink.   
The Drink_Order model does not do anything particular on database creation (creates the current order table), but exposes many methods to insert and update drink quantities into the table. Many drink details are duplicated for simplicity, and the whole data access model is not optimized for performance.

Database is kept between shutdowns of the activity, so the current order is persisted this way. The current page is not an important information to keep, so it is persisted on the Bundle object (this is to make for a nice and simple screen-rotation handling).
Both when the order is "cleared" or "sent" the table of the database containing the order is actually cleared. Anyway, different "toast" messages are displayed.

###The waiter call tab
The sole purpose of this tab is to demonstrate access to the hardware of the device; the focus is on the camera and location services.
The supposed function is to send a photo and a location to the waiter, for him to reach the table.
Upon creation a handle to the location manager is obtained. When the activity resumes, the location manager is asked to signal to the appropriate handler any location updates, and the camera preview is started.
Camera preview is rotated to compensate for screen rotation: although the camera top left pixel is always still with respect to the phone, the top left pixel of the screen is changed on rotation. The correctOrientation() subroutine handles this situation.
Camera preview and location updates are halted when the activity loses focus, to save battery.

If location services are not enabled on the device, the system configuration screen is invoked (see first lines of EnableGPS() in ActWaiterCall); if the user dismisses that he will not be asked anymore.

When the user taps on the part of the screen devoted to the activity, takePicture() is invoked on the camera. When the specified callback object is notified that a photo has been shot, it just saves the jpeg to a file and the current GPS location to another file. It then displays a "toast" message, simulating a successful notification to the waiter. 

###The game entertainer tab
This activity has the main purpose of demonstrating access to the accelerometer data. Other functionality demonstrated here is locking the screen backlight on, threading, canvas drawing and a menu connected to the MENU button on the device.
The implemented game is a classic brick breaker, where the brick number is incremented as the level increases and the pad is moved by tilting the device.
The game, once started, instantiates a particular subclass of SurfaceView (called Game_View), a Game_Status object, and locks the screen backlight on.
If the activity loses foreground the View is immediately notified (so some simple teardown is performed, to save battery life) and the screen backlight lock is released.

The game screen should not be rotated; the code in the startup/teardown sections of the activity handles this requirement too.

All the game logic is hard-coded into the Game_Thread object, inside the Game_View class.
This thread, since it is defined as an internal class of a SurfaceView, can use the same surface holder object. This is important because only the thread that owns a SurfaceView can draw on it, and implementing some sort of other message-passing among threads would have caused too much delay.
The main game loop begins with the thread creation, and performs some basic status update (ball position, bounding box checks) and draws the PNG files from the resources on the surface.

The Game_Status object exposes methods for resetting a game, increment the level or lose a life.

The Activity (ActGameEntertainer) registers itself as a listener for accelerometer events, sets up the backlight lock and creates the surface object.
On an accelerometer event, the pad speed in the Game_Status object is directly adjusted.
The Activity saves and restores game data from a Bundle object, to handle simple restarts (but no persistence on file or database is provided).
ActGameEntertainer provides also a simple contextual MENU, with only one entry, that restarts the game from scratch (calling an appropriate method on Game_Status).

The Game_View object creates a Game_Thread and starts it when the surface is actually created (thread start is handled in the surfaceCreated() event).
When the View is destroyed the thread is gracefully stopped (by using an internal status variable and a join() call).

The thread has two status variables: bRun and sStatus. bRun should be set to true as long as the game is needed, as setting it to false will cause the thread to exit its run() cycle.
sStatus controls the variables update: when it is set to false the draw loop is executed but the update branch is not taken (i.e. the ball and pad stand still).
The updateGame() routine checks for collisions, with the classic bounding box algorythm; the doDraw() routine draws all the PNGs and rectangles/texts on the canvas attached to the surface.

As a little feature, the horizontal speed of the ball is incremented/decremented with the speed of the pad when the collision occurs, to give some unpredictability to the game.

A lot of synchronization is involved in the thread operations, mainly to avoid accessing the surface holder while it's being used to have a canvas.

##Known bugs
There is a known bug on returning from the game tab to the camera tab; the camera preview is not displayed anymore although the camera is correctly started and can take photos. 