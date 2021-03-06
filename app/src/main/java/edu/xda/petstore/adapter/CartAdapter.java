package edu.xda.petstore.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
import edu.xda.petstore.model.PetCartDto;
import edu.xda.petstore.model.User;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    Context context;
    List<PetCartDto> petList;
    AlertDialog dialog;
    AlertDialog.Builder builder;

    public CartAdapter(Context context, List<PetCartDto> petList) {
        this.context = context;
        this.petList = petList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.cart_row_item, parent, false);// sử dụng layout cart_row_item

        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder,int position) {


        if (petList.get(position).getAnh() != null){// nếu thú cưng có dữ liệu ảnh thì decode và set ảnh cho tưừng items
            byte[] blob= Base64.decode( petList.get(position).getAnh(), Base64.DEFAULT); // decode thông tin ảnh đọc từ database
            Bitmap bmp= BitmapFactory.decodeByteArray(blob,0,blob.length); // decode về dạng bit map
//        ImageView image=new ImageView(this);
            holder.petImage.setImageBitmap(bmp);// set ảnh từ bit map ở trên
        }
        holder.petText.setText(petList.get(position).getMaGiong() + '-' + petList.get(position).getTenGiong());
        holder.petPriceText.setText(String.valueOf(petList.get(position).getDonGia() * petList.get(position).getQty())+" VNĐ");
        holder.qtyTxt.setText(String.valueOf(petList.get(position).getQty()));

        holder.item_cart.setOnClickListener(new View.OnClickListener(){ // bắt sự kiện khi chọn 1 item trong danh sách

            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, PetDetail.class);
                // truyền các tham số vào màn chi tiết
                i.putExtra("maGiong",petList.get(position).getMaGiong());
                i.putExtra("tenGiong",petList.get(position).getTenGiong());
                i.putExtra("moTa",petList.get(position).getMoTa());
                i.putExtra("donGia",petList.get(position).getDonGia());
                i.putExtra("anh",petList.get(position).getAnh());
                i.putExtra("id",petList.get(position).getId());
                // mở  màn chi tiết thông qua intent
                context.startActivity(i);
            }
        });

        holder.upBtn.setOnClickListener(new View.OnClickListener(){// bắt sự kiện cho nút thêm số lượng

            @Override
            public void onClick(View view) {
                int qty = Integer.parseInt(holder.qtyTxt.getText().toString()); // đặt biến số lượng của item đang chọn
                if(qty < 99) {// nếu số lượng nohr hơn 99 thì thực hiện cộng thêm 1 giá trị
                    holder.qtyTxt.setText(String.valueOf(qty += 1));// set lại text số lượng
                    holder.petPriceText.setText(String.valueOf(petList.get(position).getDonGia() * (qty))+" VNĐ");// tính toán lại số tiên fphair trả nếu số lượng thay đổi
                    // tìm thông tin thú cưng này trong giỏ hàng
                    Cart cartExist = PetDatabase.getInstance(context).cartDao().findCart(holder.current.getId(),petList.get(position).getId());
                    cartExist.setQty(qty);// set số lượng cho loại cart này
                    PetDatabase.getInstance(context).cartDao().updateCart(cartExist);// update thông tin giỏ hàng
                    //holder.qtyTxt.setText(String.valueOf(qty += 1));
                    petList.get(position).setQty(qty += 1);// set lại số lượng
                }

            }
        });
        // tương tự như nút thêm số lượng thì ta set cho nút giảm như bên dưới nhưng thay vì cộng thêm 1 giá trị thì nút giảm giảm đi 1 giá trị
        holder.downBtn.setOnClickListener(new View.OnClickListener(){// bắt sự kiện cho nút giảm số lượng

            @Override
            public void onClick(View view) {
                int qty = Integer.parseInt(holder.qtyTxt.getText().toString());
                if(qty > 1) {// nếu số lượng lớn hơn 1 thì mới được giảm (tránh trường hợp số lượng bị âm)
                    holder.qtyTxt.setText(String.valueOf(qty -= 1));
                    holder.petPriceText.setText(String.valueOf(petList.get(position).getDonGia() * (qty))+" VNĐ");
                    Cart cartExist = PetDatabase.getInstance(context).cartDao().findCart(holder.current.getId(),petList.get(position).getId());
                    cartExist.setQty(qty);
                    PetDatabase.getInstance(context).cartDao().updateCart(cartExist);
                    //holder.qtyTxt.setText(String.valueOf(qty -= 1));
                    petList.get(position).setQty(qty -= 1);
                }

            }
        });

        holder.delete_cart.setOnClickListener(new View.OnClickListener(){ // set sự kiện xóa item trong danh sách giỏ hàng

            @Override
            public void onClick(View view) {
                //tạo dialog confirm yes no
                builder = new AlertDialog.Builder(context);
                builder.setTitle("Bạn có chắc muốn xóa khỏi giỏ hàng ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() { // tạo và bắt sự kiện cho nút có
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        PetDatabase.getInstance(context).cartDao().deleteCartById(petList.get(position).getCartId());// xoa sthoong tin giỏ hàng theo id của item đc chọn (cart đang chọn)
                        petList.remove(position);// xóa item trong recycleView ở vị trí chọn để xóa
                        CartAdapter.this.notifyDataSetChanged();// thông báo set ddataa thay đổi
                        Toast.makeText(context,"Đã xóa", Toast.LENGTH_SHORT).show();//  thông báo đã xóa
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() { // tạo và bắt sự kiện hủy
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog = builder.create();
                dialog.show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return petList.size();
    }

    public  static class CartViewHolder extends RecyclerView.ViewHolder{ // holder dùng đẻ khai báo các view trong activity

        ImageView petImage;
        ImageButton upBtn;
        ImageButton downBtn;
        TextView petText;
        TextView qtyTxt;
        TextView petPriceText;
        ConstraintLayout item_cart;
        ImageView delete_cart;
        User current;


        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            current = Login.currentUser;
            item_cart = itemView.findViewById(R.id.item_cart);
            delete_cart = itemView.findViewById(R.id.delete_cart);
            petImage = itemView.findViewById(R.id.petImage);
            petText = itemView.findViewById(R.id.petText);
            petPriceText = itemView.findViewById(R.id.petPriceText);
            qtyTxt = itemView.findViewById(R.id.qtyTxt);
            upBtn = itemView.findViewById(R.id.upBtn);
            downBtn = itemView.findViewById(R.id.downBtn);

        }
    }
}
