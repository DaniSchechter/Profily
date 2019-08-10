package com.example.profily.Model;

  import android.util.Log;

  import com.example.profily.Model.Schema.Comment.Comment;
  import com.example.profily.Model.Schema.Post.Post;
  import com.google.firebase.firestore.FirebaseFirestore;
  import com.google.firebase.firestore.FirebaseFirestoreSettings;
  import com.google.firebase.firestore.Query;
  import com.google.firebase.firestore.QueryDocumentSnapshot;

  import java.util.LinkedList;

public class ModelFireBase {

    FirebaseFirestore db;

    public ModelFireBase() {
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false).build();
        db.setFirestoreSettings(settings);
    }

    // =========== POSTS ===========

    public void getAllPosts(final int numOfPosts, final Model.GetAllPostsListener listener) {
        db.collection("posts").orderBy("createdDate", Query.Direction.DESCENDING).limit(numOfPosts).addSnapshotListener(
            (queryDocumentSnapshots, fireBaseException) -> {
                LinkedList<Post> data = new LinkedList<>();
                if (fireBaseException != null) {
                    listener.onComplete(data);
                    return;
                }
                if (queryDocumentSnapshots != null) {
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        Post post = doc.toObject(Post.class);
                        data.add(post);
                    }
                    listener.onComplete(data);
                }
            }
        );
    }


    public void addPost(Post post, final Model.AddPostListener listener) {
        db.collection("posts")
                .document(post.getPostId())
                .set(post)
                .addOnCompleteListener( task -> listener.onComplete(task.isSuccessful()));
    }

    // =========== COMMENTS ===========

    public void getAllComments(final String postId, final int numOfComments, final Model.GetAllCommentsListener listener) {
        db.collection("comments").whereEqualTo("postId", postId).orderBy("createdDate", Query.Direction.DESCENDING).limit(numOfComments).addSnapshotListener(
                (queryDocumentSnapshots, fireBaseException) -> {
                    LinkedList<Comment> data = new LinkedList<>();
                    if (fireBaseException != null) {
                        listener.onComplete(data);
                        return;
                    }
                    if (queryDocumentSnapshots != null) {
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            Comment comment = doc.toObject(Comment.class);
                            data.add(comment);
                        }
                        listener.onComplete(data);
                    }
                }
        );
    }


    public void addComment(Comment comment, final Model.AddCommentListener listener) {
        db.collection("comments")
                .document(comment.getCommentId())
                .set(comment)
                .addOnCompleteListener( task -> listener.onComplete(task.isSuccessful()));
    }
}
