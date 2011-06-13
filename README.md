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

Two more objects, namely Drink_Order and Drink_OriginalList hide the abstraction of the current order model and the original list model. Such objects access the database in similar ways, instantiating an SQLiteOpenHelper and executing simple queries.
The Drink_OriginalList model handles database creation and population with dummy data, and exposes methods to retrieve the full list of drinks and details about a particular drink.   
The Drink_Order model does not do anything particular on database creation (creates the current order table), but exposes many methods to insert and update drink quantities into the table. Many drink details are duplicated for simplicity, and the whole data access model is not optimized for performance.

Database is kept between shutdowns of the activity, so the current order is persisted this way. The current page is not an important information to keep, so it is persisted on the Bundle object (this is to make for a nice and simple screen-rotation handling).

###The waiter call tab


###The game entertainer tab