package com.samayu.scaction.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.samayu.scaction.R;
import com.samayu.scaction.dto.CastingCall;
import com.samayu.scaction.dto.ProfileType;
import com.samayu.scaction.dto.UserRole;
import com.samayu.scaction.service.SessionInfo;

import java.util.List;

/**
 * Created by NandhiniGovindasamy on 9/17/18.
 */

public class CastingCallsAdapter extends BaseAdapter {

        List<CastingCall> castingCalls;

        int count = 0;
        Context context;
        LayoutInflater inflater;

        ImageButton rmv, update, expiration;



        public CastingCallsAdapter(Activity mainActivity, List<CastingCall> castingCallList) {

            // TODO Auto-generated constructor stub
            castingCalls = castingCallList;


            context = mainActivity;
            //  System.out.println(context);

            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return castingCalls.size();
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
            TextView projectName,projectCompany,role;


        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            final Holder holder;
            //if (convertView == null)
            {
                holder = new Holder();
                convertView = inflater.inflate(R.layout.casting_calls_list, null);

                holder.projectName = (TextView) convertView.findViewById(R.id.projectName);
                holder.projectCompany = (TextView) convertView.findViewById(R.id.projectCompany);
                holder.role = (TextView) convertView.findViewById(R.id.role);

                convertView.setTag(holder);

                final CastingCall castingCall = castingCalls.get(position);


                holder.projectName.setText(castingCall.getProjectName());
                holder.projectCompany.setText(castingCall.getProductionCompany());
                String roles="";
                String[] roleIds=castingCall.getRoleIds().split(",");
                List<ProfileType> profileTypeList= SessionInfo.getInstance().getProfileTypes();
                for(int i=0;i<profileTypeList.size();i++){
                    for(int j=0;j<roleIds.length;j++)
                    {
                        if(profileTypeList.get(i).getId()==Integer.parseInt(roleIds[j]))
                        {
                           roles= roles.concat(profileTypeList.get(i).getName()+",");
                        }
                    }
                }
                holder.role.setText(roles);
                return convertView;
            }

        }
}




