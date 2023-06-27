# E-commerceManagementPlatform
基于SpringBoot编写</br>
main分支，是一般的技术所编写</br>
master分支开始更新技术</br>
上次更新的是使用token保存用户信息，取代以前的session保存信息</br>
2023.6.15</br>
通过excel批量上传商品，对上传的图片加水印</br>
批量上传的三种方法，就是对传入的参数进行校验的三种方法</br>
查询订单数，可能会遇到的问题——时区，大概三个方面的问题</br>
1、MySql的时区 运行 select new(); 查看mysql的时区和当前电脑的时区能不能对上</br>
2、SpringBoot的时区 System.out.println(TimeZone.getDefault());查看</br>
3、JSON转换时区 spring.jackson.time-zone=Asia/Shanghai   配置文件中加上</br>
2023.6.27</br>
前后端联调基本完成，主要是跨域问题</br>
前端有两个模块，一个是前台，一个是后台，基于VUE编写</br>
前端先启动后台，再启动前台，后台端口:8080;前台端口:8081</br>
后端端口8083</br>
