package com.smart.elevator.view;



import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.model.LatLng;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.smart.elevator.R;
import com.smart.elevator.bean.Elevator;
import com.smart.elevator.bean.Task;
import com.smart.elevator.constant.Constant;
import com.smart.elevator.data.DBManger;
import com.smart.elevator.util.MapUtil;
import com.smart.elevator.util.QRCodeUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ReportDialog extends Dialog {

    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private boolean isBackCancelable;//控制返回键是否dismiss
    private View view;
    private Context context;
    private Button mReportDecodeBtn;
    private Button mReportSureBtn;
    private ImageView mQrcode;
    private Elevator mElevator;
    private TextView mQrcodeTv;

    private String mDecode = "";

    public ReportDialog(Context context, int layoutid, boolean isCancelable, boolean isBackCancelable) {
        super(context, R.style.MyDialog);

        this.context = context;
        this.view = LayoutInflater.from(context).inflate(layoutid, null);
        this.iscancelable = isCancelable;
        this.isBackCancelable = isBackCancelable;

        initView();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(view);//这行一定要写在前面
        setCancelable(iscancelable);//点击外部不可dismiss
        setCanceledOnTouchOutside(isBackCancelable);


    }

    public void setData(Elevator elevator){
        this.mElevator = elevator;
        Bitmap bitmap = QRCodeUtil.createQRCodeBitmap(mElevator.getLIFT_ADDRESSID(),300,300);
        if (bitmap!=null){
            mQrcode.setImageBitmap(bitmap);
            //长按，通过zxing读取图片，判断是否有二维码
            mQrcode.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View viewm) {
                    return false;
                }
            });
        }
    }

    public void initView() {
        mReportDecodeBtn = view.findViewById(R.id.decode_btn);
        mReportSureBtn = view.findViewById(R.id.report_btn);
        mQrcode = view.findViewById(R.id.sign_qrcode);
        mQrcodeTv = view.findViewById(R.id.qrcode_place);
        mReportDecodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decodeBitmap();
                Toast.makeText(getContext(),"识别成功！",Toast.LENGTH_LONG).show();
            }
        });

        mReportSureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBManger.getInstance(getContext()).reportTask(mElevator);
                Toast.makeText(getContext(),"已报修！",Toast.LENGTH_LONG).show();
                dismiss();
            }
        });
    }

    public void decodeBitmap(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap obmp = ((BitmapDrawable) (mQrcode).getDrawable()).getBitmap();
                int width = obmp.getWidth();
                int height = obmp.getHeight();
                int[] data = new int[width * height];
                obmp.getPixels(data, 0, width, 0, 0, width, height);
                RGBLuminanceSource source = new RGBLuminanceSource(width, height, data);
                BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
                QRCodeReader reader = new QRCodeReader();
                Result re = null;
                try {
                    re = reader.decode(bitmap1);
                    mDecode = re.getText();
                    LatLng latLng = DBManger.getInstance(getContext()).mDataFactory.mCurrentPosition;
                    String user_palce = latLng.longitude+","+latLng.latitude;
                    mHandler.sendEmptyMessage(1);
                } catch (NotFoundException e) {
                    e.printStackTrace();
                } catch (ChecksumException e) {
                    e.printStackTrace();
                } catch (FormatException e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    public Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    mQrcodeTv.setText("二维码地址："+mDecode);
                    break;
            }
            return false;
        }
    });

    String pattern = "yyyy-MM-dd HH:mm:ss";
    public static long getStringToDate(String dateString, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
        try{
            date = dateFormat.parse(dateString);
        } catch(ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }


    public String getDateTime(long time){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(time);
        String dateStr = simpleDateFormat.format(date);
        return dateStr;
    }
}