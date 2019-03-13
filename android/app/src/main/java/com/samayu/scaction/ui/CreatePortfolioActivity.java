package com.samayu.scaction.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.samayu.scaction.R;
import com.samayu.scaction.dto.PortfolioPicture;
import com.samayu.scaction.service.SCAClient;
import com.samayu.scaction.service.SessionInfo;

import com.ipaulpro.afilechooser.utils.FileUtils;
import com.samayu.scaction.ui.adapter.ImageHolderAdapter;


import java.io.ByteArrayOutputStream;
import java.io.File;
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
    private final static int REQUEST_PERMISSION_WRITE_EXTERNAL = 9;
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
    RecyclerView thumbnailListView,mediumListView,portfolioListView;
    TextView preview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_portfolio);
        context = this;
        LinearLayoutManager thumbnailLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager mediumLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager portfolioLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        preview = (TextView) findViewById(R.id.preview);
        choosePortfolioPicture = (LinearLayout) findViewById(R.id.choose_portfolio);
        chooseMediumPicture = (LinearLayout) findViewById(R.id.choose_medium);
        chooseThumbnailPicture = (LinearLayout) findViewById(R.id.choose_thumbnail);



        thumbnailListView= (RecyclerView) findViewById(R.id.addThumbnail);
        thumbnailListView.setLayoutManager(thumbnailLayoutManager);

        mediumListView= (RecyclerView) findViewById(R.id.addMedium);
        mediumListView.setLayoutManager(mediumLayoutManager);

        portfolioListView= (RecyclerView) findViewById(R.id.addPortfolio);
        portfolioListView.setLayoutManager(portfolioLayoutManager);

        getAllPortfolioImages();

        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CreatePortfolioActivity.this,ViewPortfolioActivity.class);
                startActivity(intent);
            }
        });
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
        if (requestCode == REQUEST_PERMISSION_WRITE_EXTERNAL) {
            if (grantResults.length > 0) {
                int grantResult = grantResults[0];
                if (grantResult == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(getApplicationContext(), "You denied write external storage permission.", Toast.LENGTH_LONG).show();
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
                        sendPortfolioToServer(currentUri,2);


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
               // setThumbnailImage(uri);

            }
            else if (requestCode == GET_MEDIUM_IMAGES) {
                Uri uri = intent.getData();
                sendPortfolioToServer(uri,1);
                //setMediumImages(uri);

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
                            String requirePermission[] = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
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
                setImagesInAdapter(response);

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

    public void getAllPortfolioImages()
    {
        Call<List<PortfolioPicture>> getAllPortfolioPictureDTOCall= new SCAClient().getClient().findAllPortfolio(SessionInfo.getInstance().getUser().getUserId());
        getAllPortfolioPictureDTOCall.enqueue(new Callback<List<PortfolioPicture>>() {
            @Override
            public void onResponse(Call<List<PortfolioPicture>> call, Response<List<PortfolioPicture>> response) {
                setImagesInAdapter(response);
            }

            @Override
            public void onFailure(Call<List<PortfolioPicture>> call, Throwable t) {


            }
        });
    }

    public void setImagesInAdapter(Response<List<PortfolioPicture>> response){
        List<PortfolioPicture> portfolioPictures=response.body();
        List<PortfolioPicture> thumbnailImages=new ArrayList<PortfolioPicture>();
        List<PortfolioPicture> mediumImages=new ArrayList<PortfolioPicture>();
        List<PortfolioPicture> portfolioImages=new ArrayList<PortfolioPicture>();
        for(PortfolioPicture portfolioPicture:portfolioPictures)
        {
            if(portfolioPicture.getType()==0){
                thumbnailImages.add(portfolioPicture);
            }
            else if(portfolioPicture.getType()==1){
                mediumImages.add(portfolioPicture);
            }
            else{
                portfolioImages.add(portfolioPicture);
            }
        }
        ImageHolderAdapter adapter;
        adapter= new ImageHolderAdapter(CreatePortfolioActivity.this, thumbnailImages);
        thumbnailListView.setVisibility(View.VISIBLE);
        thumbnailListView.setAdapter(adapter);

        adapter = new ImageHolderAdapter(CreatePortfolioActivity.this, mediumImages);
        mediumListView.setVisibility(View.VISIBLE);
        mediumListView.setAdapter(adapter);

        adapter = new ImageHolderAdapter(CreatePortfolioActivity.this, portfolioImages);
        portfolioListView.setVisibility(View.VISIBLE);
        portfolioListView.setAdapter(adapter);

    }

}






