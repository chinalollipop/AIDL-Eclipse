# AIDL-Eclipse

在Android中,每个应用程序都有自己的进程，当需要在不同的进程之间传递对象时，该如何实现呢?显然, Java中是不支持跨进程内存共享的。因此要传递对象,需要把对象解析成操作系统能够理解的数据格式,以达到跨界对象访问的目的。在JavaEE中，采用RMI通过序列化传递对象。在Android中,则采用AIDL(Android Interface DefinitionLanguage：接口描述语言)方式实现。
AIDL是一种接口定义语言，用于约束两个进程间的通讯规则，供编译器生成代码，实现Android设备上的两个进程间通信(IPC)。AIDL的IPC机制和EJB所采用的CORBA很类似，进程之间的通信信息，首先会被转换成AIDL协议消息，然后发送给对方，对方收到AIDL协议消息后再转换成相应的对象。由于进程之间的通信信息需要双向转换，所以android采用代理类在背后实现了信息的双向转换，代理类由android编译器生成，对开发人员来说是透明的。
实现进程通信，一般需要下面四个步骤：
假设Client端需要与Server端进行通信，调用Server端中的计算方法，Server端以Service方式向Client端提供服务。需要下面四个步骤:
1>在Server端中创建*.aidl文件，aidl文件的定义和接口的定义很相类，如：在com.example.aidlserver包下创建IArithmetic.aidl文件，内容如下：
/**
定义一个接口，后缀名命名为aidl
接口和方法前不用加访问权限修饰符如public,private,protected等,也不能用final,static。
此例子我是做一个计算器，加减乘除算法的例子

声明此接口之后，如果你使用的Eclipse，Eclise会在gen目录下自动生成包名一致的java文件
点击查看IArithmetic.java文件,你会看到他只会在文件最后添加上你声明的几个方法包括注释
请注意，不要修改IArithmetic.java的文件
*/

package com.example.aidlserver;


interface IArithmetic {
	//加法
	int add(int value1, int value);
	//减法
	int subtract(int value1, int value);
	//乘法
	int ride(int value1, int value);
	//除法
	double divide(int value1, int value);
}
当完成aidl文件创建后，eclipse会自动在项目的gen目录中同步生成IArithmetic.java接口文件。接口文件中生成一个Stub的抽象类，里面包括aidl定义的方法，还包括一些其它辅助方法。值得关注的是asInterface(IBinderiBinder)，它返回接口类型的实例，对于远程服务调用，远程服务返回给客户端的对象为代理对象，客户端在onServiceConnected(ComponentNamename,IBinderservice)方法引用该对象时不能直接强转成接口类型的实例，而应该使用asInterface(IBinderiBinder)进行类型转换。
编写Aidl文件时，需要注意下面几点:
  1.接口名和aidl文件名相同。
  2.接口和方法前不用加访问权限修饰符public,private,protected等,也不能用final,static。
  3.Aidl默认支持的类型包话java基本类型（int、long、boolean等）和（String、List、Map、CharSequence），使用这些类型时不需要import声明。对于List和Map中的元素类型必须是Aidl支持的类型。如果使用自定义类型作为参数或返回值，自定义类型必须实现Parcelable接口。
  4.自定义类型和AIDL生成的其它接口类型在aidl描述文件中，应该显式import，即便在该类和定义的包在同一个包中。
  5.在aidl文件中所有非Java基本类型参数必须加上in、out、inout标记，以指明参数是输入参数、输出参数还是输入输出参数。
  6.Java原始类型默认的标记为in,不能为其它标记。
2>在Server端中实现aidl文件生成的接口（本例是ArithmeticService），但并非直接实现接口，而是通过继承接口的Stub来实现（Stub抽象类内部实现了aidl接口），并且实现接口方法的代码。内容如下：
public class ServiceBinder extends IArithmetic.Stub {  
        

		@Override
		public int add(int value1, int value2) throws RemoteException {
			// TODO Auto-generated method stub
			return (value1 + value2);
		}

		@Override
		public int subtract(int value1, int value2) throws RemoteException {
			// TODO Auto-generated method stub
			return  (value1 - value2);
		}

