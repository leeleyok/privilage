package leeleyok.privilage.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import leeleyok.privilage.common.constant.AdminActionConstant;
import leeleyok.privilage.common.dataobj.Privilage;
import leeleyok.privilage.registry.DependentRegistry;
import leeleyok.privilage.storage.PrivilageStorage;

/**
 * 授权管理组件，用于给角色授权相关后台操作
 * 
 * @author leeley
 *
 */
public class RolePrivilageHandler extends Handler {
	
    /**
     * 给角色授权
     * 
     * @param role           角色
     * @param accessPoint    访问控制点
     * @param operates       操作代号列表
     * 
     * @return void
     */
	public static void addRolePrivilage(String role, String accessPoint, String operate) {
		
		if (StringUtils.isBlank(role)) {
			throw new IllegalArgumentException("param role is null");
		}
		
		if (StringUtils.isBlank(accessPoint)) {
			throw new IllegalArgumentException("param accessPoint is null");
		}
		
		if (StringUtils.isBlank(operate)) {
			throw new IllegalArgumentException("param operate is null");
		}
		
		checkComponentRegistry();
		
		//保存授权数据
		PrivilageStorage storage = DependentRegistry.getStorage();
		
		storage.addRolePrivilage(role, accessPoint, operate);
		
		//上报监控
		StringBuilder info = new StringBuilder();
		info.append("accessPoint=").append(accessPoint).append(",operate=").append(operate);
		
		DependentRegistry.getReporter().report(genReportMsg(role, AdminActionConstant.ACTION_ROLE_ADD_PRIVILAGE, info.toString()));
	}
	
	
	
	/**
	 * 删除角色在某个访问控制点的所有权限
	 * 
	 * @param role    角色
	 * @param accessPoint   访问控制点
	 */
	public static void deleteRolePrivilage(String role, String accessPoint, String operate) {
		if (StringUtils.isBlank(role)) {
			throw new IllegalArgumentException("param role is null");
		}
		
		if (StringUtils.isBlank(accessPoint)) {
			throw new IllegalArgumentException("param accessPoint is null");
		}
		
        checkComponentRegistry();
		
        //删除授权数据
        PrivilageStorage storage = DependentRegistry.getStorage();
		
		storage.deleteRolePrivilage(role, accessPoint, operate);
		
		//上报监控
		StringBuilder info = new StringBuilder();
		info.append("accessPoint=").append(accessPoint);
		
		DependentRegistry.getReporter().report(genReportMsg(role, AdminActionConstant.ACTION_ROLE_DELETE_PRIVILAGE, info.toString()));
	}
	
	
	/**
	 * 获取角色在某个访问点的权限
	 * 
	 * @param role  角色
	 * @param accessPoint 访问控制点
	 * 
	 * @return 返回操作代号
	 */
	public static List<String> queryRolePrivilage(String role, String accessPoint) {
		
		if (StringUtils.isBlank(role)) {
			throw new IllegalArgumentException("param role is null");
		}
		
		if (StringUtils.isBlank(accessPoint)) {
			throw new IllegalArgumentException("param accessPoint is null");
		}
		
		checkComponentRegistry();
		
		PrivilageStorage storage = DependentRegistry.getStorage();
		
		List<String> list = storage.getRolePrivilage(role, accessPoint);
		
		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList<String>();
		}
		
		return list;
		
	}
	
	/**
	 * 查询角色所有权限
	 * 
	 * @param role
	 * 
	 * @return 角色权限列表
	 */
	public static List<Privilage> queryRolePrivilage(String role) {
		
		if (StringUtils.isBlank(role)) {
			throw new IllegalArgumentException("param role is null");
		}
		
        checkComponentRegistry();
		
        PrivilageStorage storage = DependentRegistry.getStorage();
		
		List<Privilage> list = storage.getRolePrivilage(role);
		
		if (CollectionUtils.isEmpty(list)) {
			return new ArrayList<Privilage>();
		}
		
		return list;
	}
	
}
