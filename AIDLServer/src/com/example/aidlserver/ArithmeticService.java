/**
 * ��������Ҫ�ľ��Ǽ̳�Service��Ȼ��ͨ�� Binderȥʵ��AIDL��IArithmetic.Stub�ļ�������
 * �����ʵ�ְ���һЩ������߼�����ӡ������ˡ����㷨�ȵ�
 * ע������Ŀ�嵥�ļ���ȥע���Լ��ķ��񣬲�Ȼ���ͻ����ǻ�ȡ��������˵�����Ŷ*_*
 * Ȼ�����������AIDL�ļ���IArithmetic.aidl��������һ���Ƶ��ͻ��ˣ���������˵Ĵ������д����
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
