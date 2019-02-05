package com.samayu.scaction.ui;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.samayu.scaction.R;
import com.samayu.scaction.dto.CastingCall;
import com.samayu.scaction.dto.PortfolioPicture;
import com.samayu.scaction.service.SCAClient;
import com.samayu.scaction.service.SessionInfo;

import com.ipaulpro.afilechooser.utils.FileUtils;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePortfolioActivity extends SCABaseActivity {








    private static final int TAKE_PICTURE = 1;
    private static final int GET_FROM_GALLERY = 2;
    private static final int GET_PORTFOLIO_IMAGES = 3;
    private static final int GET_THUMBNAILS_IMAGES = 4;
    private static final int GET_MEDIUM_IMAGES = 5;
    private static final int TAKE_PORTFOLIO_IMAGES = 6;
    private static final int TAKE_THUMBNAILS_IMAGES = 7;
    private static final int TAKE_MEDIUM_IMAGES = 8;
    private static final int PIC_CROP = 3;
    private final static int REQUEST_PERMISSION_READ_EXTERNAL = 2;
    private final static int REQUEST_PERMISSION_CAMERA = 1;
    private List<Uri> userSelectedImageUriList = new ArrayList<Uri>();
    private ImageView portfolioPic, portfolioPic1, portfolioPic2, portfolioPic3, portfolioPic4, portfolioPic5,portfolioPic6,portfolioPic7,portfolioPic8,portfolioPic9,portfolioPic10,thumbnailImages,mediumImages;
    private Uri selectedImage;
    LinearLayout choosePortfolioPicture,chooseThumbnailPicture,chooseMediumPicture;
    Context context;
    private String[] title = {
            "Camera",
            "Gallery",


    };
    int currentImageSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_portfolio);
        context = this;
        choosePortfolioPicture = (LinearLayout) findViewById(R.id.choose_portfolio);
        chooseMediumPicture = (LinearLayout) findViewById(R.id.choose_medium);
        chooseThumbnailPicture = (LinearLayout) findViewById(R.id.choose_thumbnail);
        portfolioPic1 = (ImageView) findViewById(R.id.portfoliopic1);
        portfolioPic2 = (ImageView) findViewById(R.id.portfoliopic2);
        portfolioPic3 = (ImageView) findViewById(R.id.portfoliopic3);
        portfolioPic4 = (ImageView) findViewById(R.id.portfoliopic4);
        portfolioPic5 = (ImageView) findViewById(R.id.portfoliopic5);
        portfolioPic6 = (ImageView) findViewById(R.id.portfoliopic6);
        portfolioPic7 = (ImageView) findViewById(R.id.portfoliopic7);
        portfolioPic8 = (ImageView) findViewById(R.id.portfoliopic8);
        portfolioPic9 = (ImageView) findViewById(R.id.portfoliopic9);
        portfolioPic10= (ImageView) findViewById(R.id.portfoliopic10);
        thumbnailImages= (ImageView) findViewById(R.id.addThumbnail);
        mediumImages= (ImageView) findViewById(R.id.addMedium);
        choosePortfolioPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
                 show(1);

            }
        });

        chooseThumbnailPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(2);

            }
        });

        chooseMediumPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show(3);

            }
        });


    }
        /* After user choose grant read external storage permission or not. */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_READ_EXTERNAL) {
            if (grantResults.length > 0) {
                int grantResult = grantResults[0];
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    setGalleryImage(currentImageSize);
                    // If user grant the permission then open choose image popup dialog.
                    //openPictureGallery();
                } else {
                    Toast.makeText(getApplicationContext(), "You denied read external storage permission.", Toast.LENGTH_LONG).show();
                }
            }
        }
        if (requestCode == REQUEST_PERMISSION_CAMERA) {
            if (grantResults.length > 0) {
                int grantResult = grantResults[0];
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    setCameraImage(currentImageSize);
                    // If user grant the permission then open choose image popup dialog.
                    //openPictureGallery();
                } else {
                    Toast.makeText(getApplicationContext(), "You denied  camera permission.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case TAKE_PICTURE: {
//                // If request is cancelled, the result arrays are empty.
//
//                return;
//            }
//            case GET_FROM_GALLERY:{
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    //
//
//                } else {
//                    Toast.makeText(context, "Sorry!You Have No Permission to Access Camera", Toast.LENGTH_LONG).show();
//                }
//                return;
//
//            }
//            // other 'case' lines to check for other
//            // permissions this app might request.
//        }
//    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (resultCode == RESULT_OK) {
            if (requestCode == GET_PORTFOLIO_IMAGES) {
                System.out.println("mydata" + intent.getClipData().getItemCount());
                for (int i = 0; i < intent.getClipData().getItemCount(); i++) {
                    Uri uri = intent.getClipData().getItemAt(i).getUri();
                    userSelectedImageUriList.add(i, uri);
                }

                if (userSelectedImageUriList != null) {
                    int size = userSelectedImageUriList.size();
//                    adapter = new ImageHolderAdapter(CreatePortfolioActivity.this, userSelectedImageUriList);
//                    listView.setAdapter(adapter);
                    for (int i = 0; i < size; i++) {
                        Uri currentUri = userSelectedImageUriList.get(i);
                        switch (i) {
                            case 0:
                                portfolioPic1.setImageURI(currentUri);
                                portfolioPic1.setVisibility(View.VISIBLE);
                                break;
                            case 1:
                                portfolioPic2.setImageURI(currentUri);
                                portfolioPic2.setVisibility(View.VISIBLE);
                                break;
                            case 2:
                                portfolioPic3.setImageURI(currentUri);
                                portfolioPic3.setVisibility(View.VISIBLE);
                                break;
                            case 3:
                                portfolioPic4.setImageURI(currentUri);
                                portfolioPic4.setVisibility(View.VISIBLE);
                                break;
                            case 4:
                                portfolioPic5.setImageURI(currentUri);
                                portfolioPic5.setVisibility(View.VISIBLE);
                                break;
                            case 5:
                                portfolioPic6.setImageURI(currentUri);
                                portfolioPic6.setVisibility(View.VISIBLE);
                                break;
                            case 6:
                                portfolioPic7.setImageURI(currentUri);
                                portfolioPic7.setVisibility(View.VISIBLE);
                                break;
                            case 7:
                                portfolioPic8.setImageURI(currentUri);
                                portfolioPic8.setVisibility(View.VISIBLE);
                                break;
                            case 8:
                                portfolioPic9.setImageURI(currentUri);
                                portfolioPic9.setVisibility(View.VISIBLE);
                                break;
                            case 9:
                                portfolioPic10.setImageURI(currentUri);
                                portfolioPic10.setVisibility(View.VISIBLE);
                                break;

                        }

                    }
                    // selectedImage = intent.getData();
                    //imageView.setImageURI(selectedImage);
                }
            }
            else if (requestCode == TAKE_MEDIUM_IMAGES) {
                Bitmap photo = (Bitmap) intent.getExtras().get("data");
                mediumImages.setImageBitmap(photo);
                mediumImages.setVisibility(View.VISIBLE);
                //setMediumImages(getImageUri(context,photo));
                //textView.setImageBitmap(photo);

            }
            else if(requestCode==TAKE_THUMBNAILS_IMAGES)
            {
                //Uri bmp=intent.getData();
                Bitmap photo = (Bitmap) intent.getExtras().get("data");
                thumbnailImages.setImageBitmap(photo);
                thumbnailImages.setVisibility(View.VISIBLE);
                //setThumbnailImage(getImageUri(context,photo));

            }
            else if (requestCode == GET_THUMBNAILS_IMAGES) {
                Uri uri = intent.getData();
                sendPortfolioToServer(uri,0);
                setThumbnailImage(uri);

            }
            else if (requestCode == GET_MEDIUM_IMAGES) {
                Uri uri = intent.getData();
                setMediumImages(uri);

            }
        }

    }

    public void show(final int imageSize)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(CreatePortfolioActivity.this);
        builder.setItems(title, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                currentImageSize=imageSize;
                switch (which) {
                    case 0: {

                        int cameraPermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA);
                        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
                            String requirePermission[] = {Manifest.permission.CAMERA};
                            ActivityCompat.requestPermissions(CreatePortfolioActivity.this, requirePermission, REQUEST_PERMISSION_CAMERA);


                        } else {
                            //Toast.makeText(context, "Sorry!You Have No Permission to Access Camera", Toast.LENGTH_LONG).show();

                            setCameraImage(imageSize);

                        }
                        break;

                    }
                    case 1: {

                        int readExternalStoragePermission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
                        if (readExternalStoragePermission != PackageManager.PERMISSION_GRANTED) {
                            String requirePermission[] = {Manifest.permission.READ_EXTERNAL_STORAGE};
                            ActivityCompat.requestPermissions(CreatePortfolioActivity.this, requirePermission, REQUEST_PERMISSION_READ_EXTERNAL);

                        } else {

                            setGalleryImage(imageSize);
                        }
                        break;

                    }
                }
            }
        });


        //
        builder.show();

    }
    private Uri getImageUri(Context context,Bitmap bmp){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bmp, "Title", null);
        return Uri.parse(path);
    }
    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
