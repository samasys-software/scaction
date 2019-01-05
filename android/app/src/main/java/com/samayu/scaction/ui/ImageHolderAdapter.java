package com.samayu.scaction.ui;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
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
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by NandhiniGovindasamy on 12/18/18.
 */

public class ImageHolderAdapter extends BaseAdapter {

    List<Uri> uris;

    int count = 0;
    Context context;
    LayoutInflater inflater;

    ImageButton rmv, update, expiration;


    public ImageHolderAdapter(Activity mainActivity, List<Uri> uriList) {

        // TODO Auto-generated constructor stub
        uris = uriList;


        context = mainActivity;
        //  System.out.println(context);

        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return uris.size();
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
        TextView castingCallApplicationsName, castingCallApplicationsGenderAndRole;
        ImageView portfolioImages;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final com.samayu.scaction.ui.ImageHolderAdapter.Holder holder;
        //if (convertView == null)
        {
            holder = new com.samayu.scaction.ui.ImageHolderAdapter.Holder();
            convertView = inflater.inflate(R.layout.image_holder_list, null);

            holder.portfolioImages = (ImageView) convertView.findViewById(R.id.portfolioImages);

            convertView.setTag(holder);

            final Uri uri = uris.get(position);
            holder.portfolioImages.setImageURI(uri);
            return convertView;
        }

    }
}
