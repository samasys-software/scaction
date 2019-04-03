package com.samayu.scaction.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.samayu.scaction.R;
import com.samayu.scaction.dto.UserRole;
import com.samayu.scaction.ui.HomeActivity;
import com.samayu.scaction.ui.UserCastingCallsActivity;

import java.util.List;

/**
 * Created by NandhiniGovindasamy on 9/13/18.
 */

public class UserRolesAdapter extends BaseAdapter {
    List<UserRole> userRoles;

    int count = 0;
    Context context;
    LayoutInflater inflater;

    ImageButton rmv, update, expiration;



    public UserRolesAdapter(HomeActivity mainActivity, List<UserRole> userRoleDetails) {

        // TODO Auto-generated constructor stub
        userRoles = userRoleDetails;


        context = mainActivity;
        //  System.out.println(context);

        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return userRoles.size();
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
        TextView roleName;
        ImageButton update, delete, expiry;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder;
        //if (convertView == null)
        {
            holder = new Holder();
            convertView = inflater.inflate(R.layout.role_list, null);

            holder.roleName = (TextView) convertView.findViewById(R.id.roleName);
            holder.update = (ImageButton) convertView.findViewById(R.id.updateRole);
            holder.delete = (ImageButton) convertView.findViewById(R.id.deleteRole);
            holder.expiry = (ImageButton) convertView.findViewById(R.id.expirationRole);

            convertView.setTag(holder);

            final UserRole userRole = userRoles.get(position);


            holder.roleName.setText(userRole.getRoleType().getName());


            holder.update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int roleId=userRoles.get(position).getRoleType().getId();
                    if(roleId==11){
                        Intent intent=new Intent(context,UserCastingCallsActivity.class);
                        intent.putExtra("UserCastingCall",true);
                        context.startActivity(intent);
                    }

                }
            });

       /*     holder.roleName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserRole userRole1=userRoles.get(position);
                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, R.style.AlertTheme);
                    //alertDialog.setTitle();
                    alertDialog.setCancelable(true);
                    LayoutInflater inflater1 = inflater;
                    View diaView = inflater1.inflate(R.layout.role_details, null);
                    alertDialog.setView(diaView);

                    TextView rolename = (TextView) diaView.findViewById(R.id.roleName);
                    rolename.setText(userRole.getRoleType().getName());


                    alertDialog.setPositiveButton("Apply",
                            new DialogInterface.OnClickListener() {

                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(context,"Applied Successfully",Toast.LENGTH_LONG).show();
                                }

                            });
//alertDialog.show();
                    final AlertDialog checkout = alertDialog.create();
                    checkout.show();
                }
            });
*/
            return convertView;
        }

    }
}


