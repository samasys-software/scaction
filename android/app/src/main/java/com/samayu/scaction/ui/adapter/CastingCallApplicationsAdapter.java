package com.samayu.scaction.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.samayu.scaction.R;
import com.samayu.scaction.dto.CastingCallApplication;
import com.samayu.scaction.dto.ProfileType;
import com.samayu.scaction.dto.SelectedCastingCallRoles;
import com.samayu.scaction.dto.User;
import com.samayu.scaction.service.SessionInfo;
import com.samayu.scaction.ui.ApplyCastingCallActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by NandhiniGovindasamy on 11/26/18.
 */
public class CastingCallApplicationsAdapter extends RecyclerView.Adapter <CastingCallApplicationsAdapter.CastingCallsApplicationsViewHolder>{
    List<CastingCallApplication> castingCallApplications;

    int count = 0;
    Context context;
    LayoutInflater inflater;

    ImageButton rmv, update, expiration;


    // List<SelectedCastingCallRoles> selectedCastingCallRolesList=new ArrayList<SelectedCastingCallRoles>();

    public class CastingCallsApplicationsViewHolder extends RecyclerView.ViewHolder {
        public TextView castingCallApplicationsName,castingCallApplicationsGenderAndRole;
        public ImageView profilePic;
        public CastingCallsApplicationsViewHolder(View view) {
            super(view);
            profilePic = (ImageView) view.findViewById(R.id.CastingCallApplicationPic);
            castingCallApplicationsName = (TextView) view.findViewById(R.id.CastingCallApplicationName);
            castingCallApplicationsGenderAndRole = (TextView) view.findViewById(R.id.CastingCallApplicationGenderAndRole);

        }
    }

    public CastingCallApplicationsAdapter(ApplyCastingCallActivity mainActivity, List<CastingCallApplication> castingCallApplicationList) {

        // TODO Auto-generated constructor stub
        castingCallApplications = castingCallApplicationList;


        context = mainActivity;
        //  System.out.println(context);

        inflater = LayoutInflater.from(context);
    }


    @Override
    public CastingCallApplicationsAdapter.CastingCallsApplicationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.casting_calls_applications_list, parent, false);


        return new CastingCallApplicationsAdapter.CastingCallsApplicationsViewHolder(itemView) ;

    }

    @Override
    public void onBindViewHolder(final CastingCallsApplicationsViewHolder holder, final int position) {
            final CastingCallApplication castingCallApplication = castingCallApplications.get(position);
            String url=null;
//        if(SessionInfo.getInstance().getLoginType()==0){
//           url= "https://graph.facebook.com/"+castingCallApplication.getUser().getFbUser()+"/picture?height=40&weight=40";
//        }
//        else if(SessionInfo.getInstance().getLoginType()==1){
            url=castingCallApplication.getUser().getProfilePic();
//        }
        try{
            Picasso.with(context).load(url)
                    .into(holder.profilePic);

        }
        catch (Exception e)
        {

        }

        holder.castingCallApplicationsName.setText(castingCallApplication.getUser().getFbName());

        String gender="";
        if(castingCallApplication.getUser().getGender()==0)
        {
            gender="Male";
        }
        else if(castingCallApplication.getUser().getGender()==1){
            gender="Female";

        }
        else{
            gender="Other";
        }

        String role="";

        List<ProfileType> profileTypes= SessionInfo.getInstance().getProfileTypes();
        for(ProfileType profileType:profileTypes){
            if(profileType.getId()==castingCallApplication.getRoleId()){
                role=profileType.getName();
            }
        }
        holder.castingCallApplicationsGenderAndRole.setText(gender+" - "+role);





    }

    @Override
    public int getItemCount() {
        return castingCallApplications.size();
    }
}
