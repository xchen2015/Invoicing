1, Install windows service.
	Install the service named 'Tomcat7'
	C:\> service.bat install
	Install the service named 'MyService'
	C:\> service.bat install MyService

2, Update windows service.
	Update the service named 'Tomcat7'
	C:\> tomcat7 //US//Tomcat7 --Description="Apache Tomcat Server - http://tomcat.apache.org/ " \
	Update the service named 'MyService'
	C:\> tomcat7 //US//MyService --Description="Apache Tomcat Server - http://tomcat.apache.org/ " \
		
3, Remove windows service.
	Remove the service named 'Tomcat7'
	C:\> tomcat7 //DS//Tomcat7
	Remove the service named 'MyService'
	C:\> tomcat7 //DS//MyService

4, Update related files.
	catalina.bat
	set JAVA_OPTS=-server -Xms256m -Xmx256m -XX:PermSize=64M -XX:MaxNewSize=256m -XX:MaxPermSize=256m
	SET CATALINA_OPTS=-server -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5999 -Dpinfly.props.dir="%CATALINA_HOME%\PinFly\Common\properties" -Dpinfly.common.dir="%CATALINA_HOME%\PinFly\Common"
	
	service.bat
	-Dpinfly.props.dir=%CATALINA_BASE%\PinFly\Common\properties 
	-XX:MaxPermSize=512m
	
	更新后要重新生成windows service
	
5, Known problem.
	http://blog.csdn.net/kongls08/article/details/8554748
	
6, common sql:
	SELECT * FROM pc_order p;
	SELECT * FROM pc_order_item p;
	SELECT * FROM pc_goods p;
	SELECT * FROM pc_customer p;
	
	drop table pc_account;
	drop table pc_account_type;
	drop table pc_contact;
	drop table pc_customer;
	drop table pc_goods;
	drop table pc_goods_storage;
	drop table pc_order;
	drop table pc_order_item;
	drop table pc_payment;
	drop table pc_payment_type;
	drop table pc_expense;
	drop table pc_expense_type;
	
	delete from pc_contact;
	delete from pc_customer;
	delete from pc_goods;
	delete from pc_goods_storage;
	delete from pc_order;
	delete from pc_order_item;
	delete from pc_payment;
	delete from pc_payment_type;
	delete from pc_expense;
	delete from pc_expense_type;
	
7, 系统角色
	boss(超级管理员) —— boss
   	普通管理员(boss下放权力) —— admin
   	业务员用户(适用于业务员) —— salesman
   	相关支持用户(适用于配送员) —— support
   	游客(只给予查看功能) —— viewer
   	
8, 备份和恢复环境安装：设置bin目录到path环境变量

9, keytool -genkey -alias PinFeiKey2 -keyalg RSA -dname "CN=PinFei" -validity 360 -keysize 2048 -keystore crypto.keystore -storepass PinFeiKey201177! -keypass PinFeiKey201177!
   keytool -list -v -keystore crypto.keystore -storepass PinFeiKey201177!
   keytool -export -keystore crypto.keystore -storepass PinFeiKey201177! -alias PinFeiKey2 -file PinFeiCer.cer
   
   keytool -genseckey -alias PinFeiKey -keyalg AES -keysize 128 -storetype JCEKS -keystore crypto.keystore
   keytool -list -v -keystore crypto.keystore -storetype JCEKS -storepass k1wBZ8LE8OnliYuwQWGOxNpyYeB7
   hrscrypto
   k1wBZ8LE8OnliYuwQWGOxNpyYeB7
   KeuQtJBP4BaPyeJbPT54AF3J656cy5
   
   	
   	Keystore type: JKS
	Keystore provider: SUN
	
	Your keystore contains 1 entry
	
	Alias name: pinfeikey2
	Creation date: Mar 25, 2015
	Entry type: PrivateKeyEntry
	Certificate chain length: 1
	Certificate[1]:
	Owner: CN=PinFei
	Issuer: CN=PinFei
	Serial number: 55128547
	Valid from: Wed Mar 25 17:52:07 CST 2015 until: Sat Mar 19 17:52:07 CST 2016
	Certificate fingerprints:
	         MD5:  7A:73:75:18:9A:E9:91:6E:10:4B:0F:81:D4:4A:E3:8C
	         SHA1: 8F:DB:89:D8:74:F1:0B:D0:34:0E:BE:2D:14:8F:70:8F:C3:A2:D6:7B
	         Signature algorithm name: SHA1withRSA
	         Version: 3

