package com.example.alimentos.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alimentos.R;
import com.example.alimentos.datamodel;
import com.example.alimentos.login_activity;
import com.example.alimentos.myadapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    RecyclerView reclerView;
    ArrayList<datamodel> dataholder;
    private DatabaseReference mDatabaseRef;

    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
     /*   homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);       */
        View view = inflater.inflate(R.layout.fragment_home, container, false);
     //   final TextView textView = root.findViewById(R.id.text_home);
     /*   homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });   */


      //  String cityyname = ((login_activity)getActivity()).givecity().trim();




        reclerView = view.findViewById(R.id.recview);
        reclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dataholder = new ArrayList<datamodel>();

        String myfood = "firtest";

        login_activity loginn = new login_activity();
        String citty = loginn.cityyy;

       // str = str.replaceAll("\\s+", "");

  //     Toast.makeText(getContext(),"Cityname is "+citty,Toast.LENGTH_LONG).show();



        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                dataholder.clear();

                for (DataSnapshot ds :snapshot.getChildren()){
                    datamodel modelupload = ds.getValue(datamodel.class);



                    String foodimg = ""+ds.child("cityname").getValue();
                    String email =  ""+ds.child("email").getValue();



                    if (foodimg.equals(citty) && !user.getEmail().equals(email)){
                        dataholder.add(modelupload);
                    }



                }

                reclerView.setAdapter(new myadapter(getContext(),dataholder));





            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });










      /*  datamodel ob1 = new datamodel(R.drawable.potato_curry,"Potato Curry");
        dataholder.add(ob1);

        datamodel ob2 = new datamodel(R.drawable.tomato_soup,"Tomato Soup");
        dataholder.add(ob2);

        datamodel ob3 = new datamodel(R.drawable.paneer_tikka,"Paneer Tikka");
        dataholder.add(ob3);

        reclerView.setAdapter(new myadapter(dataholder));  */

        return view;
    }
}