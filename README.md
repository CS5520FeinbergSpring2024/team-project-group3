
## Pet Education App

# Please read carefully before making further modifications to the App.



# App Scheme

Common Entry Points:


MainActivity.java X
Serves as the initial screen post-login, directing users to either the shelter owner dashboard or the pet owner view based on their role.

LoginActivity.java X
Manages user login using Firebase Authentication.

RegisterActivity.java X

Handles user registration with Firebase Authentication, including role selection (shelter owner or pet owner).


Shelter Owner Functionalities:

ShelterOwnerDashboardActivity.java X
Acts as the main hub for shelter owners, offering navigation to manage shelters, pets, view adoption applications, etc.

ShelterRegistrationActivity.java X
Allows shelter owners to register their shelter, including providing details like name, location, and description.

PetRegistrationActivity.java X
Enables shelter owners to add pets to their shelter, including details like name, type (e.g., dog, cat), description, and uploading pictures.

PetManagementActivity.java X
Allows for the management of pet details, including updating information or removing pets.

ViewAdoptionsActivity.java X
Where shelter owners review adoption applications submitted by potential pet owners and can approve or reject them.


Pet Owner Functionalities:

PetOwnerViewActivity.java X
The main view for pet owners, featuring a dashboard or feed with featured shelters, pets for adoption, educational content, etc.

ShelterListActivity.java X
Displays a list of shelters for pet owners to browse through.

ShelterDetailActivity.java X
Shows detailed information about a selected shelter, including its location, description, and available pets.

PetListActivity.java X
Lists all available pets in a selected shelter, allowing pet owners to browse through potential pets for adoption.

PetDetailActivity.java X
Provides detailed information about a selected pet, including the option for pet owners to start an adoption application or chat with the shelter.

AdoptionApplicationActivity.java
Where pet owners fill out and submit their adoption application for a specific pet.

AdoptionLessonActivity.java X
Manages adoption education lessons for pet owners, ensuring they're prepared for pet adoption.

CelebrationActivity.java
A screen to celebrate successful adoption, shown after the adoption process is completed.


Shared Functionalities:

ChatActivity.java
For real-time chatting between pet owners and shelter owners, facilitated by Firebase Realtime Database or Firestore.


APIs and Services:


Firebase Authentication:
Handles user authentication, ensuring secure login and registration.

Firebase Realtime Database or Firestore:
Stores and retrieves data such as user roles, shelter details, pet information, chat messages, and adoption applications.

Firebase Storage:
Manages image and document storage, including pet pictures and adoption certificates.

Firebase Cloud Functions:
Executes backend code in response to Firebase events or HTTPS requests, useful for managing notifications, data processing, etc.

Firebase Cloud Messaging:
Sends push notifications about adoption process updates, new chat messages, or other relevant alerts.

Firebase Analytics:
Tracks user engagement, app usage, and can differentiate between shelter owner and pet owner activities for insights.

Google Maps API:
Displays maps for shelter locations, aiding pet owners in finding shelters near them.

# CAUTION:

Make sure to pass the petId of the pet for which the application is being made to 
AdoptionApplicationActivity when navigating from PetDetailActivity or any other relevant part of 
the app:

Intent intent = new Intent(CurrentActivity.this, AdoptionApplicationActivity.class);
intent.putExtra("petId", petId); // Ensure petId is correctly retrieved and passed here
startActivity(intent);

### Anna Brunkhorst Work Log

April 3

Commented out broken files during work - ignored these files when committing (no changes pushed):
- BreedViewActivity
- ShelterActivity
- ShelterOwnerDashboardActivity
- ViewAdoptionsActivity

Fixed:

Login and registration functionality
- Any sort of login or registration attempt resulted in error Toast due to bad configuration
- Edited Firebase configuration and methods, login and registration now functional
- New google-services.json download necessary for functionality

To do:

Login and Registration
- Logging in with bad info spouts internal error toast - should be more user friendly / clearer message
- Neither screen very usable in landscape mode

Post-Login
- After registration/login, user is brought to MainActivity
- MainActivity holds two buttons (shelter vs pet user) that do not function - these need fixing
- MainActivity should likely never be visible to users - can be auto-directed to proper side of app

Check through remaining pet owner side files

April 5

Commented out broken file during work - ignored this file when committing (no changes pushed):
- ShelterListAdapter

Changes:
- Created new layout-land directory for activities where portrait layout is not usable in landscape
- Created new layout for Login activity, now usable in landscape mode
- Created new layout for Register activity, now usable in landscape mode

April 6

Changes:
- New edits/malformed XML caused immediate crashes in Login page landscape layout; rebuilt cleaner, bug-free layout

