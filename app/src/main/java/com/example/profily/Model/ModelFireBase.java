package com.example.profily.Model;

  import android.graphics.Bitmap;
  import android.graphics.BitmapFactory;
  import android.net.Uri;
  import android.util.Log;
  import com.example.profily.Model.Schema.Comment.Comment;
  import com.example.profily.Model.Schema.Like.Like;
  import com.example.profily.Model.Schema.Notification.Notification;
  import com.example.profily.Model.Schema.Post.Post;
  import com.example.profily.Model.Schema.User.User;
  import com.google.android.gms.tasks.OnFailureListener;
  import com.google.android.gms.tasks.OnSuccessListener;
  import com.google.firebase.auth.FirebaseAuth;
  import com.google.firebase.auth.FirebaseUser;
  import com.google.firebase.firestore.DocumentReference;
  import com.google.firebase.firestore.FirebaseFirestore;
  import com.google.firebase.firestore.FirebaseFirestoreSettings;
  import com.google.firebase.firestore.Query;
  import com.google.firebase.firestore.QueryDocumentSnapshot;
  import com.google.firebase.storage.FirebaseStorage;
  import com.google.firebase.storage.StorageReference;
  import com.google.firebase.storage.UploadTask;

  import java.io.ByteArrayOutputStream;
  import java.util.LinkedList;

public class ModelFireBase {

    FirebaseFirestore db;
    FirebaseAuth mAuth;

    public ModelFireBase() {
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false).build();
        db.setFirestoreSettings(settings);

