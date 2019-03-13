package com.samayu.scaction.dto;

/**
 * Created by NandhiniGovindasamy on 3/12/19.
 */

public class SelectedCastingCallRoles {

    private ProfileType profileType;
    private boolean checked;

    public ProfileType getProfileType() {
        return profileType;
    }

    public void setProfileType(ProfileType profileType) {
        this.profileType = profileType;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
