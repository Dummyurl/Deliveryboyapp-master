package activities.ziffytech.code.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import activities.ziffytech.code.R;
import activities.ziffytech.code.activities.LoginActivity;
import activities.ziffytech.code.activities.CommonActivity;
import activities.ziffytech.code.activities.OrderDetail;
import activities.ziffytech.code.commanclass.CommonClass;
import activities.ziffytech.code.commanclass.MyUtility;
import activities.ziffytech.code.commanclass.VJsonRequest;
import activities.ziffytech.code.config.ApiParams;
import activities.ziffytech.code.model.Todays_order_model;


import java.util.ArrayList;
import java.util.HashMap;

public class AdapterOrderlistshowing extends RecyclerView.Adapter<AdapterOrderlistshowing.ViewHolder> implements AdapterView.OnItemClickListener
{

    private ArrayList<Todays_order_model> modelArrayList;
    Context context;
    OnItemClickListener clickListener;
    public CommonClass common;
    Todays_order_model model;
    public CommonActivity commonActivity;
    ImageView imgv_accept;

    public AdapterOrderlistshowing (Context context, ArrayList<Todays_order_model> names)
    {
        this.context=context;
        this.modelArrayList = names;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.myorders, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {

        Log.e("FILTERED LIST",""+modelArrayList);

        model=modelArrayList.get(position);

        holder.txtv_date.setText("Order Id. : "+model.getOrder_id());
        //holder.txtv_orderid.setText("Order Id. : "+model.getOrder_id());
        holder.txtv_total.setText("Total : "+model.getTotal_amt());
        holder.txtv_timeslot.setText("Date : "+model.getDelivery_date()+"  Time : "+model.getDepature_time());
        holder.txtv_cstname.setText("Name : "+model.getPatient_name()+"  ("+model.getMobile_number()+") ");
        holder.txtv_address.setText("Address : \n"+model.getAddress());

        holder.imgv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",model.getMobile_number(), null));
                context.startActivity(intent);
            }
        });


        if(model.getDelivery_status().equals("Assigned"))
        {

            holder.imgv_accept.setVisibility(View.VISIBLE);
            holder.imgv_accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {

                    Performanima(view);
                    Acceptorder(position);
                    /*AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure you want to Accept Order?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                    //selected_index = position;
                                   //commonActivity.showPrgressBar();

                                    Acceptorder(position);

                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();*/
                }
            });

        }
        else if (model.getDelivery_status().equals("Delivered"))
        {
            holder.imgv_accept.setVisibility(View.GONE);
            holder.imgv_status.setVisibility(View.VISIBLE);
            holder.imgv_status.setBackgroundResource(R.drawable.delivered);

        }
        else if (model.getDelivery_status().equals("completed"))
        {
            holder.imgv_accept.setVisibility(View.GONE);
            holder.imgv_status.setVisibility(View.VISIBLE);
            holder.imgv_status.setBackgroundResource(R.drawable.delivered);

        }
        else if(model.getDelivery_status().equals("Cancel"))
        {
            holder.imgv_accept.setVisibility(View.GONE);
            holder.imgv_status.setVisibility(View.VISIBLE);
            holder.imgv_status.setBackgroundResource(R.drawable.cancelled);
        }
        else
        {
            holder.imgv_accept.setVisibility(View.GONE);
        }


        holder.imgv_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Uri gmmIntentUri = Uri.parse("google.navigation:q=Amanora mall+amanora mall,+Pune+India");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                context.startActivity(mapIntent);

            }
        });


        /*holder.txtv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, ""+modelArrayList.get(position).getOrder_id(), Toast.LENGTH_SHORT).show();

                Intent i = new Intent(context, OrderDetail.class);
                i.putExtra("order_id", modelArrayList.get(position).getOrder_id());
                context.startActivity(i);

            }
        });*/

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, ""+modelArrayList.get(position).getOrder_id(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, OrderDetail.class);
                i.putExtra("order_id", modelArrayList.get(position).getOrder_id());
                i.putExtra("mobile_no",modelArrayList.get(position).getMobile_number());
                i.putExtra("address",modelArrayList.get(position).getAddress());
                i.putExtra("OrderStatus",modelArrayList.get(position).getDelivery_status());

                context.startActivity(i);
            }
        });


        Performanima2(holder.itemView);



    }

    private void Performanima(View view)
    {
        Animation animZoomIn = AnimationUtils.loadAnimation(context,R.anim.zoomin);
        view.startAnimation(animZoomIn);
    }
    private void Performanima2(View view)
    {
        Animation animZoomIn = AnimationUtils.loadAnimation(context,R.anim.fadedown);
        view.startAnimation(animZoomIn);
    }


    public void Acceptorder(final int position)
    {

        String status = "3";
        HashMap<String,String> params = new HashMap<>();
        params.put("order_id",modelArrayList.get(position).getOrder_id());
        params.put("delivery_boy_id",modelArrayList.get(position).getDelivery_boy_id());
        params.put("order_status",status);

        Toast.makeText(context, ""+modelArrayList.get(position).getOrder_id(), Toast.LENGTH_SHORT).show();
        Toast.makeText(context, ""+modelArrayList.get(position).getDelivery_boy_id(), Toast.LENGTH_SHORT).show();

        VJsonRequest vJsonRequest = new VJsonRequest((Activity) context, ApiParams.ACCEPT_ORDER_URL,params,
                new VJsonRequest.VJsonResponce()
                {
                    @Override
                    public void VResponce(String responce) {

                        Log.e("RESPONSE LOGIN", responce);

                        JSONObject userdata = null;
                        try {
                            userdata = new JSONObject(responce);
                            if (userdata.getInt("status") == 1) {

                                notifyDataSetChanged();
                                myUpdateOperation();
                                //commonActivity.hideProgressBar();

                            } else {
                                MyUtility.showAlertMessage(context, "Fail to accept order");
                                //commonActivity.hideProgressBar();
                            }

                        } catch (JSONException e) {

                            e.printStackTrace();
                            //commonActivity.hideProgressBar();
                        }

                    }

                    @Override
                    public void VError(String responce) {

                        //     Log.e("ERROR", responce);
                    }


                });
    }




    // holder.text_price.setText(" Rs. "+model.getTest_price());




    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }


    public void SetOnItemClickListener(
            final OnItemClickListener itemClickListener) {
        this.clickListener =  itemClickListener;
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }



    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtv_date, txtv_orderid, txtv_total, txtv_timeslot, txtv_cstname, txtv_address;
        TextView txt_call, txtv_map, txtv_detail;
        ImageView imgv_call,imgv_map,imgv_accept,imgv_status;

        ViewHolder(View itemView) {
            super(itemView);

            txtv_date = (TextView) itemView.findViewById(R.id.txtv_date);
            txtv_orderid = (TextView) itemView.findViewById(R.id.txtv_orderid);
            txtv_total = (TextView) itemView.findViewById(R.id.txtv_total);
            txtv_timeslot = (TextView) itemView.findViewById(R.id.txtv_timeslot);
            txtv_cstname = (TextView) itemView.findViewById(R.id.txtv_cstname);
            txtv_address = (TextView) itemView.findViewById(R.id.txtv_address);

            //txt_call = (TextView) itemView.findViewById(R.id.txt_call);
            //txtv_map = (TextView) itemView.findViewById(R.id.txtv_map);
            //imgv_accept = (TextView) itemView.findViewById(R.id.imgv_accept);
            txtv_detail = (TextView)itemView.findViewById(R.id.txtv_detail);
            imgv_call = (ImageView)itemView.findViewById(R.id.imgv_call);
            imgv_map = (ImageView)itemView.findViewById(R.id.imgv_map);
            imgv_accept = (ImageView)itemView.findViewById(R.id.imgv_accept);
            imgv_status = (ImageView)itemView.findViewById(R.id.imgv_status);


        }


    }

    private void myUpdateOperation()
    {
        ((Activity)context).finish();
        ((Activity)context).overridePendingTransition(0, 0);
        ((Activity)context).startActivity(((Activity)context).getIntent());
        ((Activity)context).overridePendingTransition(0, 0);
    }


}