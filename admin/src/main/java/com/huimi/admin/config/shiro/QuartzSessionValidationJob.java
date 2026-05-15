package com.huimi.admin.config.shiro;

import org.apache.shiro.session.mgt.ValidatingSessionManager;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Quartz 2.x 兼容的 Session 验证 Job
 */
public class QuartzSessionValidationJob implements Job {
    private static final Logger log = LoggerFactory.getLogger(QuartzSessionValidationJob.class);
    static final String SESSION_MANAGER_KEY = "sessionManager";

    public QuartzSessionValidationJob() {
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        ValidatingSessionManager sessionManager = (ValidatingSessionManager) dataMap.get(SESSION_MANAGER_KEY);

        if (sessionManager == null) {
            if (log.isErrorEnabled()) {
                log.error("ValidatingSessionManager not found in JobDataMap. Session validation cannot occur.");
            }
            return;
        }

        if (log.isDebugEnabled()) {
            log.debug("Executing Quartz session validation job...");
        }

        try {
            sessionManager.validateSessions();
            if (log.isDebugEnabled()) {
                log.debug("Quartz session validation job completed successfully.");
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Error while validating sessions.", e);
            }
        }
    }
}