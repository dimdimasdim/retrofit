package com.android.beginner.retrofit;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.beginner.retrofit.model.Student;
import com.android.beginner.retrofit.service.APIService;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    EditText editName;
    TextView textDetails;
    Button btnGetData, btnInsertData;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textDetails = (TextView) findViewById(R.id.textDetails);
        btnGetData = (Button) findViewById(R.id.btnGetData);
        btnInsertData = (Button) findViewById(R.id.btnInsert);
        editName = (EditText) findViewById(R.id.editName);
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPeopleDetails();
            }
        });

//        btnInsertData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                setPeopleDetails();
//            }
//        });

    }

    private void getPeopleDetails() {

        showpDialog();
        try {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.0.2.2:8080/retrofit/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIService service = retrofit.create(APIService.class);

            Call<List<Student>> call = service.getPeopleDetails();

            call.enqueue(new Callback<List<Student>>() {
                @Override
                public void onResponse(Response<List<Student>> response, Retrofit retrofit) {
                    List<Student> peopleData = response.body();
                    String details = "";
                    for (int i = 0; i < peopleData.size(); i++) {
//                        int id = peopleData.get(i).getId();
                        String name = peopleData.get(i).getName();

                        details += "Name: " + name + "\n\n";


                    }
                    //Toast.makeText(MainActivity.this, details, Toast.LENGTH_SHORT).show();
                    textDetails.setText(details);
                    hidepDialog();
                }

                @Override
                public void onFailure(Throwable t) {
                    Log.d("onFailure", t.toString());
                    hidepDialog();
                }
            });
        } catch (Exception e) {
            Log.d("onResponse", "There is an error");
            e.printStackTrace();
            hidepDialog();
        }
    }


    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}