# Maven
- [x] 导入HCNetSDK.java
- [x] 缺失的jar包导入

`-DgroupId=com.sun.jna -DartifactId=jna`

`-DgroupId=com.sun.jna.examples -DartifactId=jna.examples -Dversion=1.0`

# App层初始化
- [x] 海康通讯服务
- [x] 调用海康初始化接口，判断成功失败，失败日志
- [x] 配置sdk库路径

# 回调函数中处理逻辑
- [x] case事件
- 暂存、转发daas封装

# 数据库
- [x] 根据case建表
  > 交通事件表 tbl_radar_camera_log
- [x] domain，mapper层

# 转发入库
- 按事件入库

# 概念
- 报警方式
> “布防”报警方式：是指 SDK 主动连接设备，并发起报警上传命令，设备发生报警立即发送给 SDK
> 
> “监听”报警方式：是指 SDK 不主动发起连接设备，只是在设定的端口上监听接收设备主动上传的报警
信息。
- 
