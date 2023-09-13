//将登录用户的权限菜单树转成json串并保存到redis（不使用redis工具）
//	redisTemplate.opsForValue().set(userId + ":authTree", JSON.toJSONString(authTreeList));
//String authTreeListJson = redisTemplate.opsForValue().get(userId + ":authTree");
//  json转对象    List<Auth> allAuthTreeList = JSON.parseArray(allAuthTreeJson, Auth.class);

//------------ 问题 --------------------------------------------------------------------------
//禁用启用不要重新刷新整个list????
//自动的时间更新不准确
//更改权限后还是从redis拿，没有更新权限列表
//添加修改时间注解都会修改，那要是某个就不想执行呢

//--------------------------------------------------------------------------------------------------
//redis作用：持久层数据缓存，判断用户是否退出，保存用户权限信息

//---------------------------------------------------------------------------------------
//前端请求参数以: ?....&拼接，后台只需要设置 同样参数名的参数 接受即可 有对象，对象自动实例

//持久层标准:insert-delete-update-select   业务层增删改查：save.. remove... update...  query...

//ProductServiceImpl extends ServiceImpl<ProductMapper,Product>,服务层实现mp-ls的自带crud方法，比mapper更加全面。
//但是想要使用，必须接口也有这些方法，不然不给调，所以接口也要继承这些方法 extends IService<Product>
