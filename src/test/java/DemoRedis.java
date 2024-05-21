import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * Author: CC
 * E-mail: 203717588@qq.com
 * Date: 2022/12/6
 * Time: 19:17
 * Description:
 */
public class DemoRedis {
    // Redis
    /*
    Redis：是开源的、高性能的key-value(非关系型)数据库
    Redis默认有16个数据库
     */
    // Redis支持的5大数据类型
    /*
    1）String 字符串
    2）Hash 哈希
    3）List 列表
    4）Set 集合
    5）ZSet/Sorted Set 有序集合
     */
    // Java操作Redis数据库
    // 需要导入Java访问Redis的jar依赖

    public static void main(String[] args) {
        // 1.连接Redis数据库
        Jedis jedis = new Jedis("localhost",6379);
        // 2.ping(): 测试数据库连接，PONG表明已连接
        System.out.println(jedis.ping());
        // 3.选择索引为0(db0)的数据库
        jedis.select(0);

        // 4.Java操作Redis的5大数据类型存取

        // 1)String
        // 存储: set(key,value)
        jedis.set("陕西","西安");
        // 取出: get(key)
        System.out.println(jedis.get("陕西"));

        // 2)Hash
        HashMap<String, String> map = new HashMap<>();
        map.put("Tom","北京");
        map.put("Jerry","上海");
        // 存储：hmset(key,map)
        jedis.hmset("friends",map);
        // 取出：hget()/hgetAll()
        Map<String, String> friends = jedis.hgetAll("friends");
        System.out.println(friends);

        // 3)List
        // 存储：lpush(key,list)
        jedis.lpush("students","张三","李四");
        // 取出：lrange(key,startIndex,endIndex)
        List<String> students = jedis.lrange("students", 0, 10);
        System.out.println(students);

        // 4)Set
        // 存储：sadd(key,set)
        jedis.sadd("中国","北京","上海","深圳","上海","深圳");
        // 取出：smembers(key)
        Set<String> china = jedis.smembers("中国");
        System.out.println(china);

        // 5)ZSet
        // 存储：zadd(key,排序数Double,value)/zadd(key,map<String,Double>)
        jedis.zadd("books",66.6,"西游记");
        HashMap<String, Double> bookMap = new HashMap<>();
        bookMap.put("红楼梦",88.8);
        bookMap.put("水浒传",77.7);
        jedis.zadd("books",bookMap);
        // 取出：zrangeByScore(key,排序数min,排序数max)
        Set<String> books = jedis.zrangeByScore("books", 0, 999);
        System.out.println(books);

        // Redis设置数据过期时间

        // nxxx：NX/XX NX: 当key不存在时设置 XX: 当key存在时设置
        // expx：EX/PX EX: 单位秒 PX: 单位毫秒
        // time：过期时间
        jedis.set("key","value","NX","EX",20);
        jedis.setex("key",20,"value");
        System.out.println(jedis.get("名称"));

    }

}
