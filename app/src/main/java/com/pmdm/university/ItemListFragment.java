package com.pmdm.university;

import android.os.Bundle;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.pmdm.university.databinding.FragmentItemListBinding;
import com.pmdm.university.databinding.ItemListContentBinding;

import com.pmdm.university.entidad.University;
import com.pmdm.university.implementacion.UniversityDetailSQLiteHelper;
import com.pmdm.university.interfaz.IUniversityApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A fragment representing a list of Items. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a {@link ItemDetailFragment} representing
 * item details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListFragment extends Fragment {



    /**
     * Method to intercept global key events in the
     * item list fragment to trigger keyboard shortcuts
     * Currently provides a toast when Ctrl + Z and Ctrl + F
     * are triggered
     */
    ViewCompat.OnUnhandledKeyEventListenerCompat unhandledKeyEventListenerCompat = (v, event) -> {
        if (event.getKeyCode() == KeyEvent.KEYCODE_Z && event.isCtrlPressed()) {
            Toast.makeText(
                    v.getContext(),
                    "Undo (Ctrl + Z) shortcut triggered",
                    Toast.LENGTH_LONG
            ).show();
            return true;
        } else if (event.getKeyCode() == KeyEvent.KEYCODE_F && event.isCtrlPressed()) {
            Toast.makeText(
                    v.getContext(),
                    "Find (Ctrl + F) shortcut triggered",
                    Toast.LENGTH_LONG
            ).show();
            return true;
        }
        return false;
    };

    private FragmentItemListBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentItemListBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewCompat.addOnUnhandledKeyEventListener(view, unhandledKeyEventListenerCompat);

        RecyclerView recyclerView = binding.itemList;

        // Leaving this not using view binding as it relies on if the view is visible the current
        // layout configuration (layout, layout-sw600dp)
        View itemDetailFragmentContainer = view.findViewById(R.id.item_detail_nav_container);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://universities.hipolabs.com/")
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder().serializeNulls().create()
                ))
                .build();

        IUniversityApiService iUniversityApiService = retrofit.create(IUniversityApiService.class);
        Call<List<University>> call = iUniversityApiService.getUniversities();

        call.enqueue(new Callback<List<University>>() {
            @Override
            public void onResponse(Call<List<University>> call, Response<List<University>> response) {
                if (response.isSuccessful()) {
                    List<University> universityList = response.body();
                    setupRecyclerView((RecyclerView) recyclerView, itemDetailFragmentContainer, (ArrayList<University>)universityList);
                }
            }

            @Override
            public void onFailure(Call<List<University>> call, Throwable t) {
                Log.d("Error", t.toString());
            }
        });
    }

    private void setupRecyclerView(
            RecyclerView recyclerView,
            View itemDetailFragmentContainer,
            List<University> universityList
    ) {

        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(
                universityList,
                itemDetailFragmentContainer
        ));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<University> mValues;
        private final View mItemDetailFragmentContainer;

        SimpleItemRecyclerViewAdapter(List<University> items,
                                      View itemDetailFragmentContainer) {
            mValues = items;
            mItemDetailFragmentContainer = itemDetailFragmentContainer;
        }

        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Integer index = (int) view.getTag();

                University item = mValues.get(index);
                Bundle arguments = new Bundle();
                arguments.putString(ItemDetailFragment.ARG_ITEM_NAME,item.getName());*/

                Navigation.findNavController(view).navigate(R.id.show_item_detail);/*, arguments);*/
            }
        };

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            ItemListContentBinding binding =
                    ItemListContentBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new ViewHolder(binding);

        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            String name = mValues.get(position).getName();
            holder.mContentView.setText(name);

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(itemView ->{
                Bundle arguments = new Bundle();
                arguments.putString(ItemDetailFragment.NAME, name);
                arguments.putString(ItemDetailFragment.URL, mValues.get(position).getWebPages().get(0));
                if (mItemDetailFragmentContainer !=null){
                    Navigation.findNavController(mItemDetailFragmentContainer)
                            .navigate(R.id.item_detail_nav_container, arguments);
                }
            });


        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mContentView;

            ViewHolder(ItemListContentBinding binding) {
                super(binding.getRoot());
                mContentView = binding.content;
            }

        }
    }
}