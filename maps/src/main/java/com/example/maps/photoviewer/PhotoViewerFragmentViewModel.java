package com.example.maps.photoviewer;

import android.graphics.Bitmap;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.example.maps.domain.Photo;
import com.example.maps.repo.PhotoRepository;

public class PhotoViewerFragmentViewModel extends ViewModel {

    private PhotoRepository photoRepository;
    private Function<Photo, PhotoViewState> mapper;

    public PhotoViewerFragmentViewModel(PhotoRepository photoRepository, Function<Photo, PhotoViewState> mapper) {
        this.photoRepository = photoRepository;
        this.mapper = mapper;
    }

    LiveData<PhotoViewState> fetchPhoto(long id) {
        return Transformations.map(photoRepository.getPhoto(id), mapper);
    }
}
