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

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.event.test.CapturingEventListener;
import org.nuxeo.ecm.core.test.DefaultRepositoryInit;
import org.nuxeo.ecm.core.test.annotations.Granularity;
import org.nuxeo.ecm.core.test.annotations.RepositoryConfig;
import org.nuxeo.ecm.platform.test.PlatformFeature;
import org.nuxeo.runtime.test.runner.Features;
import org.nuxeo.runtime.test.runner.FeaturesRunner;

import javax.inject.Inject;

@RunWith(FeaturesRunner.class)
@Features({PlatformFeature.class})
@RepositoryConfig(init = DefaultRepositoryInit.class, cleanup = Granularity.METHOD)
public class TestSampleFireEvent {

    public static final String CUSTOM_EVENT = "My Custom Event";

    @Inject
    protected CoreSession session;

    @Test
    public void fireGenericEvent() {
        CapturingEventListener listener = new CapturingEventListener(CUSTOM_EVENT);
        SampleFireEvent.fireGenericEvent(session,CUSTOM_EVENT);
        Assert.assertEquals(1,listener.getCapturedEventCount(CUSTOM_EVENT));
        listener.close();
    }

    @Test
    public void fireDocumentEvent() {
        CapturingEventListener listener = new CapturingEventListener(CUSTOM_EVENT);

        DocumentModel doc = session.createDocumentModel(session.getRootDocument().getPathAsString(),"theDoc","File");
        doc = session.createDocument(doc);
        SampleFireEvent.fireDocumentEvent(doc,CUSTOM_EVENT);

        Assert.assertEquals(1,listener.getCapturedEventCount(CUSTOM_EVENT));
        listener.close();
    }

}
