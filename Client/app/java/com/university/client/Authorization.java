package com.university.client;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.university.client.api.Network;
import com.university.client.entity.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Authorization {
    public void auth(final String userName, String password, final Context context){
        User user = new User(userName, password);

        Call<User> call = Network.getInstance().getJsonApi().auth(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()){
                    Toast toast = Toast.makeText(context, "Wrong username or password", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                User userResponse = response.body();

                Network.getInstance().addToken(userResponse.getToken());

                Network.getInstance().isAdmin = userResponse.getRole().get(0).equals("ROLE_ADMIN");

                Intent intent = new Intent(context, ListActivity.class);
                context.startActivity(intent);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast toast = Toast.makeText(context, "Oops... Something wrong", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public void registration(final String userName, final String password, final Context context){
        User user = new User(userName, password);

        Call<User> call = Network.getInstance().getJsonApi().registration(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()){
                    Toast toast = Toast.makeText(context, "Wrong username or password", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                auth(userName, password, context);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast toast = Toast.makeText(context, "Oops... Something wrong", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
