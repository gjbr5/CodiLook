package kr.ceo.codilook.model;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

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

    public Bitmap getImageFromFile(Codi codi, int number) throws FileNotFoundException {
        String filename = codi.name() + number + ".png";
        FileInputStream fis = app.openFileInput(filename);
        return BitmapFactory.decodeStream(fis);
    }

    public FileDownloadTask getImageFromFirebase(Codi codi, int number) {
        String filename = codi.name() + number;
        String path = codi.name() + "/" + filename + ".png";
        StorageReference ref = storage.getReference(path);
        File localFile = new File(app.getFilesDir(), filename + ".png");
        return ref.getFile(localFile);
    }
}
