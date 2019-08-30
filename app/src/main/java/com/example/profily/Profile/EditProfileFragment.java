package com.example.profily.Profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.profily.Model.Schema.User.User;
import com.example.profily.R;

public class EditProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    //Profile vars
    private TextView profileUsername,profileDescription,profileFirstName,profileLastName;
    private TextView changeProfileImgBtn;
    private ImageView profileImage;
    private Button saveProfileBtn;


    public EditProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        profileUsername = view.findViewById(R.id.edit_profile_username);
        profileDescription = view.findViewById(R.id.edit_profile_description);
        profileFirstName = view.findViewById(R.id.edit_profile_first_name);
        profileLastName = view.findViewById(R.id.edit_profile_last_name);

        changeProfileImgBtn = view.findViewById(R.id.edit_profile_change_profile_pic);
        profileImage = view.findViewById(R.id.edit_profile_profile_image);
        saveProfileBtn = view.findViewById(R.id.edit_profile_save_profile_btn);

        String userId = null;
        if (getArguments() != null && getArguments().size()!=0)
        {
            userId = EditProfileFragmentArgs.fromBundle(getArguments()).getUserId();
        }


        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);

        profileViewModel.populateUserDetails(userId);
        profileViewModel.getUser().observe(this, userData->{
            if (userData.getUsername() != null){
                profileUsername.setText(userData.getUsername());
            }
            else{
                profileUsername.setText("");
            }
            profileFirstName.setText(userData.getFirstName());
            profileLastName.setText(userData.getLastName());
            profileDescription.setText(userData.getDescription());

            saveProfileBtn.setOnClickListener(view1 -> {
                profileViewModel.updateUser(
                        new User(userData.getUserId(),
                                userData.getProfileImageURL(),
                                profileUsername.getText().toString(),
                                profileDescription.getText().toString(),
                                profileFirstName.getText().toString(),
                                profileLastName.getText().toString()
                        ));
                getFragmentManager().popBackStack();
            });
        });

        return view;
    }



}