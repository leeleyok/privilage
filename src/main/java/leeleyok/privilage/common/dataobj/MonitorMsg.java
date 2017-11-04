package leeleyok.privilage.common.dataobj;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 监控信息，哪个业务的哪个操作者在什么时间对某个监控对象做了什么操作
 * 
 * @author leeley
 *
 */
public class MonitorMsg {

	/**
	 * 操作者
	 */
	private String operator;
	
	/**
	 * 时间
	 */
	private Date time;
	
	/**
	 * 监控点，比如对某个角色授权，给某个用户授予角色，这里的角色和用户即为监控点
	 */
	private String target;
	
	/**
	 * 操作代号
	 */
	private String operate;
	
	/**
	 * 扩展信息
	 */
	private String extendMsg;

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getExtendMsg() {
		return extendMsg;
	}

	public void setExtendMsg(String extendMsg) {
		this.extendMsg = extendMsg;
	}
	
	public String toString() {
		 return ToStringBuilder.reflectionToString(this);
	}
}
