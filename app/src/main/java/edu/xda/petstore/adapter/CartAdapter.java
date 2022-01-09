package edu.xda.petstore.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.xda.petstore.Login;
import edu.xda.petstore.PetDetail;
import edu.xda.petstore.R;
import edu.xda.petstore.database.PetDatabase;
import edu.xda.petstore.model.Cart;
import edu.xda.petstore.model.Pet;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    Context context;
    List<Pet> petList;

    public CartAdapter(Context context, List<Pet> petList) {
        this.context = context;
        this.petList = petList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cart_row_item, parent, false);

        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {


        if (petList.get(position).getAnh() != null){
            byte[] blob= Base64.decode( petList.get(position).getAnh(), Base64.DEFAULT);
            Bitmap bmp= BitmapFactory.decodeByteArray(blob,0,blob.length);
//        ImageView image=new ImageView(this);
            holder.petImage.setImageBitmap(bmp);
        }
        holder.petText.setText(petList.get(position).getMaGiong() + '-' + petList.get(position).getTenGiong());
        holder.petPriceText.setText(String.valueOf(petList.get(position).getDonGia())+" VNĐ");

        holder.itemView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, PetDetail.class);
                i.putExtra("maGiong",petList.get(position).getMaGiong());
                i.putExtra("tenGiong",petList.get(position).getTenGiong());
                i.putExtra("moTa",petList.get(position).getMoTa());
                i.putExtra("donGia",petList.get(position).getDonGia());
                i.putExtra("anh",petList.get(position).getAnh());
                context.startActivity(i);
            }
        });

//        holder.delete_cart.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View view) {
////                PetDatabase.getInstance(context).cartDao().deleteCart(new Cart(1,1));
//                Toast.makeText(context,"Đã xóa", Toast.LENGTH_SHORT).show();
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    public  static class CartViewHolder extends RecyclerView.ViewHolder{

        ImageView petImage;
        TextView petText;
        TextView petPriceText;
//        ConstraintLayout item_cart;
        ImageView delete_cart;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

//            item_cart = itemView.findViewById(R.id.item_cart);
            petImage = itemView.findViewById(R.id.petImage);
            petText = itemView.findViewById(R.id.petText);
            petPriceText = itemView.findViewById(R.id.petPriceText);

        }
    }
}
