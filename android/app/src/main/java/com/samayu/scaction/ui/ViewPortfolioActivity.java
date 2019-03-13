 package com.samayu.scaction.ui;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.samayu.scaction.R;
import com.samayu.scaction.dto.PortfolioPicture;
import com.samayu.scaction.service.SCAClient;
import com.samayu.scaction.service.SessionInfo;

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

        //CreatePortfolioActivity createPortfolioActivity=new CreatePortfolioActivity();
       //  getAllPortfolioImages();
    }


}
