/**
����һ���ӿڣ���׺������Ϊaidl
�ӿںͷ���ǰ���üӷ���Ȩ�����η���public,private,protected��,Ҳ������final,static��
������������һ�����������Ӽ��˳��㷨������

�����˽ӿ�֮�������ʹ�õ�Eclipse��Eclise����genĿ¼���Զ����ɰ���һ�µ�java�ļ�
����鿴IArithmetic.java�ļ�,��ῴ����ֻ�����ļ����������������ļ�����������ע��
��ע�⣬��Ҫ�޸�IArithmetic.java���ļ�
*/

package com.example.aidlserver;


interface IArithmetic {
	//�ӷ�
	int add(int value1, int value);
	//����
	int subtract(int value1, int value);
	//�˷�
	int ride(int value1, int value);
	//����
	double divide(int value1, int value);
}