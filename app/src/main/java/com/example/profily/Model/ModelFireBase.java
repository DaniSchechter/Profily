package com.example.profily.Model;

  import androidx.annotation.Nullable;

  import com.example.profily.Model.Schema.Post.Post;
  import com.google.firebase.firestore.EventListener;
  import com.google.firebase.firestore.FirebaseFirestore;
  import com.google.firebase.firestore.FirebaseFirestoreException;
  import com.google.firebase.firestore.FirebaseFirestoreSettings;
  import com.google.firebase.firestore.QueryDocumentSnapshot;
  import com.google.firebase.firestore.QuerySnapshot;

  import java.util.LinkedList;

public class ModelFireBase {

    FirebaseFirestore db;

    public ModelFireBase() {
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false).build();
        db.setFirestoreSettings(settings);
    }


    public void getAllPosts(final int numOfPosts, final Model.GetAllPostsListener listener) {
        db.collection("posts").startAt().limit(numOfPosts).addSnapshotListener(
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
}
