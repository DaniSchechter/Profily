package com.example.profily.Comments;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.profily.Model.Model;
import com.example.profily.Model.Schema.Comment.Comment;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CommentsViewModel extends ViewModel {
    private MutableLiveData<List<Comment>> commentsListLiveData; //todo yblalal
    private static final int delta = 10;
    private int numOfComments = 10;

    public CommentsViewModel() {
        commentsListLiveData = new MutableLiveData<>();
    }

    public void getComments(String postId){
        // Get all comments async
        Model.instance.getAllComments( postId, numOfComments, commentsList -> this.commentsListLiveData.setValue(commentsList));
    }

    public LiveData<List<Comment>> getCommentsList() {
        return this.commentsListLiveData;
    }

    public void loadMoreComments(String postId) {

        this.numOfComments += delta;
        Model.instance.getAllComments( postId, numOfComments, commentsList -> this.commentsListLiveData.setValue(commentsList));
    }

    public void addNewComment(String postId, String comment)
    {
        String userId = Model.instance.getConnectedUserId();
        Date date = new Date();
        Model.instance.addComment(new Comment(
                comment,
                userId,
                postId,
                false,
                date));
    }

    public static void deleteItem(Comment comment)
    {
            comment.setWasDeleted(true);
            Model.instance.updateComment(comment);
    }

    public static boolean checkDelete(Comment comment)
    {
        return comment.getUserCreatorId().equals(Model.instance.getConnectedUserId());
    }
}
