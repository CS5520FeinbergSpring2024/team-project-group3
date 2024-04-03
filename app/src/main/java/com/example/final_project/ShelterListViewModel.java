package com.example.final_project;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ShelterListViewModel extends ViewModel {
    private MutableLiveData<List<shelterData>> shelterListLiveData = new MutableLiveData<>();
    private ShelterRepository shelterRepository;

    public ShelterListViewModel() {
        shelterRepository = new ShelterRepository();
    }

    public void fetchSheltersByBreed(String breedName) {
        shelterRepository.fetchSheltersByBreed(breedName, new ShelterRepository.FetchSheltersCallback() {
            @Override
            public void onSheltersFetched(List<shelterData> shelters) {
                shelterListLiveData.postValue(shelters);
            }

            @Override
            public void onSheltersFetchFailed(Exception e) {
                // Optional: Handle fetch failure
            }
        });
    }

    public LiveData<List<shelterData>> getShelterListLiveData() {
        return shelterListLiveData;
    }
}

