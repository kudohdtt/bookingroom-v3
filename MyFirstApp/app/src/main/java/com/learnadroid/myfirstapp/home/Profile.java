package com.learnadroid.myfirstapp.home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.learnadroid.myfirstapp.dangnhap.Login;
import com.learnadroid.myfirstapp.home.ImageConverter;
import com.learnadroid.myfirstapp.R;
import com.learnadroid.myfirstapp.dangnhap.AccountManager;

public class Profile extends Fragment {

    TextView fullName;
    TextView userName;
    TextView sex;
    TextView birthDay;
    TextView numberPhone;
    TextView email;
    Button btLogOut;


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.chandung);
//        Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);
//
//        ImageView circularImageView = getView().findViewById(R.id.imageView);
//        circularImageView.setImageBitmap(circularBitmap);
//        ReferenceInit();
//        SetDataForTextViews();
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.activity_profile, container, false);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((MainActivity) requireActivity()).getSupportActionBar().hide();
        Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.chandung);
        Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);

        ImageView circularImageView = getView().findViewById(R.id.imageView);
        circularImageView.setImageBitmap(circularBitmap);
        ReferenceInit();
        SetDataForTextViews();
    }

//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.menu_calls, menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == R.id.action_call) {
//            Toast.makeText(getActivity(), "Clicked on " + item.getTitle(), Toast.LENGTH_SHORT)
//                    .show();
//        }
//        return true;
//    }

    private void ReferenceInit(){

        fullName = getView().findViewById(R.id.txtten);
        userName =  getView().findViewById(R.id.txttendn);
        sex =  getView().findViewById(R.id.txtgioitinh);
        birthDay =  getView().findViewById(R.id.txtngaysin);
        numberPhone =  getView().findViewById(R.id.txtsodienthoai);
        email =  getView().findViewById(R.id.txtemail);
        btLogOut = getView().findViewById(R.id.btDangxuat);
    }

    private void SetDataForTextViews(){
        fullName.setText(AccountManager.getInstance().user.getFullName());
        userName.setText(AccountManager.getInstance().user.getUserName());
        sex.setText(AccountManager.getInstance().user.getSex());
        birthDay.setText(AccountManager.getInstance().user.getBirthDay());
        numberPhone.setText(AccountManager.getInstance().user.getNumberPhone());
        email.setText(AccountManager.getInstance().user.getEmail());

        btLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Login.class);
                startActivity(intent);
            }
        });
    }

}
