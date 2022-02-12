package edu.xda.petstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import edu.xda.petstore.adapter.CartAdapter;
import edu.xda.petstore.database.PetDatabase;
import edu.xda.petstore.model.Cart;
import edu.xda.petstore.model.User;

public class PetDetail extends AppCompatActivity {

    ImageView anh;
    TextView tenGiong , moTa, donGia;

    AlertDialog dialog;
    AlertDialog.Builder builder;

    int coutNumberPetInCart(){
//        int userId = getIntent().getExtras().getInt("userId");
        int userId = Login.currentUser.getId();
        return PetDatabase.getInstance(this).cartDao().searchCart(userId).size();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_detail);

        Intent i = getIntent();
        // dùng intent lấy các tham số từ các activity khác truyền vào
        i.getStringExtra("tenGiong");
        i.getStringExtra("moTa");
        i.getExtras().getInt("donGia");
        i.getExtras().getByteArray("anh");

        anh = findViewById(R.id.pet_image_detail);
        tenGiong = findViewById(R.id.pet_name_detail);
        moTa = findViewById(R.id.pet_info_detail);
        donGia = findViewById(R.id.pet_price_detail);

        tenGiong.setText(i.getStringExtra("tenGiong"));
        moTa.setText(i.getStringExtra("moTa"));
        donGia.setText(i.getExtras().getInt("donGia") + " VNĐ");

        if (i.getExtras().getByteArray("anh") != null){ // hàm này dùng để đọc ảnh từ data base
            // ảnh có dạng BLOB
            byte[] blob= Base64.decode( i.getExtras().getByteArray("anh"), Base64.DEFAULT);// convert ảnh về dạng byte[]
            Bitmap bmp= BitmapFactory.decodeByteArray(blob,0,blob.length);// decode ảnh
//        ImageView image=new ImageView(this);
            anh.setImageBitmap(bmp);// set ảnh bằng bitmap đã decode ở trên
        }
        // sử dụng ảnh đc lưu dạng blob ở database => máy nào cũng có thể đọc được mà ko bị phụ thuộc như ảnh lưu dưới dạng đường dẫn

