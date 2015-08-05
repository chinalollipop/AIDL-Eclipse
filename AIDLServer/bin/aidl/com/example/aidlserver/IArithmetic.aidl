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