/*
 * Copyright (C) 2011 Everit Kft. (http://www.everit.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.everit.email.javamail.ecm.internal;

import java.io.IOException;
import java.io.StringReader;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Session;

import org.everit.email.javamail.ecm.JavamailSessionComponentConstants;
import org.everit.osgi.ecm.annotation.Activate;
import org.everit.osgi.ecm.annotation.Component;
import org.everit.osgi.ecm.annotation.ConfigurationPolicy;
import org.everit.osgi.ecm.annotation.Deactivate;
import org.everit.osgi.ecm.annotation.ManualService;
import org.everit.osgi.ecm.annotation.ManualServices;
import org.everit.osgi.ecm.annotation.ServiceRef;
import org.everit.osgi.ecm.annotation.attribute.StringAttribute;
import org.everit.osgi.ecm.component.ComponentContext;
import org.everit.osgi.ecm.component.ConfigurationException;
import org.osgi.framework.ServiceRegistration;

/**
 * ECM Component that registers a {@link Session} as an OSGi service based on the configuration of
 * the component.
 */
@Component(componentId = JavamailSessionComponentConstants.SERVICE_PID,
    configurationPolicy = ConfigurationPolicy.FACTORY,
    label = "Everit JavaMail Email Session",
    description = "Component that registers a configured JSR 919 Session")
@ManualServices(@ManualService({ Session.class }))
public final class JavamailSessionComponent {

  private Authenticator authenticator;

  private String properties;

  private ServiceRegistration<Session> serviceRegistration;

  /**
   * Parses the configuration and registers the Session as an OSGi service.
   *
   * @param componentContext
   *          The context of the component.
   */
  @Activate
  public void activate(final ComponentContext<JavamailSessionComponent> componentContext) {
    Properties props = new Properties();
    StringReader stringReader = new StringReader(properties);
    try {
      props.load(stringReader);
    } catch (IOException e) {
      throw new ConfigurationException("Could not load Session properties", e);
    }

    Session session;
    if (authenticator == null) {
      session = Session.getInstance(props);
    } else {
      session = Session.getInstance(props, authenticator);
    }

    Dictionary<String, ?> serviceProps = new Hashtable<>(componentContext.getProperties());
    serviceRegistration = componentContext.registerService(Session.class, session, serviceProps);
  }

  @Deactivate
  public void deactivate() {
    serviceRegistration.unregister();
  }

  @ServiceRef(referenceId = JavamailSessionComponentConstants.References.SERVICE_REF_AUTHENTICATOR,
      optional = true, label = "Authenticator",
      description = "Authenticator that is used to authenticate to the email server (SMTP).")
  public void setAuthenticator(final Authenticator authenticator) {
    this.authenticator = authenticator;
  }

  @StringAttribute(attributeId = JavamailSessionComponentConstants.ATTR_PROPERTIES,
      optional = false, label = "Properties",
      description = "Configuration of the Session in properties file format.")
  public void setProperties(final String properties) {
    this.properties = properties;
  }
}
