/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2009-2012 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.enterprise.configapi.tests;

import org.jvnet.hk2.config.Dom;
import org.jvnet.hk2.component.BaseServiceLocator;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Set;
import java.util.logging.Logger;

import com.sun.enterprise.config.serverbeans.Domain;

/**
 * Traverse a config tree using the hk2 model APIs.
 *
 * @author Jerome Dochez
 */
public class TraversalTest extends ConfigApiTest {

    static Logger logger = Logger.getAnonymousLogger();

    public String getFileName() {
        return "DomainTest";
    }

    @Test
    public void traverse() {
        BaseServiceLocator habitat = super.getHabitat();
        Domain domain = Domain.class.cast(habitat.getComponent(Domain.class.getName(), ""));
        introspect(0, Dom.unwrap(domain));
    }


    @Ignore
    private void introspect(int indent, Dom proxy) {
        indent = indent + 1;
        Set<String> ss = proxy.getAttributeNames();
        String id = "";
        for (int i = 0; i < indent; i++) {
            id = id + "    ";
        }
        logger.fine(id + "--------" + proxy.model.key);
        for (String a : ss) {

            logger.fine(id + a + "=" + proxy.attribute(a));
        }


        Set<String> elem = proxy.getElementNames();

        for (String bb : elem) {


            logger.fine(id + "<" + bb + ">");
            org.jvnet.hk2.config.ConfigModel.Property prop = proxy.model.getElement(bb);
            if (prop != null && proxy.model.getElement(bb).isLeaf()) {
                logger.fine(proxy.leafElement(bb));
            } else {
                introspect(indent, proxy.element(bb));
            }
            logger.fine(id + "</" + bb + ">");
            logger.fine("    ");

        }
    }
}
