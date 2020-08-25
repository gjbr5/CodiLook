package kr.ceo.codilook.model;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.io.File;

import kr.ceo.codilook.model.fuzzy.Codi;

public class StorageRepository {
    private static StorageRepository instance = null;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private Application app;

    private StorageRepository(Application app) {
        this.app = app;
    }

    public static StorageRepository getInstance(Application app) {
        if (instance == null) {
            instance = new StorageRepository(app);
        }
        return instance;
    }

    public void getImage(StorageReference ref, OnSuccessListener<Bitmap> onSuccessListener) {
        final String filename = ref.getName();
        Task<Bitmap> task = Tasks.call(() -> BitmapFactory.decodeStream(app.openFileInput(filename)));
        task.addOnSuccessListener(onSuccessListener);
        task.addOnFailureListener(e -> {
            FileDownloadTask task1 = ref.getFile(new File(app.getFilesDir(), ref.getName()));
            task1.addOnSuccessListener(t -> {
                Task<Bitmap> task2 = Tasks.call(() -> BitmapFactory.decodeStream(app.openFileInput(filename)));
                task2.addOnSuccessListener(onSuccessListener);
            });
        });
    }

    public Task<ListResult> getList(Codi codi) {
        StorageReference ref = storage.getReference(codi.name());
        return ref.listAll();
    }
}
