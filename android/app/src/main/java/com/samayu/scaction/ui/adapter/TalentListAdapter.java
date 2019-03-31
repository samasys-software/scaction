package com.samayu.scaction.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.samayu.scaction.BuildConfig;
import com.samayu.scaction.R;
import com.samayu.scaction.dto.PortfolioPicture;
import com.samayu.scaction.dto.SelectedCastingCallRoles;
import com.samayu.scaction.dto.User;
import com.samayu.scaction.dto.UserRole;
import com.samayu.scaction.service.SCAClient;
import com.samayu.scaction.service.SessionInfo;
import com.samayu.scaction.ui.CreatePortfolioActivity;
import com.samayu.scaction.ui.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NandhiniGovindasamy on 3/13/19.
 */

public class TalentListAdapter extends RecyclerView.Adapter <com.samayu.scaction.ui.adapter.TalentListAdapter.TalentViewHolder>{
    List<User> userList;
    User user;
    Context context;
    LayoutInflater inflater;



    public class TalentViewHolder extends RecyclerView.ViewHolder {
        public TextView talentUserName;
        public ImageView talentThumbnailImage;
        public CardView talentCardView;

        public TalentViewHolder(View view) {
            super(view);
            talentThumbnailImage = (ImageView) view.findViewById(R.id.talentThumbnailImage);
            talentUserName = (TextView) view.findViewById(R.id.talentUserName);
            talentCardView = (CardView) view.findViewById(R.id.cardview);
        }
    }

    public TalentListAdapter(Activity mainActivity, List<User> users) {

        // TODO Auto-generated constructor stub
        userList = users;

        context = mainActivity;
        //  System.out.println(context);

        inflater = LayoutInflater.from(context);
    }

    @Override
    public com.samayu.scaction.ui.adapter.TalentListAdapter.TalentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.talent_images_list, parent, false);


        return new com.samayu.scaction.ui.adapter.TalentListAdapter.TalentViewHolder(itemView) ;

    }

    @Override
    public void onBindViewHolder(final com.samayu.scaction.ui.adapter.TalentListAdapter.TalentViewHolder holder, final int position) {
       final User user=userList.get(position);
       // holder.talentCardView.setVisibility(View.GONE);

        Call<List<PortfolioPicture>> getAllPortfolioPictureDTOCall= new SCAClient().getClient().findAllPortfolio(user.getUserId());
        getAllPortfolioPictureDTOCall.enqueue(new Callback<List<PortfolioPicture>>() {
            @Override
            public void onResponse(Call<List<PortfolioPicture>> call, Response<List<PortfolioPicture>> response) {

                List<PortfolioPicture> portfolioPictureList=response.body();
                if(portfolioPictureList!=null) {


                    for (PortfolioPicture portfolioPicture : portfolioPictureList) {
                        if (portfolioPicture.getType() == 0) {
                            holder.talentCardView.setVisibility(View.VISIBLE);
                            Picasso.with(context)
                                    .load(BuildConfig.SERVER_URL + "user/downloadFile/" + user.getUserId() + "/" + portfolioPicture.getFileName())
                                    .into(holder.talentThumbnailImage);

                            holder.talentUserName.setText(user.getScreenName());

                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<List<PortfolioPicture>> call, Throwable t) {


            }
        });


        List<PortfolioPicture> portfolioPictures=SessionInfo.getInstance().getPortfolioPictureList();





    }

    @Override
    public int getItemCount() {
        return userList.size();
    }


}
