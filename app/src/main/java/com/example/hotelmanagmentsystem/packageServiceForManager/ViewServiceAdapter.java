package com.example.hotelmanagmentsystem.packageServiceForManager;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hotelmanagmentsystem.R;
import com.example.hotelmanagmentsystem.model.RequestQueueSingleton;
import com.example.hotelmanagmentsystem.packageReceptionForManager.ViewReceptionAdapter;
import com.example.hotelmanagmentsystem.packageRoomForManager.InitialRoomInfo;
import com.example.hotelmanagmentsystem.packageRoomForManager.ViewRoomMoreInfoForManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewServiceAdapter extends RecyclerView.Adapter<ViewReceptionAdapter.ViewHolder> {
    private ArrayList<String> captions1 = new ArrayList<>();
    private final Context context;
    TextView txtDeleted ;
    private final ServiceManager[] ServiceList;
    public ViewServiceAdapter(ServiceManager[] ServiceList, Context context) {
        this.ServiceList=ServiceList;
        this.context=context;
    }

    @Override
    public ViewReceptionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_service_manager,
                parent,
                false);

        return new ViewReceptionAdapter.ViewHolder(v);
    }
    Button btnDelete;
    Button btnEdit;
    String Free="";
    @Override
    public void onBindViewHolder(ViewReceptionAdapter.ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView txt1 = (TextView)cardView.findViewById(R.id.txtServiceInfo);
        if(!ServiceList[position].getFreeFor().equals("")){
            String s1 = ServiceList[position].getFreeFor().replace(","," & ");
            Free = "This Service Free For Room type " + s1;
        }
        else{
            Free = "This Service Not Free For All Rooms";
            }
        txt1.setText("Service ID: "+ ServiceList[position].getsId()+
                "\n"  +"Service Name: " + ServiceList[position].getName() + "\n" + "Description: "
                + ServiceList[position].getDescription() + "\n" +
                "Service Price: " + ServiceList[position].getPrice() +"\n" + Free);
        btnDelete = cardView.findViewById(R.id.btnDrop);
        btnEdit = cardView.findViewById(R.id.btnEdit);
        int pos = position;
        txtDeleted = cardView.findViewById(R.id.txtDrop);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               deleteService((ServiceList[pos].getsId()));

            }
        });
        btnEdit = cardView.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditServiceForManager.class);
                intent.putExtra("sId", (ServiceList[pos].getsId()));
                intent.putExtra("name", (ServiceList[pos].getName()));
                intent.putExtra("description", (ServiceList[pos].getDescription()));
                intent.putExtra("price", (ServiceList[pos].getPrice()));
                intent.putExtra("freeFor", (ServiceList[pos].getFreeFor()));
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return ServiceList.length;
    }
    private void deleteService(int id){
        String url = "http://10.0.2.2:80/delete-service.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responceJsonObject = new JSONObject(response);
                    if (responceJsonObject.has("error")) {
                        System.out.println(responceJsonObject.toString());
                        if (!responceJsonObject.getBoolean("error")) {
                            Toast.makeText(context.getApplicationContext(), responceJsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            btnDelete.setVisibility(View.GONE);
                            btnEdit.setVisibility(View.GONE);
                            txtDeleted.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(context.getApplicationContext(),
                                    "Some thing wrong happened", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("sId", id+"");
                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };

        RequestQueueSingleton.getInstance(context.getApplicationContext()).addToRequestQueue(request);

    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }

    }
}