//        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
//        if (cursor == null) {
//            return contentURI.getPath();
//        } else {
//            cursor.moveToFirst();
//            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
//            return cursor.getString(idx);
//        }

        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
    private void sendPortfolioToServer(Uri imageuri,int pictureType)
    {
        //String filePath = //mageuri.getPath();// getRealPathFromURIPath(imageuri, CreatePortfolioActivity.this);
        File file= FileUtils.getFile(this,imageuri);

        //Log.d("msg", "Filename " + file.getName());
        //RequestBody mFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        //RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        RequestBody mFile = RequestBody.create(
                MediaType.parse(getContentResolver().getType(imageuri)),
               file
        );

        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", "saple", mFile);

        Call<List<PortfolioPicture>> getPortfolioPictureDTOCall= new SCAClient().getClient().uploadPicture(SessionInfo.getInstance().getUser().getUserId(),pictureType,fileToUpload);
        getPortfolioPictureDTOCall.enqueue(new Callback<List<PortfolioPicture>>() {
            @Override
            public void onResponse(Call<List<PortfolioPicture>> call, Response<List<PortfolioPicture>> response) {
                List<PortfolioPicture> portfolioPictures=response.body();
                System.out.println(portfolioPictures.size());


            }




            @Override
            public void onFailure(Call<List<PortfolioPicture>> call, Throwable t) {


            }
        });

    }
    public void setThumbnailImage(Uri currentURI)
    {
        thumbnailImages.setImageURI(currentURI);
        thumbnailImages.setVisibility(View.VISIBLE);
    }
    public void setMediumImages(Uri currentURI)
    {
        mediumImages.setImageURI(currentURI);
        mediumImages.setVisibility(View.VISIBLE);
    }
    public void setCameraImage(int imageSize)

    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        switch (imageSize) {
            case 1:

                startActivityForResult(intent, TAKE_PORTFOLIO_IMAGES);
                break;
            case 2:

                startActivityForResult(intent, TAKE_THUMBNAILS_IMAGES);
                break;
            case 3:

                startActivityForResult(intent, TAKE_MEDIUM_IMAGES);
                break;

        }
    }
    public void setGalleryImage(int imageSize)
    {
        switch (imageSize){
            case 1:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.putExtra(Intent.EXTRA_RESULT_RECEIVER, 10);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), GET_PORTFOLIO_IMAGES);
                break;
            case 2:
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Thumbnails.INTERNAL_CONTENT_URI), GET_THUMBNAILS_IMAGES);
                break;
            case 3:
                startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_MEDIUM_IMAGES);
                break;
        }

    }

}






