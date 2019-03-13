package com.samayu.scaction.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.samayu.scaction.R;
import com.samayu.scaction.dto.CastingCallApplication;
import com.samayu.scaction.dto.ProfileType;
import com.samayu.scaction.service.SessionInfo;
import com.samayu.scaction.ui.ApplyCastingCallActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by NandhiniGovindasamy on 11/26/18.
 */

public class CastingCallApplicationsAdapter extends BaseAdapter {
    List<CastingCallApplication> castingCallApplications;

    int count = 0;
    Context context;
    LayoutInflater inflater;

    ImageButton rmv, update, expiration;



    public CastingCallApplicationsAdapter(ApplyCastingCallActivity mainActivity, List<CastingCallApplication> castingCallApplicationList) {

        // TODO Auto-generated constructor stub
        castingCallApplications = castingCallApplicationList;


        context = mainActivity;
        //  System.out.println(context);

        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return castingCallApplications.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        TextView castingCallApplicationsName,castingCallApplicationsGenderAndRole;
        ImageView profilePic;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final CastingCallApplicationsAdapter.Holder holder;
        //if (convertView == null)
        {
            holder = new CastingCallApplicationsAdapter.Holder();
            convertView = inflater.inflate(R.layout.casting_calls_applications_list, null);

            holder.profilePic = (ImageView) convertView.findViewById(R.id.CastingCallApplicationPic);
            holder.castingCallApplicationsName = (TextView) convertView.findViewById(R.id.CastingCallApplicationName);
            holder.castingCallApplicationsGenderAndRole = (TextView) convertView.findViewById(R.id.CastingCallApplicationGenderAndRole);

            convertView.setTag(holder);

            final CastingCallApplication castingCallApplication = castingCallApplications.get(position);

            try{
                Picasso.with(context).load("https://graph.facebook.com/"+castingCallApplication.getUser().getFbUser()+"/picture?height=40&weight=40")
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



            return convertView;
        }

    }
}
