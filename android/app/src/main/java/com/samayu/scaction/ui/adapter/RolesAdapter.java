package com.samayu.scaction.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.samayu.scaction.R;
import com.samayu.scaction.dto.SelectedCastingCallRoles;
import com.samayu.scaction.dto.User;
import com.samayu.scaction.dto.UserRole;
import com.samayu.scaction.service.SessionInfo;

import java.util.List;

/**
 * Created by NandhiniGovindasamy on 3/12/19.
 */

public class RolesAdapter extends RecyclerView.Adapter <RolesAdapter.RolesViewHolder>{
        List<SelectedCastingCallRoles> selectedCastingCallRolesList;
        List<SelectedCastingCallRoles> profileTypes;
        User user;
        Context context;
        LayoutInflater inflater;
        int currentUser,currentActivity;

       // List<SelectedCastingCallRoles> selectedCastingCallRolesList=new ArrayList<SelectedCastingCallRoles>();

        public class RolesViewHolder extends RecyclerView.ViewHolder {
            public TextView role;
            public CheckBox checkBox;
            public ImageView imageView;
            public RolesViewHolder(View view) {
                super(view);
                role = (TextView) view.findViewById(R.id.castingRoleName);
                checkBox = (CheckBox) view.findViewById(R.id.castingRolesChecked);
                imageView = (ImageView) view.findViewById(R.id.castingRolesSelected);
            }
        }

        public RolesAdapter(Activity mainActivity, int Activity, int currentUser1, User user1) {

            // TODO Auto-generated constructor stub
            currentActivity=Activity;

            if(currentActivity==0)
            {
                selectedCastingCallRolesList = SessionInfo.getInstance().getRolesList();
            }else {
                selectedCastingCallRolesList = SessionInfo.getInstance().getSelectedCastingCallRoles();
            }

            user=user1;

            currentUser=currentUser1;
            context = mainActivity;
            //  System.out.println(context);

            inflater = LayoutInflater.from(context);
        }

        @Override
        public RolesAdapter.RolesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.casting_calls_role_list, parent, false);


            return new RolesAdapter.RolesViewHolder(itemView) ;

        }

        @Override
        public void onBindViewHolder(final RolesAdapter.RolesViewHolder holder, final int position) {
            final SelectedCastingCallRoles selectedCastingCallRoles=selectedCastingCallRolesList.get(position);

            holder.role.setText(selectedCastingCallRoles.getProfileType().getName());
         //   holder.checkBox.setChecked(selectedCastingCallRoles.isChecked());

            if(currentActivity==1){
                if(currentUser==0){
                    holder.checkBox.setVisibility(View.GONE);
                }
                else if(currentUser==1){
                    holder.checkBox.setVisibility(View.GONE);
                }
                else if(currentUser==2){
                    holder.checkBox.setVisibility(View.INVISIBLE);
                    List<UserRole> currentUserRoles=user.getUserRoles();
                    for(UserRole userRole:currentUserRoles)
                    {
                        System.out.println(userRole.getRoleType().getId());
                        System.out.println(selectedCastingCallRoles.getProfileType().getId());

                        if(userRole.getRoleType().getId()==selectedCastingCallRoles.getProfileType().getId())
                        {
                            holder.checkBox.setVisibility(View.VISIBLE);
                        }

                    }
                }

            }
            if(selectedCastingCallRoles.isChecked())
            {
                holder.checkBox.setVisibility(View.GONE);
                holder.imageView.setVisibility(View.VISIBLE);
            }





            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(currentActivity==0){
                        if (holder.checkBox.isChecked()) {
                            SessionInfo.getInstance().getRolesList().get(position).setChecked(true);
                        } else {
                            SessionInfo.getInstance().getRolesList().get(position).setChecked(false);
                        }

                    }
                    else {
                        if (holder.checkBox.isChecked()) {
                            SessionInfo.getInstance().getSelectedCastingCallRoles().get(position).setChecked(true);
                        } else {
                            SessionInfo.getInstance().getSelectedCastingCallRoles().get(position).setChecked(false);
                        }
                    }

                }
            });




        }

        @Override
        public int getItemCount() {
            return selectedCastingCallRolesList.size();
        }
}
