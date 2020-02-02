package com.emergency.Fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.emergency.MainActivity;
import com.emergency.Model.User;
import com.emergency.R;
import com.emergency.SignupActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddContactFragment extends Fragment {
    FirebaseAuth auth;



    private ProgressDialog progressDialog;
    Button add;
    ImageButton select;
    private static final int RESULT_PICK_CONTACT = 1001;
    EditText inputEmail,phonenumber;

    public AddContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inputEmail=view.findViewById(R.id.email);
        select=view.findViewById(R.id.select_contact);
        phonenumber=view.findViewById(R.id.phone);
        add=view.findViewById(R.id.add);
        progressDialog = new ProgressDialog(getActivity());
        auth = FirebaseAuth.getInstance();
        final String uid = auth.getUid();
        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               pickContact();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String phone  = phonenumber.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressDialog.setMessage("Adding Contact, Pleas Wait,");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
               final  Contact contact = new Contact(email,phone);
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                final DatabaseReference myRef = database.getReference();
                final String key=myRef.push().getKey();
                myRef.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                myRef.child("contacts").child(uid).child(key).setValue(contact);
                                progressDialog.dismiss();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                //   Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                                // [START_EXCLUDE]
                                //   setEditingEnabled(true);
                                // [END_EXCLUDE]
                            }
                        });

                phonenumber.setText("");
                inputEmail.setText("");



            }
        });
    }


    public void pickContact() {

        Intent contactPickerIntent = new Intent(Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
        startActivityForResult(contactPickerIntent, RESULT_PICK_CONTACT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case RESULT_PICK_CONTACT:
                    Cursor cursor = null;
                    try {
                        String contactNumber = null;
                        String contactName = null;
// getData() method will have the
// Content Uri of the selected contact
                        Uri uri = data.getData();
//Query the content uri
                        cursor = getContext().getContentResolver().query(uri, null, null, null, null);
                        cursor.moveToFirst();
// column index of the phone number
                        int phoneIndex = cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER);
// column index of the contact name
                        int nameIndex = cursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                        contactNumber = cursor.getString(phoneIndex);
                        contactName = cursor.getString(nameIndex);
// Set the value to the textviews

                        phonenumber.setText(contactNumber);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }
}

  class Contact {

    public String email;

    public String phone;

    public Contact(String email, String phone) {

        this.email=email;
        this.phone=phone;
    }

      public Contact() {
          // Default constructor required for calls to DataSnapshot.getValue(User.class)
      }

}