        mAuth = FirebaseAuth.getInstance();
    }

    // =========== POSTS ===========

    public void getAllPosts(final Model.GetAllPostsListener listener) {
        db.collection("posts")
                .orderBy("createdDate", Query.Direction.DESCENDING)
                .addSnapshotListener(
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

    public void getPostById(final String postId, final Model.GetPostByIdListener listener) {
        db.collection("posts")
                .whereEqualTo("postId", postId).addSnapshotListener(
                (queryDocumentSnapshots, fireBaseException) -> {
                    Post post = new Post();
                    if (fireBaseException != null) {
                        listener.onComplete(post);
                        return;
                    }
                    if (queryDocumentSnapshots != null && queryDocumentSnapshots.getDocuments().size() > 0) {
                        post = queryDocumentSnapshots.getDocuments().get(0).toObject(Post.class);
                        listener.onComplete(post);
                    }
                }
        );
    }


    public void addPost(Post post, final Model.AddPostListener listener) {
        String postId = db.collection("posts").document().getId();
        post.setPostId(postId);
        db.collection("posts")
                .document(post.getPostId())
                .set(post)
                .addOnCompleteListener(task -> listener.onComplete(task.isSuccessful()));
    }

    public void updatePost(Post post){
        db.collection("posts")
                .document(post.getPostId())
                .set(post);
    }

    // =========== LIKES ===========

    public void likeByUser(String postId, String userId, Model.FindLikeListener listener) {
        db.collection("likes")
                .whereEqualTo("postId", postId)
                .whereEqualTo("likingUserId", userId)
                .addSnapshotListener(
                        (queryDocumentSnapshots, fireBaseException) -> {
                                if (fireBaseException != null) {
                                    Log.d("TAG", "ERROR: " + fireBaseException.toString());
                                    listener.onComplete(null);
                                    return;
                                }
                                if (queryDocumentSnapshots != null) {
                                    if (queryDocumentSnapshots.size() == 0) {
                                        Log.d("TAG", "Didn't find any like matching the params");
                                        listener.onComplete(null);
                                        return;
                                    }
                                    if (queryDocumentSnapshots.size() > 1) {
                                        Log.w("TAG",
                                                "found more than one like record for userId " + userId
                                                        + ", postId " + postId);
                                        listener.onComplete(null);
                                        return;
                                    }

                                    // Will run only once
                                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                        Like like = doc.toObject(Like.class);
                                        listener.onComplete(like.getLikeId());
                                        return;
                                    }
                                }
                        }
                );
    }

    public void getNumOfLikes(String postId, Model.GetNumberOfLikesListener listener){
        db.collection("likes")
                .whereEqualTo("postId", postId)
                .addSnapshotListener((queryDocumentSnapshots, fireBaseException) -> {
                    if (fireBaseException != null) {
                        listener.onComplete(0);
                        return;
                    }
                    if (queryDocumentSnapshots != null) {
                        listener.onComplete(queryDocumentSnapshots.getDocuments().size());
                    }
                });

    }

    public void like(String postId, String userId, Model.LikeOperationListener listener) {
        String likeId = db.collection("likes").document().getId();
        db.collection("likes")
                .document(likeId)
                .set(new Like(likeId, postId, userId))
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        listener.onComplete(null);
                        return;
                    }
                    listener.onComplete(likeId);
                });
    }

    public void unlike(String likeId, Model.UnlikeOperationListener listener) {
        db.collection("likes")
                .document(likeId)
                .delete()
                .addOnCompleteListener(task -> {
                    listener.onComplete(task.isSuccessful());
                });
    }

    // =========== COMMENTS ===========

    public void getAllComments(final String postId, final Model.GetAllCommentsListener listener) {
        db.collection("comments").whereEqualTo("postId", postId)
                .orderBy("createdDate", Query.Direction.DESCENDING)
                .addSnapshotListener(
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
        String commentId = db.collection("comments").document().getId();
        comment.setCommentId(commentId);
        db.collection("comments")
                .document(comment.getCommentId())
                .set(comment)
                .addOnCompleteListener(task -> listener.onComplete(task.isSuccessful()));

    }

    public void updateComment(Comment comment){
        db.collection("comments")
                .document(comment.getCommentId())
                .set(comment);
    }


    // =========== USERS ===========

    public void getAllUserByName(String username, final Model.GetAllUsersByNameListener listener){
        db.collection("users")
                .whereGreaterThanOrEqualTo("username", username)
                .whereLessThanOrEqualTo("username", username+'\uf8ff')
                .addSnapshotListener(
                (queryDocumentSnapshots, fireBaseException) -> {
                    LinkedList<User> data = new LinkedList<>();
                    if (fireBaseException != null) {
                        listener.onComplete(data);
                        return;
                    }
                    if (queryDocumentSnapshots != null) {
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            User user = doc.toObject(User.class);
                            data.add(user);
                        }
                        listener.onComplete(data);
                    }
                }
        );
    }


    public String getUserById ()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return null;
        }
        return user.getUid();
    }

    public void getUserById(String userId, final Model.GetUserByIdListener listener)
    {
        db.collection("users")
                .whereEqualTo("userId", userId).addSnapshotListener(
                (queryDocumentSnapshots, fireBaseException) -> {
                    if (fireBaseException != null) {
                        listener.onComplete(null);
                        return;
                    }
                    if (queryDocumentSnapshots != null && queryDocumentSnapshots.getDocuments().size() > 0) {
                        User user = queryDocumentSnapshots.getDocuments().get(0).toObject(User.class);
                        listener.onComplete(user);
                    }
                }
        );
    }

    public void logOut () {
        mAuth.signOut();
    }

    // =========== NOTIFICATIONS ===========

    public void getAllNotifications(final String userId, final Model.GetAllNotificationsListener listener) {
        db.collection("notifications")
                .whereEqualTo("effectedUserId", userId)
                .orderBy("actionDateTime", Query.Direction.DESCENDING)
                .addSnapshotListener(
                        (queryDocumentSnapshots, fireBaseException) -> {
                                LinkedList<Notification> data = new LinkedList<>();
                                if (fireBaseException != null) {
                                    listener.onComplete(data);
                                    return;
                                }
                                if (queryDocumentSnapshots != null) {
                                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                        Notification notification = doc.toObject(Notification.class);
                                        data.add(notification);
                                    }
                                    listener.onComplete(data);
                                }
                        }
                );
    }


    public void addNotification(Notification notification, final Model.AddNotificationListener listener) {
        String notificationId = db.collection("notifications").document().getId();
        notification.setNotificationId(notificationId);
        db.collection("notifications")
                .document(notification.getNotificationId())
                .set(notification)
                .addOnCompleteListener(task -> listener.onComplete(task.isSuccessful()));

    }

    // =========== PROFILE ===========

    public void getAllUserPosts(String userId, final Model.GetAllUserPostsListener listener) {
        db.collection("posts")
                .whereEqualTo("userCreatorId", userId)
                .orderBy("createdDate", Query.Direction.DESCENDING)
                .addSnapshotListener(
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

    public void addUser(User user, final Model.AddUserListener listener) {
        db.collection("users")
                .document(user.getUserId())
                .set(user)
                .addOnCompleteListener(task -> listener.onComplete(task.isSuccessful()));
    }

    // =========== FIREBASE STORAGE ===========

    public void uploadImage(Bitmap imageBmp, String collection_name, final Model.SaveImageListener listener){
        StorageReference imagesRef = FirebaseStorage.getInstance().getReference().child("images").child(collection_name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                listener.onFailure();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if (taskSnapshot.getMetadata() == null || taskSnapshot.getMetadata().getReference() == null)
                    return;

                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(
                        uri -> listener.onSuccess(uri.toString())
                );
            }
        });
    }

}
