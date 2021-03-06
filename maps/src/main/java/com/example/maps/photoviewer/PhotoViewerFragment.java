package com.example.maps.photoviewer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.maps.R;

import org.jetbrains.annotations.TestOnly;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoViewerFragment extends Fragment {

    public static final String TAG = "PhotoViewerFragment";
    private static final String EXTRA_PHOTO_ID = "EXTRA_PHOTO_ID";

    public static PhotoViewerFragment newInstance(long photoId) {
        Bundle bundle = new Bundle();
        bundle.putLong(EXTRA_PHOTO_ID, photoId);

        PhotoViewerFragment fragment = new PhotoViewerFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.imageView)
    ImageView imageView;

    @BindView(R.id.dateTextView)
    TextView dateTextView;

    private ViewModelProvider.Factory viewModelFactory = new PhotoViewerViewModelFactory(getContext());
    private PhotoViewerFragmentViewModel fragmentViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo_viewer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initViewModel();
    }

    private void initViewModel() {
        fragmentViewModel = new ViewModelProvider(this, viewModelFactory)
                .get(PhotoViewerFragmentViewModel.class);
        fragmentViewModel.fetchPhoto(getPhotoId())
                .observe(getViewLifecycleOwner(), this::showPhoto);
    }

    private void showPhoto(PhotoViewState photoViewState) {
        dateTextView.setText(photoViewState.getDateText());
        dateTextView.setOnClickListener(v -> imageView.setVisibility(View.GONE));
        imageView.setImageBitmap(photoViewState.getBitmap());
    }

    private long getPhotoId() {
        Bundle bundle = getArguments();
        return bundle != null ? bundle.getLong(EXTRA_PHOTO_ID) : -1;
    }

    @TestOnly
    void setViewModelFactory(ViewModelProvider.Factory viewModelFactory) {
        this.viewModelFactory = viewModelFactory;
    }
}
