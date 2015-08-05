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
