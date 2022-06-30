package chiya.web.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import chiya.core.base.object.ObjectUtil;
import chiya.core.base.string.StringUtil;

/**
 * 缓存通过用操作
 * 
 * @author chiya
 */
public class BaseRedisService {
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;

	/**
	 * 设置key和value
	 * 
	 * @param key   键
	 * @param value 值
	 */
	public void set(String key, Object value) {
		redisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 设置key和value和超时时间
	 * 
	 * @param key   键
	 * @param value 值
	 * @param time  超时时间，单位：毫秒
	 */
	public void set(String key, Object value, long time) {
		redisTemplate.opsForValue().set(key, value, time, TimeUnit.MILLISECONDS);
	}

	/**
	 * 根据key获取值
	 * 
	 * @param key 键
	 * @return 内容对象
	 */
	public Object get(String key) {
		return redisTemplate.opsForValue().get(key);
	}

	/**
	 * 根据key删除值
	 * 
	 * @param key 键名
	 * @return 成功/失败
	 */
	public boolean delete(String key) {
		return redisTemplate.delete(key);
	}

	/**
	 * 删除多个键
	 * 
	 * @param keys 多个键值
	 * @return 受影响数据
	 */
	public long delete(List<String> keys) {
		return redisTemplate.delete(keys);
	}

	/**
	 * 设置key的超超时时间
	 * 
	 * @param key  键
	 * @param time 超时时间
	 * @return 成功/失败
	 */
	public boolean timeOut(String key, long time) {
		return redisTemplate.expire(key, time, TimeUnit.MILLISECONDS);
	}

	/**
	 * 获取超时时间
	 * 
	 * @param key 键
	 * @return 超时时间
	 */
	public long getTimeOut(String key) {
		return redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
	}

	/**
	 * 判断这个key是否存在
	 * 
	 * @param key
	 * @return 成功/失败
	 */
	public boolean containsKey(String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 自增一个数
	 * 
	 * @param key  键名
	 * @param step 步长
	 * @return 自增后的长度
	 */
	public long increased(String key, long step) {
		return redisTemplate.opsForValue().increment(key, step);
	}

	/**
	 * 自增1
	 * 
	 * @param key 键名
	 * @return 自增后的长度
	 */
	public long increased(String key) {
		return redisTemplate.opsForValue().increment(key);
	}

	/**
	 * 自减一个数
	 * 
	 * @param key  键名
	 * @param step 步长
	 * @return 自减后的长度
	 */
	public long decrement(String key, long step) {
		return redisTemplate.opsForValue().decrement(key, step);
	}

	/**
	 * 自减一个数
	 * 
	 * @param key 键名
	 * @return 自减后的长度
	 */
	public long decrement(String key) {
		return redisTemplate.opsForValue().decrement(key);
	}

	/**
	 * 获取Hash结构中的属性
	 * 
	 * @param key     键
	 * @param hashKey 哈希key
	 * @return
	 */
	public Object hashGet(String key, String hashKey) {
		return redisTemplate.opsForHash().get(key, hashKey);
	}

	/**
	 * 根据Key设置Hash结构的值，以及超时时间
	 * 
	 * @param key     键
	 * @param hashKey 哈希key
	 * @param value   值
	 * @param time    超时时间
	 * @return 成功/失败
	 */
	public boolean hashSet(String key, String hashKey, Object value, long time) {
		redisTemplate.opsForHash().put(key, hashKey, value);
		return timeOut(key, time);
	}

	/**
	 * 根据key获取Hash结构的值
	 * 
	 * @param key     键
	 * @param hashKey 哈希key
	 * @param value   值
	 * @return 成功/失败
	 */
	public void hashSet(String key, String hashKey, Object value) {
		redisTemplate.opsForHash().put(key, hashKey, value);
	}

	/**
	 * 根据key获取全部hash结构
	 * 
	 * @param key 键
	 * @return Map<String, Object>
	 */
	public Map<Object, Object> hashGetAll(String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	/**
	 * 根据key获取Hash结构的值，和超时时间
	 * 
	 * @param key  键
	 * @param map  Map对象
	 * @param time 超时时间
	 * @return 成功/失败
	 */
	public boolean hashSetAll(String key, Map<String, ?> map, long time) {
		redisTemplate.opsForHash().putAll(key, map);
		return timeOut(key, time);
	}

	/**
	 * 根据key获取Hash结构的值
	 * 
	 * @param key 键
	 * @param map Map对象
	 * @return 成功/失败
	 */
	public void hashSetAll(String key, Map<String, ?> map) {
		redisTemplate.opsForHash().putAll(key, map);
	}

	/**
	 * 根据key删除多个hashKey
	 * 
	 * @param key     键
	 * @param hashKey 多个哈希key
	 */
	public void hashDelete(String key, Object... hashKey) {
		redisTemplate.opsForHash().delete(key, hashKey);
	}

	/**
	 * 判断Hash结构中是否有该属性
	 * 
	 * @param key
	 * @param hashKey
	 * @return 成功/失败
	 */
	public boolean hashContainsKey(String key, String hashKey) {
		return redisTemplate.opsForHash().hasKey(key, hashKey);
	}

	/**
	 * 根据key设置hash结构的自增
	 * 
	 * @param key     键
	 * @param hashKey 哈希Key
	 * @param step    自增步长
	 * @return 自增值
	 */
	public long hashIncreased(String key, String hashKey, long step) {
		return redisTemplate.opsForHash().increment(key, hashKey, step);
	}

	/**
	 * 根据key设置hash结构的自减
	 * 
	 * @param key     键
	 * @param hashKey 哈希Key
	 * @param step    自增步长
	 * @return 自减
	 */
	public long hashDecrement(String key, String hashKey, long step) {
		return redisTemplate.opsForHash().increment(key, hashKey, -step);
	}

	/**
	 * 根据key获取set结构
	 * 
	 * @param key 键
	 * @return Set<Object>
	 */
	public Set<Object> getSet(String key) {
		return redisTemplate.opsForSet().members(key);
	}

	/**
	 * 根据key获取set结构
	 * 
	 * @param key    键
	 * @param values 多个值
	 * @return 添加数据量
	 */
	public long setAdd(String key, Object... values) {
		return redisTemplate.opsForSet().add(key, values);
	}

	/**
	 * 根据key设置set结构
	 * 
	 * @param key    键
	 * @param time   超时时间
	 * @param values 多个值
	 * @return 添加数据量
	 */
	public long setAdd(String key, long time, Object... values) {
		Long count = redisTemplate.opsForSet().add(key, values);
		timeOut(key, time);
		return count;
	}

	/**
	 * 判断set中的值是否存在
	 * 
	 * @param key   键
	 * @param value 值
	 * @return 存在/不存在
	 */
	public boolean setContains(String key, Object value) {
		return redisTemplate.opsForSet().isMember(key, value);
	}

	/**
	 * 获取set的长度
	 * 
	 * @param key 键
	 * @return 长度
	 */
	public long setSize(String key) {
		return redisTemplate.opsForSet().size(key);
	}

	/**
	 * 根据key删除多个值
	 * 
	 * @param key    键
	 * @param values 多个值
	 * @return 删除的数量
	 */
	public long setDelete(String key, Object... values) {
		return redisTemplate.opsForSet().remove(key, values);
	}

	/**
	 * 根据key获取List的内容
	 * 
	 * @param key   键
	 * @param start 起始位置
	 * @param end   结束位置
	 * @return List
	 */
	public List<Object> listGet(String key, long start, long end) {
		return redisTemplate.opsForList().range(key, start, end);
	}

	/**
	 * 根据索引获取List中的属性
	 * 
	 * @param key   键
	 * @param index 下标
	 * @return Object
	 */
	public Object listGet(String key, long index) {
		return redisTemplate.opsForList().index(key, index);
	}

	/**
	 * 根据Key获取List的长度
	 * 
	 * @param key 键
	 * @return 长度
	 */
	public long listSize(String key) {
		return redisTemplate.opsForList().size(key);
	}

	/**
	 * 根据key向List中添加数据
	 * 
	 * @param key   键
	 * @param value 值
	 * @return 插入位置
	 */
	public long listAdd(String key, Object value) {
		return redisTemplate.opsForList().rightPush(key, value);
	}

	/**
	 * 根据key向List中添加数据
	 * 
	 * @param key   键
	 * @param value 值
	 * @param time  超时时间
	 * @return 插入位置
	 */
	public long listAdd(String key, Object value, long time) {
		Long index = redisTemplate.opsForList().rightPush(key, value);
		timeOut(key, time);
		return index;
	}

	/**
	 * 根据key向List中添加多个数据
	 * 
	 * @param key    键
	 * @param values 多个值
	 * @return 插入数量
	 */
	public long listAddAll(String key, Object... values) {
		return redisTemplate.opsForList().rightPushAll(key, values);
	}

	/**
	 * 根据key向List中添加多个数据,并设置超时时间
	 * 
	 * @param key    键
	 * @param time   超时时间
	 * @param values 多个值
	 * @return 插入数量
	 */
	public long listAddAll(String key, long time, Object... values) {
		Long count = redisTemplate.opsForList().rightPushAll(key, values);
		timeOut(key, time);
		return count;
	}

	/**
	 * 根据key删除值
	 * 
	 * @param key   键
	 * @param count 数量
	 * @param value 值
	 * @return 数量
	 */
	public long listDelete(String key, long count, Object value) {
		return redisTemplate.opsForList().remove(key, count, value);
	}

	/**
	 * 获取锁
	 * 
	 * @param key   键
	 * @param value 值
	 * @return true:获取锁成功/false:获取锁失败
	 */
	public boolean lock(String key, String value) {
		return redisTemplate.opsForValue().setIfAbsent(key, value);
	}

	/**
	 * 获取锁
	 * 
	 * @param key   键
	 * @param value 值
	 * @param time  过期时间
	 * @return true:获取锁成功/false:获取锁失败
	 */
	public boolean lock(String key, String value, long time) {
		return redisTemplate.opsForValue().setIfAbsent(key, value, time, TimeUnit.MILLISECONDS);
	}

	/**
	 * 解锁
	 * 
	 * @param key   键
	 * @param value 值
	 * @return true:解锁成功/false:解锁失败
	 */
	public boolean unlock(String key, String value) {
		Object obj = redisTemplate.opsForValue().get(key);
		// 与短路特性删除Key
		return StringUtil.eqString(value, ObjectUtil.toString(obj)) && delete(key);
	}

}
