什么是shiro?
1Apache的强大灵活的开源安全框架
2认证.授权.企业回话管理.安全加密

Shiro自定义Realm,内置Realm:
1.IniRealm
2.JdbcRealm

Shiro加密，Shiro散列配置：
1HashedCredentialsMAtcher
2自定义Realm中使用散列
3盐的使用

要想让包名分开，创建类的时候写上dao.类名就行了
@Service、@Repository等等这些注解要注解在impl类上面，别注解在interface上面

shiro中的数据信息是在realm中定义的，所以先查看realm,有时候定义在hashmap中而不是数据库中

shiro过滤器：
1认证过滤器：anon（不需要认证）,authBasic(HttpBasic),authc（需要认证）,user（当前存在用户即可访问）,logout
2授权过滤器：perms,roles,ssl,port

Shiro   会话（session）管理：
1SessionManager,SessionDAO(session增删改查)
2Redis实现Session共享
3Redis实现session共享存在的问题

ctrl+shift+u:可以吧所写的英文字母变成大写

servlet-api 是servlet 3.0 版本之前的地址
javax.servlet-api 是servlet 3.0 版本之后的地址

Shiro缓存管理：
1CacheManager.Cache
2Redis实现cacheManager---配置类覆盖，要写在xml文件中

写好了放在自己的GitHub上面给别人看




