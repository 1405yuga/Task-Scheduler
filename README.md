# Task-Scheduler
Task Scheduler is the android application where one can add tasks and date which will be displayed on the screen. It uses firebase Google authentication of Firebase and Firestore database to store task details. It automatically deletes the 7 days old tasks.

## Guide

### SignIn :

User can SignIn this application with Google Account. Google authentication is done with the help of Google Firebase.

<!-- add ss -->

### Main Display screen :

This is the main screen where tasks will be displayed when added.

<!-- add ss of blank screen -->

### Add task :

User can click on ` + ` floating button on the main screen to add a new task. When clicked on ` + ` button a dialog appears where user is required to enter details of a task.

<!-- add ss of add button and new task dialog -->

In ` NEW TASK ` dialog, User is required to enter Task Title of length 1 to 50 and Task Description upto length 1 to 5000. 

<!-- add ss of error of length both title n desc -->

Next, user is required to add date and time else app will add default date and time to the task. Date picker and time picker are provided to do same.
Once all details are filled, user can click on ` Add ` button and add the task.

<!-- add ss of date and time picker -->

### Switch layout :

User can click on the top right icon to switch the layout according to it's preference. User has two choices i.e Linear display or Staggered display. This preferences will be saved and retrived again on next use of this application.

> Staggered Layout
<!-- add ss of Staggered screen -->
> Linear Layout
<!-- add ss of Linear screen -->

### Navigation View :

User can click on three-dashed icon for navigation view for more profile orieneted options. When user clicks the icon in left top app bar navigation drawer is opened with various options. Here, user's profile is displayed with different options.

<!-- ss of navigation menu icon -->

### Account :

Account consists of ` SignOut ` menu through which user can log out from the current account. Before SignOut process, confirmation dialog appears. If clicked, 
` Yes ` user will be logged out and navigated to SignIn screen.

<!-- ss of signout alert -->

### Settings :

` Settings ` menu consists of two options to ` Clear all the task ` and ` Delete account `. ` Clear all task ` menu will remove all the tasks added and display main screen blank. ` Delete account ` menu will remove all the tasks added and delete the current signed-in account.

<!-- ss of settings dialog -->

### Documentations :

Documentations consists of ` About ` menu and ` Documentations ` menu .

` About ` menu navigates to the developer's profile.

` Documentations ` menu navigates to the documentation page which describes how to use this app.

### Exit :

` Exit ` menu closes the app.
