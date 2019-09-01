package com.example.profily.Comments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.profily.Model.Model;
import com.example.profily.Model.Schema.Comment.Comment;
import com.example.profily.Model.Schema.Comment.CommentWrapper;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class CommentsViewModel extends ViewModel {
    private MutableLiveData<List<CommentWrapper>> commentsListLiveData;
    private List<CommentWrapper> commentsWrappersList;

    public CommentsViewModel() {
        commentsListLiveData = new MutableLiveData<>();
        commentsWrappersList = new LinkedList<>();
    }

    public void getComments(String postId){
        // Get all comments async
        Model.instance.getAllComments( postId, commentsList -> {
            commentsWrappersList.clear();

            for(Comment comment: commentsList) {

                // Initialize the Comment Wrapper with the comment itself
                CommentWrapper commentWrapper = new CommentWrapper(comment, null);
                commentsWrappersList.add(commentWrapper);

                // Get the username of the comment
                Model.instance.getUserById(comment.getUserCreatorId(), user ->  {
                    commentWrapper.setUser(user);
                    this.commentsListLiveData.setValue(commentsWrappersList);
                });
            }
        });
    }

    public LiveData<List<CommentWrapper>> getCommentsList() {
        return this.commentsListLiveData;
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

    public static void deleteItem(CommentWrapper comment)
    {
            comment.comment.setWasDeleted(true);
            Model.instance.updateComment(comment.comment);
    }

    public static boolean checkDelete(CommentWrapper comment)
    {
        return comment.comment.getUserCreatorId().equals(Model.instance.getConnectedUserId());
    }
}
