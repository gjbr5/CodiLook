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
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private Application app;

    private StorageRepository() {
    }

    public static StorageRepository getInstance(Application app) {
        SingletonHolder.instance.init(app);
        return SingletonHolder.instance;
    }

    private void init(Application app) {
        this.app = app;
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

    public void getList(Codi codi, OnSuccessListener<ListResult> onSuccessListener) {
        StorageReference ref = storage.getReference(codi.name());
        ref.listAll().addOnSuccessListener(onSuccessListener);
    }

    public void getReference(OnSuccessListener<String> onSuccessListener) {
        StorageReference ref = storage.getReference("/reference.txt");
        ref.getBytes(10 * 1024 * 1024)
                .addOnSuccessListener(bytes -> onSuccessListener.onSuccess(new String(bytes)));
    }

    public void getUpdateList(OnSuccessListener<String> onSuccessListener) {
        Tasks.call(() -> "버전 1.0.0\r\n정식 출시").addOnSuccessListener(onSuccessListener);
    }

    private static class SingletonHolder {
        private static final StorageRepository instance = new StorageRepository();
    }
}
