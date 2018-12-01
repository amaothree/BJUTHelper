package com.assignment.amao.bjuthelper;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser implements Cloneable {

    @Override
    public Object clone() {
        BmobUser o = null;
        try {
            o = (BmobUser) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return o;
    }
}
