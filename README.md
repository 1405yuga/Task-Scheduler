# Task-Scheduler
Task Scheduler is the android application where one can add tasks and date which will be displayed on the screen. It uses firebase Google authentication of Firebase and Firestore database to store task details. It automatically deletes the 7 days old tasks.

<img src="https://github.com/1405yuga/Task-Scheduler/assets/82303711/d09dc167-aff9-4863-a250-2c072896fab0"/>

## Guide

### SignIn :

User can SignIn this application with Google Account. Google authentication is done with the help of Google Firebase.

<img src="https://github.com/1405yuga/Task-Scheduler/assets/82303711/fad2efca-8ee0-4068-b578-2d7f9706d074" width="273">

<img src="https://github.com/1405yuga/Task-Scheduler/assets/82303711/20db9965-e730-45a1-b3af-0091cfb17ad5" width="273">


### Main Display screen :

This is the main screen where tasks will be displayed when added. It consits of top app bar and a floating button.

<img src="https://github.com/1405yuga/Task-Scheduler/assets/82303711/863f125e-abe6-4e15-9927-5d8bac84e057" width="273">

### Add task :

User can click on ` + ` floating button on the main screen to add a new task. When clicked on ` + ` button a dialog appears where user is required to enter details of a task.

1. In ` NEW TASK ` dialog, User is required to enter Task Title of length 1 to 50 and Task Description upto length 1 to 5000. 
2. Next, user is required to add date and time else app will add default date and time to the task. Date picker and time picker are provided to do same.
3. Once all details are filled, user can click on ` Add ` button and add the task.
 
<img src="https://github.com/1405yuga/Task-Scheduler/assets/82303711/5613b4a5-2c66-4499-a218-25efb8869cc8" width="273">
<br />
<br />

### Switch layout :

User can click on the top right icon to switch the layout according to it's preference. User has two choices i.e `Linear display` or `Staggered display`. This preferences will be saved and retrived again on next use of this application.

Tasks are arranged with different color codes. **Red** represents task was scheduled between last 7 days , **Green** represents task is scheduled for today and **White** represents task is is scheduled for up-coming days.

<img src="https://github.com/1405yuga/Task-Scheduler/assets/82303711/cc0c090d-e099-42ca-82d1-db412c1f3773" width="273">
<img src="https://github.com/1405yuga/Task-Scheduler/assets/82303711/3cde9625-ca7e-48c7-b838-518f27ddc09c" width="273">
<img src="https://github.com/1405yuga/Task-Scheduler/assets/82303711/3a01ece1-eb21-4f1a-a3f3-0c11db08d89d" width="273">
<br />
<br />

### Navigation View :

User can click on three-dashed icon for navigation view for more profile orieneted options. When user clicks the icon in left top app bar navigation drawer is opened with various options. Here, user's profile is displayed with different options.

<img src="https://github.com/1405yuga/Task-Scheduler/assets/82303711/b8b229c7-acae-45fb-a163-7f7ce19b2271" width="273">
<img src="https://github.com/1405yuga/Task-Scheduler/assets/82303711/876af63b-2b5d-45c5-b987-a41346132409" width="273">
<br />
<br />

### Account :

Account consists of ` SignOut ` menu through which user can log out from the current account. Before SignOut process, confirmation dialog appears. If clicked, 
` Yes ` user will be logged out and navigated to SignIn screen.

<img src="https://github.com/1405yuga/Task-Scheduler/assets/82303711/4740e380-fda0-4af5-8f2d-a8621d2043eb" width="273">
<img src="https://github.com/1405yuga/Task-Scheduler/assets/82303711/c61b1378-c70f-467a-9b5c-9ec046259c9c" width="273">
<br />
<br />

### Settings :

` Settings ` menu consists of two options to ` Clear all the task ` and ` Delete account `. ` Clear all task ` menu will remove all the tasks added and display main screen blank. ` Delete account ` menu will remove all the tasks added and delete the current signed-in account.

<img src="https://github.com/1405yuga/Task-Scheduler/assets/82303711/40e05de4-f868-4253-ac25-09b8382099d1" width="273">
<img src="https://github.com/1405yuga/Task-Scheduler/assets/82303711/4a5a366b-c1b0-4101-a772-ae44456ef4b5" width="273">
<img src="https://github.com/1405yuga/Task-Scheduler/assets/82303711/53b7f1a4-003d-4296-b412-80638003c506" width="273">
<br />
<br />

### Documentations :

Documentations consists of ` About ` menu and ` Documentations ` menu .

` About ` menu navigates to the developer's profile.

<img src="https://github.com/1405yuga/Task-Scheduler/assets/82303711/72970ac4-ca0a-4a68-a85d-669a1b3f7208" width="273">

<br />
<br />

` Documentations ` menu navigates to the documentation page which describes how to use this app.

<img src="https://github.com/1405yuga/Task-Scheduler/assets/82303711/2587e336-40e3-4553-ab4a-c9c1dcd83fed" width="273">

<br />
<br />

### Exit :

` Exit ` menu closes the app.

<img src="https://github.com/1405yuga/Task-Scheduler/assets/82303711/7b1af208-d363-4fb3-a794-9f8174865740" width="273">
