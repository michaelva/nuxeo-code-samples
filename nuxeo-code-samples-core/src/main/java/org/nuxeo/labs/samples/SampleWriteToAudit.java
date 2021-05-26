/*
 * (C) Copyright 2021 Nuxeo (http://nuxeo.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Michael Vachette
 */

package org.nuxeo.labs.samples;

import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.platform.audit.api.AuditLogger;
import org.nuxeo.ecm.platform.audit.api.ExtendedInfo;
import org.nuxeo.ecm.platform.audit.api.LogEntry;
import org.nuxeo.runtime.api.Framework;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SampleWriteToAudit {

    static public void recordAuditEntry(DocumentModel doc, String eventName) {
        AuditLogger logger = Framework.getService(AuditLogger.class);

        LogEntry entry = logger.newLogEntry();
        entry.setEventId(eventName);
        entry.setEventDate(Calendar.getInstance().getTime());
        entry.setCategory("myCategory");

        //Document info if the entry is related to a document
        if (doc!=null) {
            entry.setDocUUID(doc.getId());
            entry.setDocPath(doc.getPathAsString());
            entry.setDocType(doc.getType());
            entry.setRepositoryId(doc.getRepositoryName());
            entry.setDocLifeCycle(doc.getCurrentLifeCycleState());
        }

        //Add the user
        entry.setPrincipalName("SampleUser");

        //Add extended Info
        Map<String, ExtendedInfo> extendedInfoMap = new HashMap<>();
        extendedInfoMap.put("example", logger.newExtendedInfo("the value"));
        entry.setExtendedInfos(extendedInfoMap);

        //Record
        logger.addLogEntries(Collections.singletonList(entry));
    }

}
