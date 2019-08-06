package com.example.profily.Search;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.profily.R;
import com.example.profily.Schema.User;

import java.util.Vector;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.SearchRowViewHolder>  {

private Vector<User> searchList; //TODO maybe to delete

public SearchListAdapter(Vector<User> usersList) {
        this.searchList = usersList;
        }//TODO maybe to delete

@NonNull
@Override
public SearchRowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_row, parent, false);
        return new SearchRowViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull SearchRowViewHolder holder, int position) {
        User user = searchList.elementAt(position);
        holder.bind(user);
        }

@Override
public int getItemCount() {
        return searchList.size();
        }


static class SearchRowViewHolder extends RecyclerView.ViewHolder {

    ImageView searchedhUserImage;
    TextView searchedUserUsername;

    public SearchRowViewHolder(@NonNull View itemView) {

        super(itemView);

        searchedhUserImage = itemView.findViewById(R.id.searched_user_user_image);
        searchedUserUsername = itemView.findViewById(R.id.searched_user_user_username);
    }

    public void bind(User user){

        Log.d("TAG:" , user.toString());
        String str = user.getUsername();
        searchedUserUsername.setText(str); // TODO change
    }
}
}