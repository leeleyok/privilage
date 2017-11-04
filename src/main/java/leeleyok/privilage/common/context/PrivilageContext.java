package leeleyok.privilage.common.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

/**
 * 权限控制上下文对象，用来在处理线程中传递数据，如：当前操作的用户等
 * 
 * @author leeley
 *
 */
public class PrivilageContext {
	
	private static final ThreadLocal<Map<String, Object>> context = new InheritableThreadLocal<Map<String, Object>>();
	
	public static String getUser() {
		Map<String, Object> contextDatas = context.get();
		
		if (null == contextDatas) {
			return null;
		}
		
		String user = (String) contextDatas.get(KEY_USER);
		
		return user;
	}
	
	public static void setUser(String user) {
		if (StringUtils.isBlank(user)) {
			return;
		}
		
		Map<String, Object> contextDatas = context.get();
		
		if (null != contextDatas) {
			contextDatas.put(KEY_USER, user);
			return;
		} else {
		    contextDatas = new HashMap<String, Object>();
		    contextDatas.put(KEY_USER, user);
		    context.set(contextDatas);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<String> getUserDataRanges() {
		Map<String, Object> contextDatas = context.get();
		
		if (null == contextDatas) {
			return null;
		}
		
		List<String> userDataRanges = (List<String>) contextDatas.get(KEY_USER_DATARANGES);
		
		return userDataRanges;
	}
	
	public static void setUserDataRanges(List<String> userDataRanges) {
		if (CollectionUtils.isEmpty(userDataRanges)) {
			return;
		}
		
        Map<String, Object> contextDatas = context.get();
		
		if (null != contextDatas) {
			contextDatas.put(KEY_USER_DATARANGES, userDataRanges);
			return;
		} else {
		    contextDatas = new HashMap<String, Object>();
		    contextDatas.put(KEY_USER_DATARANGES, userDataRanges);
		    context.set(contextDatas);
		}
	}
	
	private static final String KEY_USER = "user";
	private static final String KEY_USER_DATARANGES = "user_data_ranges";
	
}
