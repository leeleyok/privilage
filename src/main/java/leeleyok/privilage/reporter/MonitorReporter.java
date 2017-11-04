package leeleyok.privilage.reporter;

import leeleyok.privilage.common.dataobj.MonitorMsg;

/**
 * 监控上报接口
 * 
 * @author leeley
 *
 */
public interface MonitorReporter {
    /**
     * 上报一条监控信息
     * 
     * @param message
     */
	void report(MonitorMsg monitorMsg);
}
