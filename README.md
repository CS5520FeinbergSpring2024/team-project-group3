
# Pet Education App

Please read carefully before making further modifications.

### Dakota's work Log
(March 29th - March 30th) summary:
* Changed Firebase rules so that firebase work successfully with activities.
* Adding extra breed is simple: add an image to drawable and add a pet type to the firebase.
* Adding extra shelter is simple: add required elements to shelters in the firebase.
* Successfully fetched API data to local.


## Installation

Implement Glide to build.gradle.kts for adding web images (from api or webpage).

```bash
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
```

```text
Added  <activity android:name=".ShelterActivity"/> & <activity android:name=".BreedViewActivity"/> to androidManifest.
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
- activity_shelter.xml: shows shelter activities in list view.
- list_item_shelter.xml: the list structure for shelter.

- Created Objects:
  -
- pet type: PetType.java: the types of pets are stored in firebase as "pet_types".
- breed data: petData.java: the breed information pulled using API (check API section for more details).
- shelter Data: shelterData.java: the shelter information that are stored in firebase as "shelters".

- Created Activities & its supporting class:
  - 
- BreedViewActivity: shows pet types, once clicked: shows the breeds.
    - PetTypeAdapter: contains methods that create recyclerView that is clickable to generate list of breed for that particular pet type.
    - BreedApi: this class contains methods that fetch api data to the local and stores everything in a list of breeds.
    - PetListAdapter: contains methods that provide list-required element for each item and renders each item clickable to open a new activity that list shelters.


- ShelterActivity: shows shelters of selected breed.
    - ShelterListAdapter: contains methods that helper create list of shelters.
      ** this part needs to be modified by tomorrow so that it can open the available pet for each shelter.







    