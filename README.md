
## Pet Education App

### Dakota's work Log
(March 29th - March 30th) summary:
* Changed Firebase rules so that firebase work successfully with activities.
* Adding extra breed is simple: add an image to drawable and add a pet type to the firebase.
* Adding extra shelter is simple: add required elements to shelters in the firebase.
* Successfully fetched API data to local.

(March 31th) summary:
* Created a shelter detail page, adoptable pet list page and a pet detail page.
* Adding extra pet is simple: add required elements in that shelter.
* Solved Github conflict.

(April 20th) summary:
* Added placeholders for breeds that don't have shelters displayed.
* Added domestic animals as an option for breedlist.
* Cleaned and updated Firebase.
* Transferred the launch page from main to breedlistview.

* * Next Tasks for Dakota: 
  * UI.
  * Add loading display to the recycler view until it loads.



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
- shelter Data: shelterData.java: the shelter information that are stored in firebase as "shelters".
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


  







    