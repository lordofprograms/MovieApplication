package com.borisruzanov.popularmovies.ui.favouriteList;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.borisruzanov.popularmovies.OnItemClickListener;
import com.borisruzanov.popularmovies.R;
import com.borisruzanov.popularmovies.entity.BasePojo;
import com.borisruzanov.popularmovies.ui.detailed.DetailedFragment;
import com.borisruzanov.popularmovies.constants.Contract;
import com.borisruzanov.popularmovies.constants.FavouritesDbHelper;
import com.borisruzanov.popularmovies.entity.FavouriteModel;
import com.borisruzanov.popularmovies.udacity.ProviderContract;
import com.borisruzanov.popularmovies.ui.list.ListFragment;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavouritesFragment extends Fragment implements FavouritesAdapter.ItemClickListener {
    View view;
    Toolbar toolbar;

    RecyclerView recyclerView;
    ArrayList<FavouriteModel> favouritesList;
    FavouritesAdapter favouritesAdapter;
    ListFragment listFragment;


    /**
     * DB
     */
    private SQLiteDatabase mDb;
    FavouritesDbHelper favouritesDbHelper;
    Cursor cursor;
    Cursor info;

    public FavouritesFragment() {
        // Required empty public constructor
    }

    public FavouritesFragment getInstance(String path) {
        FavouritesFragment fragment = new FavouritesFragment();
        Bundle args = new Bundle();
        args.putString("path", path);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favourites, container, false);
        toolbar = (Toolbar) view.findViewById(R.id.main_favourite_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        setHasOptionsMenu(true);

        listFragment = new ListFragment();

        /**
         * DB
         */
        favouritesDbHelper = new FavouritesDbHelper(getActivity());
        mDb = favouritesDbHelper.getWritableDatabase();


        cursor = getDataForListFromContentProvider();
//            cursor.moveToFirst();
            favouritesList = new ArrayList<>();


            cursor.moveToFirst();
            while(cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                Log.d("TAG", "ID - " + id);

                String posterPath = cursor.getString(cursor.getColumnIndex("poster_path"));
                Log.d("TAG", "POSTER PATH - " + posterPath);

                String title = cursor.getString(cursor.getColumnIndex("title"));
                Log.d("TAG", "TITLE - " + title);

                String releaseDate = cursor.getString(cursor.getColumnIndex("release_date"));
                Log.d("TAG", "RELEASE DATE - " + releaseDate);

                String vote = cursor.getString(cursor.getColumnIndex("rating"));
                Log.d("TAG", "VOTE - " + vote);

                String overview = cursor.getString(cursor.getColumnIndex("overview"));
                Log.d("TAG", "OVERVIEW - " + overview);

                favouritesList.add(new FavouriteModel(id, posterPath, title, releaseDate, vote, overview));
            }
            cursor.close();

//        for (FavouriteModel result : favouritesList) {
//            Log.d("TAG", "Title from moviesList " + result.getTitle());
//            Log.d("TAG", "Id from moviesList " + result.getId());
//        }



        String[] projection = new String[]{
                Contract.TableInfo.COLUMN_ID,
                Contract.TableInfo.COLUMN_TITLE,
                Contract.TableInfo.COLUMN_RELEASE_DATE,
                Contract.TableInfo.COLUMN_RATING,
                Contract.TableInfo.COLUMN_OVERVIEW,
                Contract.TableInfo.COLUMN_POSTER_PATH
        };

        /**
         * Первый этап - взяли данные
         */
        info = getNeededFavouriteCursor(projection);

        favouritesAdapter = new FavouritesAdapter(favouritesList, setOnItemClickCallback());
        recyclerView = view.findViewById(R.id.recycler_favourites);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(favouritesAdapter);
        return view;
    }

    /**
     * DB
     */
    private Cursor getFavouriteCursor() {
        return mDb.query(
                Contract.TableInfo.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    private Cursor getNeededFavouriteCursor(String[] projection) {
        return mDb.query(
                Contract.TableInfo.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
    }

    private OnItemClickListener.OnItemClickCallback setOnItemClickCallback() {
        OnItemClickListener.OnItemClickCallback onItemClickCallback = new OnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {

                Bundle bundle = new Bundle();
                bundle.putString("title", favouritesList.get(position).getTitle());

                bundle.putString("overview", favouritesList.get(position).getOverview());
                bundle.putString("release_date", favouritesList.get(position).getRelease_date());
                bundle.putString("path", "favourite");
                bundle.putString("poster_path", favouritesList.get(position).getPoster_path());
                bundle.putString("id", favouritesList.get(position).getId());

                DetailedFragment detailedFragment = new DetailedFragment();
                detailedFragment.setArguments(bundle);

                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_frame_list, detailedFragment);
                transaction.commit();
            }

        };
        return onItemClickCallback;
    }

    @Override
    public void onItemClick(View view, int position) {
    }

    public Cursor getDataForListFromContentProvider(){
        Log.v("tag", "===============>>>>>>>> inside getDataForListFromContentProvider");
        try {
                return getContext().getContentResolver().query(ProviderContract.TableEntry.CONTENT_URI,
                        null,
                        null,
                        null,
                        null);
        }catch (Exception e){
            Log.e("tag", "Failed while loading data");
        }
        return null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        super.onCreateOptionsMenu(menu, inflater);
        toolbar.inflateMenu(R.menu.menu_main);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        switch (item.getItemId()) {
            case R.id.menu_sort:
                transaction.replace(R.id.main_frame_list, listFragment.getInstance("sort"));
                transaction.addToBackStack(null);
                transaction.commit();
                break;
            case R.id.menu_popular:
                transaction.replace(R.id.main_frame_list, listFragment.getInstance("popular"));
                transaction.addToBackStack(null);
                transaction.commit();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}

//       if (cursor.moveToFirst()){
//           int idIndex = cursor.getColumnIndex(Contract.TableInfo.COLUMN_ID);
//           int idTitle = cursor.getColumnIndex(Contract.TableInfo.COLUMN_TITLE);
//           int idPath = cursor.getColumnIndex(Contract.TableInfo.COLUMN_POSTER_PATH);
//           Log.d("tag", "---> ID = " + cursor.getString(idIndex) +
//           " ---> Title = " + cursor.getString(idTitle) +
//           " ---> Path = " + cursor.getString(idPath));
//       } else {
//           Log.d("tag", "---Cursor got 0 rows---");
//       }


//                    bundle.putString("overview", itemClicked.getOverview());
//                    bundle.putString("release_date", itemClicked.getRelease_date());
//                    bundle.putString("vote_average", itemClicked.getVote_average().toString());
//                    bundle.putString("poster_path", itemClicked.getPoster_path());
//                    bundle.putString("id", itemClicked.getId().toString());


//                    FavouriteModel itemClicked = favouritesList.get(cursor.moveToPosition(position));
//                    Log.d("click", "Fav -  FavouriteModel itemClicked " + itemClicked.toString());