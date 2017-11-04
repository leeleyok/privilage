package leeleyok.privilage;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import leeleyok.privilage.common.dataobj.Privilage;
import leeleyok.privilage.common.dataobj.UserRole;
import leeleyok.privilage.handler.RolePrivilageHandler;
import leeleyok.privilage.handler.UserRoleHandler;
import leeleyok.privilage.registry.DependentRegistry;
import leeleyok.privilage.reporter.MonitorReporter;
import leeleyok.privilage.storage.PrivilageStorage;

/**
 * 权限管理后台Manager, 用于：
 * 
 * 1. 用户和授权数据域绑定
 * 2. 角色绑定访问控制点及操作权限
 * 3. 用户绑定角色
 * 
 * @author leeley
 *
 */
public class AdminPrivilageManager {
	
	
    public AdminPrivilageManager(PrivilageStorage storage, MonitorReporter reporter) {
    	
    	if (null == storage) {
    		throw new RuntimeException("PrivilageStorage is null, must regist a PrivilageStorage");
    	}
    	
    	if (null == reporter) {
    		throw new RuntimeException("MonitorReporter is null, must regist a MonitorReporter");
    	}
    	
    	if (null == DependentRegistry.getStorage()) {
    	    DependentRegistry.registStorage(storage);
    	}
    	
    	if (null == DependentRegistry.getReporter()) {
    	    DependentRegistry.registReporter(reporter);
    	}
    }
    
    //角色授权相关
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
		
		List<String> operateList = RolePrivilageHandler.queryRolePrivilage(role, accessPoint);
		
		if (CollectionUtils.isNotEmpty(operateList)) {
			for (String existOpt : operateList) {
				if (StringUtils.equals(operate, existOpt)) {
					return;
				}
			}
		}
		
		RolePrivilageHandler.addRolePrivilage(role, accessPoint, operate);
	}
	
	
	/**
	 * 删除角色权限
	 * 
	 * @param role    角色
	 * @param accessPoint   访问控制点
	 */
	public static void deleteRolePrivilage(String role, String accessPoint, String operate) {
		
        List<String> operateList = RolePrivilageHandler.queryRolePrivilage(role, accessPoint);
		
		if (CollectionUtils.isNotEmpty(operateList)) {
			for (String existOpt : operateList) {
				if (StringUtils.equals(operate, existOpt)) {
					
					RolePrivilageHandler.deleteRolePrivilage(role, accessPoint, operate);
					
					return;
				}
			}
		}
		
		
	}
	
	/**
	 * 查询角色所有权限
	 * 
	 * @param role
	 * 
	 * @return 角色权限列表
	 */
	public static List<Privilage> queryRolePrivilages(String role) {
		return RolePrivilageHandler.queryRolePrivilage(role);
	}
	
	
	/**
	 * 获取角色在某个访问点的所有权限
	 * 
	 * @param role  角色
	 * @param accessPoint 访问控制点
	 * 
	 * @return 返回操作代号
	 */
	public List<String> queryRolePrivilage(String role, String accessPoint) {
		return RolePrivilageHandler.queryRolePrivilage(role, accessPoint);
	}
	
	
	//用户授权相关
	/**
	 * 在某个受控数据域内给用户赋予某个角色
	 * 
	 * @param userIdentifier   用户唯一标识
	 * @param role    角色
	 */
	public void addUserRole(String userIdentifier, String dataRange, String role) {
		//检查是否已经授予角色
		List<UserRole> userRoleList = UserRoleHandler.queryUserDataRangeRoles(userIdentifier, dataRange);
		
		if (CollectionUtils.isNotEmpty(userRoleList)) {
			for (UserRole userRole : userRoleList) {
				if (StringUtils.equals(role, userRole.getRole())) {
					
					return;
				}
			}
		}
		
		//授予角色
		UserRoleHandler.addUserRole(userIdentifier, dataRange, role);
	}
	
	/**
	 * 删除用户角色
	 * 
	 * @param userIdentifier    用户唯一标识
	 * @param role   角色
	 */
	public void deleteUserRole(String userIdentifier, String dataRange, String role) {
        List<UserRole> userRoleList = UserRoleHandler.queryUserDataRangeRoles(userIdentifier, dataRange);
		
		if (CollectionUtils.isNotEmpty(userRoleList)) {
			for (UserRole userRole : userRoleList) {
				if (StringUtils.equals(role, userRole.getRole())) {    //检查是否具有当前角色，有则删除，无则不处理
					
					UserRoleHandler.deleteUserRole(userIdentifier, dataRange, role);
					
					return;
				}
			}
		}
		
		
	}
	
	
	/**
	 * 查询用户在某个受控数据域内所有角色
	 * 
	 * @param userIdentifier   用户唯一标识
	 * 
	 * @return   角色列表
	 */
	public List<UserRole> queryUserRoles(String userIdentifier, String dataRange) {
		return UserRoleHandler.queryUserDataRangeRoles(userIdentifier, dataRange);
	}
	
	/**
	 * 查询某个受控数据域所属所有用户及其角色
	 * 
	 * @param dataRange
	 * @return
	 */
	public List<UserRole> queryDataRangeUsers(String dataRange) {
		return UserRoleHandler.queryDataRangeUsers(dataRange);
	}
	
}
