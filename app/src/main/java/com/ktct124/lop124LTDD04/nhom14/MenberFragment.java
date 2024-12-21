package com.ktct124.lop124LTDD04.nhom14;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.annotation.NonNull;


public class MenberFragment extends Fragment {
    private TextView tvMember1, tvMember2, tvMember3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menber, container, false);

        // Ánh xạ các TextView
        tvMember1 = view.findViewById(R.id.member1);
        tvMember2 = view.findViewById(R.id.member2);
        tvMember3 = view.findViewById(R.id.member3);

        // Kết nối đến Firebase Realtime Database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference membersRef = database.getReference("members");

        // Đọc dữ liệu từ Firebase
        membersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    int index = 1;
                    for (DataSnapshot child : snapshot.getChildren()) {
                        String name = child.child("name").getValue(String.class);
                        String msv = child.child("msv").getValue(String.class);

                        String displayText = name + ". MSV: " + msv;
                        if (index == 1) {
                            tvMember1.setText(displayText);
                        } else if (index == 2) {
                            tvMember2.setText(displayText);
                        } else if (index == 3) {
                            tvMember3.setText(displayText);
                        }
                        index++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi
                tvMember1.setText("Failed to load data");
            }
        });

        return view;
    }
}