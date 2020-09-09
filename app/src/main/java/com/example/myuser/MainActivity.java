package com.example.myuser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myuser.addUser.AddUserActivity;
import com.example.myuser.database.SqliteHelper;
import com.example.myuser.model.User;
import com.example.myuser.userAdapter.MyUserAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyUserAdapter.OnItemClickListner{

    RecyclerView recyclerView;
    SqliteHelper sqliteHelper;
    List<User> users =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqliteHelper = new SqliteHelper(this);
        // Find Item or view
        findItems();
    }

    private void findItems() {
        recyclerView = findViewById(R.id.user_list);
        loadUser();
    }

    @Override
    protected void onStart() {
        super.onStart();
        loadUser();
    }

    private void loadUser() {
        if(users!=null){
            users.clear();
        }
        users = sqliteHelper.getAllUsers();
        setAdapter(users);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_add_option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_add:
                startActivity(new Intent(MainActivity.this, AddUserActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setAdapter(List<User> user) {
        MyUserAdapter myAreaAdapter = new MyUserAdapter(getApplicationContext(), user, MainActivity.this);
        recyclerView.setAdapter(myAreaAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false));
    }

    @Override
    public void onDeleteClick(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(R.string.delete)
                .setMessage("Are You Sure Want to delete "+users.get(position).getName()+ "data")
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sqliteHelper.deleteUser(users.get(position).getName());
                        loadUser();
                    }
                }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }
}