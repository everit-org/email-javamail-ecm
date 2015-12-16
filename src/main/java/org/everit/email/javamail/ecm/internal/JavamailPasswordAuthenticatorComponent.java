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

import java.util.Dictionary;
import java.util.Hashtable;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

import org.everit.email.javamail.ecm.JavamailPasswordAuthenticatorComponentConstants;
import org.everit.osgi.ecm.annotation.Activate;
import org.everit.osgi.ecm.annotation.Component;
import org.everit.osgi.ecm.annotation.ConfigurationPolicy;
import org.everit.osgi.ecm.annotation.Deactivate;
import org.everit.osgi.ecm.annotation.ManualService;
import org.everit.osgi.ecm.annotation.Update;
import org.everit.osgi.ecm.annotation.attribute.PasswordAttribute;
import org.everit.osgi.ecm.annotation.attribute.StringAttribute;
import org.everit.osgi.ecm.component.ComponentContext;
import org.everit.osgi.ecm.extender.ECMExtenderConstants;
import org.osgi.framework.ServiceRegistration;

import aQute.bnd.annotation.headers.ProvideCapability;

/**
 * Java Mail Password Authenticator Component.
 */
@Component(componentId = JavamailPasswordAuthenticatorComponentConstants.SERVICE_PID,
    configurationPolicy = ConfigurationPolicy.FACTORY,
    label = "Everit JavaMail Password Authenticator",
    description = "Registers an Authenticator as OSGi service that uses the configured username"
        + "-password pair for authentication.")
@ProvideCapability(ns = ECMExtenderConstants.CAPABILITY_NS_COMPONENT,
    value = ECMExtenderConstants.CAPABILITY_ATTR_CLASS + "" + "=${@class}")
@ManualService({ Authenticator.class })
public class JavamailPasswordAuthenticatorComponent {

  private String password;

  private PasswordAuthenticator passwordAuthenticator;

  private ServiceRegistration<Authenticator> serviceRegistration;

  private String userName;

  /**
   * Creates the password authenticator and registers it as an OSGi service.
   *
   * @param componentContext
   *          The context of the component.
   */
  @Activate
  public void activate(
      final ComponentContext<JavamailPasswordAuthenticatorComponent> componentContext) {

    passwordAuthenticator =
        new PasswordAuthenticator(new PasswordAuthentication(userName, password));

    Dictionary<String, ?> properties = new Hashtable<>(componentContext.getProperties());

    serviceRegistration =
        componentContext.registerService(Authenticator.class, passwordAuthenticator, properties);
  }

  @Deactivate
  public void deactivate() {
    serviceRegistration.unregister();
  }

  @PasswordAttribute(attributeId = JavamailPasswordAuthenticatorComponentConstants.ATTR_PASSWORD,
      optional = true, dynamic = true, label = "Password",
      description = "The password that is used during the authentication. "
          + "The attribute can be dinamically updated without restarting the component.",
      priority = 1)
  public void setPassword(final String password) {
    this.password = password;
  }

  @StringAttribute(attributeId = JavamailPasswordAuthenticatorComponentConstants.ATTR_USER_NAME,
      optional = true, dynamic = true, label = "User name",
      description = "The user name that is used during the authentication. "
          + "The attribute can be dinamically updated without restarting the component.",
      priority = 0)
  public void setUserName(final String userName) {
    this.userName = userName;
  }

  @Update
  public void update() {
    passwordAuthenticator.setPasswordauthentication(new PasswordAuthentication(userName, password));
  }
}
