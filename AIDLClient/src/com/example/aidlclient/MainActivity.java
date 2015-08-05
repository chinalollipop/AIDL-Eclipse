package com.example.aidlclient;

import com.example.aidlserver.IArithmetic;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	EditText etValue1, etValue2;
	Button bAdd, bSubtract, bRide, bDivide;
	TextView mTvResult;
	ServiceConnection AddServiceConnection;
	private IArithmetic arithmeticService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// �󶨵�����
		this.bindService(new Intent("com.example.aidlserver.IArithmetic"),
				this.serviceConnection, BIND_AUTO_CREATE);
		etValue1 = (EditText) findViewById(R.id.value1);
		etValue2 = (EditText) findViewById(R.id.value2);
		mTvResult = (TextView) findViewById(R.id.tv_result);
		bAdd = (Button) findViewById(R.id.add);
		bSubtract = (Button) findViewById(R.id.subtract);
		bRide = (Button) findViewById(R.id.ride);
		bDivide = (Button) findViewById(R.id.divide);
		bAdd.setOnClickListener(this);
		bSubtract.setOnClickListener(this);
		bRide.setOnClickListener(this);
		bDivide.setOnClickListener(this);
	}

	/**
	 * ��ȡAIDL�ӿڶ����Bindle�������ڴ��ݻ�������
	 * java�������ͣ�int��long��boolean�ȣ��ͣ�String��List��Map��CharSequence��
	 * ���Զ���Ķ���[��Ҫ�Լ�ʵ����Parcelable]
	 */
	private ServiceConnection serviceConnection = new ServiceConnection() {
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {

			arithmeticService = IArithmetic.Stub.asInterface(service);
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			arithmeticService = null;
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (TextUtils.isEmpty(etValue1.getText().toString())
				|| TextUtils.isEmpty(etValue2.getText().toString())) {
			Toast.makeText(getBaseContext(), "�������������", 0).show();
			return;
		}
		int num1 = Integer.parseInt(etValue1.getText().toString());
		int num2 = Integer.parseInt(etValue2.getText().toString());
		switch (v.getId()) {
		// �ӷ�
		case R.id.add: {
			Log.d("ClientActivity", "num1 Add num2 = ");
			try {
				mTvResult
						.setText("Result:" + arithmeticService.add(num1, num2));
				Log.d("ClientActivity", "Binding - Add operation");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			break;
		// ����
		case R.id.subtract: {

			Log.d("ClientActivity", "num1 Subtract num2 = ");
			try {
				mTvResult.setText("Result:"
						+ arithmeticService.subtract(num1, num2));
				Log.d("ClientActivity", "Binding - Add operation");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			break;

		// �˷�
		case R.id.ride: {

			Log.d("ClientActivity", "num1 Ride num2 = ");
			try {
				mTvResult.setText("Result:"
						+ arithmeticService.ride(num1, num2));
				Log.d("ClientActivity", "Binding - Add operation");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			break;

		// ����
		case R.id.divide: {
			Log.d("ClientActivity", "num1 Divide num2 = ");
			try {
				mTvResult.setText("Result:"
						+ arithmeticService.divide(num1, num2));
				Log.d("ClientActivity", "Binding - Add operation");
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
			break;

		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// ����󶨷���
		this.unbindService(serviceConnection);
	}
}
