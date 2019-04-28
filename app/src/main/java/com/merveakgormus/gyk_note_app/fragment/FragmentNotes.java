package com.merveakgormus.gyk_note_app.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.merveakgormus.gyk_note_app.R;

import java.util.ArrayList;

public class FragmentNotes extends Fragment {

    ListView lwNotes;
    ImageView imgGoAddNote;

    ArrayAdapter<String> arrayNotesAdapter;
    ArrayList<String> noteList;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference dbRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        lwNotes = view.findViewById(R.id.lwNotes);
        imgGoAddNote = view.findViewById(R.id.imgGoAddNote);

        firebaseDatabase = FirebaseDatabase.getInstance();
        dbRef = firebaseDatabase.getReference("MyNote");

        noteList = new ArrayList<>();
        noteList = getMyNotes();

        arrayNotesAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,
                android.R.id.text1, noteList);
        lwNotes.setAdapter(arrayNotesAdapter);

        return view;
    }

    ArrayList<String> myNote;
    String note;
    private ArrayList<String> getMyNotes(){
        myNote = new ArrayList<>();

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    note = ds.child("note").getValue().toString();
                    myNote.add(note);
                    Log.d("NOTE:", ""+note);
                }
                arrayNotesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return myNote;
    }
}
