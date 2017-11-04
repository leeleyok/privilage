package leeleyok.privilage.common.dataobj;

import org.apache.commons.lang.builder.ToStringBuilder;

public class UserRole {
	/**
	 * 用户唯一标识
	 */
	private String userIdentifier;
	
	/**
	 * 受控数据域
	 */
	private String dataRange;
	
	/**
	 * 角色
	 */
    private String role;

	public String getUserIdentifier() {
		return userIdentifier;
	}

	public void setUserIdentifier(String userIdentifier) {
		this.userIdentifier = userIdentifier;
	}

	public String getDataRange() {
		return dataRange;
	}

	public void setDataRange(String dataRange) {
		this.dataRange = dataRange;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	public String toString() {
		 return ToStringBuilder.reflectionToString(this);
	}

}