April 9

Fixed:
- Unable to register or login new users due to private Firestore permissions. Loosened permissions to allow for registration

New Todos:
- Filling out registration page with an already-used email should notify user of such rather than vague error
- Beautify PetOwnerViewActivity
- Fix pages linked to PetOwnerViewActivity; all appear blank with exception of View Pets, which has no effect. Ensure data exists for these pages, beautify, etc.

April 10

Changes:
- Edits to PetOwnerViewActivity

New Todos:
- (See above)
- ChatListActivity chat_list_item needs more info like shelter name for each chat

April 14-15

Changes:
- Rebuilt and cleared Login page crashes

April 16

Changes:
- Built usable landscape layout for registration page

April 17-18

Changes
- Created test pet user Test (test@gmail.com) and shelter user TestS(testS@gmail.com)
- Fixed ShelterRegistrationActivity; error caused by geocoding on main thread had been preventing registration

New Todos:
- Some button(s) on PetOwnerViewActivity take user back to login
- ShelterRegistrationActivity should return user to ManageSheltersActivity upon successful registration
- Error when clicking on shelter in ManageSheltersActivity: geopoint cannot be cast to parcelable

April 19

Changes
- ShelterRegistrationActivity now returns user to ManageSheltersActivity upon successful registration

April 20

Changes:
- Fixed adding pets as shelter user, especially pet images:
  - Added permissions requests and checks for images
  - Temporary local file saving
  - Set up Firebase Storage to hold images and eliminate errors

New Todos:
- As shelter user, "Manage Pets" button returns Toast "Invalid Shelter ID" and does not display pets
  - This is because user ID is passed in but shelter ID is needed
  - Also, why is Manage Pets separate from Add Pets vs the all-in-one Manage Shelters model?
- Similarly, "View Chats" button returns Toast "No user email provided"
- As pet user
  - Browse Shelters appears blank even when directly on top of a shelter
  - View Pets does nothing
  - Learn About Adoption crashes
    - Likely due to mismatch between code's expected data structure and database contents
  - Chat with Shelters appears blank with Toast "No user email provided"

April 20, part 2

Changes
- Adjusted Firestore permissions to fix ShelterRegistration
- Added Firebase auth to build.gradle

New Todos:
- As shelter user, "View Adoptions" crashes app
- As pet user, "Browse Shelters" produces Toast "Failed to get current location" (device-dependent?)

April 21

Changes
- Reworked Lessons and associated adapters and activities within code and Firebase, solving major functionality issues
- Redesigned pages involving Lessons to display correct information


### Dakota's work Log
(March 29th - March 30th) summary:
* Changed Firebase rules so that firebase work successfully with activities.
* Adding extra breed is simple: add an image to drawable and add a pet type to the firebase.
* Adding extra shelter is simple: add required elements to shelters in the firebase.
* Successfully fetched API data to local.

(March 31th) summary:
* Created a shelter detail page, adoptable pet list page and a pet detail page.
* Adding extra pet is simple: add required elements in that shelter.
* After adding back Sadok's code, the app has authentication problem, so a new branch is created to prevent overriding the functioning code.

* * Next Tasks for Dakota: 
  * UI.
  * Add placeholders for breeds that don't have shelters displayed.
  * Add domestic animals (mud).
  * Add loading display to the recycler view until it loads.
  * Add more data to the database.


### Sadok Belakhoua work Log

(March 15th recap):
* Created MainActivity, LoginActivity, RegisterActivity, User
* Created Firebase repo and set initial schema
* Set design language and feel of app through Material Design

(April 1st update recap):

Significant update: 

* Added a Lesson object, AdoptionLessonActivity, LessonsAdapter, and UserLessonProgress
* Added lessons to Firebase
* Changed Shelteractivity and added new "breedlist" field to the shelter schema to improve query performance
* Created the PetDetailActivity, PetListActivity, and their PetsAdapter for the recyclerview
* Created a PreferencesUtil file 
* Created a ShelterList item view holder and model
* Expanded Shelter with BreedList field and getter
* Created a ShelterRepository object
* Greatly expanded the User object
* Added a UserLessonProgress class
* Added an AdoptionApplicationActivity class 

(April 3rd update recap):

* Created the AddPetActivity and LessonDetailActivity classes
* Added ManageSheltersActivity, ShelterAdapter, ShelterDetailActivity, ViewadoptionsActivity and ShelterRegistrationActivity
* Renamed petData to Pet, shelterData to Shelter
* Created lesson content for 3 lessons

(April 4th Update):

