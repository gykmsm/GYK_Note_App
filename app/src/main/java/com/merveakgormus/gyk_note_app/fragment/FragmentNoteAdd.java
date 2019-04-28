package com.merveakgormus.gyk_note_app.fragment;

import android.arch.lifecycle.Lifecycle;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.merveakgormus.gyk_note_app.R;
import com.merveakgormus.gyk_note_app.model.NoteModel;


public class FragmentNoteAdd extends Fragment {

    Button btnAddNote;
    EditText edtNote;

    String note;
    String noteId;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference mRef;

    NoteModel mynote;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_add, container, false);

        firebaseDatabase = FirebaseDatabase.getInstance();
        mRef = firebaseDatabase.getReference("MyNote");

        btnAddNote = view.findViewById(R.id.btnAddNote);
        edtNote = view.findViewById(R.id.edtNote);

        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                note = edtNote.getText().toString();

                AddNoteDatabase(note);
            }
        });

        return view;
    }

    private void AddNoteDatabase(String note){
        mynote = new NoteModel("Test",note,"--");
        noteId = mRef.push().getKey();
        mRef.child(noteId).child("key").setValue(note);
        Toast.makeText(getContext(), "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
        edtNote.setText("");
    }
}
