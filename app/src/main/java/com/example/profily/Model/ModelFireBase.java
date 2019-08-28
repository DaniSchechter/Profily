package com.example.profily.Model;

import android.graphics.Bitmap;
import android.net.Uri;

import com.example.profily.Model.Schema.Comment.Comment;
import com.example.profily.Model.Schema.Notification.Notification;
import com.example.profily.Model.Schema.Post.Post;
import com.example.profily.Model.Schema.User.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Date;
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

    public void getAllPosts(final int numOfPosts, final Model.GetAllPostsListener listener) {
        db.collection("posts").whereEqualTo("wasDeleted", false).orderBy("createdDate", Query.Direction.DESCENDING).limit(numOfPosts).addSnapshotListener(
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
                    if (queryDocumentSnapshots != null) {
                        post = queryDocumentSnapshots.getDocuments().get(0).toObject(Post.class);
                        listener.onComplete(post);
                    }
                }
        );
    }


    public void addPost(Post post, final Model.AddPostListener listener) {
        db.collection("posts")
                .document(post.getPostId())
                .set(post)
                .addOnCompleteListener(task -> listener.onComplete(task.isSuccessful()));
    }

    public void uploadImage(Bitmap imageBmp , final Model.SaveImageListener listener)
    {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        Date d = new Date();
        StorageReference imagesRef = storage.getReference().child("images").child("image_" + d.getTime() + "jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception exception) {
                listener.onFail();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        listener.onComplete(uri.toString());

                    }
                });

            }
        });
    }

    // =========== COMMENTS ===========

    public void getAllComments(final String postId, final int numOfComments, final Model.GetAllCommentsListener listener) {
        db.collection("comments").whereEqualTo("postId", postId).whereEqualTo("wasDeleted", false).orderBy("createdDate", Query.Direction.DESCENDING).limit(numOfComments).addSnapshotListener(
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

    public void getUserById(String userId, final Model.GetConnectedUserListener listener)
    {
        db.collection("users")
                .whereEqualTo("userId", userId).addSnapshotListener(
                (queryDocumentSnapshots, fireBaseException) -> {
                    User user = new User();
                    if (fireBaseException != null) {
                        listener.onComplete(user);
                        return;
                    }
                    if (queryDocumentSnapshots != null) {
                        user = queryDocumentSnapshots.getDocuments().get(0).toObject(User.class);
                        listener.onComplete(user);
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

    public void logOut () {
        mAuth.signOut();
    }

    // =========== NOTIFICATIONS ===========

    public void getAllNotifications(final String userId, final int numOfNotifications, final Model.GetAllNotificationsListener listener) {
        db.collection("notifications")
                .whereEqualTo("effectedUserId", userId)
                .orderBy("actionDateTime", Query.Direction.DESCENDING)
                .limit(numOfNotifications)
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
        db.collection("notifications")
                .document(notification.getNotificationId())
                .set(notification)
                .addOnCompleteListener(task -> listener.onComplete(task.isSuccessful()));

    }

    // =========== PROFILE ===========

    public void getAllUserPosts(String userId, final int numOfPosts, final Model.GetAllUserPostsListener listener) {
        db.collection("posts").whereEqualTo("userCreatorId", userId).orderBy("createdDate", Query.Direction.DESCENDING).limit(numOfPosts).addSnapshotListener(
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
}
