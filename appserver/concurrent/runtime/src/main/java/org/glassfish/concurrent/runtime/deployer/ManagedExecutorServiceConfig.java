/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2010-2013 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package org.glassfish.concurrent.runtime.deployer;

import org.glassfish.concurrent.config.ManagedExecutorService;
import org.glassfish.concurrent.config.PoolInfo;

import java.io.Serializable;

/**
 * Contains configuration information for a ManagedExecutorService object
 */
public class ManagedExecutorServiceConfig extends BaseConfig  {

    private String contextInfo;
    private int hungAfterSeconds;
    private boolean longRunningTasks;
    private int threadPriority;
    private int corePoolSize;
    private int keepAliveSeconds;
    private int maximumPoolSize;
    private int taskQueueCapacity;
    private int threadLifeTimeSeconds;

    public ManagedExecutorServiceConfig(ManagedExecutorService config) {
        super(config.getJndiName());
        contextInfo = config.getContextInfo();
        hungAfterSeconds = parseInt(config.getHungAfterSeconds(), 0);
        longRunningTasks = Boolean.valueOf(config.getLongRunningTasks());
        threadPriority = parseInt(config.getThreadPriority(), Thread.NORM_PRIORITY);
        PoolInfo poolInfo = config.getPoolInfo();
        if (poolInfo != null) {
            corePoolSize = parseInt(poolInfo.getCoreSize(), 0);
            keepAliveSeconds = parseInt(poolInfo.getKeepAliveSeconds(), Integer.MAX_VALUE);
            maximumPoolSize = parseInt(poolInfo.getMaximumSize(), Integer.MAX_VALUE);
            taskQueueCapacity = parseInt(poolInfo.getTaskQueueCapacity(), Integer.MAX_VALUE);
            threadLifeTimeSeconds = parseInt(poolInfo.getThreadLifetimeSeconds(), 0);
        }

    }

    public String getContextInfo() {
        return contextInfo;
    }

    public int getHungAfterSeconds() {
        return hungAfterSeconds;
    }

    public boolean isLongRunningTasks() {
        return longRunningTasks;
    }

    public int getThreadPriority() {
        return threadPriority;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public int getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public int getTaskQueueCapacity() {
        return taskQueueCapacity;
    }

    public int getThreadLifeTimeSeconds() {
        return threadLifeTimeSeconds;
    }

    public int parseInt(String strValue, int defaultValue) {
        if (strValue != null) {
            try {
                int intValue = Integer.parseInt(strValue);
                return intValue;
            } catch (NumberFormatException e) {
                // ignore, just return default in this case
            }
        }
        return defaultValue;
    }

    @Override
    TYPE getType() {
        return TYPE.MANAGED_EXECUTOR_SERVICE;
    }
}