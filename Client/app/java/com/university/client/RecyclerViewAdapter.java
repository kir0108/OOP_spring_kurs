package com.university.client;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.university.client.api.Network;
import com.university.client.entity.Article;
import com.university.client.entity.Balance;
import com.university.client.entity.Operation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> implements Filterable {

    private int numberItems;
    private List<Operation> data = new ArrayList<>();
    private List<Operation> dataAll = new ArrayList<>();
    private Context context;
    private Dialog dialog;
    private Button saveButton;
    private Button createButton;
    private int pos;

    public RecyclerViewAdapter(List<Operation> listOp) {
        numberItems = 0;
        if (!listOp.isEmpty()) {
            numberItems = listOp.size();
        }

        dataAll.clear();
        dataAll.addAll(listOp);
        data.clear();
        data.addAll(dataAll);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        int layoutIdForList = R.layout.item_operation;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForList, parent, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);

        dialog = new Dialog(context);
        dialog.setContentView(R.layout.fragment_operation);

        saveButton = dialog.findViewById(R.id.save);
        createButton = dialog.findViewById(R.id.create);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText articleName = (EditText) dialog.findViewById(R.id.article);
                EditText debit = (EditText) dialog.findViewById(R.id.debit);
                EditText credit = (EditText) dialog.findViewById(R.id.credit);
                EditText amount = (EditText) dialog.findViewById(R.id.amount);
                EditText createDate = (EditText) dialog.findViewById(R.id.date);

                String sArticle = articleName.getText().toString();
                String sDebit = debit.getText().toString();
                String sCredit = credit.getText().toString();
                String sAmount = amount.getText().toString();
                String sDate = createDate.getText().toString();

                if (!dataIsSuccessful(sArticle, sDebit, sCredit, sAmount, sDate)){
                    return;
                }

                final long n = 0;

                addOperation(new Operation(n, new Article(n, sArticle), Float.parseFloat(sDebit),
                        Float.parseFloat(sCredit), sDate, new Balance(n, sDate, Float.parseFloat(sDebit),
                        Float.parseFloat(sCredit), Float.parseFloat(sAmount))));

                dialog.hide();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!Network.getInstance().isAdmin){
                    Toast toast = Toast.makeText(context, "You cant do it", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                EditText articleName = (EditText) dialog.findViewById(R.id.article);
                EditText debit = (EditText) dialog.findViewById(R.id.debit);
                EditText credit = (EditText) dialog.findViewById(R.id.credit);
                EditText amount = (EditText) dialog.findViewById(R.id.amount);
                EditText createDate = (EditText) dialog.findViewById(R.id.date);

                String sArticle = articleName.getText().toString();
                String sDebit = debit.getText().toString();
                String sCredit = credit.getText().toString();
                String sAmount = amount.getText().toString();
                String sDate = createDate.getText().toString();

                if (!dataIsSuccessful(sArticle, sDebit, sCredit, sAmount, sDate)){
                    return;
                }

                Operation operation = data.get(pos);

                int f = 0;

                if (!data.get(pos).getArticle().getName().equals(sArticle))
                {
                    operation.getArticle().setName(sArticle);
                    f++;
                }

                if (!(data.get(pos).getBalance().getDebit().toString().equals(sDebit) &&
                        data.get(pos).getBalance().getCredit().toString().equals(sCredit) &&
                        data.get(pos).getBalance().getAmount().toString().equals(sAmount) &&
                        data.get(pos).getBalance().getCreateDate().equals(sDate)))
                {
                    operation.setBalance(new Balance(operation.getBalance().getId(), sDate,
                            Float.parseFloat(sDebit), Float.parseFloat(sCredit), Float.parseFloat(sAmount)));
                    f++;
                }

                if (f > 0) {
                    operation.setDebit(Float.parseFloat(sDebit));
                    operation.setCredit(Float.parseFloat(sCredit));
                    operation.setCreateDate(sDate);
                    operation.getArticle().setName(sArticle);
                    operation.getBalance().setCreateDate(sDate);
                    operation.getBalance().setAmount(Float.parseFloat(sAmount));
                    operation.getBalance().setCredit(Float.parseFloat(sCredit));
                    operation.getBalance().setDebit(Float.parseFloat(sDebit));
                    dialog.hide();
                    editOperation(operation);
                }
            }
        });

        myViewHolder.frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberItems != 0) {
                    EditText articleName = (EditText) dialog.findViewById(R.id.article);
                    EditText debit = (EditText) dialog.findViewById(R.id.debit);
                    EditText credit = (EditText) dialog.findViewById(R.id.credit);
                    EditText amount = (EditText) dialog.findViewById(R.id.amount);
                    EditText createDate = (EditText) dialog.findViewById(R.id.date);

                    articleName.setText(data.get(myViewHolder.getAdapterPosition()).getArticle().getName(), TextView.BufferType.EDITABLE);
                    debit.setText(data.get(myViewHolder.getAdapterPosition()).getDebit().toString(), TextView.BufferType.EDITABLE);
                    credit.setText(data.get(myViewHolder.getAdapterPosition()).getCredit().toString(), TextView.BufferType.EDITABLE);
                    amount.setText(data.get(myViewHolder.getAdapterPosition()).getBalance().getAmount().toString(), TextView.BufferType.EDITABLE);
                    createDate.setText(data.get(myViewHolder.getAdapterPosition()).getCreateDate(), TextView.BufferType.EDITABLE);
                }

                pos = myViewHolder.getAdapterPosition();

                saveButton.setVisibility(View.VISIBLE);
                createButton.setVisibility(View.INVISIBLE);

                dialog.show();
            }
        });

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (numberItems != 0) {
            holder.tv_Article.setText(data.get(position).getArticle().getName());
            holder.tv_Date.setText(data.get(position).getBalance().getCreateDate());
            if (position % 2 == 0) {
                holder.frameLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.back));
            } else {
                holder.frameLayout.setBackgroundColor(ContextCompat.getColor(context, R.color.dark));
            }
        }
    }

    @Override
    public int getItemCount() {
        return numberItems;
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Operation> filteredList = new ArrayList<>();

            String[] constraints = ((String) constraint).split(" ");

            if(constraint.toString().isEmpty()){
                filteredList.addAll(dataAll);
            } else {
                for (Operation operation: dataAll) {
                    boolean t = true;
                    for (String str: constraints) {
                        if (!operation.toString().contains(str)) {
                            t = false;
                        }
                    }
                    if (t){
                        filteredList.add(operation);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            data.clear();
            data.addAll((Collection<? extends Operation>) results.values);
            numberItems = data.size();
            notifyDataSetChanged();
        }
    };

    public boolean dataIsSuccessful(String sArticle, String sDebit, String sCredit, String sAmount, String sDate) {
        if (sArticle.isEmpty() || sDebit.isEmpty() || sCredit.isEmpty() || sAmount.isEmpty() || sDate.isEmpty()) {
            return false;
        }

        try {
            Float sDeb = Float.parseFloat(sDebit);
            Float sCred = Float.parseFloat(sCredit);
            Float sAm = Float.parseFloat(sAmount);
        } catch (NumberFormatException ex) {
            return false;
        }

        if (sDate.length() != 8) {
            return false;
        }

        String[] q = sDate.split("\\.");
        if (q.length != 3) {
            return false;
        }

        try {
            int t = Integer.parseInt(q[1]);
            if (t > 12 || t < 1) {
                return false;
            }
            int k = Integer.parseInt(q[0]);
            if (k < 1 || (k > 31 && (t == 1 || t == 3 || t == 5 || t == 7 || t == 8 || t == 10 || t == 12))
                    || (k > 30 && (t == 4 || t == 6 || t == 9 || t == 11)) || (k > 28 && t == 2)) {
                return false;
            }

            if (Integer.parseInt(q[2]) > 99 || Integer.parseInt(q[2]) < 0) {
                return false;
            }

            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_Article;
        TextView tv_Date;
        LinearLayout frameLayout;

        public MyViewHolder(View itemView) {
            super(itemView);

            frameLayout = itemView.findViewById(R.id.linear);
            tv_Article = itemView.findViewById(R.id.articleView);
            tv_Date = itemView.findViewById(R.id.dateView);
        }
    }

    public void clickAdd(){
        dialog.show();
        saveButton.setVisibility(View.INVISIBLE);
        createButton.setVisibility(View.VISIBLE);

        EditText articleName = (EditText) dialog.findViewById(R.id.article);
        EditText debit = (EditText) dialog.findViewById(R.id.debit);
        EditText credit = (EditText) dialog.findViewById(R.id.credit);
        EditText amount = (EditText) dialog.findViewById(R.id.amount);
        EditText createDate = (EditText) dialog.findViewById(R.id.date);

        articleName.setText("", TextView.BufferType.EDITABLE);
        debit.setText("", TextView.BufferType.EDITABLE);
        credit.setText("", TextView.BufferType.EDITABLE);
        amount.setText("", TextView.BufferType.EDITABLE);
        createDate.setText("", TextView.BufferType.EDITABLE);
    }

    public void addOperation(Operation operation){
        Call<JsonElement> call= Network.getInstance().getJsonApi().addOperation(operation);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (!response.isSuccessful()){
                    Toast toast = Toast.makeText(context, "Uncorrected data", Toast.LENGTH_SHORT);
                    toast.show();
                }
                getAllOperations();
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                Toast toast = Toast.makeText(context, "Cant connect to server", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public void editOperation(Operation operation) {

        Call<JsonElement> call = Network.getInstance().getJsonApi().editOperation(operation.getId(), operation);

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                getAllOperations();
            }
        });
    }

    public void deleteOperation(final int pos){

        Call<JsonElement> call = Network.getInstance().getJsonApi().deleteOperation(data.get(pos).getId());

        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                getAllOperations();
            }
        });
    }

    public void getAllOperations() {
        Call<JsonArray> call = Network.getInstance().getJsonApi().getAllOperations();
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if (response.isSuccessful()) {
                    dataAll.clear();

                    for (int i = 0; i < response.body().size(); i++) {
                        JsonObject operationObject = response.body().get(i).getAsJsonObject();

                        dataAll.add(new Operation(operationObject.get("id").getAsLong(),
                                new Article(operationObject.get("article").getAsJsonObject().get("id").getAsLong(),
                                        operationObject.get("article").getAsJsonObject().get("name").getAsString()),
                                operationObject.get("debit").getAsFloat(), operationObject.get("credit").getAsFloat(),
                                operationObject.get("createDate").getAsString(),
                                new Balance(operationObject.get("balance").getAsJsonObject().get("id").getAsLong(),
                                        operationObject.get("balance").getAsJsonObject().get("createDate").getAsString(),
                                        operationObject.get("balance").getAsJsonObject().get("debit").getAsFloat(),
                                        operationObject.get("balance").getAsJsonObject().get("credit").getAsFloat(),
                                        operationObject.get("balance").getAsJsonObject().get("amount").getAsFloat())));
                    }
                }

                data.clear();
                data.addAll(dataAll);
                numberItems = dataAll.size();
                notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                Toast toast = Toast.makeText(context, "Cant connect to server", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }
}
