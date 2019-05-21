package com.amith.schoollabapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amith.schoollabapp.Adapter.UserAdapter;
import com.amith.schoollabapp.Model.User;
import com.amith.schoollabapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class UserFragment extends Fragment {

    private RecyclerView recyclerView;

    private UserAdapter userAdapter;
    private List<User> mUsers;

    EditText search_users;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mUsers = new ArrayList<>();

        readUsers();

//        search_users = view.findViewById(R.id.seach_users);
//        search_users.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
//                searchUsers(charSequence.toString().toLowerCase());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        return view;
    }

//    private void searchUsers(String s) {
//
//        final FirebaseUser fuser = FirebaseAuth.getInstance().getCurrentUser();
//        Query query = FirebaseDatabase.getInstance().getReference("Users").orderByChild("search")
//                .startAt(s)
//                .endAt(s+"\uf8ff");
//
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                mUsers.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    User user = snapshot.getValue(User.class);
//
//                    assert user != null;
//                    assert fuser != null;
//                    if (!user.getId().equals(fuser.getUid())){
//                        mUsers.add(user);
//                    }
//                }
//
//                userAdapter = new UserAdapter(getContext(), mUsers);//, false
//                recyclerView.setAdapter(userAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
//    }


    private void readUsers(){

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Toast.makeText(UserFragment.this.getContext(), "user = "+firebaseUser, Toast.LENGTH_SHORT).show();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");

        Toast.makeText(UserFragment.this.getContext(), "A", Toast.LENGTH_SHORT).show();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (search_users.getText().toString().equals("")) {
                    mUsers.clear();
                    Toast.makeText(UserFragment.this.getContext(), "a", Toast.LENGTH_SHORT).show();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User user = snapshot.getValue(User.class);

                        assert user != null;
                        assert firebaseUser != null;
                        if (!user.getId().equals(firebaseUser.getUid())) { //choose all users without logged user
                            mUsers.add(user);
                        }
                    }

                    try {
                        userAdapter = new UserAdapter(getContext(), mUsers);//, false
                        //recyclerView.setAdapter(userAdapter);
                        Toast.makeText(UserFragment.this.getContext(), "1", Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        Toast.makeText(UserFragment.this.getContext(), "2 : "+e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
