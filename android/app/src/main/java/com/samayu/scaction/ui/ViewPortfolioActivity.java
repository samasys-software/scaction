 package com.samayu.scaction.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.samayu.scaction.R;
import com.samayu.scaction.dto.PortfolioPicture;
import com.samayu.scaction.service.SCAClient;
import com.samayu.scaction.service.SessionInfo;
import com.samayu.scaction.ui.adapter.ImageHolderAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

 public class ViewPortfolioActivity extends SCABaseActivity {
     LinearLayout viewThumbnailPicture,viewPortfolioPicture,viewMediumPicture;
     RecyclerView thumbnailListView,mediumListView,portfolioListView;
     Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_portfolio);
        context=this;

        LinearLayoutManager thumbnailLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager mediumLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager portfolioLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);






        thumbnailListView= (RecyclerView) findViewById(R.id.viewPortfolioThumbnail);
        thumbnailListView.setLayoutManager(thumbnailLayoutManager);

        mediumListView= (RecyclerView) findViewById(R.id.viewPortfolioMedium);
        mediumListView.setLayoutManager(mediumLayoutManager);

        portfolioListView= (RecyclerView) findViewById(R.id.viewPortfolioImage);
        portfolioListView.setLayoutManager(portfolioLayoutManager);

        long userId=getIntent().getExtras().getLong("userId");

        Call<List<PortfolioPicture>> getAllPortfolioPictureDTOCall= new SCAClient().getClient().findAllPortfolio(userId);
        getAllPortfolioPictureDTOCall.enqueue(new Callback<List<PortfolioPicture>>() {
            @Override
            public void onResponse(Call<List<PortfolioPicture>> call, Response<List<PortfolioPicture>> response) {
                List<PortfolioPicture> portfolioPictures=response.body();
                setImagesInAdapter(portfolioPictures);
            }

            @Override
            public void onFailure(Call<List<PortfolioPicture>> call, Throwable t) {


            }
        });






        //CreatePortfolioActivity createPortfolioActivity=new CreatePortfolioActivity();
       //  getAllPortfolioImages();
    }

     public void setImagesInAdapter(List<PortfolioPicture> response){

         List<PortfolioPicture> thumbnailImages=new ArrayList<PortfolioPicture>();
         List<PortfolioPicture> mediumImages=new ArrayList<PortfolioPicture>();
         List<PortfolioPicture> portfolioImages=new ArrayList<PortfolioPicture>();
         for(PortfolioPicture portfolioPicture:response)
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
         adapter= new ImageHolderAdapter(ViewPortfolioActivity.this, thumbnailImages,false);
         thumbnailListView.setVisibility(View.VISIBLE);
         thumbnailListView.setAdapter(adapter);

         adapter = new ImageHolderAdapter(ViewPortfolioActivity.this, mediumImages,false);
         mediumListView.setVisibility(View.VISIBLE);
         mediumListView.setAdapter(adapter);

         adapter = new ImageHolderAdapter(ViewPortfolioActivity.this, portfolioImages,false);
         portfolioListView.setVisibility(View.VISIBLE);
         portfolioListView.setAdapter(adapter);

     }


}
