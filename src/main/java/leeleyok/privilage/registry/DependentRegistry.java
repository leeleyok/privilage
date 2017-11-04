package leeleyok.privilage.registry;

import leeleyok.privilage.reporter.MonitorReporter;
import leeleyok.privilage.storage.PrivilageStorage;

/**
 * 需要业务系统自己实现的组件注册管理，将业务实现和权限控制解耦
 * 
 * @author leeley
 *
 */
public class DependentRegistry {
	/**
	 * 角色授权存储组件
	 */
	private static PrivilageStorage storage;
	
	
	/**
	 * 监控数据上报组件
	 */
	private static MonitorReporter reporter;
	
	public static void registStorage(PrivilageStorage bizStorage) {
		if (null == bizStorage) {
			throw new RuntimeException("must regist a PrivilageStorage");
		}
		
		storage = bizStorage;
	}
	
	public static void registReporter(MonitorReporter bizReporter) {
		if (null == bizReporter) {
			throw new RuntimeException("must regist a MonitorReporter");
		}
		
		reporter = bizReporter;
	}


	public static PrivilageStorage getStorage() {
		return storage;
	}

	public static MonitorReporter getReporter() {
	    return reporter;
	}
}