		@Override
		public int ride(int value1, int value2) throws RemoteException {
			// TODO Auto-generated method stub
			return  (value1 * value2);
		}

		@Override
		public double divide(int value1, int value2) throws RemoteException {
			// TODO Auto-generated method stub
			return  (value1 / value2);
		}         
    }  
3>在Server端中创建一个Service（服务），在服务的onBind(Intentintent)方法中返回实现了aidl接口的对象（本例是ServiceBinder）。内容如下：
/**
 * 此类最主要的就是继承Service，然后通过 Binder去实现AIDL的IArithmetic.Stub的几个方法
 * 具体的实现包括一些具体的逻辑，如加、减、乘、除算法等等
 * 注意在项目清单文件中去注册自己的服务，不然，客户端是获取不到服务端的数据哦*_*
 * 然后把你声明的AIDL文件如IArithmetic.aidl，跟包名一起复制到客户端，这样服务端的代码就算写完了
 */

package com.example.aidlserver;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

public class ArithmeticService extends Service {

	private ServiceBinder sBinder = new ServiceBinder();
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return sBinder;
	}
	public class ServiceBinder extends IArithmetic.Stub {  
        

		@Override
		public int add(int value1, int value2) throws RemoteException {
			// TODO Auto-generated method stub
			return (value1 + value2);
		}

		@Override
		public int subtract(int value1, int value2) throws RemoteException {
			// TODO Auto-generated method stub
			return  (value1 - value2);
		}

		@Override
		public int ride(int value1, int value2) throws RemoteException {
			// TODO Auto-generated method stub
			return  (value1 * value2);
		}

		@Override
		public double divide(int value1, int value2) throws RemoteException {
			// TODO Auto-generated method stub
			return  (value1 / value2);
		}         
    }  

}
编写完成之后，开始编写项目清单文件【注册服务】
其他应用可以通过隐式意图访问服务,意图的动作可以自定义，AndroidManifest.xml配置代码如下：
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.aidlserver"
    android:versionCode="1"
    android:versionName="1.0" >

    <uses-sdk
        android:minSdkVersion="15"
        android:targetSdkVersion="15" />

    <application
        android:allowBackup="true"
        android:icon="@drawable/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/AppTheme" >
        <activity
            android:name=".MainActivity"
            android:label="@string/app_name" >
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <service
            android:name="com.example.aidlserver.ArithmeticService"
            android:process=":remote" >
            <intent-filter>
                <action android:name="com.example.aidlserver.IArithmetic" />
            </intent-filter>
        </service>
    </application>

</manifest>
4>把Server端中aidl文件所在package连同aidl文件一起拷贝到Client客户端中，eclipse会自动在Client端中的gen目录中为aidl文件同步生成IArithmetic.java接口文件,接下来就可以在Client端和Server端之间的通信，代码如下：
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
		// 绑定到服务
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
	 * 获取AIDL接口定义的Bindle对象，用于传递基本数据
	 * java基本类型（int、long、boolean等）和（String、List、Map、CharSequence）
	 * 和自定义的对象[需要自己实例化Parcelable]
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
			Toast.makeText(getBaseContext(), "请输入具体数字", 0).show();
			return;
		}
		int num1 = Integer.parseInt(etValue1.getText().toString());
		int num2 = Integer.parseInt(etValue2.getText().toString());
		switch (v.getId()) {
		// 加法
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
		// 减法
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

		// 乘法
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

		// 除法
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
		// 解除绑定服务
		this.unbindService(serviceConnection);
	}
}

先启动Server服务端的，然后运行Client的程序。
   最后我们在总结一下android中 本地服务和 AIDL服务的区别吧。
     本地服务不支持onBind(),它从onBind()返回null，这种类型的服务只能由承载服务的应用程序组件访问。可以调用 startService（）来调用本地服务。AIDL服务可以同时供 同一进程内的组件和其他应用程序的组件使用。这种类型的服务在AIDL 文件中为自身与其客户端定义一个契约。服务实现 AIDL契约，而客户端绑定到 AIDL定义。服务通过从 onBind()方法 返回AIDL接口的实现，来实现契约。客户端通过调用 bindService()来绑定到AIDL服务，并调用 unBindService()来从服务断开。
