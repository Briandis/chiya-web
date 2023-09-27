package chiya.web.redis;

import org.springframework.beans.factory.annotation.Autowired;

import chiya.core.base.thread.ThreadUtil;

/**
 * redis分布式锁
 * 
 * @author chiya
 *
 */
public class RedisLock {

	@Autowired
	BaseRedisService redisService;

	/**
	 * 获取锁
	 * 
	 * @param redisKey   reids锁对象
	 * @param retry      重试次数
	 * @param sleep      睡眠时间
	 * @param unlockTime 锁多久后自动释放
	 */
	public void lock(String redisKey, int retry, int sleep, int unlockTime) {
		String longId = String.valueOf(Thread.currentThread().getId());
		int index = 0;
		while (index < retry) {
			if (redisService.lock(redisKey, longId, unlockTime)) { return; }
			ThreadUtil.sleep(sleep);
		}
		throw new RuntimeException("redis lock timeout");
	}

	/**
	 * 获取锁，重试延迟300ms，获取锁后最多持有3000ms
	 * 
	 * @param redisKey 获取的key
	 * @param retry    重试次数
	 */
	public void lock(String redisKey, int retry) {
		lock(redisKey, retry, 300, 3000);
	}

	/**
	 * 获取锁，尝试获取10次,重试延迟300ms，获取锁后最多持有3000ms
	 * 
	 * @param redisKey redsi的key
	 */
	public void lock(String redisKey) {
		lock(redisKey, 10);
	}

	/**
	 * 解锁
	 * 
	 * @param riedsKey redis的锁
	 */
	public void unlock(String riedsKey) {
		String longId = String.valueOf(Thread.currentThread().getId());
		redisService.unlock(riedsKey, longId);
	}

}
