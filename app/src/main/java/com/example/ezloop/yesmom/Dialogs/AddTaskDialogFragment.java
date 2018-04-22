package com.example.ezloop.yesmom.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ezloop.yesmom.MainActivity;
import com.example.ezloop.yesmom.R;
import com.example.ezloop.yesmom.SQLite.DBAdapter;

public class AddTaskDialogFragment extends DialogFragment {

    private long taskID = 0;
    EditText etTaskName;
    EditText etTaskDesc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_add_task_dialog, container,
                false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_new_task);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();

        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
        }

        setHasOptionsMenu(true);

        //INITIATE ELEMENTS
        etTaskName = (EditText) view.findViewById(R.id.etNewTaskName);
        etTaskDesc = (EditText) view.findViewById(R.id.etNewTaskDesc);

        return view;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.save_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.save) {
            DBAdapter dbAdapter = new DBAdapter(getContext());
            dbAdapter.openDB();
            taskID = dbAdapter.insertTask(etTaskName.getText().toString(),
                                          etTaskDesc.getText().toString());
            dbAdapter.closeDB();
            Toast.makeText(getContext(), "New task saved!", Toast.LENGTH_SHORT).show();
            dbAdapter.closeDB();
            ((MainActivity)getActivity()).updateRecView();
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            View currentFocusedView = getActivity().getCurrentFocus();
            if (currentFocusedView != null) {
                inputMethodManager.hideSoftInputFromWindow(currentFocusedView.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
            dismiss();
            return true;
        } else if (id == android.R.id.home) {
            dismiss();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