* Created AdoptionApplication, PetManagementActivity and AdoptionApplicationAdapter
* Created PetRegistrationActivity
* Added Firebase Storage integration for the app
* Updated ShelterOwnerDashboardActivity accordingly
* Udated ViewAdoptionsActivity accordingly
* Updated Firebase schema to better reflect app architecture and for better clarity and search performance
* Deleted breed-related unnecessary code

(April 5th Update):

* Created all chat-related files
* Changed Firebase database schema to allow for chat feature

(April 6th, 7th and 8th Updates):

* Added messaging and notifications capabilities

(April 9th Updates):

* Registered app with AppCheck on Firebase, to enable integration with Play Integrity

(April 10th Updates):

* Greatly simplified the login and registration procedure, relying only on an email address now. 

## Installation

Implement Glide to build.gradle.kts for adding web images (from api or webpage).

## Font
Font Size:  https://medium.com/@wayanwina189/ui-typography-guidelines-ios-android-and-website-font-size-guideline-part-2-abcee14df8cb
** Sometimes the title is 24 **
FontType: Title: Roboto bold, Text: Roboto.

## Current Database Structure:
```text
pet type:
"pet_types": {
  "type1": {
    "imageUrl": "dog_type",
    "name": "dog"
  },
  "type2": {
    "imageUrl": "cat_type",
    "name": "cat"
  }
}
Shelter:
"shelters": {
  "Shelter1": {
    "address": "350 South Huntington Ave. Boston, MA 02130",
    "adoptablePet": {
      "cat1": {
        "age": "0.5",
        "breed": "domestic shorthair",
        "description": "Mimi is a cute little kitten who loves explore.",
        "gender": "female",
        "id": "2024/3/21/001",
        "imageUrl": "",
        "name": "Mimi",
        "price": "300"
      }
    },
    "breeds": {
      "cat1": "Abyssinian",
      "dog1": "Affenpinscher"
    },
    "description": "",
    "imageUrl": "",
    "location": "Boston, MA",
    "name": "MSPCA",
    "phoneNumber": "(617) 522-7400",
    "yearofbusiness": "156"
  }
}
```

```bash
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
```

```text
Added  <activity android:name=".ShelterActivity"/> & <activity android:name=".ShelterListActivity"/> & <activity android:name=".BreedViewActivity"/> to androidManifest.
Added Brokenlink.png to drawable to serve as a placeholder for image sections.
Added cat_type.jpg & dog_type.JPG as placeholder images for pet types in MainActivity.
```

```API
Cat API: 
https://developers.thecatapi.com/view-account/ylX4blBYT9FaoVd6OhvR?report=aZyiLrsCh 

Dog API:
https://developers.thedogapi.com/view-account/ylX4blBYT9FaoVd6OhvR?report=8FfZAkNzs 

Api Key: 
check BreedAPI: apiKey

```
- Created Layout:
  -
- activity_main.xml: the main activity layout which contains a button that enters the breed activity.
- activity_breed.xml: shows recycler view for pet type and listView for pet breeds.
- list_pet_type.xml: the recycler view structure for pet types in BreedViewActivity
- list_item_pet.xml: the breed list structure.
- activity_shelterlist.xml: shows shelter activities in list view.
- list_item_shelter.xml: the list structure for shelter.
- activity_shelter.xml: shows a shelter's content.
- activity_pet_adoptable.xml: shows the layout of adoptable pets.
- list_item_pet.xml: an list item of a pet.
- activity_pet.xml: shows the information for an adoptable pet.

- Created Objects:
  -
- pet type: PetType.java: the types of pets are stored in firebase as "pet_types".
- breed data: breedData.java: the breed information pulled using API (check API section for more details).
- shelter Data: Shelter.java: the shelter information that are stored in firebase as "shelters".
  - Parcelable
- pet data: petData.java: the pet information that are stored in shelters.
  - petType can be used to filter pet data if needed.
  - Parcelable

- Created Activities & its supporting class:
  - 
- BreedViewActivity: shows pet types, once clicked: shows the breeds.
    - PetTypeAdapter: contains methods that create recyclerView that is clickable to generate list of breed for that particular pet type.
    - BreedApi: this class contains methods that fetch api data to the local and stores everything in a list of breeds.
    - BreedListAdapter: contains methods that provide list-required element for each item and renders each item clickable to open a new activity that list shelters.

- ShelterListActivity: shows shelters of selected breed.
    - ShelterListAdapter: contains methods that helper create list of shelters.

- ShelterActivity: shows the content of each shelter.

- PetAdoptableActivity: shows a list of adoptable pets.
  - PetAdoptableAdapter: the adapter for each list item.


  







    