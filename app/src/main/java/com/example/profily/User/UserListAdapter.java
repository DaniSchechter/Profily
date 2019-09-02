package com.example.profily.User;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.profily.R;
import com.example.profily.Model.Schema.User.User;
import com.example.profily.Search.SearchFragmentDirections;

import java.util.List;
import java.util.Vector;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserRowViewHolder>  {

    private List<User> searchList; //TODO maybe to delete

    public UserListAdapter(Vector<User> usersList) {
        this.searchList = usersList;
    }//TODO maybe to delete

    @NonNull
    @Override
    public UserRowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row, parent, false);
        return new UserRowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRowViewHolder holder, int position) {
        User user = searchList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return searchList.size();
    }

    public void setUsers(List<User> usersList){
        if(usersList != null) {
            this.searchList = usersList;
            notifyDataSetChanged(); //TODO need to check exactly what this function does
        }
    }


    static class UserRowViewHolder extends RecyclerView.ViewHolder {

        ImageView userImage;
        TextView userUsername;

        public UserRowViewHolder(@NonNull View itemView) {

            super(itemView);

            userImage = itemView.findViewById(R.id.searched_user_user_image);
            userUsername = itemView.findViewById(R.id.searched_user_user_username);
        }

        public void bind(User user){

            userUsername.setText(user.getUsername());
            if(!user.getProfileImageURL().isEmpty()) {
                Glide.with(userImage.getContext()).load(user.getProfileImageURL()).into(userImage);
            } else {
                userImage.setImageResource(R.drawable.profile);
            }

            userUsername.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                        SearchFragmentDirections.actionSearchFragmentToProfileFragment(
                                user.getUserId()
                        )
                )
            );

            userImage.setOnClickListener(
                    Navigation.createNavigateOnClickListener(
                            SearchFragmentDirections.actionSearchFragmentToProfileFragment(
                                    user.getUserId()
                            )
                    )
            );
        }
    }
}