package com.samayu.scaction.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.samayu.scaction.BuildConfig;
import com.samayu.scaction.R;
import com.samayu.scaction.dto.PortfolioPicture;
import com.samayu.scaction.dto.ProfileType;
import com.samayu.scaction.dto.SelectedCastingCallRoles;
import com.samayu.scaction.dto.User;
import com.samayu.scaction.dto.UserRole;
import com.samayu.scaction.service.SCAClient;
import com.samayu.scaction.service.SessionInfo;
import com.samayu.scaction.ui.CreatePortfolioActivity;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NandhiniGovindasamy on 3/12/19.
 */

public class CastingCallsRolesAdapter extends RecyclerView.Adapter <com.samayu.scaction.ui.adapter.CastingCallsRolesAdapter.RolesViewHolder>{
        List<SelectedCastingCallRoles> selectedCastingCallRolesList;
        User user;
        Context context;
        LayoutInflater inflater;
        int currentUser;

       // List<SelectedCastingCallRoles> selectedCastingCallRolesList=new ArrayList<SelectedCastingCallRoles>();

        public class RolesViewHolder extends RecyclerView.ViewHolder {
            public TextView role;
            public CheckBox checkBox;
            public RolesViewHolder(View view) {
                super(view);
                role = (TextView) view.findViewById(R.id.castingRoleName);
                checkBox = (CheckBox) view.findViewById(R.id.castingRolesChecked);
            }
        }

        public CastingCallsRolesAdapter(Activity mainActivity,int currentUser1,User user1) {

            // TODO Auto-generated constructor stub
            selectedCastingCallRolesList = SessionInfo.getInstance().getSelectedCastingCallRoles();
            user=user1;

            currentUser=currentUser1;
            context = mainActivity;
            //  System.out.println(context);

            inflater = LayoutInflater.from(context);
        }

        @Override
        public com.samayu.scaction.ui.adapter.CastingCallsRolesAdapter.RolesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.casting_calls_role_list, parent, false);


            return new com.samayu.scaction.ui.adapter.CastingCallsRolesAdapter.RolesViewHolder(itemView) ;

        }

        @Override
        public void onBindViewHolder(final com.samayu.scaction.ui.adapter.CastingCallsRolesAdapter.RolesViewHolder holder, final int position) {
            final SelectedCastingCallRoles selectedCastingCallRoles=selectedCastingCallRolesList.get(position);

            holder.role.setText(selectedCastingCallRoles.getProfileType().getName());
            holder.checkBox.setChecked(false);

            if(currentUser==1){
                holder.checkBox.setVisibility(View.GONE);
            }
            else if(currentUser==2){
                List<UserRole> currentUserRoles=user.getUserRoles();
                for(UserRole userRole:currentUserRoles)
                {
                    if(userRole.getRoleType().getId()!=selectedCastingCallRoles.getProfileType().getId())
                    {
                        holder.checkBox.setVisibility(View.GONE);
                    }
                }
            }



            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(holder.checkBox.isChecked()){
                       SessionInfo.getInstance().getSelectedCastingCallRoles().get(position).setChecked(true);
                    }
                    else{
                        SessionInfo.getInstance().getSelectedCastingCallRoles().get(position).setChecked(false);
                    }

                }
            });




        }

        @Override
        public int getItemCount() {
            return selectedCastingCallRolesList.size();
        }
}
