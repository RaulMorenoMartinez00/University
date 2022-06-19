package com.pmdm.university;

import android.content.ClipData;
import android.os.Binder;
import android.os.Bundle;
import android.view.DragEvent;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.pmdm.university.entidad.University;
import com.pmdm.university.entidad.UniversityDetail;
import com.pmdm.university.implementacion.UniversityDetailSQLiteHelper;
import com.pmdm.university.placeholder.PlaceholderContent;
import com.pmdm.university.databinding.FragmentItemDetailBinding;


public class ItemDetailFragment extends Fragment {

    public static final String NAME="name";
    public static final String URL="url";

    private UniversityDetail detail;

    private FragmentItemDetailBinding binding;
    private UniversityDetailSQLiteHelper db;

    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new UniversityDetailSQLiteHelper(getContext());

        String name = getArguments().getString(NAME);
        if (name!=null) {
            detail= db.getUniversityDetail(name);
            if(detail ==null)
                detail =  new UniversityDetail();


            detail.setName(name);
            detail.setUrl(getArguments().getString(URL));

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentItemDetailBinding.inflate(inflater, container, false);
        View rootView = binding.getRoot();

        updateContent();

        binding.fab.setOnClickListener(view -> {
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.NAME, detail.getName());
            Navigation.findNavController(rootView)
                    .navigate(R.id.nav_host_fragment_item_detail, arguments);
        });

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void updateContent() {
        if(detail !=null){
            binding.universityName.setText(detail.getName());
            binding.itemDetail.setText("La URl es: " + detail.getUrl());
            if(detail.getDescription() != null)
                    binding.itemDetail.setText(detail.getDescription());

            if(detail.getImageUrl() !=null && detail.getImageUrl().length() >0){
                ImageView itemImage = binding.itemImage;
                Glide.with(this)
                        .load(detail.getImageUrl())
                        .into(itemImage);
            }
        }

    }
}