        findViewById(R.id.cart_detail).setOnClickListener(new View.OnClickListener() { // bắt sự kiện thêm thú cưng vào trong giỏ hàng của user hiện tại
            @Override
            public void onClick(View view) {
                //tạo dialog yes no
                builder = new AlertDialog.Builder(PetDetail.this);
                builder.setTitle("Bạn có chắc muốn thêm vào giỏ hàng ?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() { // tạo và bắt sự kiện khi nhấn yês
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent i2 = getIntent();
                        // check xem nếu thú cưng hiện tại đã có tong giỏ hàng hay chưa
                        Cart cartExist = PetDatabase.getInstance(PetDetail.this).cartDao().findCart(Login.currentUser.getId(),i2.getExtras().getInt("id"));
                        Cart cartInfo = new Cart(Login.currentUser.getId(),i2.getExtras().getInt("id"),1);// tạo mới thông tin giỏ hàng
                        // nếu thú cưng hiện tại đã tồn tại trong cử hàng (tức cartExist != null) thì chỉ cần cộng số lượng của thông tin giỏ hàng đó (cộng thêm qty)
                        // nếu chưa có thú cưng này trong giỏ hàng thì thêm mới cartInfo mới khai báo ở trên vào trong giỏ hàng với qty = 1 (số lượng 1 )
                        if (cartExist == null){ // nếu chưa tồn tại thú cưng này trong giỏ hàng
                            PetDatabase.getInstance(PetDetail.this).cartDao().insertCart(cartInfo); // insert thông tin mới vào trong giỏ hàng
                        }
                        else {// nếu đã toonf tại thú cưng này trong giỏ hàng thì tìm thông tin thú cưng đó và cộng số lượng (tức qty ) lên 1 giá trị
                            int qty = cartExist.getQty();
                            cartExist.setQty(qty += 1);// cộng thêm 1 số lượng
                            PetDatabase.getInstance(PetDetail.this).cartDao().updateCart(cartExist);// cập nhật thoonng tin số lượng trong giỏ hàng
                        }
                        // thông báo thêm vào giỏ hàng thành công
                        Toast.makeText(PetDetail.this,"Thêm vào giỏ hàng thành công", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() { // bắt sự kiện hủy
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog = builder.create();
                dialog.show();


            }
        });

        // bắt sự kiện cho nút mua
        findViewById(R.id.buy_detail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //tạo dialog xác nhận thông tin cá nhân trước khi mua hàng
                builder = new AlertDialog.Builder(PetDetail.this);
                builder.setTitle("Xác nhận thông tin liên hệ");

                // tạo chay dialog để ađ được sự kiện cho nút xác nhận
                // tạo input cho dialog xác nhận mua
                final EditText tel = new EditText(PetDetail.this);// tạo text số ddienj thoại
                final EditText address = new EditText(PetDetail.this);// tạo text địa chỉ
                final TextView telTxt = new TextView(PetDetail.this);// label cho text số điện thoại
                final TextView addressTxt = new TextView(PetDetail.this);// label cho text đaiạ chỉ
                final Button confirm = new Button(PetDetail.this);// nút confirm
                confirm.setText("Xác nhận"); // nút confimr


                telTxt.setText("Số điện thoại liên hệ");// set text cho label
                addressTxt.setText("Địa chỉ liên hệ/ nhận hàng");// set text cho label
                tel.setText(Login.currentUser.getTel());// set giá trị cho số điện thoại
                address.setText(Login.currentUser.getAddress());// set giá trị cho số điện thoại
                // bên dưới là tạo dialog bằng tay
                LinearLayout layout = new LinearLayout(PetDetail.this);

                layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.addView(telTxt);
                layout.addView(tel);
                layout.addView(addressTxt);
                layout.addView(address);
                layout.addView(confirm);
                builder.setView(layout); // set dialog
                confirm.setOnClickListener(new View.OnClickListener() { // bắt sự kiện cho nút xác nhận
                    @Override
                    public void onClick(View view) {
                        if (TextUtils.isEmpty(tel.getText()) || TextUtils.isEmpty(address.getText())){ // nếu chưa đủ thông tin số điện thoại và địa chỉ thì thông báo cần cung cấp đầy đủ thông tin trước khi mua
                            Toast.makeText(PetDetail.this,"Vui lòng cung cấp đầy đủ thông tin xác nhận trước khi mua hàng", Toast.LENGTH_SHORT).show();
                        }
                        else{ // nếu đã đủ thông tin đại chỉ  và số diện thoại thì cập nhật thông tin cho người dùng đang đăng nhập và thông báo mua thành công
                            User u = Login.currentUser; // lấy user hiện tại đang đăng nhập
                            u.setAddress(address.getText().toString());// set giá trị số diueenj thoại
                            u.setTel(tel.getText().toString());// set giá trị điuạ chỉ
                            PetDatabase.getInstance(PetDetail.this).userDao().updateUser(u);// update user hiện tại
                            dialog.dismiss();// tự động đóng modal khi đã xác nhận
                            // thông báo mua hàng thành công
                            Toast.makeText(PetDetail.this,"Cám ơn đã mua hàng của cửa hàng PetStore , chúng tôi sẽ liên hệ và giao hàng cho bạn sớm nhất có thể", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

                dialog = builder.create();// tạo dialog trên
                dialog.show();//  show dialog


            }
        });

        findViewById(R.id.back_button).setOnClickListener(new View.OnClickListener() { // bắt sự kiện cho nút quaylaij
            @Override
            public void onClick(View view) {
                finish();// finish activity hiện tại => chuyển về activity trước đó

//                Intent i = new Intent(PetDetail.this, Home.class);
//                PetDetail.this.startActivity(i);
            }
        });
    }
}