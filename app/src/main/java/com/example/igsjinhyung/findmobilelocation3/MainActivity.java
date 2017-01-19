package com.example.igsjinhyung.findmobilelocation3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button btn_getlatlon, btn_battery, btn_sendServer;
    private Context mContext;
    public static String TAG = "fml";

    private TextView tv_getlatlon, tv_battery, tv_sendServer;
    private TextView mTextLevel, mTextAction, mTextStatus;

    // GPSTracker class
    private GpsManager gps;
    private BroadcastReceiver mBatteryRecv;

    private int b_per;//battery percent
    private  double latitude ;
    private double longitude;
    private InsertData send_server;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getBaseContext();

        btn_getlatlon = (Button) findViewById(R.id.btn_getlatlon);
        btn_battery = (Button) findViewById(R.id.btn_battery);
        btn_sendServer = (Button) findViewById(R.id.btn_sendServer);

        tv_getlatlon = (TextView) findViewById(R.id.tv_getlatlon);
        tv_sendServer = (TextView) findViewById(R.id.tv_sendServer);


        mTextLevel = (TextView) findViewById(R.id.textLevel);
        mTextAction = (TextView) findViewById(R.id.textAction);
        mTextStatus = (TextView) findViewById(R.id.textStatus);
        ;

        BroadcastReceiver mBatteryRecv;

        btn_getlatlon.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "위도경도를 얻어오는 중....", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "btn_getlatlonaaa");

                gps = new GpsManager(mContext);
                String gps_str = gps.getNewWorkSetting();
                // GPS
                if (gps.isGetLocation()) {

                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();

                    tv_getlatlon.setText("위도 : " + String.valueOf(latitude) + " , 경도 : " + String.valueOf(longitude));

                    Toast.makeText(mContext, "위도경도 로딩 완료", Toast.LENGTH_SHORT).show();
                } else {
                    // GPS �� ����Ҽ� �����Ƿ�
                    gps.showSettingsAlert();
                }


            }
        });

        btn_battery.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : click event
                Toast.makeText(mContext, "배터리 확인은 자동으로 변동 됩니다.", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "btn_battery");
            }
        });
        btn_sendServer.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : click event
                Toast.makeText(mContext, "서버에 통신중", Toast.LENGTH_SHORT).show();
                Log.i(TAG, "btn_sendServer");


                TelephonyManager telManager = (TelephonyManager)mContext.getSystemService(mContext.TELEPHONY_SERVICE);
                String phone_num = telManager.getLine1Number();

                phone_num = phone_num.replace("+82", "0");

                Log.i(TAG, "phone_num : "+phone_num);

                InsertData task = new InsertData();
                task.execute((String)phone_num,""+latitude,""+longitude,""+b_per);






            }
        });


        // ��ε�ĳ��Ʈ ���ù� ��ü�� ����
        mBatteryRecv = new BroadcastReceiver() {
            // �̺�Ʈ�� �����ϴ� �̺�Ʈ �Լ�
            public void onReceive(Context context, Intent intent) {
                // ���͸� �ܷ��� ȭ�鿡 ǥ��
                showLevel(intent);
                // ���͸� �̺�Ʈ ������ ȭ�鿡 ǥ��
                showAction(intent);
                // ���͸� ���¸� ȭ�鿡 ǥ��
                showStatus(intent);
            }
        };


        // ���͸� ���� üũ�� ��ε�ĳ��Ʈ ������ ���
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        filter.addAction(Intent.ACTION_BATTERY_LOW);  // ���͸��� ����
        filter.addAction(Intent.ACTION_BATTERY_OKAY);  // ���͸��� ����
        filter.addAction(Intent.ACTION_POWER_CONNECTED);  // �������̺� ����
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);  // �������̺� �и�
        registerReceiver(mBatteryRecv, filter);


    }

    // ��ε�ĳ��Ʈ ������ ��� ����
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBatteryRecv);
    }

    // ���͸� �ܷ��� ȭ�鿡 ǥ��
    public void showLevel(Intent intent) {
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100);
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
        int percent = (int) ((float) level / (float) scale * 100.);
        b_per = percent;
        mTextLevel.setText("Level - " + percent + "%");
    }

    // ���͸� �̺�Ʈ ������ ȭ�鿡 ǥ��
    public void showAction(Intent intent) {
        String strAction = intent.getAction();

        if (strAction == Intent.ACTION_BATTERY_CHANGED)
            mTextAction.setText("Battery Changed");
        else if (strAction == Intent.ACTION_BATTERY_LOW)
            mTextAction.setText("Battery Low");
        else if (strAction == Intent.ACTION_BATTERY_OKAY)
            mTextAction.setText("Battery OK");
        else if (strAction == Intent.ACTION_POWER_CONNECTED)
            mTextAction.setText("Power Connected");
        else if (strAction == Intent.ACTION_POWER_DISCONNECTED)
            mTextAction.setText("Power Disonnected");
    }

    // ���͸� ���¸� ȭ�鿡 ǥ��
    public void showStatus(Intent intent) {
        int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS,
                BatteryManager.BATTERY_STATUS_UNKNOWN);
        switch (status) {
            case BatteryManager.BATTERY_STATUS_CHARGING:
                mTextStatus.setText("Charging");
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                mTextStatus.setText("Discharging");
                break;
            case BatteryManager.BATTERY_STATUS_FULL:
                mTextStatus.setText("Full");
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                mTextStatus.setText("Not Charging");
                break;
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                mTextStatus.setText("Unknown");
                break;
        }
    }


}
