<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:background="@android:color/white"
    android:layout_margin="10dp">

    <RelativeLayout
        android:id="@+id/rel1"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_margin="15dp">

        <de.hdodenhof.circleimageview.CircleImageView
            android:id="@+id/iv_profile"
            android:layout_width="45dp"
            android:layout_height="45dp"
            android:src="@drawable/profile_pic1"
            />
        <LinearLayout
            android:layout_toRightOf="@+id/iv_profile"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginLeft="10dp"
            android:orientation="vertical">
            <TextView
                android:id="@+id/tv_name"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="John Doe"
                android:textSize="18sp"
                android:textColor="@android:color/black"/>

            <TextView
                android:id="@+id/tv_time"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="9 hrs"
                android:textSize="13sp"
                android:textColor="@android:color/black"/>
        </LinearLayout>

    </RelativeLayout>

    <TextView
        android:id="@+id/tv_status"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_below="@+id/rel1"
        android:textColor="@android:color/black"
        android:textSize="16sp"
        android:layout_marginLeft="15dp"
        android:layout_marginRight="15dp"
        android:layout_marginBottom="15dp"
        android:text="This is a sample status to check the UI."/>

    <ImageView
        android:id="@+id/iv_postpic"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:src="@drawable/img_post2"
        android:scaleType="fitCenter"
        android:adjustViewBounds="true"
        android:layout_below="@+id/tv_status"
        />

    <RelativeLayout
        android:id="@+id/rel2"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginRight="15dp"
        android:layout_marginLeft="15dp"
        android:layout_marginTop="15dp"
        android:layout_below="@+id/iv_postpic">

        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:gravity="center">

            <ImageView
                android:layout_width="15dp"
                android:layout_height="15dp"
                android:src="@drawable/ic_like"/>
            <TextView
                android:id="@+id/tv_like"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_marginLeft="5dp"
                android:text="20"
                android:textColor="@android:color/black"
                android:textSize="13sp"/>

        </LinearLayout>

        <TextView
            android:id="@+id/tv_comment"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_alignParentRight="true"
            android:textColor="@android:color/black"
            android:textSize="13sp"
            android:text="10 comments"/>

    </RelativeLayout>

    <ImageView
        android:id="@+id/iv_line"
        android:layout_width="match_parent"
        android:layout_height="0.5dp"
        android:layout_below="@+id/rel2"
        android:layout_marginTop="10dp"
        android:background="@color/light_grey"
        />

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="45dp"
        android:layout_below="@+id/iv_line">

        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_weight="1">
            <LinearLayout
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_centerInParent="true">
                <ImageView
                    android:layout_width="18dp"
                    android:layout_height="18dp"
                    android:adjustViewBounds="true"
                    android:scaleType="fitCenter"
                    android:src="@drawable/ic_like_btn"/>
                <TextView
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content"
                    android:layout_marginLeft="10dp"
                    android:text="Like"
                    android:textSize="13sp"
                    android:textColor="@android:color/black"/>

            </LinearLayout>
        </RelativeLayout>

        <RelativeLayout
            an