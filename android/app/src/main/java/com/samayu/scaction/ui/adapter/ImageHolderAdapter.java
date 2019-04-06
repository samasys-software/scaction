package com.samayu.scaction.ui.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.samayu.scaction.BuildConfig;
import com.samayu.scaction.R;
import com.samayu.scaction.dto.PortfolioPicture;
import com.samayu.scaction.service.SCAClient;
import com.samayu.scaction.service.SessionInfo;
import com.samayu.scaction.ui.CreatePortfolioActivity;
import com.samayu.scaction.ui.SCABaseActivity;
import com.samayu.scaction.ui.ViewPortfolioActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NandhiniGovindasamy on 12/18/18.
 */

public class ImageHolderAdapter extends RecyclerView.Adapter <ImageHolderAdapter.ImageViewHolder>{
    List<PortfolioPicture> portfolioPictures;
    Context context;
    LayoutInflater inflater;
    String imageUrl=null;
    boolean edit;
    long userId;
    ProgressDialog progressDialog;

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView portfolioImages,remove;
        public ImageViewHolder(View view) {
            super(view);
            portfolioImages = (ImageView) view.findViewById(R.id.portfolioImages);
            remove = (ImageView) view.findViewById(R.id.remove);
        }
    }

    public ImageHolderAdapter(Activity mainActivity,long userId1, List<PortfolioPicture> portfolioPicturesList,boolean edit1) {

        // TODO Auto-generated constructor stub
        portfolioPictures = portfolioPicturesList;

        edit=edit1;
        userId=userId1;


        context = mainActivity;
       progressDialog=new ProgressDialog(context);
       progressDialog.setIndeterminate(true);

        //  System.out.println(context);

        inflater = LayoutInflater.from(context);
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_holder_list, parent, false);


        return new ImageViewHolder(itemView) ;

    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        final PortfolioPicture portfolioPicture = portfolioPictures.get(position);
        if(!edit){
            holder.remove.setVisibility(View.GONE);
        }


        //String imageUrl=BuildConfig.SERVER_URL+"Users/NandhiniGovindasamy/Documents/scactionimages/14/"+portfolioPicture.getFileName();
        //String imageUrl=BuildConfig.SERVER_URL+"Users/NandhiniGovindasamy/Documents/scactionimages/14/sample.jpg";

        Picasso.with(context)
                .load(BuildConfig.SERVER_URL+"user/downloadFile/"+userId+"/"+portfolioPicture.getFileName())
                .into(holder.portfolioImages);

        holder.portfolioImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    downloadImage(portfolioPicture.getFileName());

            }
        });

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, R.style.AlertTheme);
                    //alertDialog.setTitle();
                alertDialog.setCancelable(true);
//                    LayoutInflater inflater1 = MainActivity.this.getLayoutInflater();
                View diaView = inflater.inflate(R.layout.alert_base, null);
                TextView sortTextHint=(TextView) diaView.findViewById(R.id.sortTextHint);
                sortTextHint.setText("Remove This Photo From Your Portfolio?");
                alertDialog.setView(diaView);

                alertDialog.setNegativeButton("CANCEL",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                    // Write your code here to execute after dialog
                                dialog.cancel();

                            }
                        });

                alertDialog.setPositiveButton("Ok",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    deleteImage(portfolioPicture.getId());

                                }

                            });
//alertDialog.show();
                    final AlertDialog registerUser = alertDialog.create();
                    registerUser.show();

                }
            });




    }

    @Override
    public int getItemCount() {
        return portfolioPictures.size();
    }
    public void deleteImage(long portfolioId){
        progressDialog.show();
        Call<List<PortfolioPicture>> deletePortfolioDTOCall= new SCAClient().getClient().deletePicture(SessionInfo.getInstance().getUser().getUserId(),portfolioId);
        deletePortfolioDTOCall.enqueue(new Callback<List<PortfolioPicture>>() {
            @Override
            public void onResponse(Call<List<PortfolioPicture>> call, Response<List<PortfolioPicture>> response) {
                ((CreatePortfolioActivity)context). setImagesInAdapter(response.body(),SessionInfo.getInstance().getUser().getUserId());
                progressDialog.dismiss();

            }

            @Override
            public void onFailure(Call<List<PortfolioPicture>> call, Throwable t) {
                progressDialog.dismiss();


            }
        });
    }

    public void downloadImage(String fileName){
        Call<ResponseBody> downloadPortfolioDTOCall= new SCAClient().getClient().downloadFile(fileName,SessionInfo.getInstance().getUser().getUserId());
        downloadPortfolioDTOCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(context,"Download Successfully",Toast.LENGTH_LONG).show();

                //imageUrl=response.body();
                //boolean writeToDisk = writeToDisk(response.body());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {


            }
        });
    }
    private boolean writeToDisk(ResponseBody body) {
        try { File mediaStorageDir = new File(
                Environment.DIRECTORY_PICTURES,"Portfolio");
//                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
//                "ProfileImage");

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists()) {

                if (!mediaStorageDir.mkdirs()) {
                    Log.e("ProfileImage", "Oops! Failed create "
                            + "ProfileImage" + " directory");
                }
            }
            File futureStudioIconFile = new File(mediaStorageDir.getPath() + File.separator
                    + "userImage.png");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("NO", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }


}

