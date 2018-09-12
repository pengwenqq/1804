package com.jt.web.thread;

import com.jt.web.pojo.User;

public class UserThreadLocal {
	/**
	 * 关于参数的说明 如果需要传递多值,则使用Map集合封装
	 */
	private static ThreadLocal<User> userThread = new ThreadLocal<>();

	public static void set(User user) {
		userThread.set(user);
	}

	static public User get() {
		return userThread.get();
	}

	static public void remove() {
		// 防止内存泄露
		userThread.remove();
	}

}
